package io.spring.main.apis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.spring.main.enums.GbOneOrTwo;
import io.spring.main.jparepos.category.JpaIfCategoryRepository;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.JpaIfAddGoodsRepository;
import io.spring.main.jparepos.goods.JpaIfBrandRepository;
import io.spring.main.jparepos.goods.JpaIfGoodsAddGoodsRepository;
import io.spring.main.jparepos.goods.JpaIfGoodsMasterRepository;
import io.spring.main.jparepos.goods.JpaIfGoodsOptionRepository;
import io.spring.main.jparepos.goods.JpaIfGoodsTextOptionRepository;
import io.spring.main.jparepos.goods.JpaItadgsRepository;
import io.spring.main.jparepos.goods.JpaItasrdRepository;
import io.spring.main.jparepos.goods.JpaItasrnRepository;
import io.spring.main.jparepos.goods.JpaItasrtRepository;
import io.spring.main.jparepos.goods.JpaItitmmRepository;
import io.spring.main.jparepos.goods.JpaItlkagRepository;
import io.spring.main.jparepos.goods.JpaItmmotRepository;
import io.spring.main.jparepos.goods.JpaItvariRepository;
import io.spring.main.mapper.GoodsMapper;
import io.spring.main.model.goods.AddGoodsData;
import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfAddGoods;
import io.spring.main.model.goods.entity.IfBrand;
import io.spring.main.model.goods.entity.IfCategory;
import io.spring.main.model.goods.entity.IfGoodsAddGoods;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import io.spring.main.model.goods.entity.IfGoodsOption;
import io.spring.main.model.goods.entity.IfGoodsTextOption;
import io.spring.main.model.goods.entity.Itadgs;
import io.spring.main.model.goods.entity.Itasrd;
import io.spring.main.model.goods.entity.Itasrn;
import io.spring.main.model.goods.entity.Itasrt;
import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.goods.entity.Itlkag;
import io.spring.main.model.goods.entity.Itmmot;
import io.spring.main.model.goods.entity.Itvari;
import io.spring.main.model.goods.idclass.ItvariId;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:godourl.yml")
public class GoodsSearch {
    private final JpaIfGoodsMasterRepository jpaIfGoodsMasterRepository;
    private final JpaIfGoodsOptionRepository jpaIfGoodsOptionRepository;
    private final JpaIfGoodsTextOptionRepository jpaIfGoodsTextOptionRepository;
    private final JpaIfAddGoodsRepository jpaIfAddGoodsRepository;
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
    private final GoodsMapper goodsMapper;

    private final List<String> goodsSearchGotListPropsMap;


    // 고도몰 관련 값들
    @Value("${pKey}")
    private String pKey;
    @Value("${key}")
    private String key;
    @Value("${url.godo.goodsSearch}")
    private String goodsSearchUrl;
    @Value("${url.godo.goodsAddSearch}")
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

    @Transactional
    public void saveIfTables(String goodsNo, String fromDt, String toDt, String page){ //, List<IfGoodsOption> ifGoodsOptionList, List<IfGoodsTextOption> ifGoodsTextOptionList, List<IfGoodsAddGoods> ifGoodsAddGoodsList){
        if(page == null){
            page = "";
        }
        if(goodsNo == null){
            goodsNo = "";
        }

		System.out.println("start page ==> " + page);


		
        List<GoodsSearchData> goodsSearchDataList = this.retrieveGoods(goodsNo, fromDt, toDt, page); // test용 goodsNo : 1000040120
//        String assortId = "";

        // 1. if table 저장
        for(GoodsSearchData goodsSearchData : goodsSearchDataList){

			System.out.println("goodsNo ==> " + goodsSearchData.getGoodsNo());

			// System.out.println("goodsSearchData ==> " + goodsSearchData);

            // goodsDescription에 너무 긴 애가 들어있는 애 거르기
            String goodsDescription = goodsSearchData.getGoodsDescription();
            if(goodsDescription.split(StringFactory.getStrDataImage()).length >= 2){
                log.debug("goodsDescription is too long. goodsNo :" + goodsSearchData.getGoodsNo());
                continue;
            }
            // goodsNo가 겹치는 애가 있는지 확인
			IfGoodsMaster ifGoodsMaster_old = jpaIfGoodsMasterRepository
					.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo()));

			if (ifGoodsMaster_old != null) {
				goodsSearchData.setAssortId(ifGoodsMaster_old.getAssortId());
			}

//            if(ifGoodsMaster == null){ // insert
            this.saveIfGoodsMaster(goodsSearchData); // if_goods_master : itasrt, itasrn, itasrd  * 여기서 assortId 생성
            this.saveIfGoodsTextOption(goodsSearchData); // if_goods_text_option : itmmot
            this.saveIfGoodsAddGoods(goodsSearchData); // if_goods_add_goods : itlkag, itadgs
//            }

//            if(jpaIfGoodsOptionRepository.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo())).size() == 0){
                this.saveIfGoodsOption(goodsSearchData); // if_goods_option : itvari, ititmm
//            }
        }
		System.out.println("end page ==> " + page);
    }

    private IfGoodsMaster saveIfGoodsMaster(GoodsSearchData goodsSearchData) {
        boolean isUpdate = false;
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
        IfGoodsMaster origIfGoodsMaster = jpaIfGoodsMasterRepository.findByChannelGbAndGoodsNo(StringFactory.getGbOne(), Long.toString(goodsSearchData.getGoodsNo()));
        IfGoodsMaster ifGoodsMaster = null;

        ifGoodsMaster = objectMapper.convertValue(goodsSearchData, IfGoodsMaster.class);//origIfGoodsMaster.clone();//new IfGoodsMaster(origIfGoodsMaster);
        if(origIfGoodsMaster != null){ // update
            ifGoodsMaster.setRegDt(origIfGoodsMaster.getRegDt());
            isUpdate = true;
        }
        ifGoodsMaster.setAssortId(assortId); // assort_id 설정
        ifGoodsMaster.setChannelGb(StringFactory.getGbOne()); // 01 하드코딩

        // 이미지 데이터 list 형태로 돼있음 -> string property에 set해주기
        ifGoodsMaster.setMainImageData(goodsSearchData.getMainImageData() != null? goodsSearchData.getMainImageData().get(0):null);
        ifGoodsMaster.setListImageData(goodsSearchData.getListImageData() != null? goodsSearchData.getListImageData().get(0):null);
        ifGoodsMaster.setDetailImageData(goodsSearchData.getDetailImageData() != null? goodsSearchData.getDetailImageData().get(0):null);
        ifGoodsMaster.setMagnifyImageData(goodsSearchData.getMagnifyImageData() != null? goodsSearchData.getMagnifyImageData().get(0):null);
        // 고도몰에선 string인데 우리 db에선 01, 02인 애들을 01, 02로 바꾸기
        ifGoodsMaster.setGoodsSellFl(GbOneOrTwo.valueOf(goodsSearchData.getGoodsSellFl()).getFieldName());//Utilities.ynToOneTwo(ifGoodsMaster.getGoodsSellFl()));
        ifGoodsMaster.setGoodsDisplayFl(GbOneOrTwo.valueOf(goodsSearchData.getGoodsDisplayFl()).getFieldName());//Utilities.ynToOneTwo(ifGoodsMaster.getGoodsDisplayFl()));
        ifGoodsMaster.setOptionFl(GbOneOrTwo.valueOf(goodsSearchData.getOptionFl()).getFieldName());//(Utilities.ynToOneTwo(ifGoodsMaster.getOptionFl()));
        ifGoodsMaster.setSizeType(GbOneOrTwo.valueOf(goodsSearchData.getSizeType()).getFieldName());//(Utilities.ynToOneTwo(ifGoodsMaster.getSizeType()));
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
        
        boolean isChanged = false;

        if(isUpdate){ // update인 경우 기존 값과 비교
            isChanged = !ifGoodsMaster.equals(origIfGoodsMaster);
			// todo : 변경 체크 안되는듯 -> 수정 완료
//			isChanged = true;
        }


        if(!isUpdate || (isUpdate && isChanged)){ // insert인 경우, update고 값이 변한 경우
        	
			ifGoodsMaster.setUploadStatus("01");
        	
            jpaIfGoodsMasterRepository.save(ifGoodsMaster);
        }
//        log.debug("----- cateCd : " + ifGoodsMaster.getCateCd());
        return ifGoodsMaster;
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
            ifGoodsTextOption.setMustFl(GbOneOrTwo.valueOf(ifGoodsTextOption.getMustFl()).getFieldName());//(Utilities.ynToOneTwo(ifGoodsTextOption.getMustFl()));
            ifGoodsTextOption.setUploadStatus(StringFactory.getGbOne());
            jpaIfGoodsTextOptionRepository.save(ifGoodsTextOption);
        }
    }

    /**
     *  ifGoodsAddGoods와 ifAddGoods 생성
     */
    private void saveIfGoodsAddGoods(GoodsSearchData goodsSearchData){ //, List<IfGoodsAddGoods> addGoodsDataListOut) {
        List<GoodsSearchData.AddGoodsData> addGoodsDataList = goodsSearchData.getAddGoodsData();
//        System.out.println("addGoodsData length : " + addGoodsDataList.size());
        if(addGoodsDataList == null){
            log.debug("addGoodsDataList is null.");
            return;
        }
        // if_goods_add_goods 저장
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
        // if_add_goods 저장
        for(GoodsSearchData.AddGoodsData addGoodsData : addGoodsDataList){ // goodsNoData 기준으로 if_goods_add_goods에 저장
            List<String> goodsNoData = addGoodsData.getGoodsNoData();
            if(goodsNoData == null){
                break;
            }
            for(String addGoods : goodsNoData){
                IfAddGoods ifAddGoods = jpaIfAddGoodsRepository.findByAddGoodsNo(addGoods);
                if(ifAddGoods == null){ // insert
                    // add_goods_id 채번
                    String addGoodsId = this.getAddGoodsId('A', jpaIfAddGoodsRepository.findMaxAddGoodsId(), 9);
                    ifAddGoods = objectMapper.convertValue(goodsSearchData, IfAddGoods.class);
                    ifAddGoods.setAddGoodsId(addGoodsId);
                }
                ifAddGoods.setAddGoodsNo(addGoods);
                ifAddGoods.setTitle(addGoodsData.getTitle());

                jpaIfAddGoodsRepository.save(ifAddGoods);
            }
        }
    }

    private void saveIfGoodsOption(GoodsSearchData goodsSearchData){ //, List<IfGoodsOption> ifGoodsOptionList) {
        List<GoodsSearchData.OptionData> optionDataList = goodsSearchData.getOptionData();
        if(optionDataList == null){
            log.debug("optionDataList is null.");
        }
        else{

			List<IfGoodsOption> n = new ArrayList<IfGoodsOption>();
            for(GoodsSearchData.OptionData optionData : optionDataList){
                IfGoodsOption ifGoodsOption = objectMapper.convertValue(optionData,IfGoodsOption.class);
                ifGoodsOption.setAssortId(goodsSearchData.getAssortId());
                ifGoodsOption.setUploadStatus(StringFactory.getGbOne());
                ifGoodsOption.setOptionName(goodsSearchData.getOptionName());
               // jpaIfGoodsOptionRepository.save(ifGoodsOption);

				n.add(ifGoodsOption);
			}
            
			List<IfGoodsOption> l = jpaIfGoodsOptionRepository.findByGoodsNo(goodsSearchData.getGoodsNo().toString());


			// 추가

			// 삭제

			List<IfGoodsOption> optionDeleteList = new ArrayList<IfGoodsOption>();

			for (IfGoodsOption o : l) {
				Boolean delYn = true;

				for (IfGoodsOption o1 : n) {

					if (o.getChannelGb().equals(o1.getChannelGb()) && o.getGoodsNo().equals(o1.getGoodsNo())
							&& o.getSno().equals(o1.getSno())) {
						delYn = false;
						break;
					}

				}

				if (delYn == true) {
					optionDeleteList.add(o);
				}
			}

			for (IfGoodsOption o : optionDeleteList) {
				
				jpaIfGoodsOptionRepository.delete(o);
			}
			
			for (IfGoodsOption o : n) {
				
				;
				jpaIfGoodsOptionRepository.save(o);
				
				
				
			}
            
        }
    }


    public List<IfGoodsMaster> getIfGoodsMasterListWhereUploadStatus01(){
        List<IfGoodsMaster> ifGoodsMasterList = jpaIfGoodsMasterRepository.findByUploadStatus(StringFactory.getGbOne()); // if_goods_master에서 upload_status가 01인 애 전부 가져옴
        return ifGoodsMasterList;
    }

    @Transactional
    public IfGoodsMaster saveOneGoodsNo(String goodsNo, IfGoodsMaster ifGoodsMaster) {

		System.out.println("saveOneGoodsNo");
		System.out.println("goodsNo ==> " + goodsNo);

        // 2. itasrt, itasrn, itasrd (from if_goods_master) 저장
        // itadgs (from if_goods_add_goods) 저장
        Itasrt itasrt = this.saveItasrt(ifGoodsMaster); // itasrt
        this.saveItasrn(ifGoodsMaster); // itasrn
        this.saveItasrd(ifGoodsMaster); // itasrd
        // itadgs

        // 3. if_goods_master 테이블 updateStatus 02로 업데이트
        ifGoodsMaster.setUploadStatus(StringFactory.getGbTwo()); // 02 하드코딩
        jpaIfGoodsMasterRepository.save(ifGoodsMaster);


        // 4. itvari (from if_goods_option) 저장
        List<IfGoodsOption> ifGoodsOptionList = jpaIfGoodsOptionRepository.findByGoodsNo(goodsNo);
        
        if(ifGoodsOptionList == null || ifGoodsOptionList.size() == 0){
            Itvari itvari = this.saveSingleItvari(itasrt.getAssortId());
            this.saveSingleItitmm(itasrt, itvari);
        }
        else{
            for(IfGoodsOption ifGoodsOption : ifGoodsOptionList){

                this.saveItvari(ifGoodsOption); // itvari
            }
        }

        // 5. ititmm (from if_goods_option) 저장
        for (IfGoodsOption ifGoodsOption : ifGoodsOptionList) {
            this.saveItitmm(ifGoodsOption, ifGoodsMaster); // ititmm
        }

        //ititmm에 는 있는데 if_goods_option 에 없는것 삭제
		List<Ititmm> listItitmm = jpaItitmmRepository.findByAssortId(ifGoodsMaster.getAssortId());

		List<Ititmm> deletedListItitmm = new ArrayList<Ititmm>();

		List<HashMap<String, Object>> matchedListItitmm = new ArrayList<HashMap<String, Object>>();



		for (Ititmm o11 : listItitmm) {
			Boolean delYn = true;

			for (IfGoodsOption o12 : ifGoodsOptionList) {

				String o11Val1 = "";
				String o11Val2 = "";
				
				if(o11.getVariationSeq1()!=null && !o11.getVariationSeq1().equals("")) {
					ItvariId iv = new ItvariId(ifGoodsMaster.getAssortId(),o11.getVariationSeq1());	
					Itvari itvari1 =  jpaItvariRepository.findById(iv).orElse(null);
					
					o11Val1 = itvari1.getOptionNm();
				}else {
					o11Val1 = "";
				}

				if (o11.getVariationSeq2() != null && !o11.getVariationSeq2().equals("")) {
					ItvariId iv = new ItvariId(ifGoodsMaster.getAssortId(), o11.getVariationSeq2());
					Itvari itvari2 = jpaItvariRepository.findById(iv).orElse(null);

					o11Val2 = itvari2.getOptionNm();
				} else {
					o11Val2 = "";
				}

				/*
				 * 
				 * if (o11.getItvari1() == null) { o11Val1 = ""; } else { o11Val1 =
				 * o11.getItvari1().getOptionNm() == null ? "" : o11.getItvari1().getOptionNm();
				 * 
				 * }
				 * 
				 * 
				 * 
				 * if (o11.getItvari2() == null) { o11Val2 = ""; } else { o11Val2 =
				 * o11.getItvari2().getOptionNm() == null ? "" : o11.getItvari2().getOptionNm();
				 * 
				 * }
				 */
				String o12Val1 = o12.getOptionValue1() == null ? "" : o12.getOptionValue1();
				String o12Val2 = o12.getOptionValue2() == null ? "" : o12.getOptionValue2();
				
				

				if (o11Val1.equals(o12Val1) && o11Val2.equals(o12Val2)) {

					HashMap<String, Object> m = new HashMap<String, Object>();

					m.put("assortId", ifGoodsMaster.getAssortId());
					m.put("itemId", o11.getItemId());
					m.put("goodsNo", ifGoodsMaster.getGoodsNo());
					m.put("sno", o12.getSno());
					delYn = false;

					matchedListItitmm.add(m);

					break;
				}
			}

			if (delYn == true && ifGoodsOptionList.size() > 0) {
				deletedListItitmm.add(o11);
			}

		}
        
		System.out.println("------************deletedListItitmm*****************");
		System.out.println(deletedListItitmm.size());
		System.out.println(deletedListItitmm);

		System.out.println("------************deletedListItitmm*****************");

        
		System.out.println("------************matchedListItitmm*****************");
		System.out.println(matchedListItitmm.size());
		System.out.println(matchedListItitmm);

		System.out.println("------************matchedListItitmm*****************");

		for (Ititmm o : deletedListItitmm) {
			jpaItitmmRepository.delete(o);
		}

        // 6. if_goods_option 테이블 updateStatus 02로 업데이트
        for(IfGoodsOption ifGoodsOption : ifGoodsOptionList){

			for (HashMap<String, Object> m : matchedListItitmm) {
				if (ifGoodsOption.getGoodsNo().equals(m.get("goodsNo").toString())
						&& ifGoodsOption.getSno().equals(m.get("sno").toString())) {
					ifGoodsOption.setAssortId(m.get("assortId").toString());
					ifGoodsOption.setItemId(m.get("itemId").toString());
				}
			}

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

        // 9. itadgs (from if_goods_add_goods) 저장
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            IfAddGoods ifAddGoods = jpaIfAddGoodsRepository.findByAddGoodsNo(ifGoodsAddGoods.getAddGoodsNo());
            ifGoodsAddGoods.setAddGoodsId(ifAddGoods.getAddGoodsId());
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡ addGoodsId : " + ifGoodsAddGoods.getAddGoodsId() + ", assortId : " + ifGoodsAddGoods.getAssortId());
            //OpenApi호출
            AddGoodsData addGoodsData = this.retrieveAddGoods(ifGoodsAddGoods.getGoodsNo());

            ifGoodsAddGoods.setGoodsNm(addGoodsData.getGoodsNm());
            ifGoodsAddGoods.setOptionNm(addGoodsData.getOptionNm());
            ifGoodsAddGoods.setBrandCd(addGoodsData.getBrandCd());
            ifGoodsAddGoods.setMakerNm(addGoodsData.getMakerNm());
            ifGoodsAddGoods.setGoodsPrice(addGoodsData.getGoodsPrice());
            ifGoodsAddGoods.setStockCnt(addGoodsData.getStockCnt());
            ifGoodsAddGoods.setViewFl(GbOneOrTwo.valueOf(addGoodsData.getViewFl()).getFieldName());//(Utilities.ynToOneTwo(addGoodsData.getViewFl()));
            ifGoodsAddGoods.setUploadStatus(StringFactory.getGbOne());
            ifGoodsAddGoods.setSoldOutFl(GbOneOrTwo.valueOf(addGoodsData.getSoldOutFl()).getFieldName());//(Utilities.ynToOneTwo(addGoodsData.getSoldOutFl()));

            // 21-10-07 추가
            ifAddGoods.setGoodsNm(ifGoodsAddGoods.getGoodsNm());
            ifAddGoods.setOptionNm(ifGoodsAddGoods.getOptionNm());
            ifAddGoods.setBrandCd(ifGoodsAddGoods.getBrandCd());
            ifAddGoods.setMakerNm(ifGoodsAddGoods.getMakerNm());
            ifAddGoods.setGoodsPrice(ifGoodsAddGoods.getGoodsPrice());
            ifAddGoods.setStockCnt(ifGoodsAddGoods.getStockCnt());
            ifAddGoods.setViewFl(ifGoodsAddGoods.getViewFl());
            ifAddGoods.setSoldOutFl(ifGoodsAddGoods.getSoldOutFl());

            Itadgs itadgs = jpaItadgsRepository.findByAddGoodsId(ifAddGoods.getAddGoodsId());
            if(itadgs == null){ // insert
                itadgs = new Itadgs(ifGoodsMaster, ifGoodsAddGoods);
            }
            else { // update
                itadgs.setLocalSale(ifAddGoods.getGoodsPrice());
                itadgs.setShortYn(ifAddGoods.getSoldOutFl());
                itadgs.setOptionNm(ifAddGoods.getOptionNm());
                itadgs.setAddGoodsState(ifAddGoods.getViewFl());
                itadgs.setBrandId(ifAddGoods.getBrandCd());
                itadgs.setMakerNm(ifAddGoods.getMakerNm());
                itadgs.setStockCnt(ifAddGoods.getStockCnt());
                itadgs.setImageUrl(ifGoodsMaster.getMainImageData());
            }
            jpaItadgsRepository.save(itadgs);
            // 21-10-06 addGoods도 똑같이 itasrt에 들어가기로 함
            Itasrt addGoodsItasrt = new Itasrt(itadgs);
            jpaItasrtRepository.save(addGoodsItasrt);
//            jpaIfGoodsAddGoodsRepository.save(addGoodsData);
        }

        // 10. itlkag (from if_goods_add_goods) 저장
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            Itlkag itlkag = jpaItlkagRepository.findByAssortIdAndAddGoodsIdAndEffEndDt(ifGoodsAddGoods.getAssortId(), ifGoodsAddGoods.getAddGoodsId(), Utilities.getStringToDate(StringFactory.getDoomDay()));
            if(itlkag == null){ // insert
                itlkag = new Itlkag(ifGoodsAddGoods);
                jpaItlkagRepository.save(itlkag);
            }
//            else { // update
//                itlkag.setEffEndDt(new Date());
//                Itlkag newItlkag = new Itlkag(itlkag);
//                jpaItlkagRepository.save(newItlkag);
//            }

            // if_goods_add_goods에도 add_goods_id 저장 (아직 save는 노노.. 이따가 uploadStatus 저장할 때 같이)
        }

        // 11. if_goods_add_goods 테이블에 add_goods_id 삽입하고 updateStatus 02로 업데이트
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            StringFactory.getGbTwo(); // 02 하드코딩
            jpaIfGoodsAddGoodsRepository.save(ifGoodsAddGoods);
        }

        return ifGoodsMaster;
    }

    // itasrn 저장 함수
    private Itasrn saveItasrn(IfGoodsMaster ifGoodsMaster){
        Date effEndDt = null;
        effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
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
        itasrdLong.setMemo(ifGoodsMaster.getGoodsDescription().replace("\\\"", "\"").replace("\\\'", "\'")); // 0002
        itasrdLong.setOrdDetCd(StringFactory.getGbOne()); // 01 : 상세, 02 : 간략
        itasrdLong.setTextHtmlGb(StringFactory.getGbOne()); // 01 : html, 02 : text
        jpaItasrdRepository.save(itasrdLong);
    }

    private Itvari saveSingleItvari(String assortId) {
        // option이 없는 경우. seq 0001, 옵션구분 01, variation구분 01, 옵션명 '단품'
        Itvari itvari = new Itvari(assortId);
        jpaItvariRepository.save(itvari);
        return itvari;
    }
    private void saveSingleItitmm(Itasrt itasrt, Itvari itvari) {
        // option이 없는 경우. seq 0001, 옵션구분 01, variation구분 01, 옵션명 '단품'
        Ititmm ititmm = new Ititmm(itasrt, itvari);
        jpaItitmmRepository.save(ititmm);
    }

    private void saveItvari(IfGoodsOption ifGoodsOption) { // 01 색깔, 02 사이즈 저장

        Itvari itvariColor = new Itvari(ifGoodsOption);
        // 옵션 01 : 색깔 저장
        itvariColor.setOptionGb(StringFactory.getGbOne()); // 01 하드코딩
        itvariColor.setVariationGb(StringFactory.getGbOne()); // 01 하드코딩
        itvariColor.setOptionNm(ifGoodsOption.getOptionValue1());
        String seq = "";
        System.out.println("--------------------------- " + ifGoodsOption.getAssortId());
        List<Itvari> itvariList0 = jpaItvariRepository.findByAssortId(ifGoodsOption.getAssortId());
        Itvari itvariList = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(),itvariColor.getOptionGb(), itvariColor.getOptionNm());
        if(itvariList == null){
            seq = this.getSeq(jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId()),4);
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
            seq = this.getSeq(jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId()),4);
        }

        if (jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(),
                itvariSize.getOptionGb(), itvariSize.getOptionNm()) == null) {
            itvariSize.setSeq(seq);
            jpaItvariRepository.save(itvariSize);
        }
    }

    private void saveItitmm(IfGoodsOption ifGoodsOption, IfGoodsMaster ifGoodsMaster) {
        Ititmm ititmm = new Ititmm(ifGoodsOption);
        // op1이 없으면 단품으로 처리
        Itvari itvariOp1 = jpaItvariRepository.findByAssortIdAndOptionNm(ititmm.getAssortId(), ifGoodsOption.getOptionValue1()).get(0);
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
            Itvari itvariList = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ititmm.getAssortId(), StringFactory.getGbTwo(), ifGoodsOption.getOptionValue2());
            Itvari itvariOp2 = itvariList;
            ititmm.setVariationGb2(itvariOp2.getVariationGb());
            ititmm.setVariationSeq2(itvariOp2.getSeq());
        }

		Ititmm itm = null;
		if (itvariOp1 != null) {

			itm = jpaItitmmRepository.findByAssortIdAndVariationSeq1AndVariationSeq2(ititmm.getAssortId(),
					ititmm.getVariationSeq1(), ititmm.getVariationSeq2());

		}

		if (itm == null) {
			// itemId 채번
			String itemId = jpaItitmmRepository.findMaxItemIdByAssortId(ititmm.getAssortId());
			itemId = getSeq(itemId, 4);
			ifGoodsOption.setItemId(itemId);
			ititmm.setItemId(itemId);
			ititmm.setItemNm(ifGoodsMaster.getGoodsNm());
			jpaItitmmRepository.save(ititmm);
		}


    }


    private void saveItmmot(IfGoodsTextOption ifGoodsTextOption) {
        Itmmot itmmot = new Itmmot(ifGoodsTextOption);
        // optionTextId 채번
        String optionTextId = getSeq(jpaItmmotRepository.findMaxSeqByAssortId(itmmot.getAssortId()),4);
        itmmot.setOptionTextId(optionTextId);
        jpaItmmotRepository.save(itmmot);
    }

	// TODO : 원커밋? 올커밋?
	@Transactional
	public void saveAllIfTables(int maxPage, String fromDt, String toDt) { // , List<IfGoodsOption> ifGoodsOptionList,
																// List<IfGoodsTextOption> ifGoodsTextOptionList,
																// List<IfGoodsAddGoods> ifGoodsAddGoodsList){

		List<GoodsSearchData> allGoodsSearchDataList = new ArrayList<GoodsSearchData>();

		for (int i = 1; i < maxPage + 1; i++) {
			List<GoodsSearchData> goodsSearchDataList = retrieveGoodsToPage(i, "", fromDt, toDt);

			allGoodsSearchDataList.addAll(goodsSearchDataList);

		}

//        String assortId = "";

		System.out.println("*------------------allgoods----------------------------------------");
		System.out.println(allGoodsSearchDataList.size());
		System.out.println("*----------------------------------------------------------");

		// 1. if table 저장
		for (GoodsSearchData goodsSearchData : allGoodsSearchDataList) {

			System.out.println(goodsSearchData.getGoodsNo());

            if(goodsSearchData == null){
                continue;
            }
			// goodsDescription에 너무 긴 애가 들어있는 애 거르기
			String goodsDescription = goodsSearchData.getGoodsDescription();
			if (goodsDescription.split(StringFactory.getStrDataImage()).length >= 2) {
				log.debug("goodsDescription is too long. goodsNo :" + goodsSearchData.getGoodsNo());
				continue;
			}
			// goodsNo가 겹치는 애가 있는지 확인
			if (jpaIfGoodsMasterRepository.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo())) == null) {
				this.saveIfGoodsMaster(goodsSearchData); // if_goods_master : itasrt, itasrn, itasrd * 여기서 assortId 생성
				this.saveIfGoodsTextOption(goodsSearchData); // if_goods_text_option : itmmot
				this.saveIfGoodsAddGoods(goodsSearchData); // if_goods_add_goods : itlkag, itadgs
			}
			if (jpaIfGoodsOptionRepository.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo())).size() == 0) {
				this.saveIfGoodsOption(goodsSearchData); // if_goods_option : itvari, ititmm
			}
		}
	}

    private Itasrt saveItasrt(IfGoodsMaster ifGoodsMaster) {
        Itasrt itasrt = new Itasrt(ifGoodsMaster); // itasrt
        jpaItasrtRepository.save(itasrt);
        return itasrt;
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

    // addGoodsId 등  문자가 붙은 채번 함수
    private String getAddGoodsId(char str, String nextVal, int length) {
        // nextVal이 null일 때 (첫번째 채번)
        if(nextVal == null)
        {
            nextVal = Utilities.getStringNo(str, StringFactory.getStrOne(), length); // A00000001 들어감
        }
        else{
            nextVal = Long.toString(Long.parseLong(nextVal.substring(1)) + 1);
            nextVal = Utilities.getStringNo(str, nextVal, length);
        }
        return nextVal;
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

	// TODO : 전체호출 ?? 은 어떻게? -> page로 
    // goods xml 받아오는 함수
    public List<GoodsSearchData> retrieveGoods(String goodsNo, String fromDt, String toDt, String page) {
        //OpenApi호출
        String urlstr = goodsSearchUrl + StringFactory.getStrQuestion() + StringFactory.getGoodsSearchParams()[0] + StringFactory.getStrEqual() +
                pKey + StringFactory.getStrAnd() +StringFactory.getGoodsSearchParams()[1]
                + StringFactory.getStrEqual() + key
//                + StringFactory.getStrAnd() + StringFactory.getGoodsSearchParams()[3]
//                + StringFactory.getStrEqual()
                + "&goodsNo="+goodsNo + "&page=" + page;
//        System.out.println("##### " + urlstr);
        NodeList nodeList =  CommonXmlParse.getXmlNodes(urlstr);
        List<GoodsSearchData> goodsSearchData = new ArrayList<>();

//		System.out.println(nodeList);

        List<Map<String, Object>> list = commonXmlParse.retrieveNodeMaps(StringFactory.getStrGoodsData(), nodeList, goodsSearchGotListPropsMap);

    	HashMap<String, Object> r = commonXmlParse.getPagination(nodeList);

        for(Map<String, Object> item : list){

            GoodsSearchData gsData = objectMapper.convertValue(item, GoodsSearchData.class);
			goodsSearchData.add(gsData);
        }
		return goodsSearchData;
    }

	// TODO : 전체호출 ?? 은 어떻게? -> page 이용
	// goods xml 받아오는 함수
	public List<GoodsSearchData> retrieveGoodsToPage(int page, String goodsNo, String fromDt, String toDt) {

		// OpenApi호출
		String urlstr = goodsSearchUrl + StringFactory.getStrQuestion() + StringFactory.getGoodsSearchParams()[0]
				+ StringFactory.getStrEqual() + pKey + StringFactory.getStrAnd()
				+ StringFactory.getGoodsSearchParams()[1] + StringFactory.getStrEqual() + key
//                + StringFactory.getStrAnd() + StringFactory.getGoodsSearchParams()[3]
//                + StringFactory.getStrEqual()
				+ "&goodsNo=" + goodsNo + "&page=" + String.valueOf(page);
//        System.out.println("##### " + urlstr);
		NodeList nodeList = CommonXmlParse.getXmlNodes(urlstr);
		List<GoodsSearchData> goodsSearchData = new ArrayList<>();

//		System.out.println(nodeList);

		List<Map<String, Object>> list = commonXmlParse.retrieveNodeMaps(StringFactory.getStrGoodsData(), nodeList,
				goodsSearchGotListPropsMap);

		HashMap<String, Object> r = commonXmlParse.getPagination(nodeList);

		for (Map<String, Object> item : list) {

			GoodsSearchData gsData = objectMapper.convertValue(item, GoodsSearchData.class);
			goodsSearchData.add(gsData);
		}
		return goodsSearchData;
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

    /**
     * tmitem, tmmapi 넣어주는 쿼리 실행
     */
    @Transactional
    public void insertTms(){
        goodsMapper.insertTmmapi();
        goodsMapper.updateTmitem();
        goodsMapper.insertTmitem();
    }
}
