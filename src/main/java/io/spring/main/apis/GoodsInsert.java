package io.spring.main.apis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.*;
import io.spring.main.model.goods.GoodsInsertData;
import io.spring.main.model.goods.entity.Itasrd;
import io.spring.main.model.goods.entity.Itasrt;
import io.spring.main.model.goods.entity.Tmmapi;
import io.spring.main.model.goods.entity.XmlTest;
import io.swagger.models.Xml;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:godourl.yml")
public class GoodsInsert {
    private final JpaIfGoodsMasterRepository jpaIfGoodsMasterRepository;
    private final JpaIfGoodsOptionRepository jpaIfGoodsOptionRepository;
    private final JpaIfGoodsTextOptionRepository jpaIfGoodsTextOptionRepository;
    private final JpaIfGoodsAddGoodsRepository jpaIfGoodsAddGoodsRepository;
    private final JpaSequenceDataRepository jpaSequenceDataRepository;
    private final JpaItasrtRepository jpaItasrtRepository;
    private final JpaItasrnRepository jpaItasrnRepository;
    private final JpaItasrdRepository jpaItasrdRepository;
    private final JpaItvariRepository jpaItvariRepository;
    private final JpaItitmmRepository jpaItitmmRepository;
    private final JpaItmmotRepository jpaItmmotRepository;
    private final JpaItlkagRepository jpaItlkagRepository;
    private final JpaItadgsRepository jpaItadgsRepository;
    private final JpaIfBrandRepository jpaIfBrandRepository;
    private final JpaIfCategoryRepository jpaIfCategoryRepository;
    private final JpaTmmapiRepository jpaTmmapiRepository;
    private final JpaTmitemRepository jpaTmitemRepository;
    private final JpaXmlTestRepository jpaXmlTestRepository;
//    private final ObjectMapper objectMapper;
//    private MappingJackson2XmlHttpMessageConverter xmlConverter;
//    private final XmlMapper xmlMapper = new XmlMapper();

    // 고도몰 관련 값들
    @Value("${pKey}")
    private String pKey;
    @Value("${key}")
    private String key;
    @Value("${url.goodsInsert}")
    private String goodsInsertUrl;
    // xml 저장 주소
    @Value("${url.xmlUrl}")
    private String xmlSaveUrl;

    // tmmapi, tmitem을 뒤져서 jobStatus가 01인 애들을 훑어서 고도몰에 보낸 후 joinStatus를 02로 바꿈.
    public void insertGoods() {
        // tmmapi에서 joinStatus가 01인 애들 찾아오기 (tmitem도 엮여서 옴)
        List<Tmmapi> tmmapiList = jpaTmmapiRepository.findByJoinStatus(StringFactory.getGbOne()); // 02

        GoodsInsertData goodsInsertData = null;

        // tmmapi에서 해당 상품정보 불러서 고도몰 api 모양으로 만들기
        for(Tmmapi tmmapi : tmmapiList){
            goodsInsertData = makeGoodsSearchObject(tmmapi);
            // tmitem에서 해당 상품정보 불러서 고도몰 api 모양으로 만들기
            String xmlUrl = makeGoodsSearchXml(goodsInsertData, tmmapi.getAssortId());
            // api 전송
            sendXmlToGodo(xmlUrl);
        }
    }

    private void sendXmlToGodo(String xmlUrl) {
        BufferedReader br = null;
        String urlstr = goodsInsertUrl + "?partner_key=" + pKey + "&key=" + key + "&data_url=" + xmlUrl;
        try{
            URL url = new URL(urlstr);
            System.out.println("url : " + urlstr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            System.out.println("con properties : " + con.getRequestProperties());

            // 응답 읽기
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }
            System.out.println("+++++ " + result);
        }
        catch (Exception e){
            log.debug(e.getMessage());
        }
    }

    // itasrt, itasrd에서 불러옴
    private GoodsInsertData makeGoodsSearchObject(Tmmapi tmmapi) {
        Itasrt itasrt = jpaItasrtRepository.getOne(tmmapi.getAssortId());
        List<Itasrd> itasrdList = jpaItasrdRepository.findByAssortId(tmmapi.getAssortId());
        Itasrd itasrd1 = null;
        Itasrd itasrd2 = null;
        if(itasrdList.size() == 2){
            for(Itasrd itasrd : itasrdList){
                if(itasrd.getOrdDetCd().equals(StringFactory.getGbOne())){ // 01 : 상세 설명
                    itasrd2 = itasrd;
                }
                else{ // 02 : 짧은 설명
                    itasrd1 = itasrd;
                }
            }
        }
        return new GoodsInsertData(new GoodsInsertData.GoodsData(itasrt, itasrd1, itasrd2));
    }
    // goodsInsertData를 xml로 만들고 db에 저장 후 해당 xml을 가져올 수 있는 주소를 반환
    private String makeGoodsSearchXml(GoodsInsertData goodsInsertData, String assortId){
        String xmlString = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        // xml 만들기
        try{
            XmlMapper xmlMapper = new XmlMapper();

            Map<String, GoodsInsertData.GoodsData> map = new HashMap<>();
            map.put("data",goodsInsertData.getGoodsData());
            xmlString = xmlMapper.writeValueAsString(map);
            System.out.println("xmlString : +++++ " + xmlString);
            // key값 교체
            InputSource is = new InputSource(new StringReader(xmlString));

            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expr = xPath.compile("//HashMap/data");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = (Node) expr.evaluate(nodeList.item(i), XPathConstants.NODE);
                System.out.println("====== "+node.getNodeValue());
            }
        }
        catch (Exception e){
            log.debug(e.getMessage());
        }
        
        // 만든 xml DB에 저장하기
        XmlTest xmlTest = new XmlTest(assortId, xmlString);
        jpaXmlTestRepository.save(xmlTest);

        return xmlSaveUrl + "?assortId=" + assortId;
    }
}
