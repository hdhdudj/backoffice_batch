package io.spring.main.apis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.*;
import io.spring.main.model.goods.GoodsInsertData;
import io.spring.main.model.goods.entity.*;
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

import javax.swing.text.html.Option;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
    private final ObjectMapper objectMapper;
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

        for(Tmmapi tmmapi : tmmapiList){
            // tmmapi에 해당하는 tmitem 리스트 가져오기
//            List<Tmitem> tmitemList = jpaTmitemRepository.findBy
            // tmmapi, tmitem에서 해당 상품정보 불러서 전달용 객체로 만들기
            goodsInsertData = makeGoodsSearchObject(tmmapi);
            // 객체를 고도몰 api 모양으로 만들기
            String xmlUrl = makeGoodsSearchXml(goodsInsertData, tmmapi.getAssortId());
            // api 전송
            String goodsNoifSuccess = sendXmlToGodo(xmlUrl);
            if(goodsNoifSuccess != null){
                // tmmapi의 joinStatus를 02로 바꾸기
                tmmapi.setJoinStatus(StringFactory.getGbTwo());
                // tmmapi의 channelGoodsNo를
//                jpaTmmapiRepository.save(tmmapi);
            }
            // tmitem의 channelGoodsNo와 channelOptionsNo 값 goods_search api로 받아오기
        }
    }

    private String sendXmlToGodo(String xmlUrl) {
        BufferedReader br = null;
        String goodsNoIfSuccess = null;
        String urlstr = goodsInsertUrl
                + StringFactory.getStrQuestion()
                + StringFactory.getGoodsSearchParams()[0] //"partner_key"
                + StringFactory.getStrEqual()
                + pKey
                + StringFactory.getStrAnd()
                + StringFactory.getGoodsSearchParams()[1] //"key"
                + StringFactory.getStrEqual()
                + key
                + StringFactory.getStrAnd()
                + StringFactory.getStrDataUrl() //"data_url"
                + StringFactory.getStrEqual()
                + xmlUrl;
        try{
            URL url = new URL(urlstr);
//            System.out.println("url : " + urlstr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            System.out.println("con properties : " + con.getRequestProperties());

            // 응답 읽기
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), StringFactory.getStrUtf8()));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }
            System.out.println("            " + result);
            goodsNoIfSuccess = parseReturnXml(result);
        }
        catch (Exception e){
            log.debug(e.getMessage());
        }
        return goodsNoIfSuccess;
    }

    // xml을 고도몰 goods_insert에 보내고 돌아오는 응답 xml을 parse해서 성공했으면 고도몰의 goodsNo, 실패했으면 null을 리턴
    private String parseReturnXml(String result) {
        BufferedReader br = null;
        // DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        // xml 파싱하기
        try{
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expr = xPath.compile("//header");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            String headerCode = null;

            // <header/> 아래에 있는 <code/>의 값을 가져옴. 000 : 성공, 그 외 값은 실패.
            headerCode = (String)GoodsSearch.getNodeValue(nodeList.item(0).getFirstChild());
            if(headerCode.equals(StringFactory.getStrSuccessCode())){
                // <data/> 아래에 있는 <goodsno/>의 값을 가져옴.
                expr = xPath.compile("//goodsno");
                Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
                String successGoodsNo = (String)GoodsSearch.getNodeValue(node);
                System.out.println("+++++ " + successGoodsNo);
                return successGoodsNo;
            }
        }
        catch(Exception e){
            log.debug(e.getMessage());
        }
        return null;
    }

    // , ititmm : tmitem 고도몰 goods_insert api를 만들기 위한 GoodsInsertData 객체를 만드는 함수
    private GoodsInsertData makeGoodsSearchObject(Tmmapi tmmapi) {
        GoodsInsertData goodsInsertData = new GoodsInsertData(makeGoodsDataFromTmmapi(tmmapi), makeOptionDataFromTmitem(tmmapi));

        return goodsInsertData;
    }

    // itasrt, itasrd : tmmapi -> GoodsInsertData.GoodsData 만드는 함수
    private GoodsInsertData.GoodsData makeGoodsDataFromTmmapi(Tmmapi tmmapi){
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
        // itasrt에서 optionUseYn이 02인 애는 null로 해버리기
        itasrt.setOptionUseYn(itasrt.getOptionUseYn() != null && itasrt.getOptionUseYn().equals(StringFactory.getGbTwo())? null : itasrt.getOptionUseYn());

        return new GoodsInsertData.GoodsData(itasrt, itasrd1, itasrd2);
    }

    // ititmm : tmitem -> GoodsInsertData.OptionData 만드는 함수
    private List<GoodsInsertData.OptionData> makeOptionDataFromTmitem(Tmmapi tmmapi){
        List<Tmitem> tmitemList = tmmapi.getTmitemList();
        List<GoodsInsertData.OptionData> optionDataList = new ArrayList<>();
        for(Tmitem tmitem : tmitemList){
            GoodsInsertData.OptionData optionData = new GoodsInsertData.OptionData(tmitem);
            optionDataList.add(optionData);
        }
        return optionDataList;
    }
    
    // goodsInsertData를 xml로 만드는 함수
    private String makeGoodsSearchXml(GoodsInsertData goodsInsertData, String assortId){
        String xmlContent = null;
//        goodsInsertData.getGoodsData()[0].setAssortId(null); // xml에 assortId를 포함시키지 않기 위해 null로 설정

        try {
            // Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(GoodsInsertData.class);

            // Create Marshaller
            Marshaller marshaller = jaxbContext.createMarshaller();

            // Print XML String to Console
            StringWriter stringWriter = new StringWriter();

            // Write XML to StringWriter
            marshaller.marshal(goodsInsertData, stringWriter);

            // Verify XML Content
            xmlContent = stringWriter.toString();
            System.out.println(xmlContent);

        } catch (Exception e) {
            e.getMessage();
        }

        return getXmlUrl(assortId, xmlContent);
    }

    // xml string을 db에 저장하고 주소 반환
    private String getXmlUrl(String assortId, String xmlContent){
        // 만든 xml DB에 저장하기
        XmlTest xmlTest = new XmlTest(assortId, xmlContent);
        jpaXmlTestRepository.save(xmlTest);

        return xmlSaveUrl
                + StringFactory.getStrQuestion()
                + StringFactory.getStrAssortId()
                + StringFactory.getStrEqual()
                + assortId;
    }
}
