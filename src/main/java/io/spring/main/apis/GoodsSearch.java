package io.spring.main.apis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.spring.main.enums.GbOneOrTwo;
import io.spring.main.interfaces.IfGoodsAddGoodsMapper;
import io.spring.main.interfaces.IfGoodsMasterMapper;
import io.spring.main.interfaces.IfGoodsOptionMapper;
import io.spring.main.interfaces.ItasrtMapper;
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
import io.spring.main.jparepos.goods.JpaTmitemRepository;
import io.spring.main.jparepos.goods.JpaTmmapiRepository;
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
import io.spring.main.model.goods.entity.Tmitem;
import io.spring.main.model.goods.entity.Tmmapi;
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

    // mapstruct mapper
    private final ItasrtMapper itasrtMapper;
    private final IfGoodsAddGoodsMapper ifGoodsAddGoodsMapper;
    private final IfGoodsMasterMapper ifGoodsMasterMapper;
    private final IfGoodsOptionMapper ifGoodsOptionMapper;

    private final List<String> goodsSearchGotListPropsMap;
    private final EntityManager em;

	private final JpaTmmapiRepository jpaTmmapiRepository;
	private final JpaTmitemRepository jpaTmitemRepository;



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
    public void saveIfTables(String goodsNo, String searchDateType, String dateParam, String page){ //, List<IfGoodsOption> ifGoodsOptionList, List<IfGoodsTextOption> ifGoodsTextOptionList, List<IfGoodsAddGoods> ifGoodsAddGoodsList){
        if(page == null){
            page = "";
        }
        int day = Integer.parseInt(dateParam); // 긁어올 날짜 단위(일)
        if(goodsNo == null){
            goodsNo = "";
        }

        String fromDt = "";
        String toDt = "";
        if(searchDateType == null || searchDateType.trim().equals("")){
            searchDateType = "";
        }
        else {
            fromDt = Utilities.getAnotherDate(null, StringFactory.getDateFormat(),Calendar.DATE, day * -1);
            toDt = Utilities.getAnotherDate(null, StringFactory.getDateFormat(),Calendar.DATE, 0);
        }

		System.out.println("start page ==> " + page);

        long start = System.currentTimeMillis();
        List<GoodsSearchData> goodsSearchDataList = this.retrieveGoods(goodsNo, searchDateType, fromDt, toDt, page); // test용 goodsNo : 1000040120
//        String assortId = "";
        log.debug("Step1, retrieveGoods : "+(System.currentTimeMillis()-start));
        // 1. if table 저장
        for(GoodsSearchData goodsSearchData : goodsSearchDataList){


			System.out.println("goodsNo ==> " + goodsSearchData.getGoodsNo());

			System.out.println("modDt ==> " + goodsSearchData.getModDt());

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
            start = System.currentTimeMillis();
            IfGoodsMaster igm = this.saveIfGoodsMaster(goodsSearchData); // if_goods_master : itasrt, itasrn, itasrd  * 여기서 assortId 생성
            log.debug("Step1, saveIfGoodsMaster : "+(System.currentTimeMillis()-start));


			// 마스터정보가 수정및 생성되는 건에 대해서만 데이타 처리를 함.
			if (igm != null) {
				start = System.currentTimeMillis();
				boolean isIfGoodsTextOptionChanged = this.saveIfGoodsTextOption(goodsSearchData); // if_goods_text_option
																									// : itmmot
				log.debug("Step1, saveIfGoodsTextOption : " + (System.currentTimeMillis() - start));
				start = System.currentTimeMillis();
				this.saveIfGoodsAddGoods(goodsSearchData); // if_goods_add_goods : itlkag, itadgs
				log.debug("Step1, saveIfGoodsAddGoods : " + (System.currentTimeMillis() - start));
//                }

//                if(jpaIfGoodsOptionRepository.findByGoodsNo(Long.toString(goodsSearchData.getGoodsNo())).size() == 0){
				start = System.currentTimeMillis();
				boolean isIfGoodsOptionChanged = this.saveIfGoodsOption(goodsSearchData); // if_goods_option : itvari,
																							// ititmm
				log.debug("Step1, saveIfGoodsOption : " + (System.currentTimeMillis() - start));

				// 옵션만 따로 수정되는 경우는 없다고 생각하여 마스터를 기준으로 수정하도록함.

//				if (isIfGoodsTextOptionChanged || isIfGoodsOptionChanged) {
				// igm.setUploadStatus(StringFactory.getGbOne()); // 01 하드코딩
				// jpaIfGoodsMasterRepository.save(igm);
				// }

            }

			// ---igm

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

		Boolean eqCheck = false;

		String godoModDt = null;

		Date modDt1 = null;
		Date oriModDt1 = null;
		
		// String oriModDt = "9999-12-31 23:59:59";
		if (origIfGoodsMaster != null) {
			oriModDt1 = origIfGoodsMaster.getModDt() == null ? null : origIfGoodsMaster.getModDt();
		}

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//		Date from = new Date();

//		String to = transFormat.format(from);

		Date mod1 = null;
		Date mod2 = null;

		try {
			mod1 = sdf2.parse(goodsSearchData.getModDt());
			// Date mod2 = sdf2.parse(origIfGoodsMaster.getModDt().toString());
			if (origIfGoodsMaster == null) {
				mod2 = null;
			} else {
				mod2 = origIfGoodsMaster.getModDt();
			}

			if (mod1 != null && mod2 != null) {

				if (mod1.equals(mod2)) {
					System.out.println(mod1.toString() + ":" + mod2.toString());
					eqCheck = true;
				}

				System.out.println(mod1.toString() + ":" + mod2.toString() + ":" + eqCheck);

			} else {
				System.out.println("null 있음:" + eqCheck);
			}

			// godoModDt = modDt1.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// if (oriModDt1.equals(modDt1)) {
		// System.out.println(oriModDt1.toString() + ":" + modDt1.toString());
		//// eqCheck = true;
		// }
//


		


		// System.out.println(oriModDt1.toString() + ":" + modDt1.toString() + ":" +
		// eqCheck);

		if (eqCheck == false) {

			System.out.println("-----------------------------이미처리한데이타-----------------------------");

			ifGoodsMaster = ifGoodsMasterMapper.to(goodsSearchData);// objectMapper.convertValue(goodsSearchData,
																	// IfGoodsMaster.class);//origIfGoodsMaster.clone();//new
																	// IfGoodsMaster(origIfGoodsMaster);
			if (origIfGoodsMaster != null) { // update
				ifGoodsMaster.setRegDt(origIfGoodsMaster.getRegDt());
				isUpdate = true;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date modDt = sdf.parse(goodsSearchData.getModDt().toString());
				ifGoodsMaster.setModDt(modDt);
			} catch (Exception e) {
				e.printStackTrace();
			}


			ifGoodsMaster.setAssortId(assortId); // assort_id 설정
			ifGoodsMaster.setChannelGb(StringFactory.getGbOne()); // 01 하드코딩

			// 이미지 데이터 list 형태로 돼있음 -> string property에 set해주기
			ifGoodsMaster.setMainImageData(
					goodsSearchData.getMainImageData() != null ? goodsSearchData.getMainImageData().get(0) : null);
			ifGoodsMaster.setListImageData(
					goodsSearchData.getListImageData() != null ? goodsSearchData.getListImageData().get(0) : null);
			ifGoodsMaster.setDetailImageData(
					goodsSearchData.getDetailImageData() != null ? goodsSearchData.getDetailImageData().get(0) : null);
			ifGoodsMaster.setMagnifyImageData(
					goodsSearchData.getMagnifyImageData() != null ? goodsSearchData.getMagnifyImageData().get(0)
							: null);
			// 고도몰에선 string인데 우리 db에선 01, 02인 애들을 01, 02로 바꾸기
			ifGoodsMaster.setGoodsSellFl(GbOneOrTwo.valueOf(goodsSearchData.getGoodsSellFl()).getFieldName());// Utilities.ynToOneTwo(ifGoodsMaster.getGoodsSellFl()));
			ifGoodsMaster.setGoodsDisplayFl(GbOneOrTwo.valueOf(goodsSearchData.getGoodsDisplayFl()).getFieldName());// Utilities.ynToOneTwo(ifGoodsMaster.getGoodsDisplayFl()));
			ifGoodsMaster.setOptionFl(GbOneOrTwo.valueOf(goodsSearchData.getOptionFl()).getFieldName());// (Utilities.ynToOneTwo(ifGoodsMaster.getOptionFl()));
			ifGoodsMaster.setSizeType(GbOneOrTwo.valueOf(goodsSearchData.getSizeType()).getFieldName());// (Utilities.ynToOneTwo(ifGoodsMaster.getSizeType()));
			// 매핑 테이블 이용해 고도몰 코드를 백오피스 코드로 전환 (brandCd, cateCd)
			IfBrand ifBrand = jpaIfBrandRepository.findByChannelGbAndChannelBrandId(StringFactory.getGbOne(),
					ifGoodsMaster.getBrandCd()); // 채널구분 01 하드코딩
			if (ifBrand != null) {
				ifGoodsMaster.setBrandCd(ifBrand.getBrandId());
			}
			IfCategory ifCategory = jpaIfCategoryRepository
					.findByChannelGbAndChannelCategoryId(StringFactory.getGbOne(), ifGoodsMaster.getCateCd()); // 채널구분
																												// 01
																												// 하드코딩
			if (ifCategory != null) {
				ifGoodsMaster.setCateCd(ifCategory.getCategoryId());
			} else {
				ifGoodsMaster.setCateCd("");
			}

			boolean isChanged = false;

			if (isUpdate) { // update인 경우 기존 값과 비교
				isChanged = !ifGoodsMaster.equals(origIfGoodsMaster);
				// todo(완) : 변경 체크 안되는듯 -> 21-10-12 수정 완료
//				isChanged = true;
			}


			if (!isUpdate || (isUpdate && isChanged)) { // insert인 경우, update고 값이 변한 경우

				ifGoodsMaster.setUploadStatus("01");

				jpaIfGoodsMasterRepository.save(ifGoodsMaster);
			}
			return ifGoodsMaster; // 처리가 되지않았던 건이라면 수정된데이타 리턴
		}

//        log.debug("----- cateCd : " + ifGoodsMaster.getCateCd());
		// return origIfGoodsMaster; // 처리가 되았던 건이라면 기존데이타 리턴
		return null;
    }

    private boolean saveIfGoodsTextOption(GoodsSearchData goodsSearchData){ //, List<IfGoodsTextOption> ifGoodsTextOptionList) {
        List<GoodsSearchData.TextOptionData> textOptionDataList = goodsSearchData.getTextOptionData();
        if(textOptionDataList == null){
            log.debug("textOptionDataList is null.");
            return false;
        }
        for(GoodsSearchData.TextOptionData textOptionData : textOptionDataList){
            IfGoodsTextOption ifGoodsTextOption = objectMapper.convertValue(textOptionData,IfGoodsTextOption.class);
            ifGoodsTextOption.setAssortId(goodsSearchData.getAssortId());
            ifGoodsTextOption.setChannelGb(StringFactory.getGbOne());
            // yn을 0102로
            ifGoodsTextOption.setMustFl(GbOneOrTwo.valueOf(ifGoodsTextOption.getMustFl()).getFieldName());//(Utilities.ynToOneTwo(ifGoodsTextOption.getMustFl()));
            if(!textOptionData.equals(ifGoodsTextOption)){
                return true;
            }
            ifGoodsTextOption.setUploadStatus(StringFactory.getGbOne());
            jpaIfGoodsTextOptionRepository.save(ifGoodsTextOption);
        }
        return false;
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
                IfGoodsAddGoods ifGoodsAddGoods = ifGoodsAddGoodsMapper.to(StringFactory.getGbOne(), goodsSearchData.getGoodsNo(), goodsSearchData); // 채널 01 하드코딩
                //objectMapper.convertValue(goodsSearchData, IfGoodsAddGoods.class);
                ifGoodsAddGoods.setAssortId(goodsSearchData.getAssortId());
                ifGoodsAddGoods.setAddGoodsNo(addGoods);
                ifGoodsAddGoods.setStockCnt(goodsSearchData.getStock());
                ifGoodsAddGoods.setViewFl(GbOneOrTwo.valueOf(goodsSearchData.getGoodsDisplayFl()).getFieldName());
                ifGoodsAddGoods.setSoldOutFl(GbOneOrTwo.valueOf(goodsSearchData.getSoldOutFl()).getFieldName());
                ifGoodsAddGoods.setTitle(addGoodsData.getTitle());

                jpaIfGoodsAddGoodsRepository.save(ifGoodsAddGoods);
            }
        }
        // if_add_goods 저장
		// todo: add_goods를 불르는 api로 주문시에 addGoods없는것 생성이 필요할듯 2021-10-12
        // 		
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

                List<IfGoodsAddGoods> ifGoodsAddGoodsList = jpaIfGoodsAddGoodsRepository.findByChannelGbAndAddGoodsNo(StringFactory.getGbOne(), ifAddGoods.getAddGoodsNo());
                for(IfGoodsAddGoods ig : ifGoodsAddGoodsList){
                    ig.setAddGoodsId(ifAddGoods.getAddGoodsId());
                    jpaIfGoodsAddGoodsRepository.save(ig);
                }
            }
        }
    }

    private boolean saveIfGoodsOption(GoodsSearchData goodsSearchData){ //, List<IfGoodsOption> ifGoodsOptionList) {

		System.out.println("===================================================================");

		System.out.println("saveIfGoodsOption");


		List<GoodsSearchData.OptionData> optionDataList1 = goodsSearchData.getOptionData();

		System.out.println("optionDataList length =>" + optionDataList1.size());

		if (optionDataList1.size() == 0) {
			System.out.println("===================================================================");
			System.out.println("optionDataList is null.");
			HashMap<String, Object> r = CommonXmlParse.getGoodsOptionData(goodsSearchData.getGoodsNo().toString());

			if (r.get("optionSno") == null) {
				log.debug("optionDataList value is null.");
			} else {
				GoodsSearchData.OptionData opt = new GoodsSearchData.OptionData();

				List<GoodsSearchData.OptionData> l = new ArrayList<GoodsSearchData.OptionData>();

				opt.setSno(Long.parseLong(r.get("optionSno").toString()));
				opt.setGoodsNo(goodsSearchData.getGoodsNo());
				opt.setOptionNo(1L);
				opt.setOptionValue1("단품");
				opt.setOptionViewFl("y");
				opt.setOptionSellFl("y");
				opt.setRegDt(goodsSearchData.getRegDt());
				opt.setModDt(new Date());


				l.add(opt);

				goodsSearchData.setOptionData(l);

			}

            log.debug("optionDataList is null.");
			log.debug("make single optionData");
//            return false;
        }

		List<GoodsSearchData.OptionData> optionDataList = goodsSearchData.getOptionData();

        List<IfGoodsOption> n = new ArrayList<IfGoodsOption>();
        for(GoodsSearchData.OptionData optionData : optionDataList){
            IfGoodsOption ifGoodsOption = ifGoodsOptionMapper.to(optionData, goodsSearchData);//objectMapper.convertValue(optionData,IfGoodsOption.class);
//            ifGoodsOption.setAssortId(goodsSearchData.getAssortId());
//            ifGoodsOption.setUploadStatus(StringFactory.getGbOne());
//            ifGoodsOption.setOptionName(goodsSearchData.getOptionName());
           // jpaIfGoodsOptionRepository.save(ifGoodsOption);
            n.add(ifGoodsOption);
        }
        List<IfGoodsOption> l = jpaIfGoodsOptionRepository.findByGoodsNo(goodsSearchData.getGoodsNo().toString());
        // 추가
        // 삭제
        List<IfGoodsOption> optionDeleteList = new ArrayList<IfGoodsOption>();
        boolean isDeleted = false;
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
                isDeleted = true;
                optionDeleteList.add(o);
            }
        }

        for (IfGoodsOption o : optionDeleteList) {
            jpaIfGoodsOptionRepository.delete(o);
        }

        for (IfGoodsOption o : n) {
            jpaIfGoodsOptionRepository.save(o);
        }
        if(isDeleted){
            return true;
        }
        return false;
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
        long start = System.currentTimeMillis();
        Itasrt itasrt = this.saveItasrt(ifGoodsMaster); // itasrt
        log.debug("Step2, saveItasrt : " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        this.saveItasrd(ifGoodsMaster); // itasrd
        log.debug("Step2, saveItasrd : " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        this.saveItasrn(ifGoodsMaster); // itasrn
        log.debug("Step2, saveItasrn : " + (System.currentTimeMillis()-start));

        // 3. if_goods_master 테이블 updateStatus 02로 업데이트
//        ifGoodsMaster.setUploadStatus(StringFactory.getGbTwo()); // 02 하드코딩
		ifGoodsMaster.setUploadStatus(StringFactory.getGbFour()); // 02 하드코딩

        jpaIfGoodsMasterRepository.save(ifGoodsMaster);


        // 4. itvari (from if_goods_option) 저장
        List<IfGoodsOption> ifGoodsOptionList = jpaIfGoodsOptionRepository.findByGoodsNo(goodsNo);

        String asdf = "";
        start = System.currentTimeMillis();

		String singleItitmmSeq = "";

        if(ifGoodsOptionList == null || ifGoodsOptionList.size() == 0){
            Itvari itvari = this.saveSingleItvari(itasrt.getAssortId());
			singleItitmmSeq = this.saveSingleItitmm(itasrt, itvari);
            asdf = "saveSingleItitmm";

			System.out.println(singleItitmmSeq);

        }
        else{
            for(IfGoodsOption ifGoodsOption : ifGoodsOptionList){

				this.saveItvari2(ifGoodsOption); // itvari
            }
            asdf = "saveItvari";
        }
        log.debug("Step2, "+asdf+" : " + (System.currentTimeMillis()-start));

        // 5. ititmm (from if_goods_option) 저장
        start = System.currentTimeMillis();
        for (IfGoodsOption ifGoodsOption : ifGoodsOptionList) {
			this.saveItitmm2(ifGoodsOption, ifGoodsMaster); // ititmm
        }
        log.debug("Step2, saveItitmm : " + (System.currentTimeMillis()-start));

        //ititmm에 는 있는데 if_goods_option 에 없는것 삭제
		// List<Ititmm> listItitmm =
		// jpaItitmmRepository.findByAssortId(ifGoodsMaster.getAssortId());
		// todo index 생성이 필요함.
		List<Ititmm> listItitmm = jpaItitmmRepository.findByAssortIdAndDelYn(ifGoodsMaster.getAssortId(), "02");

		List<Ititmm> deletedListItitmm = new ArrayList<Ititmm>();

		List<HashMap<String, Object>> matchedListItitmm = new ArrayList<HashMap<String, Object>>();


        start = System.currentTimeMillis();
		for (Ititmm o11 : listItitmm) {
			Boolean delYn = true;

			for (IfGoodsOption o12 : ifGoodsOptionList) {

				String o11Val1 = "";
				String o11Val2 = "";
				String o11Val3 = "";

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

                if (o11.getVariationSeq3() != null && !o11.getVariationSeq3().equals("")) {
                    ItvariId iv = new ItvariId(ifGoodsMaster.getAssortId(), o11.getVariationSeq3());
                    Itvari itvari3 = jpaItvariRepository.findById(iv).orElse(null);

                    o11Val3 = itvari3.getOptionNm();
                } else {
                    o11Val3 = "";
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
				String o12Val3 = o12.getOptionValue3() == null ? "" : o12.getOptionValue3();

//                System.out.println("1. "+o11Val1+", " +o12Val1);
//                System.out.println("2. "+o11Val2+", " +o12Val2);
//                System.out.println("3. "+o11Val3+", " +o12Val3);

				if (o12Val1.equals(o11Val1) && o12Val2.equals(o11Val2) && o12Val3.equals(o11Val3)) {

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
            log.debug("Step2, ititmm에 없는애들 중복제거 : " + (System.currentTimeMillis()-start));

			// if (delYn == true && ifGoodsOptionList.size() > 0) {

			// todo 옵션이 있다가 옵션을 다 삭제한건에 대해서 단품을 남겨둘 방법을 일단 생각해야함.
			if (delYn == true && !singleItitmmSeq.equals(o11.getItemId())) { // ifGoodsOptionList.size() > 0 <-- 이조건이 왜
																				// 들어가 있는지 모르겠음.????

                o11.setDelYn(StringFactory.getGbOne()); // 01 삭제 02 기본
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

//			jpaItitmmRepository.delete(o);
			jpaItitmmRepository.save(o);
		}

        // 6. if_goods_option 테이블 updateStatus 02로 업데이트
        start = System.currentTimeMillis();
        for(IfGoodsOption ifGoodsOption : ifGoodsOptionList){

			for (HashMap<String, Object> m : matchedListItitmm) {
				if (ifGoodsOption.getGoodsNo().equals(m.get("goodsNo").toString())
						&& ifGoodsOption.getSno().equals(m.get("sno").toString())) {
					ifGoodsOption.setAssortId(m.get("assortId").toString());
					ifGoodsOption.setItemId(m.get("itemId").toString());
				}
			}

			ifGoodsOption.setUploadStatus(StringFactory.getGbTwo()); // 02 하드코딩
			// ifGoodsOption.setUploadStatus(StringFactory.getGbFour()); // 02 하드코딩

            jpaIfGoodsOptionRepository.save(ifGoodsOption);
        }
        log.debug("Step2, ifGoodsOption 상태 update : " + (System.currentTimeMillis()-start));

        List<IfGoodsTextOption> ifGoodsTextOptionList = jpaIfGoodsTextOptionRepository.findByGoodsNo(goodsNo);

        start = System.currentTimeMillis();
        // 7. itmmot (from if_goods_text_option) 저장
        for(IfGoodsTextOption ifGoodsTextOption : ifGoodsTextOptionList){
            this.saveItmmot(ifGoodsTextOption);
        }
        log.debug("Step2, saveItmmot : " + (System.currentTimeMillis()-start));

        start = System.currentTimeMillis();
        // 8. if_goods_text_option 테이블 updateStatus 02로 업데이트
        for(IfGoodsTextOption ifGoodsTextOption : ifGoodsTextOptionList){
            ifGoodsTextOption.setUploadStatus(StringFactory.getGbTwo()); // 02 하드코딩
            jpaIfGoodsTextOptionRepository.save(ifGoodsTextOption);
        }
        log.debug("Step2, setUploadStatus ifGoodsTextOption : " + (System.currentTimeMillis()-start));

        List<IfGoodsAddGoods> ifGoodsAddGoodsList = jpaIfGoodsAddGoodsRepository.findByGoodsNo(goodsNo);

        start = System.currentTimeMillis();
        // 9. itadgs (from if_goods_add_goods) 저장
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            IfAddGoods ifAddGoods = jpaIfAddGoodsRepository.findByAddGoodsNo(ifGoodsAddGoods.getAddGoodsNo());
            ifGoodsAddGoods.setAddGoodsId(ifAddGoods.getAddGoodsId());
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡ addGoodsId : " + ifGoodsAddGoods.getAddGoodsId() + ", assortId : " + ifGoodsAddGoods.getAssortId());
            //OpenApi호출
            AddGoodsData addGoodsData = this.retrieveAddGoods(ifGoodsAddGoods.getAddGoodsNo());

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
            Itasrt addGoodsItasrt = this.saveItadgsToItasrt(ifAddGoods, ifGoodsAddGoods, itadgs);//new Itasrt(itadgs);

			// todo:20211012 addGoods 도 ititmm을 만들어줘함

			Itvari addGoodsitvari = this.saveSingleItvari(addGoodsItasrt.getAssortId());
			this.saveSingleItitmm(addGoodsItasrt, addGoodsitvari);
            
//            jpaIfGoodsAddGoodsRepository.save(addGoodsData);
        }
        log.debug("Step2, itadgs 저장 : " + (System.currentTimeMillis()-start));

        start = System.currentTimeMillis();
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
        log.debug("Step2, itlkag 저장 : " + (System.currentTimeMillis()-start));

        start = System.currentTimeMillis();
        // 11. if_goods_add_goods 테이블에 add_goods_id 삽입하고 updateStatus 02로 업데이트
        for(IfGoodsAddGoods ifGoodsAddGoods : ifGoodsAddGoodsList){
            StringFactory.getGbTwo(); // 02 하드코딩
            jpaIfGoodsAddGoodsRepository.save(ifGoodsAddGoods);
        }
        log.debug("Step2, if_goods_add_goods 테이블에 add_goods_id 삽입하고 updateStatus 02로 업데이트 : " + (System.currentTimeMillis()-start));
        return ifGoodsMaster;
    }

    // itadgs를 통해 itasrt를 만듦
    private Itasrt saveItadgsToItasrt(IfAddGoods ig, IfGoodsAddGoods ia, Itadgs itadgs) {
        boolean isUpdate = true;
        IfGoodsMaster im = jpaIfGoodsMasterRepository.findByChannelGbAndGoodsNo(StringFactory.getGbOne(), ia.getGoodsNo());
        Itasrt itasrt = jpaItasrtRepository.findByAssortId(itadgs.getAddGoodsId());
        if(itasrt == null){ // insert
            itasrt = new Itasrt(itadgs);
            itasrt.setStorageId("000002"); // 000002 하드코딩
        }
        else { // update
            Itasrt origItasrt = itasrtMapper.copy(itasrt);
            itasrt = itasrtMapper.to(itadgs.getAddGoodsId(), itadgs);
            if(origItasrt.equals(itasrt)){
                isUpdate = false;
            }
        }
        if(isUpdate){
            jpaItasrtRepository.save(itasrt);
        }
        return itasrt;
    }

    // itasrn 저장 함수
    private Itasrn saveItasrn(IfGoodsMaster ifGoodsMaster){
        log.debug("ifGoodsMaster.goodsNo : " + ifGoodsMaster.getGoodsNo());
        Date effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        System.out.println("itasrn.assortId : " + ifGoodsMaster.getAssortId());
        Itasrn itasrn = jpaItasrnRepository.findByAssortIdAndEffEndDt(ifGoodsMaster.getAssortId(), effEndDt);
        Itasrn newItasrn = null;
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
            newItasrn = new Itasrn(ifGoodsMaster);
            newItasrn.setAssortId(ifGoodsMaster.getAssortId());
			jpaItasrnRepository.save(newItasrn);
        }
        Itasrn itasrn1 = jpaItasrnRepository.save(itasrn);
        return itasrn1;
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
    	
		Itvari itvari = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(assortId, StringFactory.getGbOne(),
				StringFactory.getStrSingleGoods());
    	
    	if(itvari == null ) {
    		
			String seq = this.getSeq(jpaItvariRepository.findMaxSeqByAssortId(assortId), 4);

			Itvari newItvari = new Itvari(assortId, seq);
			// em.clear();
			jpaItvariRepository.save(newItvari);
			return newItvari;
    	}else {
    		

			return itvari;
    	}
    	
        // option이 없는 경우. seq 0001, 옵션구분 01, variation구분 01, 옵션명 '단품'
        
    }

	private String saveSingleItitmm(Itasrt itasrt, Itvari itvari) {
        // option이 없는 경우. seq 0001, 옵션구분 01, variation구분 01, 옵션명 '단품'

		Ititmm ititmm = jpaItitmmRepository.findByAssortIdAndVariationSeq1AndVariationSeq2AndVariationSeq3(
				itasrt.getAssortId(), itvari.getSeq(), null, null);

		if (ititmm == null) {

			String seq = this.getSeq(jpaItitmmRepository.findMaxItemIdByAssortId(itasrt.getAssortId()), 4);

			Ititmm newItitmm = new Ititmm(itasrt, seq, itvari);
			jpaItitmmRepository.save(newItitmm);

			return newItitmm.getItemId();

		} else {
			return ititmm.getItemId();
		}

    }

	private void saveItvari2(IfGoodsOption ifGoodsOption) { // 01 색깔, 02 사이즈 저장
		// saveItvari 몬가 이상한듯
		// 이건 옵션이 있다는 가정하에 만드는것이므로 단품 생각 안해도됨.

		// String maxSeq =
		// jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId());

		List<Itvari> itvariList = new ArrayList<Itvari>();

		// 속성1번 데이타 만들기
		if (ifGoodsOption.getOptionValue1() != null && ifGoodsOption.getOptionValue1().trim().length() > 0) {
			Itvari it1 = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(), "01",
					ifGoodsOption.getOptionValue1());

			if (it1 == null) {
				it1 = new Itvari(ifGoodsOption);
				it1.setOptionGb("01");
				it1.setVariationGb("01");
				it1.setOptionNm(ifGoodsOption.getOptionValue1());

			}

			itvariList.add(it1);

		}

		// 속성2번 데이타 만들기
		if (ifGoodsOption.getOptionValue2() != null && ifGoodsOption.getOptionValue2().trim().length() > 0) {
			Itvari it2 = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(), "02",
					ifGoodsOption.getOptionValue2());

			if (it2 == null) {
				it2 = new Itvari(ifGoodsOption);
				it2.setOptionGb("02");
				it2.setVariationGb("02");
				it2.setOptionNm(ifGoodsOption.getOptionValue2());

			}

			itvariList.add(it2);

		}

		// 속성3번 데이타 만들기
		if (ifGoodsOption.getOptionValue3() != null && ifGoodsOption.getOptionValue3().trim().length() > 0) {
			Itvari it3 = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(), "03",
					ifGoodsOption.getOptionValue3());

			if (it3 == null) {
				it3 = new Itvari(ifGoodsOption);
				it3.setOptionGb("03");
				it3.setVariationGb("03");
				it3.setOptionNm(ifGoodsOption.getOptionValue3());

			}

			itvariList.add(it3);

		}

		String maxSeq = jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId());

		for (Itvari o : itvariList) {

			if (o.getSeq() == null || o.getSeq().trim().length() == 0) {

				maxSeq = this.getSeq(maxSeq, 4);
				String seq = maxSeq;
				o.setSeq(seq);
				jpaItvariRepository.save(o);

			}
		}

	}

    private void saveItvari(IfGoodsOption ifGoodsOption) { // 01 색깔, 02 사이즈 저장
        List<String> gbList = new ArrayList<>();
        gbList.add(StringFactory.getGbOne());
        gbList.add(StringFactory.getGbTwo());
        gbList.add(StringFactory.getGbThree());
        List<String> optionNmList = new ArrayList<>();
        optionNmList.add(ifGoodsOption.getOptionValue1());
        optionNmList.add(ifGoodsOption.getOptionValue2());
        optionNmList.add(ifGoodsOption.getOptionValue3());
        int i = 0;

		String maxSeq = jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId());

        for(String gb : gbList){
            Itvari itvari = new Itvari(ifGoodsOption);
            itvari.setOptionGb(gb);
            itvari.setVariationGb(gb);
            itvari.setOptionNm(optionNmList.get(i));
            String seq = "";
            Itvari itvariList = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(),itvari.getOptionGb(), itvari.getOptionNm());
            if(itvariList == null){
				maxSeq = this.getSeq(maxSeq, 4);
				seq = maxSeq;
                itvari.setSeq(seq);
                jpaItvariRepository.save(itvari);
            }
            i++;
        }
        

        
//
//        Itvari itvariColor = new Itvari(ifGoodsOption);
//        // 옵션 01 : 색깔 저장
//        itvariColor.setOptionGb(StringFactory.getGbOne()); // 01 하드코딩
//        itvariColor.setVariationGb(StringFactory.getGbOne()); // 01 하드코딩
//        itvariColor.setOptionNm(ifGoodsOption.getOptionValue1());
//        String seq = "";
//        System.out.println("--------------------------- " + ifGoodsOption.getAssortId());
////        List<Itvari> itvariList0 = jpaItvariRepository.findByAssortId(ifGoodsOption.getAssortId());
//        Itvari itvariList = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(),itvariColor.getOptionGb(), itvariColor.getOptionNm());
//        if(itvariList == null){
//            seq = this.getSeq(jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId()),4);
//            itvariColor.setSeq(seq);
//            jpaItvariRepository.save(itvariColor);
//        }
//        // 사이즈
//        Itvari itvariSize = new Itvari(ifGoodsOption);
//        if(ifGoodsOption.getOptionName().split(StringFactory.getSplitGb()).length >= 2){
//            itvariSize.setOptionGb(StringFactory.getGbTwo()); // 02 하드코딩
//            itvariSize.setVariationGb(StringFactory.getGbTwo()); // 02 하드코딩
//            itvariSize.setOptionNm(ifGoodsOption.getOptionValue2());
//        }
//        // 재질
//        Itvari itvariMaterial = new Itvari(ifGoodsOption);
//        if(ifGoodsOption.getOptionName().split(StringFactory.getSplitGb()).length >= 3){
//            itvariMaterial.setOptionGb(StringFactory.getGbThree()); // 03 하드코딩
//            itvariMaterial.setVariationGb(StringFactory.getGbThree()); // 03 하드코딩
//            itvariMaterial.setOptionNm(ifGoodsOption.getOptionValue3());
//        }
//        else{
//            return;
//        }
//        // 옵션 02 : 사이즈 저장
//        if(!seq.equals("")){
//            seq = Utilities.plusOne(seq,4);
//        }
//        else{
//            seq = this.getSeq(jpaItvariRepository.findMaxSeqByAssortId(ifGoodsOption.getAssortId()),4);
//        }
//
//        if (jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(),
//                itvariSize.getOptionGb(), itvariSize.getOptionNm()) == null) {
//            itvariSize.setSeq(seq);
//            jpaItvariRepository.save(itvariSize);
//        }
    }

	private void saveItitmm2(IfGoodsOption ifGoodsOption, IfGoodsMaster ifGoodsMaster) {

		String variSeq1 =null;
		String variSeq2 = null;
		String variSeq3 = null;
		
		Itvari it1 = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(), "01",
				ifGoodsOption.getOptionValue1());
		
		System.out.println("it1.getSeq() => " + it1.getSeq());

		Itvari it2 = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(), "02",
				ifGoodsOption.getOptionValue2());
		
		Itvari it3 = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ifGoodsOption.getAssortId(), "03",
				ifGoodsOption.getOptionValue3());
		
		if (it1 != null) {
			variSeq1 = it1.getSeq();
		}

		if (it2 != null) {
			variSeq2 = it2.getSeq();
		}
		
		if (it3 != null) {
			variSeq3 = it3.getSeq();
		}
		
		List<Ititmm> l = jpaItitmmRepository.findByAssortIdAndVariationSeq1AndVariationSeq2AndVariationSeq3AndDelYn(
				ifGoodsMaster.getAssortId(), variSeq1, variSeq2, variSeq3, "02");

		Ititmm itm = null;
		if (l.size() > 0) {
			itm = l.get(0);
		} else {
			itm = jpaItitmmRepository.findByAssortIdAndVariationSeq1AndVariationSeq2AndVariationSeq3(
					ifGoodsMaster.getAssortId(), variSeq1, variSeq2, variSeq3);
		}

		if (itm == null) {
			Ititmm ititmm = new Ititmm(ifGoodsOption);
			// itemId 채번
			String itemId = jpaItitmmRepository.findMaxItemIdByAssortId(ifGoodsMaster.getAssortId());
			itemId = getSeq(itemId, 4);
			ifGoodsOption.setItemId(itemId);
			ititmm.setItemId(itemId);
			ititmm.setItemNm(ifGoodsMaster.getGoodsNm());
			jpaItitmmRepository.save(ititmm);
		} else {
			itm.setDelYn("02");
			itm.setUpdDt(new Date());
			jpaItitmmRepository.save(itm);
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
            ititmm.setItemId(StringUtils.leftPad(StringFactory.getStrOne(),4,'0'));
            ititmm.setItemNm(StringFactory.getStrSingleGoods()); // 단품
            jpaItitmmRepository.save(ititmm);
            return;
        }
        else{
            ititmm.setVariationGb1(itvariOp1.getVariationGb());
            ititmm.setVariationSeq1(itvariOp1.getSeq());
        }
        if(ifGoodsOption.getOptionName() != null && ifGoodsOption.getOptionName().split(StringFactory.getSplitGb()).length >= 2){
            Itvari itvariList = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ititmm.getAssortId(), StringFactory.getGbTwo(), ifGoodsOption.getOptionValue2());
            Itvari itvariOp2 = itvariList;
            ititmm.setVariationGb2(itvariOp2.getVariationGb());
            ititmm.setVariationSeq2(itvariOp2.getSeq());
        }
        if(ifGoodsOption.getOptionName() != null && ifGoodsOption.getOptionName().split(StringFactory.getSplitGb()).length >= 3){
            Itvari itvariList = jpaItvariRepository.findByAssortIdAndOptionGbAndOptionNm(ititmm.getAssortId(), StringFactory.getGbThree(), ifGoodsOption.getOptionValue3());
            Itvari itvariOp3 = itvariList;
            ititmm.setVariationGb3(itvariOp3.getVariationGb());
            ititmm.setVariationSeq3(itvariOp3.getSeq());
        }

		Ititmm itm = null;
		if (itvariOp1 != null) {

			itm = jpaItitmmRepository.findByAssortIdAndVariationSeq1AndVariationSeq2AndVariationSeq3(ititmm.getAssortId(),
					ititmm.getVariationSeq1(), ititmm.getVariationSeq2(), ititmm.getVariationSeq3());

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
        Itasrt itasrt = jpaItasrtRepository.findByAssortId(ifGoodsMaster.getAssortId());
        if(itasrt == null){ // insert
            itasrt = new Itasrt(ifGoodsMaster); // itasrt
            jpaItasrtRepository.save(itasrt);
        }
        else{
            Itasrt newItasrt = new Itasrt(ifGoodsMaster);
            if(!itasrt.equals(newItasrt)){
                itasrt = newItasrt;
                jpaItasrtRepository.save(itasrt);
            }
        }
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

	// TODO(완) : 전체호출 ?? 은 어떻게? -> page로 수정완료
    // goods xml 받아오는 함수
    public List<GoodsSearchData> retrieveGoods(String goodsNo, String searchDateType, String fromDt, String toDt, String page) {
        //OpenApi호출
        String urlstr = goodsSearchUrl + StringFactory.getStrQuestion() + StringFactory.getGoodsSearchParams()[0] + StringFactory.getStrEqual() +
                pKey + StringFactory.getStrAnd() +StringFactory.getGoodsSearchParams()[1]
                + StringFactory.getStrEqual() + key
//                + StringFactory.getStrAnd() + StringFactory.getGoodsSearchParams()[3]
//                + StringFactory.getStrEqual()
                +"&startDate=" + fromDt + "&endDate=" + toDt
                + "&goodsNo="+goodsNo + "&page=" + page + "&searchDateType=" + searchDateType;
//        System.out.println("##### " + urlstr);
        NodeList nodeList =  CommonXmlParse.getXmlNodes(urlstr);
        List<GoodsSearchData> goodsSearchData = new ArrayList<>();

//		System.out.println(nodeList);

        List<Map<String, Object>> list = commonXmlParse.retrieveNodeMaps(StringFactory.getStrGoodsData(), nodeList, goodsSearchGotListPropsMap);

    	HashMap<String, Object> r = commonXmlParse.getPagination(nodeList);


        for(Map<String, Object> item : list){
			System.out.println("*******************************************************************");
			System.out.println(item);
			System.out.println("*******************************************************************");

			GoodsSearchData gsData = objectMapper.convertValue(item, GoodsSearchData.class);

			System.out.println("============================================================================");
			System.out.println(gsData.getModDt());
			System.out.println("============================================================================");

			goodsSearchData.add(gsData);
        }
		return goodsSearchData;
    }

	// TODO(완) : 전체호출 ?? 은 어떻게? -> page 이용
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
                + "=" + key+"&addGoodsNo="+goodsNo;
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

	/**
	 * tmitem, tmmapi 넣어주는 쿼리 실행
	 */
	@Transactional
	public void insertTms1() {


		List<Tmmapi> tmmapiList = new ArrayList<Tmmapi>();
		List<Tmitem> tmitemList = new ArrayList<Tmitem>();


			List<IfGoodsMaster> list = jpaIfGoodsMasterRepository.findByUploadStatus("04");

			for (IfGoodsMaster o : list) {

				System.out.println("step3 insertTms1 =>> " + o.getGoodsNo());

				Tmmapi tm = jpaTmmapiRepository.findByChannelGbAndChannelGoodsNo("01", o.getGoodsNo());

				System.out.println("step3 insertTms1 end=>> " + o.getGoodsNo());

				if (tm == null) {
					System.out.println("step3 tmmapi insert =>> " + o.getGoodsNo());

					tm = new Tmmapi(o);

//					tmmapiList.add(tm);

				} else {

					tm.setChannelGoodsNo(o.getGoodsNo());
					tm.setUploadDt(new Date());

					tm.setUpdDt(new Date());

					// tmmapiList.add(tm);
				}

				List<Ititmm> l = jpaItitmmRepository.findByAssortId(o.getAssortId());

				for (Ititmm o1 : l) {
					IfGoodsOption igo1 = jpaIfGoodsOptionRepository.findByAssortIdAndItemId(o1.getAssortId(),
							o1.getItemId());

					if (igo1 != null) {

						/*
						 * List<Tmitem> timList =
						 * jpaTmitemRepository.findByAssortIdAndItemId(o1.getAssortId(),
						 * o1.getItemId());
						 * 
						 * System.out.println("timList size =>> " + timList.size());
						 * 
						 * for (Tmitem o11 : timList) { jpaTmitemRepository.delete(o11); }
						 */
						Tmitem tim = jpaTmitemRepository
								.findByAssortIdAndItemIdAndChannelGbAndChannelGoodsNoAndChannelOptionsNo(
										o1.getAssortId(), o1.getItemId(), igo1.getChannelGb(), igo1.getGoodsNo(),
										igo1.getSno());

						if (tim == null) {

							System.out.println("step3 Tmitem insert =>> " + o.getGoodsNo());

							Tmitem tim1 = new Tmitem(o, o1, igo1);
//							 tmitemList.add(tim1);
							// jpaTmitemRepository.save(tm1);

//							jpaTmitemRepository.saveAndFlush(tim1);
							jpaTmitemRepository.save(tim1);
						} else {
							tim.setUpdDt(new Date());

//							tmitemList.add(tim);
//							jpaTmitemRepository.saveAndFlush(tim);
							jpaTmitemRepository.save(tim);
						}



					}


				}

				// jpaTmmapiRepository.saveAll(tmmapiList);
				jpaTmmapiRepository.save(tm);

				// jpaTmitemRepository.saveAll(tmitemList);

				o.setUploadStatus("02");
				o.setUpdDt(new Date());

				System.out.println("o.getAssortId ==> " + o.getAssortId());

				jpaIfGoodsMasterRepository.save(o);
				// o.getAssortId()

			}


		// goodsMapper.insertTmmapi();
		// goodsMapper.updateTmitem();
		// goodsMapper.insertTmitem();
    }

	private GoodsSearchData.OptionData getOptionData(String goodsNo){
		
		
		GoodsSearchData.OptionData o = new GoodsSearchData.OptionData();
		
		return o;
	}
}
