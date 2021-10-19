package io.spring.main.apis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import io.spring.main.enums.GodoOrderStatus;
import io.spring.main.interfaces.TbOrderDetailMapper;
import io.spring.main.interfaces.TbOrderMasterMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.spring.main.enums.DeliveryMethod;
import io.spring.main.enums.GoodsOrAddGoods;
import io.spring.main.enums.TrdstOrderStatus;
import io.spring.main.interfaces.IfOrderDetailMapper;
import io.spring.main.interfaces.IfOrderMasterMapper;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.JpaIfAddGoodsRepository;
import io.spring.main.jparepos.goods.JpaIfGoodsAddGoodsRepository;
import io.spring.main.jparepos.goods.JpaItasrtRepository;
import io.spring.main.jparepos.goods.JpaItitmcRepository;
import io.spring.main.jparepos.goods.JpaItitmmRepository;
import io.spring.main.jparepos.goods.JpaItitmtRepository;
import io.spring.main.jparepos.goods.JpaTmitemRepository;
import io.spring.main.jparepos.goods.JpaTmmapiRepository;
import io.spring.main.jparepos.order.JpaIfOrderDetailRepository;
import io.spring.main.jparepos.order.JpaIfOrderMasterRepository;
import io.spring.main.jparepos.order.JpaOrderLogRepository;
import io.spring.main.jparepos.order.JpaTbMemberAddressRepository;
import io.spring.main.jparepos.order.JpaTbMemberRepository;
import io.spring.main.jparepos.order.JpaTbOrderDetailRepository;
import io.spring.main.jparepos.order.JpaTbOrderHistoryRepository;
import io.spring.main.jparepos.order.JpaTbOrderMasterRepository;
import io.spring.main.model.goods.entity.IfAddGoods;
import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.goods.entity.Tmitem;
import io.spring.main.model.goods.entity.Tmmapi;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.entity.OrderLog;
import io.spring.main.model.order.entity.TbMember;
import io.spring.main.model.order.entity.TbMemberAddress;
import io.spring.main.model.order.entity.TbOrderDetail;
import io.spring.main.model.order.entity.TbOrderHistory;
import io.spring.main.model.order.entity.TbOrderMaster;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:godourl.yml")
public class OrderSearch {
    // 고도몰 관련 값들
    @Value("${pKey}")
    private String pKey;
    @Value("${key}")
    private String key;
    @Value("${url.godo.orderSearch}")
    private String orderSearchUrl;
    @Value("${url.server.changeOrderStatus}")
    private String serverChangeStatusUrl;

    private final GoodsSearch goodsSearch;
    private final JpaItasrtRepository jpaItasrtRepository;
    private final JpaIfOrderMasterRepository jpaIfOrderMasterRepository;
    private final JpaIfOrderDetailRepository jpaIfOrderDetailRepository;
    private final JpaSequenceDataRepository jpaSequenceDataRepository;
    private final JpaTbOrderMasterRepository jpaTbOrderMasterRepository;
    private final JpaTbOrderHistoryRepository jpaTbOrderHistoryRepository;
    private final JpaTbMemberRepository jpaTbMemberRepository;
    private final JpaTbMemberAddressRepository jpaTbMemberAddressRepository;
    private final JpaTbOrderDetailRepository jpaTbOrderDetailRepository;
    private final JpaOrderLogRepository jpaOrderLogRepository;
    private final JpaItitmtRepository jpaItitmtRepository;
    private final JpaItitmcRepository jpaItitmcRepository;
    private final JpaItitmmRepository jpaItitmmRepository;
    private final JpaTmmapiRepository jpaTmmapiRepository;
    private final JpaTmitemRepository jpaTmitemRepository;
	private final JpaIfGoodsAddGoodsRepository jpaIfGoodsAddGoodsRepository;
	private final JpaIfAddGoodsRepository jpaIfAddGoodsRepository;

    private final EntityManager em;
    private final ObjectMapper objectMapper;
    private final CommonXmlParse commonXmlParse;
    private final List<String> orderSearchGotListPropsMap;

    // mapStruct mapper
    private final IfOrderMasterMapper ifOrderMasterMapper;
    private final IfOrderDetailMapper ifOrderDetailMapper;
    private final TbOrderDetailMapper tbOrderDetailMapper;
    private final TbOrderMasterMapper tbOrderMasterMapper;


    // 고도몰에서 일주일치 주문을 땡겨와서 if_order_master, if_order_detail에 저장하는 함수
    @Transactional
	public void saveIfTables(String orderNo, String startDt, String endDt, String mode) {
		List<OrderSearchData> orderSearchDataList = retrieveOrders(orderNo, startDt, endDt, mode);

        // 1. if table 저장
        for(OrderSearchData orderSearchData : orderSearchDataList){
            IfOrderMaster ifOrderMaster = this.saveIfOrderMaster(orderSearchData); // if_order_master : tb_member, tb_member_address, tb_order_master  * 여기서 ifNo 생성

			System.out.println(
					"***********************************OrderSearchData*****************************************");

			if (ifOrderMaster == null) {
				continue;
			}

			System.out.println(ifOrderMaster);

            this.saveIfOrderDetail(orderSearchData); // if_order_detail : tb_order_detail, tb_order_history
			System.out.println(
					"***********************************OrderSearchData*****************************************");
        }
    }

    public List<IfOrderMaster> getIfOrderMasterListWhereIfStatus01(){
        List<IfOrderMaster> ifOrderMasterList = jpaIfOrderMasterRepository.findByIfStatus(StringFactory.getGbOne()); // if_order_master에서 if_status가 01인 애 전부 가져옴
        return ifOrderMasterList;
    }

    private IfOrderMaster saveIfOrderMaster(OrderSearchData orderSearchData) {

		System.out.println(orderSearchData.getOrderNo());

        String ifNo;
        // ifNo 채번
        IfOrderMaster ioMaster = jpaIfOrderMasterRepository.findByChannelGbAndChannelOrderNo(StringFactory.getGbOne(), Long.toString(orderSearchData.getOrderNo())); // 채널은 01 하드코딩
        IfOrderMaster ifOrderMaster = null;
        if(ioMaster == null){ // insert
            String num = jpaSequenceDataRepository.nextVal(StringFactory.getSeqIforderMaster());
            if(num == null){
                num = StringFactory.getStrOne();
            }
            ifNo = StringUtils.leftPad(num, 9, '0');
			orderSearchData.setIfNo(ifNo);
        }
		else { //todo(완) :업데이트 있음 2021-10-12
            ifOrderMaster = ioMaster;
            ifOrderMaster.setIfStatus(StringFactory.getGbOne());
			orderSearchData.setIfNo(ifOrderMaster.getIfNo());

        }

        if(ifOrderMaster == null){ // insert
            ifOrderMaster = ifOrderMasterMapper.to(orderSearchData, orderSearchData.getOrderInfoData().get(0));//objectMapper.convertValue(orderSearchData, IfOrderMaster.class);
            ifOrderMaster.setIfStatus(StringFactory.getGbOne());
        }
        // not null 컬럼들 설정
//        ifOrderMaster.setChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
//        ifOrderMaster.setChannelOrderStatus(orderSearchData.getOrderStatus());
		// System.out.println("getAddField ===> " + orderSearchData.getAddField());

		try { // 오류가 난다면 pcode * 처리

			if (Utilities.makeStringToMap(orderSearchData.getAddField()) != null) {


				String q1 = (String) (((Map<String, Object>) (Utilities.makeStringToMap(orderSearchData.getAddField())
						.get("1"))).get("name"));

				
				System.out.println("q1 ===> " + q1);
				
				if (q1.contains("개인통관 고유번호")) {
					ifOrderMaster.setCustomerId((String) (((Map<String, Object>) (Utilities
							.makeStringToMap(orderSearchData.getAddField()).get("1"))).get("data")));

				} else {
					ifOrderMaster.setCustomerId(StringFactory.getStrStar());
				}

				// System.out.println((String) (((Map<String, Object>) (Utilities
				// .makeStringToMap(orderSearchData.getAddField()).get("1"))).get("data")));

//				ifOrderMaster.setCustomerId((String) (((Map<String, Object>) (Utilities
				// .makeStringToMap(orderSearchData.getAddField()).get("1"))).get("data")));

				// if((String) (((Map<String, Object>) (Utilities
				// .makeStringToMap(orderSearchData.getAddField()).get("1"))).get("name"))) {

				// }

			} else {
				ifOrderMaster.setCustomerId(StringFactory.getStrStar());
			}

		} catch (Exception e) {
			ifOrderMaster.setCustomerId(StringFactory.getStrStar());
		}

        jpaIfOrderMasterRepository.save(ifOrderMaster);

        return ifOrderMaster;
    }

    private void saveIfOrderDetail(OrderSearchData orderSearchData) {

		log.debug("saveIfOrderDetail");

        if(orderSearchData.getOrderGoodsData() == null){
            log.debug("orderSearchData.orderGoodsData가 null 입니다.");
            return;
        }
        Map<Long, OrderSearchData.AddGoodsData> addGoodsDataMap = new HashMap<>();
        if (orderSearchData.getAddGoodsData() != null && orderSearchData.getAddGoodsData().size() > 0) {
            for (OrderSearchData.AddGoodsData addGoodsData : orderSearchData.getAddGoodsData()) {
                addGoodsDataMap.put(addGoodsData.getSno(), addGoodsData);
            }
        }
        for (OrderSearchData.OrderGoodsData orderGoodsData : orderSearchData.getOrderGoodsData()) {
            boolean isUpdate = true;
            // todo(완) : 상품정보 고도몰 api에서 가져오던것 주문정보에서 가져오도록 수정 완료 2021-10-12
            orderGoodsData.setDeliveryMethodFl(this.changeDeliMethodToCode(orderGoodsData.getDeliveryMethodFl()));
            orderGoodsData.setGoodsType(this.changeGoodsAddGoodsToCode(orderGoodsData.getGoodsType()));
            IfOrderDetail ifOrderDetail = jpaIfOrderDetailRepository.findByChannelGbAndChannelOrderNoAndChannelOrderSeq(StringFactory.getGbOne(), Long.toString(orderGoodsData.getOrderNo()), Long.toString(orderGoodsData.getSno()));
            if (ifOrderDetail == null) { // insert
                ifOrderDetail = ifOrderDetailMapper.to(orderSearchData, orderGoodsData);//new IfOrderDetail(orderSearchData.getIfNo());

                String seq = jpaIfOrderDetailRepository.findMaxIfNoSeq(orderSearchData.getIfNo());
                if (seq == null) {
                    seq = StringUtils.leftPad(StringFactory.getStrOne(), 3, '0');
                } else {
                    seq = Utilities.plusOne(seq, 3);
                }
                ifOrderDetail.setIfNoSeq(seq);
            } else { // update
                IfOrderDetail newIfOrderDetail = ifOrderDetailMapper.to(orderSearchData, orderGoodsData);
                newIfOrderDetail.setIfNo(ifOrderDetail.getIfNo());
                newIfOrderDetail.setIfNoSeq(ifOrderDetail.getIfNoSeq());
                isUpdate = !newIfOrderDetail.equals(ifOrderDetail);
                ifOrderDetail = newIfOrderDetail;
            }

            if (orderGoodsData.getClaimData() != null) {
                if (orderGoodsData.getClaimData().size() > 1) {
                    System.out.println(
                            "--------------------------Claim Data SIZE BIG-----------------------------------------------------------------------");
                }

                // 21-10-13 claim 입력
                ifOrderDetail.setClaimHandleMode(orderGoodsData.getClaimData().get(0).getHandleMode());
                ifOrderDetail.setClaimHandleReason(orderGoodsData.getClaimData().get(0).getHandleReason());

				// System.out.println(orderGoodsData.getClaimData().get(0).getHandleDetailReason());

                ifOrderDetail.setClaimHandleDetailReason(orderGoodsData.getClaimData().get(0).getHandleDetailReason());
            }
            if(isUpdate){
                jpaIfOrderDetailRepository.save(ifOrderDetail);
            }

            this.saveAddGoods(orderSearchData, ifOrderDetail, addGoodsDataMap);
        }
    }

//    private void saveIfOrderDetail(OrderSearchData orderSearchData) {
//        //
//
//        log.debug("saveIfOrderDetail");
//
//        if(orderSearchData.getOrderGoodsData() == null){
//            log.debug("orderSearchData.orderGoodsData가 null 입니다.");
//            return;
//        }
//        Map<Long, OrderSearchData.AddGoodsData> addGoodsDataMap = new HashMap<>();
//        if(orderSearchData.getAddGoodsData() != null && orderSearchData.getAddGoodsData().size() > 0){
//            for(OrderSearchData.AddGoodsData addGoodsData : orderSearchData.getAddGoodsData()){
//                addGoodsDataMap.put(addGoodsData.getSno(), addGoodsData);
//            }
//        }
//
//        System.out.println(orderSearchData);
//
//        for(OrderSearchData.OrderGoodsData orderGoodsData : orderSearchData.getOrderGoodsData()){
//
//            System.out.println(orderGoodsData);
//
//            IfOrderDetail ifOrderDetail = jpaIfOrderDetailRepository.findByIfNoAndChannelOrderNoAndChannelOrderSeq(orderSearchData.getIfNo(), Long.toString(orderGoodsData.getOrderNo()), Long.toString(orderGoodsData.getSno()));
//            if(ifOrderDetail == null){
//                ifOrderDetail = new IfOrderDetail(orderSearchData.getIfNo());
//                String seq = jpaIfOrderDetailRepository.findMaxIfNoSeq(orderSearchData.getIfNo());
//                if(seq == null){
//                    seq = StringUtils.leftPad(StringFactory.getStrOne(), 3, '0');
//                }
//                else {
//                    seq = Utilities.plusOne(seq, 3);
//                }
//                ifOrderDetail.setIfNoSeq(seq);
//            }
//            // not null
//            ifOrderDetail.setChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
//            ifOrderDetail.setChannelOrderSeq(Long.toString(orderGoodsData.getSno()));
//            ifOrderDetail.setChannelOrderStatus(orderGoodsData.getOrderStatus());
//            ifOrderDetail.setChannelGoodsType(this.changeGoodsAddGoodsToCode(orderGoodsData.getGoodsType()));
//            ifOrderDetail.setChannelGoodsNo(orderGoodsData.getGoodsNo());
//            ifOrderDetail.setChannelOptionsNo(Long.toString(orderGoodsData.getOptionSno()));
//            ifOrderDetail.setChannelOptionInfo(orderGoodsData.getOptionInfo());
//            // goodsNm 가져오기
//            // todo : 상품정보 고도몰 api에서 가져오던것 주문정보에서 가져오도록 수정 2021-10-12
//            // List<GoodsSearchData> goodsSearchDataList =
//            // goodsSearch.retrieveGoods(orderGoodsData.getGoodsNo(), "", "", "");
//            ifOrderDetail.setChannelGoodsNm(orderGoodsData.getGoodsNm());
////            ifOrderDetail.setChannelGoodsNm(jpaTmitemRepository.f/indByChannelGbAndChannelGoodsNoAndChannelOptionsNo(StringFactory.getGbOne(), orderGoodsData.getGoodsNo(), Long.toString(orderGoodsData.getOptionSno())).geta);
//            //
//            ifOrderDetail.setChannelParentGoodsNo(Long.toString(orderGoodsData.getParentGoodsNo()));
//            ifOrderDetail.setGoodsCnt(orderGoodsData.getGoodsCnt());
//            ifOrderDetail.setGoodsPrice(orderGoodsData.getGoodsPrice());
//            ifOrderDetail.setGoodsDcPrice(orderGoodsData.getGoodsDcPrice());
//            ifOrderDetail.setCouponDcPrice(orderGoodsData.getCouponGoodsDcPrice());
//            ifOrderDetail.setMemberDcPrice(orderGoodsData.getMemberDcPrice());
//            ifOrderDetail.setDeliveryMethodGb(this.changeDeliMethodToCode(orderGoodsData.getDeliveryMethodFl()));
//            ifOrderDetail.setDeliPrice(orderGoodsData.getGoodsDeliveryCollectPrice());
////            ifOrderDetail.setOrderId(orderSearchData.getMemId().split(StringFactory.getStrAt())[0]); // tb_order_detail.order_id
//            ifOrderDetail.setDeliveryInfo(orderGoodsData.getDeliveryCond());
//
//            // 21-10-05 추가
//            ifOrderDetail.setScmNo(orderGoodsData.getScmNo());
//
//            // 21-10-06 추가
//            ifOrderDetail.setListImageData(orderGoodsData.getListImageData());
//            ifOrderDetail.setOptionTextInfo(orderGoodsData.getOptionTextInfo());
//
//            if (orderGoodsData.getClaimData() != null) {
//                if (orderGoodsData.getClaimData().size() > 1) {
//                    System.out.println(
//                            "--------------------------Claim Data SIZE BIG-----------------------------------------------------------------------");
//                }
//
//                // 21-10-13 claim 입력
//                ifOrderDetail.setClaimHandleMode(orderGoodsData.getClaimData().get(0).getHandleMode());
//                ifOrderDetail.setClaimHandleReason(orderGoodsData.getClaimData().get(0).getHandleReason());
//                ifOrderDetail.setClaimHandleDetailReason(orderGoodsData.getClaimData().get(0).getHandleDetailReason());
//
//            }
//
//
//
//            em.persist(ifOrderDetail);
//
//            this.saveAddGoods(orderSearchData, ifOrderDetail, addGoodsDataMap);
//        }
//    }


    /**
     * orderGoodsData에 딸린 addGoodsData가 있는지 판단하고 있으면 새로운 ifOrderDetail 저장
     */
    private void saveAddGoods(OrderSearchData orderSearchData, IfOrderDetail ifOrderDetail0, Map<Long, OrderSearchData.AddGoodsData> addGoodsDataMap) {
        if(addGoodsDataMap.size() == 0){
            log.debug("해당 orderSearchData는 addGoodsData가 없음.");
            return;
        }
        long newSno = Long.parseLong(ifOrderDetail0.getChannelOrderSeq());
        while(newSno > 0){
            OrderSearchData.AddGoodsData agData = addGoodsDataMap.get(newSno + 1);
            if(agData == null){
                newSno = -1;
            }
            else{
                IfOrderDetail ifOrderDetail = jpaIfOrderDetailRepository.findByIfNoAndChannelOrderNoAndChannelOrderSeq(orderSearchData.getIfNo(), Long.toString(agData.getOrderNo()), Long.toString(agData.getSno()));
                if(ifOrderDetail == null){
                    ifOrderDetail = new IfOrderDetail(orderSearchData.getIfNo());
                    String seq = jpaIfOrderDetailRepository.findMaxIfNoSeq(orderSearchData.getIfNo());
                    if(seq == null){
                        seq = StringUtils.leftPad(StringFactory.getStrOne(), 3, '0');
                    }
                    else {
                        seq = Utilities.plusOne(seq, 3);
                    }
                    ifOrderDetail.setIfNoSeq(seq);
                }
                // not null
                ifOrderDetail.setChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
                ifOrderDetail.setChannelOrderSeq(Long.toString(agData.getSno()));
                ifOrderDetail.setChannelOrderStatus(agData.getOrderStatus());
                ifOrderDetail.setChannelGoodsType(this.changeGoodsAddGoodsToCode(agData.getGoodsType()));
                ifOrderDetail.setChannelGoodsNo(Long.toString(agData.getAddGoodsNo()));
                ifOrderDetail.setChannelOptionsNo(Long.toString(agData.getOptionSno()));
                ifOrderDetail.setChannelOptionInfo(agData.getOptionInfo());

				// todo(완) : 상품정보 고도몰 api에서 가져오던것 주문정보에서 가져오도록 수정 2021-10-12
                // goodsNm 가져오기
				// List<GoodsSearchData> goodsSearchDataList =
				// goodsSearch.retrieveGoods(Long.toString(agData.getAddGoodsNo()), "", "", "");
                ifOrderDetail.setChannelGoodsNm(agData.getGoodsNm());
//            ifOrderDetail.setChannelGoodsNm(jpaTmitemRepository.f/indByChannelGbAndChannelGoodsNoAndChannelOptionsNo(StringFactory.getGbOne(), orderGoodsData.getGoodsNo(), Long.toString(orderGoodsData.getOptionSno())).geta);
                //
                ifOrderDetail.setChannelParentGoodsNo(Long.toString(agData.getParentGoodsNo()));
                ifOrderDetail.setGoodsCnt(Long.parseLong(agData.getGoodsCnt()));
                ifOrderDetail.setGoodsPrice(agData.getGoodsPrice());
                ifOrderDetail.setGoodsDcPrice(agData.getGoodsDcPrice());
                ifOrderDetail.setCouponDcPrice(agData.getCouponGoodsDcPrice());
                ifOrderDetail.setMemberDcPrice(agData.getMemberDcPrice());
                ifOrderDetail.setDeliveryMethodGb(this.changeDeliMethodToCode(agData.getDeliveryMethodFl()));
                ifOrderDetail.setDeliPrice(agData.getGoodsDeliveryCollectPrice());
//            ifOrderDetail.setOrderId(orderSearchData.getMemId().split(StringFactory.getStrAt())[0]); // tb_order_detail.order_id
                ifOrderDetail.setDeliveryInfo(agData.getDeliveryCond());

                // 21-10-05 추가
                ifOrderDetail.setScmNo(agData.getScmNo());
                ifOrderDetail.setParentChannelOrderSeq(ifOrderDetail0.getChannelOrderSeq());

                em.persist(ifOrderDetail);
//                this.saveAddGoodsIfOrdetDetail(agData);
                newSno++;
            }
        }
    }

    // 문자 형식 된 code를 숫자 형식으로 변환하는 함수
    // delivery : 001, air : 002, ship : 003, quick : 004, 기타 : 005
    private String changeDeliMethodToCode(String deliveryMethodFl) {
        return DeliveryMethod.valueOf(deliveryMethodFl).getFieldName();
    }

    // goods -> 001, add_goods -> 002로 반환
    private String changeGoodsAddGoodsToCode(String goodsType) {
        return GoodsOrAddGoods.valueOf(goodsType).getFieldName();
    }

    // orderStatus가 trdstStatus 안에 존재하는지 판단해줌
    private boolean isTrdstOrderStatus(String goodsType) {
        List<String> enumList = new ArrayList<>();
        for(TrdstOrderStatus val : TrdstOrderStatus.values()){
            enumList.add(val.toString());
        }
        if(enumList.contains(goodsType)){
            return true;
        }
        else {
            return false;
        }
    }

    // goods xml 받아오는 함수
	public List<OrderSearchData> retrieveOrders(String orderNo, String fromDt, String toDt, String mode) {
        //OpenApi호출
        fromDt = fromDt == null? "":fromDt;
        toDt = toDt == null? "":toDt;

		mode = mode == null ? "modify" : mode;

        String urlstr = orderSearchUrl + StringFactory.getStrQuestion() + StringFactory.getOrderSearchParams()[0] + StringFactory.getStrEqual() +
                pKey + StringFactory.getStrAnd() +StringFactory.getOrderSearchParams()[1]
                + StringFactory.getStrEqual() + key
                + StringFactory.getStrAnd() + StringFactory.getOrderSearchParams()[3]
                + StringFactory.getStrEqual() + fromDt
                + StringFactory.getStrAnd() + StringFactory.getOrderSearchParams()[4]
                + StringFactory.getStrEqual() + toDt
				+ "&dateType=" + mode
                + "&orderNo="+ orderNo; //2106281109256643";
//        System.out.println("##### " + urlstr);

        NodeList nodeList =  CommonXmlParse.getXmlNodes(urlstr);

        List<OrderSearchData> orderSearchDataList = new ArrayList<>();

        List<Map<String, Object>> list = commonXmlParse.retrieveNodeMaps(StringFactory.getStrOrderData(), nodeList, orderSearchGotListPropsMap);

        for(Map<String, Object> item : list){
            OrderSearchData orderSearchData = objectMapper.convertValue(item, OrderSearchData.class);
            orderSearchDataList.add(orderSearchData);
        }
        return orderSearchDataList;
    }

    @Transactional
    public IfOrderMaster saveOneIfNo(IfOrderMaster ifOrderMaster) {
//        IfOrderMaster ifOrderMaster1 = jpaIfOrderMasterRepository.findByChannelGbAndChannelOrderNo(StringFactory.getGbOne(), ifOrderMaster.getChannelOrderNo()); // 채널은 01 하드코딩
//        // 이미 저장한 주문이면 pass
//        if(ifOrderMaster1.getIfStatus().equals(StringFactory.getGbTwo())){ // ifStatus가 02면 저장 포기
//            log.debug("이미 저장된 주문입니다.");
//            return ifOrderMaster1;
//        }
        // tb_order_master, tb_member, tb_member_address 저장
        TbMember tbMember = this.saveTbMember(ifOrderMaster);
        TbMemberAddress tbMemberAddress = this.saveTbMemberAddress(ifOrderMaster, tbMember);

        // tb_order_detail, tb_order_history
        int num = 0;
        for(IfOrderDetail ifOrderDetail : ifOrderMaster.getIfOrderDetail()){
            TbOrderDetail tbOrderDetail = this.saveTbOrderDetail(ifOrderDetail);
            if(tbOrderDetail != null){
                this.saveTbOrderHistory(ifOrderDetail, tbOrderDetail);
            }
            else{
                log.debug("tbOrderDetail의 값이 변화 없음.");
            }
            
            if(num == 0 && tbOrderDetail != null){
                TbOrderMaster tbOrderMaster = this.saveTbOrderMaster(ifOrderMaster, tbOrderDetail, tbMember, tbMemberAddress);
                jpaTbOrderMasterRepository.save(tbOrderMaster);
            }
            num++;
        }
        
        if(num == 0){
            ifOrderMaster.setOrderId(null);
        }
        ifOrderMaster.setIfStatus(StringFactory.getGbTwo()); // ifStatus 02로 변경
//        em.persist(ifOrderMaster);
//        log.debug("ifNo : "+ifOrderMaster.getIfNo());
        return ifOrderMaster;
    }

    /**
     * tbOrderDetail row 하나 저장하는 함수
     * @param ifOrderDetail
     * @return
     */
    private TbOrderDetail saveTbOrderDetail(IfOrderDetail ifOrderDetail) {
        // ifOrderDetail의 정보를 tbOrderDetail에 저장할 때, tmmapi의 goodsNo를 확인해서 assortId를 추출해야 함.
        // 옵션이 없는 애들은 tmmapi에서 assortId 찾아오기, 옵션이 있는 애들은 tmitem에서 assortId, itemId 찾아오기
        // goods 정보를 고도몰에서 가져올 필요x. 우리가 가진 데이터로도 가능.
        // optionData가 있는 애들은 각 optionData마다 tbOrderDetail 생성됨.
//        GoodsSearchData goodsSearchData = goodsSearch.retrieveGoods(ifOrderDetail.getChannelGoodsNo(),"","", "").get(0);
//        System.out.println("----------------------- : " + tbOrderMaster.getOrderId() + " " + goodsSearchData.getGoodsNm());
        TbOrderDetail tbOrderDetail = jpaTbOrderDetailRepository.findByChannelOrderNoAndChannelOrderSeq(ifOrderDetail.getChannelOrderNo(), ifOrderDetail.getChannelOrderSeq());//, goodsSearchData.getGoodsNm());
        System.out.println("===== itemNm : " + ifOrderDetail.getChannelGoodsNm() + " ===== goodsNo : " + ifOrderDetail.getChannelGoodsNo());
        System.out.println("===== orderId : " + ifOrderDetail.getOrderId() + " ===== goodsNm : " + ifOrderDetail.getChannelGoodsNm());
     
        Tmitem tmitem = jpaTmitemRepository.findByChannelGbAndChannelGoodsNoAndChannelOptionsNo(StringFactory.getGbOne(), ifOrderDetail.getChannelGoodsNo(), ifOrderDetail.getChannelOptionsNo());
        Tmmapi tmmapi = jpaTmmapiRepository.findByChannelGbAndChannelGoodsNo(StringFactory.getGbOne(), ifOrderDetail.getChannelGoodsNo());
        
        if(tmmapi == null){
            log.debug("tmmapi에 해당 goodsNo 정보가 들어가 있지 않습니다.");
        }
        
        else if(tmitem == null){
            log.debug("tmitem에 해당 goodsNo 정보가 들어가 있지 않습니다.");
        }
        
		if (ifOrderDetail.getChannelGoodsType().equals("001")) { // goods
            Ititmm ititmm = this.getItitmmWithItasrt(tmmapi == null? null : tmmapi.getAssortId(), tmitem == null? StringFactory.getFourStartCd():tmitem.getItemId()); // tmitem이 없으면 0001
			tbOrderDetail = this.saveSingleTbOrderDetail(tbOrderDetail, ifOrderDetail, ititmm, null);
		} else { // add_goods

			System.out.println("addGood => " + ifOrderDetail.getChannelGoodsNo());

			Ititmm ititmm = this.getItitmmWithItasrt(tmmapi == null ? null : tmmapi.getAssortId(),
					tmitem == null ? StringFactory.getFourStartCd() : tmitem.getItemId()); // tmitem이 없으면 0001

			IfAddGoods agItem = jpaIfAddGoodsRepository.findByAddGoodsNo(ifOrderDetail.getChannelGoodsNo());
			System.out.println("agItem => " + agItem);

			// IfGoodsAddGoods ag = agItem.get(0);
			tbOrderDetail = this.saveSingleTbOrderDetail(tbOrderDetail, ifOrderDetail, ititmm, agItem);
        }
		
		//ifOrderDetail.getChannelGoodsNo()

//        Ititmm ititmm = this.getItitmmWithItasrt(tmmapi == null? null : tmmapi.getAssortId(), tmitem == null? StringFactory.getFourStartCd():tmitem.getItemId()); // tmitem이 없으면 0001
//        tbOrderDetail = this.saveSingleTbOrderDetail(tbOrderDetail, ifOrderDetail, ititmm);
        return tbOrderDetail;
    }

    private Ititmm getItitmmWithItasrt(String assortId, String itemId) {
        TypedQuery<Ititmm> query = em.createQuery("select m from Ititmm m join fetch m.itasrt t where m.assortId=?1 and m.itemId=?2", Ititmm.class);
        query.setParameter(1,assortId).setParameter(2,itemId);
        List<Ititmm> ititmmList = query.getResultList();
        Ititmm ititmm;
        if(ititmmList.size() > 0){
            ititmm = ititmmList.get(0);
        }
        else {
            ititmm = null;
        }
        return ititmm;
    }

    /**
     * 단일 tbOrderDetail 객체를 생성 후 save 해주는 함수. (+ orderLog 추가) 기존 대비 변한 값이 없으면 null을 return
     * @param ifOrderDetail
     * @param ititmm
     */
	private TbOrderDetail saveSingleTbOrderDetail(TbOrderDetail outTbOrderDetail, IfOrderDetail ifOrderDetail,
			Ititmm ititmm, IfAddGoods ag) {
        boolean flag = outTbOrderDetail == null; // true : insert, false : update
        TbOrderDetail tbOrderDetail;
        TbOrderDetail compareTbOrderDetail = null;

        if(flag){ // insert
            List<TbOrderDetail> tbOrderDetailList = jpaTbOrderDetailRepository.findByChannelOrderNo(ifOrderDetail.getChannelOrderNo());
            int num = tbOrderDetailList.size();
            String orderId;
            if(tbOrderDetailList.size() > 0){
                orderId = tbOrderDetailList.get(0).getOrderId();
            }
            else {
                // orderId 채번
                String nextVal = jpaSequenceDataRepository.nextVal(StringFactory.getSeqTbOrderDetail());
                orderId = Utilities.getStringNo('O',nextVal, 9);
            }
            String orderSeq = Utilities.plusOne(Integer.toString(num),4);
            orderSeq = orderSeq == null? StringFactory.getFourStartCd() : orderSeq;
            tbOrderDetail = tbOrderDetailMapper.to(orderId, orderSeq, ifOrderDetail, ititmm);//new TbOrderDetail(orderId, orderSeq);

            ifOrderDetail.setOrderId(tbOrderDetail.getOrderId());
            ifOrderDetail.setOrderSeq(tbOrderDetail.getOrderSeq());
            jpaIfOrderDetailRepository.save(ifOrderDetail);
        }
        else { // update
            compareTbOrderDetail = tbOrderDetailMapper.copy(outTbOrderDetail);//tbOrderDetailMapper.to(outTbOrderDetail.getOrderId(), outTbOrderDetail.getOrderSeq(), ifOrderDetail, ititmm);//tbOrderDetailMapper.copy(outTbOrderDetail);//new TbOrderDetail(outTbOrderDetail);
            tbOrderDetail = tbOrderDetailMapper.to(outTbOrderDetail.getOrderId(), outTbOrderDetail.getOrderSeq(), ifOrderDetail, ititmm);//tbOrderDetailMapper.copy(outTbOrderDetail);//new TbOrderDetail(outTbOrderDetail);

            // trdstOrderStatus를 가지고 있으면 update 하지 않음.
            if(this.isTrdstOrderStatus(compareTbOrderDetail.getStatusCd())){
                log.debug("trdst의 orderStatus를 탄 주문은 update 할 수 없습니다. orderId : " + compareTbOrderDetail.getOrderId() + ", orderSeq : " + compareTbOrderDetail.getSetOrderSeq());
                return null;
            }
        }

		if (StringFactory.getThreeSecondCd().equals(ifOrderDetail.getChannelGoodsType())) { // add_goods인 경우
			if (ag != null) {
				tbOrderDetail.setItemId(StringFactory.getFourStartCd()); // 0001 하드코딩
				tbOrderDetail.setAssortId(ag.getAddGoodsId());
			}
		}

//        System.out.println("----------------------- : " + tbOrderDetail.getOrderId() + ", " + tbOrderDetail.getOrderSeq());

        String orderStatus = StringFactory.getStrPOne().equals(ifOrderDetail.getChannelOrderStatus())? StringFactory.getStrA01():ifOrderDetail.getChannelOrderStatus();

		// claim에 따른 상품상태변경 (r = 환불접수, b = 반품접수, e = 교환접수
		if (ifOrderDetail.getClaimHandleMode() != null) {
			if (ifOrderDetail.getClaimHandleMode().equals(StringFactory.getStrE())) {
				orderStatus = GodoOrderStatus.e1.getFieldName();
			} else if (ifOrderDetail.getClaimHandleMode().equals(StringFactory.getStrB())) {
				orderStatus = GodoOrderStatus.b1.getFieldName();
			}
		}

        tbOrderDetail.setStatusCd(orderStatus); // 고도몰에서는 A01 상태만 가져옴.

        float goodsDcPrice = ifOrderDetail.getGoodsDcPrice() == null? 0 : ifOrderDetail.getGoodsDcPrice();
        float memberDcPrice = ifOrderDetail.getMemberDcPrice() == null? 0 : ifOrderDetail.getMemberDcPrice();
        float couponDcPrice = ifOrderDetail.getCouponDcPrice() == null? 0 : ifOrderDetail.getCouponDcPrice();
        float adminDcPrice = ifOrderDetail.getAdminDcPrice() == null? 0 : ifOrderDetail.getAdminDcPrice();
        tbOrderDetail.setDcSumPrice(goodsDcPrice + memberDcPrice + couponDcPrice + adminDcPrice);

        tbOrderDetail.setParentOrderSeq(StringUtils.leftPad(ifOrderDetail.getIfNoSeq(), 4,'0'));
        
        // 부모 ifOrderDetail 찾기
        IfOrderDetail ifOrderDetailParent = jpaIfOrderDetailRepository.findByIfNoAndChannelOrderNoAndChannelOrderSeq(ifOrderDetail.getIfNo(), ifOrderDetail.getChannelOrderNo(), ifOrderDetail.getParentChannelOrderSeq());
//        if(ifOrderDetailParent == null){
//            tbOrderDetail.setParentOrderSeq(ifOrderDetail.getOrderSeq() == null? tbOrderDetail.getParentOrderSeq():ifOrderDetail.getOrderSeq());
//        }
        if(ifOrderDetailParent != null){
            tbOrderDetail.setParentOrderSeq(StringUtils.leftPad(ifOrderDetailParent.getIfNoSeq(), 4,'0'));
        }

        boolean isEqual = tbOrderDetail.equals(compareTbOrderDetail);

        if(!flag && isEqual){ // update인데 tbOrderDetail의 값이 불변
            tbOrderDetail = null;
        }
        else{
            jpaTbOrderDetailRepository.save(tbOrderDetail);
        }

        if(tbOrderDetail != null){
            this.saveOrderLog(tbOrderDetail.getStatusCd(), tbOrderDetail);
        }
        return tbOrderDetail;
    }

    /**
     * tbOrderDetail.statusCd가 변동될 때마다 로그를 기록함.
     * @param tbOrderDetail
     */
    private void saveOrderLog(String statusCd, TbOrderDetail tbOrderDetail) {
        OrderLog orderLog = new OrderLog(tbOrderDetail);
        orderLog.setPrevStatus(statusCd);
        jpaOrderLogRepository.save(orderLog);
    }

    private TbOrderMaster saveTbOrderMaster(IfOrderMaster ifOrderMaster, TbOrderDetail tbOrderDetail, TbMember tbMember, TbMemberAddress tbMemberAddress) {
        TbOrderMaster tbOrderMaster = jpaTbOrderMasterRepository.findByChannelOrderNo(ifOrderMaster.getChannelOrderNo());
        TbOrderMaster origTM = null;
        if(tbOrderMaster == null){ // insert
            tbOrderMaster = tbOrderMasterMapper.to(tbOrderDetail.getOrderId(), ifOrderMaster, tbMember, tbMemberAddress);//new TbOrderMaster(tbOrderDetail.getOrderId());
            ifOrderMaster.setOrderId(tbOrderMaster.getOrderId());
            jpaIfOrderMasterRepository.save(ifOrderMaster);
        }
        else{ // update
            origTM = tbOrderMasterMapper.copy(tbOrderMaster); // 기존 tbOrderMaster
            tbOrderMaster = tbOrderMasterMapper.to(tbOrderDetail.getOrderId(), ifOrderMaster, tbMember, tbMemberAddress); // 새로 입력된 정보를 기반으로 만든 tbOrderMaster
        }

		if (ifOrderMaster.getCustomerId().trim().length() >= 13) {
		    tbOrderMaster.setCustPcode(ifOrderMaster.getCustomerId().trim().substring(0, 13));// 고객번호는 13자리번호여서 13자리만 추출
		}else {
			tbOrderMaster.setCustPcode(ifOrderMaster.getCustomerId().trim());// 고객번호는 13자리번호여서 13자리만 추출
		}

        tbOrderMaster.setPayDt(Utilities.dateToLocalDateTime(ifOrderMaster.getPayDt()));

        if(origTM != null){ // update인 경우
            tbOrderMaster = tbOrderMaster.equals(origTM)? origTM : tbOrderMaster;
            origTM = null;
        }

        return tbOrderMaster;
    }

    private void saveTbOrderHistory(IfOrderDetail ifOrderDetail, TbOrderDetail tbOrderDetail) {
        TbOrderHistory tbOrderHistory = jpaTbOrderHistoryRepository.findByOrderIdAndOrderSeqAndEffEndDt(tbOrderDetail.getOrderId(), tbOrderDetail.getOrderSeq(), Utilities.getStringToDate(StringFactory.getDoomDay()));
        boolean isInsert = false;
        if(tbOrderHistory == null){ // insert
            isInsert = true;
            tbOrderHistory = new TbOrderHistory(tbOrderDetail);
        }
        else{ // update
            tbOrderHistory.setEffEndDt(new Date());
            tbOrderHistory.setLastYn(StringUtils.leftPad(StringFactory.getStrOne(), 3,'0')); // 001 하드코딩

            TbOrderHistory newTbOrderHistory = new TbOrderHistory(tbOrderDetail);
//            newTbOrderHistory.setOrderSeq(tbOrderHistory.getOrderSeq());
            newTbOrderHistory.setStatusCd(tbOrderDetail.getStatusCd()); // 추후 수정
            jpaTbOrderHistoryRepository.save(newTbOrderHistory);
        }
        if(isInsert){
            tbOrderHistory.setStatusCd(tbOrderDetail.getStatusCd());
        }
        jpaTbOrderHistoryRepository.save(tbOrderHistory);
    }

    private TbMember saveTbMember(IfOrderMaster ifOrderMaster) {
    	
		TbMember tbMember = null;

		// memNo가 0 이라면 비회원
		if (ifOrderMaster.getMemNo().equals("0")) {
			tbMember = new TbMember(ifOrderMaster);

			System.out.println(tbMember);
			tbMember.setCustGb("02");
			
			String loginId = "";

			if (!ifOrderMaster.getMemNo().equals("0")) {
				if (!ifOrderMaster.getOrderEmail().equals("") && ifOrderMaster.getOrderEmail() != null) {
					loginId = ifOrderMaster.getOrderEmail(); // 아이디 그냥 이메일 사용
				} else {
					loginId = ifOrderMaster.getMemNo() + "@member";
				}

			} else {
				loginId = ifOrderMaster.getChannelOrderNo() + "@guest";

			}
			
			tbMember.setLoginId(loginId);
					
			
			
			
		} else {

			
			
			System.out.println("getOrderEmail ==> " + ifOrderMaster.getOrderEmail());

			// if(ifOrderMaster.getOrderEmail())

			if (!ifOrderMaster.getOrderEmail().equals("") && ifOrderMaster.getOrderEmail() != null) {
				// loginId = ifOrderMaster.getOrderEmail(); // 아이디 그냥 이메일 사용
				tbMember = jpaTbMemberRepository.findByLoginId(ifOrderMaster.getOrderEmail());
			} else {
				// loginId = ifOrderMaster.getMemNo() + "@guest";
				tbMember = jpaTbMemberRepository.findByLoginId(ifOrderMaster.getMemNo() + "@member");
			}



			if (tbMember == null) {
				tbMember = new TbMember(ifOrderMaster);
				tbMember.setCustGb("01");

				String loginId = "";

				if (!ifOrderMaster.getMemNo().equals("0")) {
					if (!ifOrderMaster.getOrderEmail().equals("") && ifOrderMaster.getOrderEmail() != null) {
						loginId = ifOrderMaster.getOrderEmail(); // 아이디 그냥 이메일 사용
					} else {
						loginId = ifOrderMaster.getMemNo() + "@member";
					}

				} else {
					loginId = tbMember.getCustId().toString() + "@guest";

				}

				tbMember.setLoginId(loginId);


			}

		}
		


    	//비회원인경우 일련번호로 채번하여 아이디 생성
		// TbMember tbMember =
		// //jpaTbMemberRepository.findByCustId(Long.parseLong(ifOrderMaster.getMemNo()));
		// if(tbMember == null){
		// tbMember = new TbMember(ifOrderMaster);
		// }
        tbMember.setCustTel(ifOrderMaster.getOrderTel());
        tbMember.setCustHp(ifOrderMaster.getOrderTel());
        tbMember.setCustZipcode(ifOrderMaster.getOrderZipcode());
        tbMember.setCustAddr1(ifOrderMaster.getOrderAddr1());
        tbMember.setCustAddr2(ifOrderMaster.getOrderAddr2());



        em.persist(tbMember);

        return tbMember;
    }

    private TbMemberAddress saveTbMemberAddress(IfOrderMaster ifOrderMaster, TbMember tbMember) {

		System.out.println(tbMember.getCustId());
		System.out.println(tbMember.getCustNm());

        TbMemberAddress tbMemberAddress = jpaTbMemberAddressRepository.findByCustId(tbMember.getCustId());
        if(tbMemberAddress == null){
            tbMemberAddress = new TbMemberAddress(ifOrderMaster, tbMember);
        }
        tbMemberAddress.setDeliTel(ifOrderMaster.getReceiverTel());
        tbMemberAddress.setDeliHp(ifOrderMaster.getReceiverTel());
        tbMemberAddress.setDeliZipcode(ifOrderMaster.getReceiverZipcode());
        tbMemberAddress.setDeliAddr1(ifOrderMaster.getReceiverAddr1());
        tbMemberAddress.setDeliAddr2(ifOrderMaster.getReceiverAddr2());

        tbMemberAddress.setDeliNm(ifOrderMaster.getReceiverName());

        em.persist(tbMemberAddress);

        return tbMemberAddress;
    }

    /**
     * tbOrderDetail의 상태(statusCd)를 바꿔줌
     * @param tbOrderDetail
     * @return
     */
    @Transactional
    public TbOrderDetail changeOneToStatusCd(TbOrderDetail tbOrderDetail) {
        // todo(완) : 2021-10-12 공급사 주문 (scmNo가 63,64)인 경우 tb_order_detail 을 만들고 상태에 대한 변경은 없음.
        if (tbOrderDetail.getScmNo().equals(StringFactory.getScmNo63())
                || tbOrderDetail.getScmNo().equals(StringFactory.getScmNo64())) {
            log.debug("공급사 (scmNo : " + tbOrderDetail.getScmNo() + ") 주문입니다.");
            return null;
        }

        IfOrderMaster ifOrderMaster1 = tbOrderDetail.getIfOrderMaster();
        if(!tbOrderDetail.getIfOrderMaster().getChannelOrderStatus().equals(StringFactory.getStrPOne())){
            log.debug("해당 주문의 orderStatus가 p1이 아닙니다. orderId : " + tbOrderDetail.getOrderId() + ", orderSeq : " + tbOrderDetail.getOrderSeq());
            ifOrderMaster1.setIfStatus(StringFactory.getGbFour());
            jpaIfOrderMasterRepository.save(ifOrderMaster1);
            return null;
        }
        List<TbOrderDetail> tbOrderDetailList = jpaTbOrderDetailRepository.findByOrderId(tbOrderDetail.getOrderId());
        List<Integer> resList = new ArrayList<>();
        for(TbOrderDetail td : tbOrderDetailList){
//            if(!td.getStatusCd().equals(StringFactory.getStrA01())){
//              log.debug("이미 status 변경 과정을 거친 주문입니다.");
//              continue;
//            }
            String url = serverChangeStatusUrl + "?orderId=" + td.getOrderId() + "&orderSeq=" + td.getOrderSeq();
            log.debug("===== url : " + url);
            int res = this.get(url);
            resList.add(res);
        }
        int successNum = 0;
        for(int res : resList){
            if(res == 200){ // 03 :
                successNum++;
            }
        }
        IfOrderMaster ifOrderMaster = jpaIfOrderMasterRepository.findByChannelGbAndChannelOrderNo(StringFactory.getGbOne(), tbOrderDetail.getChannelOrderNo()); // 채널은 01 하드코딩
        if(successNum == resList.size()){
            ifOrderMaster.setIfStatus(StringFactory.getGbThree());
        }
        else{
            ifOrderMaster.setIfStatus(StringFactory.getGbFour());
        }
        jpaIfOrderMasterRepository.save(ifOrderMaster);
        return null;
    }

    private int get(String requestURL) {
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            getRequest.addHeader("x-api-key", ""); //KEY 입력

            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                System.out.println(body);
                return 200;
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
                log.debug("response is error : " + response.getStatusLine().getStatusCode());
                return -1;
            }
        } catch (Exception e){
            System.err.println(e.toString());
            log.debug(e.toString());
        }
        return -1;
    }
}
