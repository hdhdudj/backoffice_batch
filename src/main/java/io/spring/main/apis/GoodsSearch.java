package io.spring.main.apis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.*;
import io.spring.main.model.goods.*;
import io.spring.main.model.goods.entity.*;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.transaction.Transactional;
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
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoodsSearch {
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
    private final ObjectMapper objectMapper;

//    private static PoolManager poolManager = null;
//    private static SqlSession session = null;
//    @Transactional
    public void getGoodsSeq(String fromDt, String toDt){
        // objectMapper 설정
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // if table entity 리스트 생성
        List<IfGoodsMaster> ifGoodsMasterList = new ArrayList<>(); // if_goods_master
        
        // 트랜잭션1. if table 저장 함수
        saveIfTables(fromDt, toDt, ifGoodsMasterList); //, ifGoodsOptionList, ifGoodsTextOptionList, ifGoodsAddGoodsList);

        // 트랜잭션2. if table의 한 줄을 자체 table에 저장할 때 goodsNo 하나 기준으로 어떤 if table에서 실패하면 주루룩 실패해야 함.
        ifGoodsMasterList = jpaIfGoodsMasterRepository.findByUploadStatus(StringFactory.getGbOne()); // if_goods_master에서 upload_status가 01인 애 전부 가져옴
        for(IfGoodsMaster ifGoodsMaster : ifGoodsMasterList){
            saveOneGoodsNo(ifGoodsMaster.getGoodsNo(), ifGoodsMaster);
        }
    }

    @Transactional
    public void saveOneGoodsNo(String goodsNo, IfGoodsMaster ifGoodsMaster) {
        // 2. itasrt, itasrn, itasrd (from if_goods_master) 저장
        // itadgs (from if_goods_add_goods) 저장
        Itasrt itasrt = this.saveItasrt(ifGoodsMaster); // itasrt
        this.saveItasrn(ifGoodsMaster); // itasrn
        this.saveItasrd(ifGoodsMaster); // itasrd

        // 3. if_goods_master 테이블 updateStatus 02로 업데이트
        ifGoodsMaster.setUploadStatus(StringFactory.getGbTwo()); // 02 하드코딩
        jpaIfGoodsMasterRepository.save(ifGoodsMaster);

        // 4. itvari (from if_goods_option) 저장
        List<IfGoodsOption> ifGoodsOptionList = jpaIfGoodsOptionRepository.findByGoodsNo(goodsNo);
        if(ifGoodsOptionList == null || ifGoodsOptionList.size() == 0){
            Itvari itvari = this.saveSingleItvari(itasrt.getAssortId());
            saveSingleItitmm(itasrt, itvari);
        }
        else{
            for(IfGoodsOption ifGoodsOption : ifGoodsOptionList){
                this.saveItvari(ifGoodsOption); // itvari
            }
        }

        // 5. ititmm (from if_goods_option) 저장
        for (IfGoodsOption ifGoodsOption : ifGoodsOptionList) {
            this.saveItitmm(ifGoodsOption); // ititmm
        }

        // 6. if_goods_option 테이블 updateStatus 02로 업데이트
        for(IfGoodsOption ifGoodsOption : ifGoodsOptionList){
            ifGoodsOption.setUploadStatus(StringFactory.getGbTwo()); // 02 하드코딩
            jpaIfGoodsOptionRepository.save(ifGoodsOption);
        }

        List<IfGoodsTextOption> ifGoodsTextOptionList = jpaIfGoodsTextOptionRepository.findByGoodsNo(goodsNo);
        // 7. itmmot (from if_goods_text_option) 저장
        for(IfGoodsTextOption ifGoodsTextOption : ifGoodsTextOptionList){
            this.saveItmmot(ifGoodsTextOption);
        }

        // 8. if_goods_text_option 테이블 updateStatus 02로 업데이트
        for(IfGoodsTextOption ifGoodsTextOption : ifGoodsTextOptionList){
            ifGoodsTextOption.setUploadStatus(StringFactory.getGbTwo()); // 02 하드코딩
            jpaIfGoodsTextOptionRepository.save(ifGoodsTextOption);
        }

        List<IfGoodsAddGoods> ifGoodsAddGoodsList = jpaIfGoodsAddGoodsRepository.findByGoodsNo(goodsNo);
        // 9. itlkag (from if_goods_add_goods) 저장
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            // add_goods_id 채번
            String addGoodsId = getNo(jpaItlkagRepository.findMaxAddGoodsIdByAssortId(ifGoodsAddGoods.getAssortId()), 9);
            Itlkag itlkag = new Itlkag(ifGoodsAddGoods);
            itlkag.setAddGoodsId(addGoodsId);
            jpaItlkagRepository.save(itlkag);

            // if_goods_add_goods에도 add_goods_id 저장 (아직 save는 노노.. 이따가 uploadStatus 저장할 때 같이)
            ifGoodsAddGoods.setAddGoodsId(addGoodsId);
        }
        // 10. itadgs (from if_goods_add_goods) 저장
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            //OpenApi호출
            AddGoodsData addGoodsData = retrieveAddGoods(ifGoodsAddGoods.getGoodsNo());

            ifGoodsAddGoods.setGoodsNm(addGoodsData.getGoodsNm());
            ifGoodsAddGoods.setOptionNm(addGoodsData.getOptionNm());
            ifGoodsAddGoods.setBrandCd(addGoodsData.getBrandCd());
            ifGoodsAddGoods.setMakerNm(addGoodsData.getMakerNm());
            ifGoodsAddGoods.setGoodsPrice(addGoodsData.getGoodsPrice());
            ifGoodsAddGoods.setStockCnt(addGoodsData.getStockCnt());
            ifGoodsAddGoods.setViewFl(ynToOneTwo(addGoodsData.getViewFl()));
            ifGoodsAddGoods.setUploadStatus(StringFactory.getGbOne());
            ifGoodsAddGoods.setSoldOutFl(ynToOneTwo(addGoodsData.getSoldOutFl()));
            Itadgs itadgs = new Itadgs(ifGoodsAddGoods);
            jpaItadgsRepository.save(itadgs);
//            jpaIfGoodsAddGoodsRepository.save(addGoodsData);
        }

        // 11. if_goods_add_goods 테이블에 add_goods_id 삽입하고 updateStatus 02로 업데이트
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            StringFactory.getGbTwo(); // 02 하드코딩
            jpaIfGoodsAddGoodsRepository.save(ifGoodsAddGoods);
        }
    }

    private void saveSingleItitmm(Itasrt itasrt, Itvari itvari) {
        // option이 없는 경우. seq 0001, 옵션구분 01, variation구분 01, 옵션명 '단품'
        Ititmm ititmm = new Ititmm(itasrt, itvari);
        jpaItitmmRepository.save(ititmm);
    }

    private Itvari saveSingleItvari(String assortId) {
        // option이 없는 경우. seq 0001, 옵션구분 01, variation구분 01, 옵션명 '단품'
        Itvari itvari = new Itvari(assortId);
        jpaItvariRepository.save(itvari);
        return itvari;
    }

    @Transactional
    public void saveIfTables(String fromDt, String toDt, List<IfGoodsMaster> ifGoodsMasterList){ //, List<IfGoodsOption> ifGoodsOptionList, List<IfGoodsTextOption> ifGoodsTextOptionList, List<IfGoodsAddGoods> ifGoodsAddGoodsList){
        List<GoodsData> goodsDataList = retrieveGoods(fromDt, toDt);
//        String assortId = "";

        // 1. if table 저장
        for(GoodsData goodsData : goodsDataList){
            // goodsNo가 겹치는 애가 있는지 확인
            if(jpaIfGoodsMasterRepository.findByGoodsNo(Long.toString(goodsData.getGoodsNo())) == null){
                ifGoodsMasterList.add(this.saveIfGoodsMaster(goodsData)); // if_goods_master : itasrt, itasrn, itasrd  * 여기서 assortId 생성
                this.saveIfGoodsTextOption(goodsData); // if_goods_text_option : itmmot
                this.saveIfGoodsAddGoods(goodsData); // if_goods_add_goods : itlkag, itadgs
            }
            if(jpaIfGoodsOptionRepository.findByGoodsNo(Long.toString(goodsData.getGoodsNo())).size() == 0){
                this.saveIfGoodsOption(goodsData); // if_goods_option : itvari, ititmm
            }
        }
    }

    private void saveItmmot(IfGoodsTextOption ifGoodsTextOption) {
        Itmmot itmmot = new Itmmot(ifGoodsTextOption);
        // optionTextId 채번
        String optionTextId = getSeq(jpaItmmotRepository.findMaxSeqByAssortId(itmmot.getAssortId()),4);
        itmmot.setOptionTextId(optionTextId);
        jpaItmmotRepository.save(itmmot);
    }

    private void saveItvari(IfGoodsOption ifGoodsOption) { // 01 색깔, 02 사이즈 저장
        Itvari itvariColor = new Itvari(ifGoodsOption);
        // 옵션 01 : 색깔 저장
        itvariColor.setOptionGb(StringFactory.getGbOne()); // 01 하드코딩
        itvariColor.setVariationGb(StringFactory.getGbOne()); // 01 하드코딩
        itvariColor.setOptionNm(ifGoodsOption.getOptionValue1());
        String seq = "";
        if(jpaItvariRepository.findByOptionGbAndOptionNm(itvariColor.getOptionGb(), itvariColor.getOptionNm()) == null){
            seq = getSeq(jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId()),4);
            itvariColor.setSeq(seq);
            jpaItvariRepository.save(itvariColor);
        }

        Itvari itvariSize = new Itvari(ifGoodsOption);
        if(ifGoodsOption.getOptionName().split(StringFactory.getSplitGb()).length >= 2){
            itvariSize.setOptionGb(StringFactory.getGbTwo()); // 02 하드코딩
            itvariSize.setVariationGb(StringFactory.getGbTwo()); // 02 하드코딩
            itvariSize.setOptionNm(ifGoodsOption.getOptionValue2());
        }
        else{
            return;
        }
        // 옵션 02 : 사이즈 저장
        if(!seq.equals("")){
            seq = Utilities.plusOne(seq,4);
        }
        else{
            seq = getSeq(jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId()),4);
        }
        if(jpaItvariRepository.findByOptionGbAndOptionNm(itvariSize.getOptionGb(), itvariSize.getOptionNm()) == null){
            itvariSize.setSeq(seq);
            jpaItvariRepository.save(itvariSize);
        }
    }

    private void saveItitmm(IfGoodsOption ifGoodsOption) {
        Ititmm ititmm = new Ititmm(ifGoodsOption);
        // itemId 채번
        String itemId = jpaItitmmRepository.findMaxItemIdByAssortId(ititmm.getAssortId());
        itemId = getSeq(itemId, 4);
        ifGoodsOption.setItemId(itemId);
        ititmm.setItemId(itemId);
        // op1이 없으면 단품으로 처리
        Itvari itvariOp1 = jpaItvariRepository.findByAssortIdAndOptionNm(ititmm.getAssortId(), ifGoodsOption.getOptionValue1());
        // null 처리, 없으면 단품으로(01, 01, 단품)
        if(itvariOp1 == null){
            ititmm.setVariationGb1(StringFactory.getGbOne()); // 01
            ititmm.setVariationSeq1(StringUtils.leftPad(StringFactory.getStrOne(),4,'0'));
        }
        else{
            ititmm.setVariationGb1(itvariOp1.getVariationGb());
            ititmm.setVariationSeq1(itvariOp1.getSeq());
        }
        if(ifGoodsOption.getOptionName().split(StringFactory.getSplitGb()).length >= 2){
            Itvari itvariOp2 = jpaItvariRepository.findByAssortIdAndOptionNm(ititmm.getAssortId(), ifGoodsOption.getOptionValue2());
            ititmm.setVariationGb2(itvariOp2.getVariationGb());
            ititmm.setVariationSeq2(itvariOp2.getSeq());
        }
        jpaItitmmRepository.save(ititmm);
    }

    private Itasrt saveItasrt(IfGoodsMaster ifGoodsMaster) {
        Itasrt itasrt = new Itasrt(ifGoodsMaster); // itasrt
        String assortId = ifGoodsMaster.getAssortId();
        itasrt.setAssortId(assortId);
        jpaItasrtRepository.save(itasrt);
        return itasrt;
    }

    private void saveItasrd(IfGoodsMaster ifGoodsMaster) {
        Itasrd itasrdShort = new Itasrd(ifGoodsMaster); // itasrd -> 짧은 설명과 긴 설명 두 개 저장해야 됨.
        itasrdShort.setAssortId(ifGoodsMaster.getAssortId());
        itasrdShort.setSeq(StringUtils.leftPad(StringFactory.getStrOne(), 4, '0'));
        itasrdShort.setMemo(ifGoodsMaster.getShortDescription()); // 0001
        itasrdShort.setOrdDetCd(StringFactory.getGbTwo()); // 01 : 상세, 02 : 간략
        itasrdShort.setTextHtmlGb(StringFactory.getGbTwo()); // 01 : html, 02 : text
        jpaItasrdRepository.save(itasrdShort);
        Itasrd itasrdLong = new Itasrd(ifGoodsMaster);
        itasrdLong.setAssortId(ifGoodsMaster.getAssortId());
        itasrdLong.setSeq(StringUtils.leftPad(StringFactory.getStrTwo(), 4, '0'));
        itasrdLong.setMemo(ifGoodsMaster.getGoodsDescription()); // 0002
        itasrdLong.setOrdDetCd(StringFactory.getGbOne()); // 01 : 상세, 02 : 간략
        itasrdLong.setTextHtmlGb(StringFactory.getGbOne()); // 01 : html, 02 : text
        jpaItasrdRepository.save(itasrdLong);
    }

    // itasrn 저장 함수
    private Itasrn saveItasrn(IfGoodsMaster ifGoodsMaster){
        Date effEndDt = null;
        try
        {
            effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        }
        catch (Exception e){
            log.debug(e.getMessage());
        }
        Itasrn itasrn = jpaItasrnRepository.findByAssortIdAndEffEndDt(ifGoodsMaster.getAssortId(), effEndDt);
        if(itasrn == null){ // insert
            itasrn = new Itasrn(ifGoodsMaster);
            itasrn.setAssortId(ifGoodsMaster.getAssortId());
        }
        else{ // update
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.SECOND, -1);
            itasrn.setEffEndDt(cal.getTime());
            // update 후 새 이력 insert
            Itasrn newItasrn = new Itasrn(ifGoodsMaster);
            newItasrn.setAssortId(ifGoodsMaster.getAssortId());
            jpaItasrnRepository.save(newItasrn);
        }
        jpaItasrnRepository.save(itasrn);
        return itasrn;
    }

    // seq, itemId 등 채번 함수
    private String getSeq(String maxSeq, int seqLength) {
        String seq = "";
        if(maxSeq == null){
            seq = StringUtils.leftPad(StringFactory.getStrOne(),seqLength,'0'); // 0001
        }
        else {
            seq = Utilities.plusOne(maxSeq, seqLength);
        }
        return seq;
    }

    // assortId 등 채번 함수
    private String getNo(String nextVal, int length) {
        // nextVal이 null일 때 (첫번째 채번)
        if(nextVal == null)
        {
            nextVal = StringUtils.leftPad(StringFactory.getStrOne(), length, '0'); // 000000001 들어감
        }
        else{
            nextVal = Utilities.plusOne(nextVal, length);
        }
        return nextVal;
    }

    private IfGoodsMaster saveIfGoodsMaster(GoodsData goodsData) {
        String assortId = "";
        if(goodsData.getAssortId() == null){
            assortId = StringUtils.leftPad(jpaSequenceDataRepository.nextVal(StringFactory.getSeqItasrtStr()), 9, '0');
            goodsData.setAssortId(assortId);
        }
        else{
            assortId = goodsData.getAssortId();
        }
        IfGoodsMaster ifGoodsMaster = jpaIfGoodsMasterRepository.findByGoodsNo(Long.toString(goodsData.getGoodsNo()));
        if(ifGoodsMaster == null){
            ifGoodsMaster = objectMapper.convertValue(goodsData, IfGoodsMaster.class);
        }
        ifGoodsMaster.setAssortId(goodsData.getAssortId()); // assort_id 설정
        ifGoodsMaster.setChannelGb(StringFactory.getGbOne()); // 채널 01 하드코딩
        ifGoodsMaster.setUploadStatus(StringFactory.getGbOne()); // update_status 01 하드코딩
        // y/n을 01/02로 바꾸기
        ifGoodsMaster.setGoodsSellFl(ynToOneTwo(ifGoodsMaster.getGoodsSellFl()));
        ifGoodsMaster.setGoodsDisplayFl(ynToOneTwo(ifGoodsMaster.getGoodsDisplayFl()));
        ifGoodsMaster.setOptionFl(ynToOneTwo(ifGoodsMaster.getOptionFl()));
        ifGoodsMaster.setSizeType(ynToOneTwo(ifGoodsMaster.getSizeType()));
        // 매핑 테이블 이용해 고도몰 코드를 백오피스 코드로 전환 (brandCd, cateCd)
        IfBrand ifBrand = jpaIfBrandRepository.findByChannelBrandId(ifGoodsMaster.getBrandCd());
        if(ifBrand != null){
            ifGoodsMaster.setBrandCd(ifBrand.getBrandId());
        }
        IfCategory ifCategory = jpaIfCategoryRepository.findByChannelCategoryId(ifGoodsMaster.getCateCd());
        if(ifCategory != null){
            ifGoodsMaster.setCateCd(ifCategory.getCategoryId());
        }

        jpaIfGoodsMasterRepository.save(ifGoodsMaster);
        return ifGoodsMaster;
    }

    // 'y'는 '01'로, 'n'은 '02'로 변환
    private String ynToOneTwo(String yn){
        String returnStr = null;
        if(yn == null){
        }
        else if(yn.equals(StringFactory.getStrY())){ // 'y'
            returnStr = StringFactory.getGbOne();
        }
        else if(yn.equals(StringFactory.getStrN())){ // 'n'
            returnStr = StringFactory.getGbTwo();
        }
        else if(yn.equals(StringFactory.getStrLight())){ // 'light'
            returnStr = StringFactory.getGbOne();
        }
        else if(yn.equals(StringFactory.getStrFurn())){ // 'furn' 02
            returnStr = StringFactory.getGbTwo();
        }
        return returnStr;
    }

    private void saveIfGoodsOption(GoodsData goodsData){ //, List<IfGoodsOption> ifGoodsOptionList) {
        List<GoodsData.OptionData> optionDataList = goodsData.getOptionData();
        if(optionDataList == null){
            log.debug("optionDataList is null.");
        }
        else{
            for(GoodsData.OptionData optionData : optionDataList){
                IfGoodsOption ifGoodsOption = objectMapper.convertValue(optionData,IfGoodsOption.class);
                ifGoodsOption.setAssortId(goodsData.getAssortId());
                ifGoodsOption.setUploadStatus(StringFactory.getGbOne());
                ifGoodsOption.setOptionName(goodsData.getOptionName());
                jpaIfGoodsOptionRepository.save(ifGoodsOption);
            }
        }
    }

    private void saveIfGoodsTextOption(GoodsData goodsData){ //, List<IfGoodsTextOption> ifGoodsTextOptionList) {
        List<GoodsData.TextOptionData> textOptionDataList = goodsData.getTextOptionData();
        if(textOptionDataList == null){
            log.debug("textOptionDataList is null.");
            return;
        }
        for(GoodsData.TextOptionData textOptionData : textOptionDataList){
            IfGoodsTextOption ifGoodsTextOption = objectMapper.convertValue(textOptionData,IfGoodsTextOption.class);
            ifGoodsTextOption.setAssortId(goodsData.getAssortId());
            ifGoodsTextOption.setChannelGb(StringFactory.getGbOne());
            // yn을 0102로
            ifGoodsTextOption.setMustFl(ynToOneTwo(ifGoodsTextOption.getMustFl()));
            ifGoodsTextOption.setUploadStatus(StringFactory.getGbOne());
            jpaIfGoodsTextOptionRepository.save(ifGoodsTextOption);
        }
    }

    private void saveIfGoodsAddGoods(GoodsData goodsData){ //, List<IfGoodsAddGoods> addGoodsDataListOut) {
        List<GoodsData.AddGoodsData> addGoodsDataList = goodsData.getAddGoodsData();
//        System.out.println("addGoodsData length : " + addGoodsDataList.size());
        if(addGoodsDataList == null){
            log.debug("addGoodsDataList is null.");
            return;
        }
        for(GoodsData.AddGoodsData addGoodsData : addGoodsDataList){ // goodsNoData 기준으로 if_goods_add_goods에 저장
            List<String> goodsNoData = addGoodsData.getGoodsNoData();
            for(String addGoods : goodsNoData){
                IfGoodsAddGoods ifGoodsAddGoods = objectMapper.convertValue(goodsData, IfGoodsAddGoods.class);
                ifGoodsAddGoods.setAssortId(goodsData.getAssortId());
                ifGoodsAddGoods.setAddGoodsNo(addGoods);
                ifGoodsAddGoods.setTitle(addGoodsData.getTitle());
                ifGoodsAddGoods.setUploadStatus(StringFactory.getGbOne()); // 01 하드코딩

                jpaIfGoodsAddGoodsRepository.save(ifGoodsAddGoods);
            }
        }
    }
    
    // addGoods xml 받아오는 함수
    private AddGoodsData retrieveAddGoods(String goodsNo){
        //OpenApi호출
        String urlstr = StringFactory.getGodoUrl() + StringFactory.getGoodsSearch() + "?" + StringFactory.getGoodsSearchParams()[0] + "=" +
                StringFactory.getPKey() + "&" +StringFactory.getGoodsSearchParams()[1]
                + "=" + StringFactory.getKey()+"&"+goodsNo+"="+goodsNo;
        NodeList nodeList =  getXmlNodes(urlstr);
        AddGoodsData map = null;

        // xml 파싱
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList child = nodeList.item(i).getChildNodes();
            for (int j = 0; j < child.getLength(); j++) {
                Node lNode = child.item(j);
                if(lNode.getNodeName().equals(StringFactory.getStrReturn())){
                    NodeList mNodes = lNode.getChildNodes();
                    for (int k = 0; k < mNodes.getLength(); k++) {
                        Node mNode = mNodes.item(k);
                        if(mNode.getNodeName().equals(StringFactory.getStrGoodsData())) { // goods_data
                            map = makeAddGoodsDataForGoodsAddSearch(mNode);
                        }
                    }
                }
            }
        }

        return map;
    }

    private AddGoodsData makeAddGoodsDataForGoodsAddSearch(Node cNode) {
        Map<String, Object> map = new HashMap<String, Object>();
        NodeList cNodes = cNode.getChildNodes();
        List<String> goodsNoDataList = new ArrayList<>();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node node = cNodes.item(i);
            map.put(node.getNodeName(),getNodeValue(node));
        }
        AddGoodsData o = objectMapper.convertValue(map, AddGoodsData.class);
        return o;
    }

    // goods xml 받아오는 함수
    private List<GoodsData> retrieveGoods(String fromDt, String toDt) {

        //OpenApi호출
        String urlstr = StringFactory.getGodoUrl() + StringFactory.getGoodsSearch() + "?" + StringFactory.getGoodsSearchParams()[0] + "=" +
                StringFactory.getPKey() + "&" +StringFactory.getGoodsSearchParams()[1]
                + "=" + StringFactory.getKey();// +"&goodsNo=1000032220";
        NodeList nodeList =  getXmlNodes(urlstr);

        List<GoodsData> goodsDatas = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int y = 0; y < child.getLength(); y++) {
                    Node lNode = child.item(y);
                    // printTree(lNode, 1);
                    if (lNode.getNodeName() == StringFactory.getStrReturn()) {
                        NodeList mNodes = lNode.getChildNodes();
                        // List<OrderData> orderMasterDatas = new ArrayList<OrderData>();
                        for (int mi = 0; mi < mNodes.getLength(); mi++) {
//								for (int mi = 0; mi < 1; mi++) {
                            Node mNode = mNodes.item(mi);
                            if (mNode.getNodeName() == StringFactory.getStrGoodsData()) {
//                                EsGoods map = makeGoodsmaster(mNode);
                                GoodsData map = makeGoodsmaster(mNode);
                                goodsDatas.add(map);
                                // order master array append
                            }
                        }
                        System.out.println("-----------------------------------------------------------------");
                        System.out.println(goodsDatas.size());
                        System.out.println("-----------------------------------------------------------------");
                    }
                }
            }
            return goodsDatas;
    }

    private NodeList getXmlNodes(String urlstr){
        // TODO Auto-generated method stub
        BufferedReader br = null;
        // DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        List<GoodsData> goodsDatas = new ArrayList<>();

        try {
            //OpenApi호출
//            System.out.println(urlstr);
            //+ "&orderStatus=p1";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

            // 응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }
//            System.out.println(result);
//
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));

            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            // XPathExpression expr = xpath.compile("/response/body/items/item");
            XPathExpression expr = xpath.compile("//data");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            return nodeList;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private GoodsData makeGoodsmaster(Node root) {
        Map<String, Object> map = new HashMap<String, Object>();
        // List<Map<String, Object>> deliveryDatas = new ArrayList<Map<String,
        // Object>>();
        List<GoodsData.OptionData> optionDataList = new ArrayList<>();

        List<GoodsData.TextOptionData> textOptionDataList = new ArrayList<>();
        // List<Map<String, Object>> goodsDatas = new ArrayList<Map<String, Object>>();
        List<GoodsData.AddGoodsData> adGoodsDataList = new ArrayList<>();
        // List<Map<String, Object>> addGoodsDatas = new ArrayList<Map<String,
        // Object>>();
        List<GoodsData.GoodsMustInfoData> goodsMustInfoDataList = new ArrayList<>();

        NodeList cNodes = root.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node cNode = cNodes.item(i);
            //String idx = cNode.getAttributes().getNamedItem("idx").getNodeValue();
            // System.out.println(cNode.getAttributes().getNamedItem("idx").getNodeValue());
//            log.debug("+++++ nodeName : " + cNode.getNodeName());
            if (cNode.getNodeName().equals(StringFactory.getStrOptionData())) {
                GoodsData.OptionData optionData = makeOptionData(cNode);
                optionDataList.add(optionData);
            } else if (cNode.getNodeName().equals(StringFactory.getStrTextOptionData())) {
                GoodsData.TextOptionData textOptionData = makeTextOptionData(cNode);
                textOptionDataList.add(textOptionData);
            } else if (cNode.getNodeName().equals(StringFactory.getStrAddGoodsData())) {
                // Map<String, Object> goodsData = makeOrderGoodsData(cNode);
                GoodsData.AddGoodsData addGoodsData = makeAddGoodsData(cNode);
//                log.debug("----- addGoodsData : " + addGoodsData.getGoodsNoData().size());
                adGoodsDataList.add(addGoodsData);
            } else if (cNode.getNodeName().equals(StringFactory.getStrGoodsMustInfoData())) {
                // Map<String, Object> addGoodsData = makeAddGoodsData(cNode);
                GoodsData.GoodsMustInfoData goodsMustInfoData = makeGoodsMustInfoData(cNode);
                goodsMustInfoDataList.add(goodsMustInfoData);
            } else {

                if (cNode.getNodeName() == StringFactory.getStrClaimData()) {
                    System.out.println("claimData 데이타 이상 - 확인필요");
                }

                if ("확인필요한값!".equals((String) getNodeValue(cNode))) {
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("데이타 이상 - 확인필요");
                    System.out.println("-----------------------------------------------------------------");
                } else {
//                    log.debug("----- " + cNode.getNodeName() + " : "+  getNodeValue(cNode));
                    map.put(this.controlSnakeCaseException(cNode.getNodeName()), getNodeValue(cNode));
                }

            }

            map.put(StringFactory.getStrOptionData(),optionDataList);
            map.put(StringFactory.getStrTextOptionData(),textOptionDataList);
            map.put(StringFactory.getStrAddGoodsData(),adGoodsDataList);
            map.put(StringFactory.getStrGoodsMustInfoData(),goodsMustInfoDataList);
        }

        GoodsData o = objectMapper.convertValue(map, GoodsData.class);
        return o;
    }

    // 고도몰 table column명이 camleCase로 돼있는데 몇 개만 snake로 돼있어서 걔네 처리용
    private String controlSnakeCaseException(String nodeNm){
        String[] splitStrs = nodeNm.split("_");
        if(splitStrs.length > 2){
            nodeNm = this.snakeToCamel(nodeNm);
        }
        return nodeNm;
    }
    private String snakeToCamel(String str){
        String[] miniStrs = str.split("_");
        str = miniStrs[0];
        for(int j = 1 ; j < miniStrs.length; j++){
            str += miniStrs[j].substring(0,1).toUpperCase() + miniStrs[j].substring(1);
        }
        return str;
    }

    private GoodsData.AddGoodsData makeAddGoodsData(Node cNode) {
        Map<String, Object> map = new HashMap<String, Object>();
        NodeList cNodes = cNode.getChildNodes();
        List<String> goodsNoDataList = new ArrayList<>();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node node = cNodes.item(i);
            if(node.getNodeName().equals("goodsNoData")){
//                System.out.println("===== : "+getNodeValue(node).toString());
                goodsNoDataList.add(getNodeValue(node).toString());//node.getNodeValue());
            }
            else{
                map.put(node.getNodeName(),getNodeValue(node));
            }
        }
        map.put("goodsNoData", goodsNoDataList);
        GoodsData.AddGoodsData o = objectMapper.convertValue(map, GoodsData.AddGoodsData.class);
        return o;
    }

    private static GoodsData.GoodsMustInfoData makeGoodsMustInfoData(Node cNode) {
        return null;
    }

    private GoodsData.TextOptionData makeTextOptionData(Node cNode) {
        Map<String, Object> map = new HashMap<String, Object>();
        NodeList cNodes = cNode.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node node = cNodes.item(i);
            map.put(node.getNodeName(),getNodeValue(node));
        }
        GoodsData.TextOptionData o = objectMapper.convertValue(map, GoodsData.TextOptionData.class);
        return o;
    }

    private GoodsData.OptionData makeOptionData(Node cNode) {
        Map<String, Object> map = new HashMap<String, Object>();
        NodeList cNodes = cNode.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node node = cNodes.item(i);
            map.put(node.getNodeName(),getNodeValue(node));

        }
        GoodsData.OptionData o = objectMapper.convertValue(map, GoodsData.OptionData.class);
        return o;
    }

    public static Object getNodeValue(Node n) {

        if (n.getChildNodes().getLength() == 0) {
            return n.getNodeValue();
        } else {
            if (n.getNodeValue() == null && n.getFirstChild().getNodeType() == 3) {

                return n.getFirstChild().getNodeValue();
            } else if (n.getNodeValue() == null && n.getFirstChild().getNodeType() == 4) {

                return n.getFirstChild().getNodeValue();

            } else {
                return "확인필요한값!";
            }

        }



    }

    public static void printTree(Node root, int level) {

        System.out.println("name : " + root.getNodeName() + " level - " + level);
        // System.out.println(root.getChildNodes().getLength());

        if (root.getChildNodes().getLength() > 0) {

            NodeList cNodes = root.getChildNodes();

            for (int i = 0; i < cNodes.getLength(); i++) {
                Node cNode = cNodes.item(i);
                if (cNode.getNodeType() == 1) {
                    printTree(cNode, level + 1);
                } else {
                    System.out.println("==level=== " + level);
                    System.out.println("====" + cNode.getNodeValue());
                }
            }

        } else {

            System.out.println("-------" + level);
            System.out.println("-------" + root.getNodeValue());
        }

    }
}
