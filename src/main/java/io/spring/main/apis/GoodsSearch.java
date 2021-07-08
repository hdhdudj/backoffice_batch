package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.jparepos.category.JpaIfCategoryRepository;
import io.spring.main.util.StringFactory;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.*;
import io.spring.main.model.goods.*;
import io.spring.main.model.goods.entity.*;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:godourl.yml")
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
    private final CommonXmlParse commonXmlParse;

    private final List<String> goodsSearchGotListPropsMap;


    // 고도몰 관련 값들
    @Value("${pKey}")
    private String pKey;
    @Value("${key}")
    private String key;
    @Value("${url.goodsSearch}")
    private String goodsSearchUrl;
    @Value("${url.goodsAddSearch}")
    private String goodsAddSearchUrl;

//    private static PoolManager poolManager = null;
//    private static SqlSession session = null;
//    @Transactional
//    public void searchGoodsSeq(String fromDt, String toDt){
////        // objectMapper 설정
////        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
////        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
////        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//
//        // if table entity 리스트 생성
//        List<IfGoodsMaster> ifGoodsMasterList = new ArrayList<>(); // if_goods_master
//
//        // 트랜잭션1. if table 저장 함수
//        saveIfTables(fromDt, toDt); //, ifGoodsOptionList, ifGoodsTextOptionList, ifGoodsAddGoodsList);
//
//        // 트랜잭션2. if table의 한 줄을 자체 table에 저장할 때 goodsNo 하나 기준으로 어떤 if table에서 실패하면 주루룩 실패해야 함.
//        ifGoodsMasterList = jpaIfGoodsMasterRepository.findByUploadStatus(StringFactory.getGbOne()); // if_goods_master에서 upload_status가 01인 애 전부 가져옴
//        for(IfGoodsMaster ifGoodsMaster : ifGoodsMasterList){
//            saveOneGoodsNo(ifGoodsMaster.getGoodsNo(), ifGoodsMaster);
//        }
//    }

    public List<IfGoodsMaster> getIfGoodsMasterListWhereUploadStatus01(){
        List<IfGoodsMaster> ifGoodsMasterList = jpaIfGoodsMasterRepository.findByUploadStatus(StringFactory.getGbOne()); // if_goods_master에서 upload_status가 01인 애 전부 가져옴
        return ifGoodsMasterList;
    }

    @Transactional
    public IfGoodsMaster saveOneGoodsNo(String goodsNo, IfGoodsMaster ifGoodsMaster) {
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
            ifGoodsAddGoods.setViewFl(Utilities.ynToOneTwo(addGoodsData.getViewFl()));
            ifGoodsAddGoods.setUploadStatus(StringFactory.getGbOne());
            ifGoodsAddGoods.setSoldOutFl(Utilities.ynToOneTwo(addGoodsData.getSoldOutFl()));
            Itadgs itadgs = new Itadgs(ifGoodsAddGoods);
            jpaItadgsRepository.save(itadgs);
//            jpaIfGoodsAddGoodsRepository.save(addGoodsData);
        }

        // 11. if_goods_add_goods 테이블에 add_goods_id 삽입하고 updateStatus 02로 업데이트
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            StringFactory.getGbTwo(); // 02 하드코딩
            jpaIfGoodsAddGoodsRepository.save(ifGoodsAddGoods);
        }

        return ifGoodsMaster;
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
    public void saveIfTables(String fromDt, String toDt){ //, List<IfGoodsOption> ifGoodsOptionList, List<IfGoodsTextOption> ifGoodsTextOptionList, List<IfGoodsAddGoods> ifGoodsAddGoodsList){
        List<GoodsSearchData> goodsSearchDataList = retrieveGoods("", fromDt, toDt);
//        String assortId = "";

        // 1. if table 저장
        for(GoodsSearchData goodsSearchData : goodsSearchDataList){
            // goodsDescription에 너무 긴 애가 들어있는 애 거르기
            String goodsDescription = goodsSearchData.getGoodsDescription();
            if(goodsDescription.split(StringFactory.getStrDataImage()).length >= 2){
                log.debug("goodsDescription is too long. goodsNo :" + goodsSearchData.getGoodsNo());
                continue;
            }
            // goodsNo가 겹치는 애가 있는지 확인
            if(jpaIfGoodsMasterRepository.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo())) == null){
                this.saveIfGoodsMaster(goodsSearchData); // if_goods_master : itasrt, itasrn, itasrd  * 여기서 assortId 생성
                this.saveIfGoodsTextOption(goodsSearchData); // if_goods_text_option : itmmot
                this.saveIfGoodsAddGoods(goodsSearchData); // if_goods_add_goods : itlkag, itadgs
            }
            if(jpaIfGoodsOptionRepository.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo())).size() == 0){
                this.saveIfGoodsOption(goodsSearchData); // if_goods_option : itvari, ititmm
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

    private IfGoodsMaster saveIfGoodsMaster(GoodsSearchData goodsSearchData) {
        String assortId = "";
        if(goodsSearchData.getAssortId() == null){
            String num = jpaSequenceDataRepository.nextVal(StringFactory.getSeqItasrtStr());
            if(num == null){
                num = StringFactory.getStrOne();
            }
            assortId = StringUtils.leftPad(num, 9, '0');
            goodsSearchData.setAssortId(assortId);
        }
        else{
            assortId = goodsSearchData.getAssortId();
        }
        IfGoodsMaster ifGoodsMaster = jpaIfGoodsMasterRepository.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo()));
        if(ifGoodsMaster == null){
            ifGoodsMaster = objectMapper.convertValue(goodsSearchData, IfGoodsMaster.class);
        }
        ifGoodsMaster.setAssortId(goodsSearchData.getAssortId()); // assort_id 설정
        // y/n을 01/02로 바꾸기
        ifGoodsMaster.setGoodsSellFl(Utilities.ynToOneTwo(ifGoodsMaster.getGoodsSellFl()));
        ifGoodsMaster.setGoodsDisplayFl(Utilities.ynToOneTwo(ifGoodsMaster.getGoodsDisplayFl()));
        ifGoodsMaster.setOptionFl(Utilities.ynToOneTwo(ifGoodsMaster.getOptionFl()));
        ifGoodsMaster.setSizeType(Utilities.ynToOneTwo(ifGoodsMaster.getSizeType()));
        // 매핑 테이블 이용해 고도몰 코드를 백오피스 코드로 전환 (brandCd, cateCd)
        IfBrand ifBrand = jpaIfBrandRepository.findByChannelGbAndChannelBrandId(StringFactory.getGbOne(), ifGoodsMaster.getBrandCd()); // 채널구분 01 하드코딩
        if(ifBrand != null){
            ifGoodsMaster.setBrandCd(ifBrand.getBrandId());
        }
        IfCategory ifCategory = jpaIfCategoryRepository.findByChannelGbAndChannelCategoryId(StringFactory.getGbOne(), ifGoodsMaster.getCateCd()); // 채널구분 01 하드코딩
        if(ifCategory != null){
            ifGoodsMaster.setCateCd(ifCategory.getCategoryId());
        }
        else {
            ifGoodsMaster.setCateCd("");
        }
//        log.debug("----- cateCd : " + ifGoodsMaster.getCateCd());
        jpaIfGoodsMasterRepository.save(ifGoodsMaster);
        return ifGoodsMaster;
    }



    private void saveIfGoodsOption(GoodsSearchData goodsSearchData){ //, List<IfGoodsOption> ifGoodsOptionList) {
        List<GoodsSearchData.OptionData> optionDataList = goodsSearchData.getOptionData();
        if(optionDataList == null){
            log.debug("optionDataList is null.");
        }
        else{
            for(GoodsSearchData.OptionData optionData : optionDataList){
                IfGoodsOption ifGoodsOption = objectMapper.convertValue(optionData,IfGoodsOption.class);
                ifGoodsOption.setAssortId(goodsSearchData.getAssortId());
                ifGoodsOption.setUploadStatus(StringFactory.getGbOne());
                ifGoodsOption.setOptionName(goodsSearchData.getOptionName());
                jpaIfGoodsOptionRepository.save(ifGoodsOption);
            }
        }
    }

    private void saveIfGoodsTextOption(GoodsSearchData goodsSearchData){ //, List<IfGoodsTextOption> ifGoodsTextOptionList) {
        List<GoodsSearchData.TextOptionData> textOptionDataList = goodsSearchData.getTextOptionData();
        if(textOptionDataList == null){
            log.debug("textOptionDataList is null.");
            return;
        }
        for(GoodsSearchData.TextOptionData textOptionData : textOptionDataList){
            IfGoodsTextOption ifGoodsTextOption = objectMapper.convertValue(textOptionData,IfGoodsTextOption.class);
            ifGoodsTextOption.setAssortId(goodsSearchData.getAssortId());
            ifGoodsTextOption.setChannelGb(StringFactory.getGbOne());
            // yn을 0102로
            ifGoodsTextOption.setMustFl(Utilities.ynToOneTwo(ifGoodsTextOption.getMustFl()));
            ifGoodsTextOption.setUploadStatus(StringFactory.getGbOne());
            jpaIfGoodsTextOptionRepository.save(ifGoodsTextOption);
        }
    }

    private void saveIfGoodsAddGoods(GoodsSearchData goodsSearchData){ //, List<IfGoodsAddGoods> addGoodsDataListOut) {
        List<GoodsSearchData.AddGoodsData> addGoodsDataList = goodsSearchData.getAddGoodsData();
//        System.out.println("addGoodsData length : " + addGoodsDataList.size());
        if(addGoodsDataList == null){
            log.debug("addGoodsDataList is null.");
            return;
        }
        for(GoodsSearchData.AddGoodsData addGoodsData : addGoodsDataList){ // goodsNoData 기준으로 if_goods_add_goods에 저장
            List<String> goodsNoData = addGoodsData.getGoodsNoData();
            if(goodsNoData == null){
                break;
            }
            for(String addGoods : goodsNoData){
                IfGoodsAddGoods ifGoodsAddGoods = objectMapper.convertValue(goodsSearchData, IfGoodsAddGoods.class);
                ifGoodsAddGoods.setAssortId(goodsSearchData.getAssortId());
                ifGoodsAddGoods.setAddGoodsNo(addGoods);
                ifGoodsAddGoods.setTitle(addGoodsData.getTitle());

                jpaIfGoodsAddGoodsRepository.save(ifGoodsAddGoods);
            }
        }
    }
    
    // addGoods xml 받아오는 함수
    private AddGoodsData retrieveAddGoods(String goodsNo){
        //OpenApi호출
        String urlstr = goodsAddSearchUrl + "?" + StringFactory.getGoodsSearchParams()[0] + "=" +
                pKey + "&" +StringFactory.getGoodsSearchParams()[1]
                + "=" + key+"&"+goodsNo+"="+goodsNo;
        NodeList nodeList = CommonXmlParse.getXmlNodes(urlstr);
        List<AddGoodsData> addGoodsDataList = new ArrayList<>();

        List<Map<String, Object>> list = commonXmlParse.retrieveNodeMaps(StringFactory.getStrGoodsData(), nodeList, goodsSearchGotListPropsMap);

        //
        for(Map<String, Object> item : list){
            AddGoodsData adData = objectMapper.convertValue(item, AddGoodsData.class);
            addGoodsDataList.add(adData);
        }
        return addGoodsDataList.get(0);
    }

    // goods xml 받아오는 함수
    public List<GoodsSearchData> retrieveGoods(String goodsNo, String fromDt, String toDt) {
        //OpenApi호출
        String urlstr = goodsSearchUrl + StringFactory.getStrQuestion() + StringFactory.getGoodsSearchParams()[0] + StringFactory.getStrEqual() +
                pKey + StringFactory.getStrAnd() +StringFactory.getGoodsSearchParams()[1]
                + StringFactory.getStrEqual() + key
//                + StringFactory.getStrAnd() + StringFactory.getGoodsSearchParams()[3]
//                + StringFactory.getStrEqual()
                + "&goodsNo="+goodsNo;
//        System.out.println("##### " + urlstr);
        NodeList nodeList =  CommonXmlParse.getXmlNodes(urlstr);
        List<GoodsSearchData> goodsSearchData = new ArrayList<>();
        List<Map<String, Object>> list = commonXmlParse.retrieveNodeMaps(StringFactory.getStrGoodsData(), nodeList, goodsSearchGotListPropsMap);

        for(Map<String, Object> item : list){
            GoodsSearchData gsData = objectMapper.convertValue(item, GoodsSearchData.class);
            goodsSearchData.add(gsData);
        }
        return goodsSearchData;
    }
}
