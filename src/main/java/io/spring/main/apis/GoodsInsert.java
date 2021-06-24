package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.DataShareBean;
import io.spring.main.jparepos.category.JpaIfCategoryRepository;
import io.spring.main.util.StringFactory;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.*;
import io.spring.main.model.goods.GoodsInsertData;
import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.*;
import org.springframework.context.annotation.ComponentScan;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:godourl.yml")
@ComponentScan(basePackages={"io.spring.main"})
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
    private final GoodsSearch goodsSearch;
    private final EntityManager entityManager;
    private final DataShareBean<Tmmapi> dataShareBean;
//    private final EntityManagerFactory entityManagerFactory;

//    private MappingJackson2XmlHttpMessageConverter xmlConverter;
//    private final XmlMapper xmlMapper = new XmlMapper();

    // 고도몰 관련 값들
    @Value("${pKey}")
    private String pKey;
    @Value("${key}")
    private String key;
    @Value("${url.goodsInsert}")
    private String goodsInsertUrl;
    @Value("${url.goodsUpdate}")
    private String goodsUpdateUrl;
    // xml 저장 주소
    @Value("${url.xmlUrl}")
    private String xmlSaveUrl;

    @Transactional
    public void sendApi(){
        Map<String, Tmmapi> map = this.dataShareBean.getMap();
        for(String xmlUrl : map.keySet()){
            // api 전송
            String goodsNoIfSuccess = sendXmlToGoodsInsert(xmlUrl, map.get(xmlUrl));
            Tmmapi tmmapi = map.get(xmlUrl);
//            System.out.println("##### "+tmmapi.getAssortNm());
            // tmmapi의 joinStatus와 uploadYn을 01로 바꾸기
            tmmapi.setJoinStatus(StringFactory.getGbOne());
            tmmapi.setUploadYn(StringFactory.getGbOne());
            // tmmapi의 channelGoodsNo를 return 받은 goodsNo로 설정한 후 저장
            tmmapi.setChannelGoodsNo(goodsNoIfSuccess);
            jpaTmmapiRepository.save(tmmapi);

            if(goodsNoIfSuccess == null){
                break;
            }
            // 위에서 받은 goodsNoIfSuccess로 goods_search api로 받아오기
            List<GoodsSearchData> goodsSearchDataList = goodsSearch.retrieveGoods(goodsNoIfSuccess,"", "");
            GoodsSearchData goodsSearchData = null;
            if(goodsSearchDataList.size() > 0){
                goodsSearchData = goodsSearchDataList.get(0);
            }
            else{
                break;
            }
            List<GoodsSearchData.OptionData> optionDataList = goodsSearchData.getOptionData();
            List<Tmitem> tmitemList = tmmapi.getTmitemList();
            // tmitem에 channelGoodsNo(goodsNo), channelOptionsNo(optionData의 sno) set해주기
            tmitemList.stream().forEach(x -> {
                optionDataList.stream().forEach(y -> {
                    if(x.getItemId().equals(y.getOptionCode())){
                        x.setChannelGoodsNo(goodsNoIfSuccess);
                        x.setChannelOptionsNo(Long.toString(y.getSno()));
                    }
                });
                // tmitem 저장
                jpaTmitemRepository.save(x);
            });
        }
    }

    private String sendXmlToGoodsInsert(String xmlUrl, Tmmapi tmmapi) {
        BufferedReader br = null;
        String goodsNoIfSuccess = null;
        String urlStr = null;
        if(tmmapi.getUploadType().equals(StringFactory.getGbTwo())){
            urlStr = this.goodsUpdateUrl;
        }
        else {
            urlStr = this.goodsInsertUrl;
        }
        urlStr = urlStr
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
            URL url = new URL(urlStr);
            System.out.println("url : " + urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // 응답 읽기
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), StringFactory.getStrUtf8()));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }
            System.out.println(" ----- result : " + result);
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
            XPathExpression expr = xPath.compile("//header"); // header 하드코딩
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            String headerCode = null;

            // <header/> 아래에 있는 <code/>의 값을 가져옴. 000 : 성공, 그 외 값은 실패.
            headerCode = (String) CommonXmlParse.getNodeValue(nodeList.item(0).getFirstChild());
            if(headerCode.equals(StringFactory.getStrSuccessCode())){
                // <data/> 아래에 있는 <goodsno/>의 값을 가져옴.
                expr = xPath.compile("//goodsno"); // goodsno 하드코딩
                Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
                String successGoodsNo = (String) CommonXmlParse.getNodeValue(node);
//                System.out.println("+++++ " + successGoodsNo);
                return successGoodsNo;
            }
        }
        catch(Exception e){
            log.debug(e.getMessage());
        }
        return null;
    }

    // , ititmm : tmitem 고도몰 goods_insert api를 만들기 위한 GoodsInsertData 객체를 만드는 함수
    public GoodsInsertData makeGoodsSearchObject(Tmmapi tmmapi) {
        GoodsInsertData goodsInsertData = new GoodsInsertData(makeGoodsDataFromTmmapi(tmmapi), makeOptionDataFromTmitem(tmmapi));
        System.out.println("----- : "+tmmapi.getChannelGoodsNo());
        goodsInsertData.getGoodsData()[0].setGoodsNo(tmmapi.getChannelGoodsNo());
        System.out.println("----- goodsInsertData.getGoodsData()[0].goodsNo : "+goodsInsertData.getGoodsData()[0].getGoodsNo());
        return goodsInsertData;
    }

    // itasrt, itasrd : tmmapi -> GoodsInsertData.GoodsData 만드는 함수
    private GoodsInsertData.GoodsData makeGoodsDataFromTmmapi(Tmmapi tmmapi){
        Itasrt itasrt = jpaItasrtRepository.getOne(tmmapi.getAssortId());
        // 브랜드코드, 카테고리코드 고도몰 버전으로 교체
        itasrt.setBrandId(getGodoBrandCd(itasrt.getBrandId()));
//        itasrt.setCategoryId(itasrt.getCategoryId());//(getGodoCateCd(itasrt.getDispCategoryId(), tmmapi.getUploadType()));
        //
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
        return new GoodsInsertData.GoodsData(itasrt, itasrd1, itasrd2);
    }
    
    // 우리 브랜드코드로 고도몰 브랜드코드 가져오기
    private String getGodoBrandCd(String brandId){
        String brandCd = null;
//        if(uploadType.equals(StringFactory.getGbOne())){
            IfBrand ifBrand = jpaIfBrandRepository.findByChannelGbAndBrandId(StringFactory.getGbOne(), brandId);
            if(ifBrand == null){
                log.debug("brand code is not exist.");
                return brandCd;
            }
            brandCd = ifBrand.getChannelBrandId();
//        }
        return brandCd;
    }
    
//    // 우리 카테고리로 고도몰 카테고리코드 가져오기
//    private String getGodoCateCd(String cateId, String uploadType){
//        System.out.println("+++++ cateId 전 : " + cateId);
//        String cateCd = null;
//        if(uploadType.equals(StringFactory.getGbOne())) {
//            IfCategory ifCategory = jpaIfCategoryRepository.findByChannelGbAndCategoryId(StringFactory.getGbOne(), cateId);
//            if (ifCategory == null) {
//                log.debug("category code is not exist.");
//                return cateCd;
//            }
//            cateCd = ifCategory.getChannelCategoryId();
//
//            System.out.println("+++++ cateId 후 : " + cateId);
//        }
//        return cateCd;
//    }

    // ititmm : tmitem -> GoodsInsertData.OptionData 만드는 함수
    private GoodsInsertData.OptionData[] makeOptionDataFromTmitem(Tmmapi tmmapi){
        List<Tmitem> tmitemList = tmmapi.getTmitemList();
        GoodsInsertData.OptionData[] optionDataList = new GoodsInsertData.OptionData[tmitemList.size()];
        int i = 0;
        for(Tmitem tmitem : tmitemList){
            GoodsInsertData.OptionData optionData = new GoodsInsertData.OptionData(tmitem);
            optionData.setIdx(i + 1);
            optionDataList[i] = optionData;
            i++;
        }
        return optionDataList;
    }
    
    // goodsInsertData를 xml로 만드는 함수
    public String makeGoodsSearchXml(GoodsInsertData goodsInsertData, String assortId){
        String xmlContent = null;
        String ret="";

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
            System.out.println("----- : 저장할 xml : "+xmlContent);
            ret =  getXmlUrl(assortId, xmlContent);
//            System.out.println("ret : "+ret);

        } catch (Exception e) {
            e.getMessage();
        }

        return ret;
    }

    // xml string을 db에 저장하고 주소 반환
    private String getXmlUrl(String assortId, String xmlContent){
        // 만든 xml DB에 저장하기
        XmlTest xmlTest = jpaXmlTestRepository.findById(assortId).orElseGet(() -> new XmlTest(assortId));
        xmlTest.setXml(xmlContent);
//        jpaXmlTestRepository.save(xmlTest);
        entityManager.persist(xmlTest);
        return xmlSaveUrl
                + StringFactory.getStrQuestion()
                + StringFactory.getStrAssortId()
                + StringFactory.getStrEqual()
                + assortId;
    }
}
