package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.enums.DeliveryMethod;
import io.spring.main.enums.GoodsOrAddGoods;
import io.spring.main.enums.TrdstOrderStatus;
import io.spring.main.jparepos.common.*;
import io.spring.main.jparepos.goods.*;
import io.spring.main.jparepos.order.*;
import io.spring.main.model.goods.*;
import io.spring.main.model.goods.entity.*;
import io.spring.main.model.order.*;
import io.spring.main.model.order.entity.*;
import io.spring.main.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

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
    private final EntityManager em;
    private final ObjectMapper objectMapper;
    private final CommonXmlParse commonXmlParse;
    private final List<String> orderSearchGotListPropsMap;


    // 고도몰에서 일주일치 주문을 땡겨와서 if_order_master, if_order_detail에 저장하는 함수
    @Transactional
    public void saveIfTables(String orderNo, String startDt, String endDt){
        List<OrderSearchData> orderSearchDataList = retrieveOrders(orderNo, startDt, endDt);

        // 1. if table 저장
        for(OrderSearchData orderSearchData : orderSearchDataList){
            IfOrderMaster ifOrderMaster = this.saveIfOrderMaster(orderSearchData); // if_order_master : tb_member, tb_member_address, tb_order_master  * 여기서 ifNo 생성
            if(ifOrderMaster == null){
                continue;
            }
            this.saveIfOrderDetail(orderSearchData); // if_order_detail : tb_order_detail, tb_order_history
        }
    }

    public List<IfOrderMaster> getIfOrderMasterListWhereIfStatus01(){
        List<IfOrderMaster> ifOrderMasterList = jpaIfOrderMasterRepository.findByIfStatus(StringFactory.getGbOne()); // if_order_master에서 if_status가 01인 애 전부 가져옴
        return ifOrderMasterList;
    }

    private IfOrderMaster saveIfOrderMaster(OrderSearchData orderSearchData) {
        String ifNo;
        // ifNo 채번
        IfOrderMaster ioMaster = jpaIfOrderMasterRepository.findByChannelGbAndChannelOrderNo(StringFactory.getGbOne(), Long.toString(orderSearchData.getOrderNo())); // 채널은 01 하드코딩
        IfOrderMaster ifOrderMaster = null;
        if(ioMaster == null){ // insert
            String num = jpaSequenceDataRepository.nextVal(StringFactory.getSeqIforderMaster());
            if(num == null){
                num = StringFactory.getStrOne();
            }
//            ifNo = Utilities.getStringNo('O', num, 9);
            ifNo = StringUtils.leftPad(num, 9, '0');
        }
        else { // update 되는 일은 원칙적으로 없음.
            return null;
//            ifNo = ioMaster.getIfNo();
//            ifOrderMaster = ioMaster;
        }
        orderSearchData.setIfNo(ifNo);
//        IfOrderMaster ifOrderMaster2 = null;
        if(ifOrderMaster == null){ // insert
            ifOrderMaster = objectMapper.convertValue(orderSearchData, IfOrderMaster.class);
            ifOrderMaster.setIfStatus(StringFactory.getGbOne());
        }
        // not null 컬럼들 설정
        ifOrderMaster.setChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
        ifOrderMaster.setChannelOrderStatus(orderSearchData.getOrderStatus());
        ifOrderMaster.setCustomerId((String)(((Map<String, Object>)(Utilities.makeStringToMap(orderSearchData.getAddField()).get("1"))).get("data")));
        ifOrderMaster.setOrderDate(orderSearchData.getOrderDate());
        // https://docs.google.com/spreadsheets/d/1Uou2nQFtydm6Jam8LXG77v1uVnqBbxJDxCq0OcJ2MHc/edit#gid=841263646
        ifOrderMaster.setOrderName(orderSearchData.getOrderInfoData().get(0).getOrderName());
        ifOrderMaster.setOrderTel(orderSearchData.getOrderInfoData().get(0).getOrderCellPhone());
        ifOrderMaster.setOrderZipcode(orderSearchData.getOrderInfoData().get(0).getOrderZipcode());
        ifOrderMaster.setOrderAddr1(orderSearchData.getOrderInfoData().get(0).getOrderAddress());
        ifOrderMaster.setOrderAddr2(orderSearchData.getOrderInfoData().get(0).getOrderAddressSub());
        ifOrderMaster.setReceiverName(orderSearchData.getOrderInfoData().get(0).getReceiverName());
        ifOrderMaster.setReceiverTel(orderSearchData.getOrderInfoData().get(0).getReceiverCellPhone());
        ifOrderMaster.setReceiverZipcode(orderSearchData.getOrderInfoData().get(0).getReceiverZipcode());
        ifOrderMaster.setReceiverAddr1(orderSearchData.getOrderInfoData().get(0).getReceiverAddress());
        ifOrderMaster.setReceiverAddr2(orderSearchData.getOrderInfoData().get(0).getReceiverAddressSub());
        ifOrderMaster.setOrderMemo(orderSearchData.getOrderInfoData().get(0).getOrderMemo());
        if(orderSearchData.getOrderGoodsData() != null && orderSearchData.getOrderGoodsData().size() > 0){
            ifOrderMaster.setPayDt(orderSearchData.getOrderGoodsData().get(0).getPaymentDt());
        }
//        ifOrderMaster.setOrderId(orderSearchData.getMemId().split(StringFactory.getStrAt())[0]); // tb_order_master.order_id

        ifOrderMaster.setPayGb(orderSearchData.getSettleKind());
        ifOrderMaster.setPayAmt(orderSearchData.getSettlePrice());
        ifOrderMaster.setOrderDate(orderSearchData.getOrderDate());

        jpaIfOrderMasterRepository.save(ifOrderMaster);

        return ifOrderMaster;
    }

    private void saveIfOrderDetail(OrderSearchData orderSearchData) {
        //
        if(orderSearchData.getOrderGoodsData() == null){
            log.debug("orderSearchData.orderGoodsData가 null 입니다.");
            return;
        }
        Map<Long, OrderSearchData.AddGoodsData> addGoodsDataMap = new HashMap<>();
        if(orderSearchData.getAddGoodsData() != null && orderSearchData.getAddGoodsData().size() > 0){
            for(OrderSearchData.AddGoodsData addGoodsData : orderSearchData.getAddGoodsData()){
                addGoodsDataMap.put(addGoodsData.getSno(), addGoodsData);
            }
        }
        for(OrderSearchData.OrderGoodsData orderGoodsData : orderSearchData.getOrderGoodsData()){
            IfOrderDetail ifOrderDetail = jpaIfOrderDetailRepository.findByIfNoAndChannelOrderNoAndChannelOrderSeq(orderSearchData.getIfNo(), Long.toString(orderGoodsData.getOrderNo()), Long.toString(orderGoodsData.getSno()));
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
            ifOrderDetail.setChannelOrderSeq(Long.toString(orderGoodsData.getSno()));
            ifOrderDetail.setChannelOrderStatus(orderGoodsData.getOrderStatus());
            ifOrderDetail.setChannelGoodsType(this.changeGoodsAddGoodsToCode(orderGoodsData.getGoodsType()));
            ifOrderDetail.setChannelGoodsNo(orderGoodsData.getGoodsNo());
            ifOrderDetail.setChannelOptionsNo(Long.toString(orderGoodsData.getOptionSno()));
            ifOrderDetail.setChannelOptionInfo(orderGoodsData.getOptionInfo());
            // goodsNm 가져오기
            List<GoodsSearchData> goodsSearchDataList = goodsSearch.retrieveGoods(orderGoodsData.getGoodsNo(), "", "", "");
            ifOrderDetail.setChannelGoodsNm(orderGoodsData.getGoodsNm());
//            ifOrderDetail.setChannelGoodsNm(jpaTmitemRepository.f/indByChannelGbAndChannelGoodsNoAndChannelOptionsNo(StringFactory.getGbOne(), orderGoodsData.getGoodsNo(), Long.toString(orderGoodsData.getOptionSno())).geta);
            //
            ifOrderDetail.setChannelParentGoodsNo(Long.toString(orderGoodsData.getParentGoodsNo()));
            ifOrderDetail.setGoodsCnt(orderGoodsData.getGoodsCnt());
            ifOrderDetail.setGoodsPrice(orderGoodsData.getGoodsPrice());
            ifOrderDetail.setGoodsDcPrice(orderGoodsData.getGoodsDcPrice());
            ifOrderDetail.setCouponDcPrice(orderGoodsData.getCouponGoodsDcPrice());
            ifOrderDetail.setMemberDcPrice(orderGoodsData.getMemberDcPrice());
            ifOrderDetail.setDeliveryMethodGb(this.changeDeliMethodToCode(orderGoodsData.getDeliveryMethodFl()));
            ifOrderDetail.setDeliPrice(orderGoodsData.getGoodsDeliveryCollectPrice());
//            ifOrderDetail.setOrderId(orderSearchData.getMemId().split(StringFactory.getStrAt())[0]); // tb_order_detail.order_id
            ifOrderDetail.setDeliveryInfo(orderGoodsData.getDeliveryCond());

            // 21-10-05 추가
            ifOrderDetail.setScmNo(orderGoodsData.getScmNo());

            // 21-10-06 추가
            ifOrderDetail.setListImageData(orderGoodsData.getListImageData());
            ifOrderDetail.setOptionTextInfo(orderGoodsData.getOptionTextInfo());

            em.persist(ifOrderDetail);

            this.saveAddGoods(orderSearchData, ifOrderDetail, addGoodsDataMap);
        }
    }

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
                // goodsNm 가져오기
                List<GoodsSearchData> goodsSearchDataList = goodsSearch.retrieveGoods(Long.toString(agData.getAddGoodsNo()), "", "", "");
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
        if(TrdstOrderStatus.valueOf(goodsType) == null){
            return false;
        }
        else {
            return true;
        }
    }

    // goods xml 받아오는 함수
    public List<OrderSearchData> retrieveOrders(String orderNo, String fromDt, String toDt) {
        //OpenApi호출
        fromDt = fromDt == null? "":fromDt;
        toDt = toDt == null? "":toDt;
        String urlstr = orderSearchUrl + StringFactory.getStrQuestion() + StringFactory.getOrderSearchParams()[0] + StringFactory.getStrEqual() +
                pKey + StringFactory.getStrAnd() +StringFactory.getOrderSearchParams()[1]
                + StringFactory.getStrEqual() + key
                + StringFactory.getStrAnd() + StringFactory.getOrderSearchParams()[3]
                + StringFactory.getStrEqual() + fromDt
                + StringFactory.getStrAnd() + StringFactory.getOrderSearchParams()[4]
                + StringFactory.getStrEqual() + toDt
                + "&dateType=modify"
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
//            if(!ifOrderDetail.getChannelOrderStatus().equals(StringFactory.getStrPOne())){
//                log.debug("결제 완료되지 않은 주문입니다.");
//                continue;
//            }
            TbOrderDetail tbOrderDetail = this.saveTbOrderDetail(ifOrderDetail);
            if(tbOrderDetail != null){
                this.saveTbOrderHistory(ifOrderDetail, tbOrderDetail);
            }
            if(num == 0 && tbOrderDetail != null){
                TbOrderMaster tbOrderMaster = this.saveTbOrderMaster(ifOrderMaster, tbOrderDetail, tbMember, tbMemberAddress);
                em.persist(tbOrderMaster);
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
        Ititmm ititmm = this.getItitmmWithItasrt(tmmapi == null? null : tmmapi.getAssortId(), tmitem == null? StringFactory.getFourStartCd():tmitem.getItemId()); // tmitem이 없으면 0001
        tbOrderDetail = this.saveSingleTbOrderDetail(tbOrderDetail, ifOrderDetail, ititmm);
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
    private TbOrderDetail saveSingleTbOrderDetail(TbOrderDetail outTbOrderDetail, IfOrderDetail ifOrderDetail, Ititmm ititmm) {
        boolean flag = outTbOrderDetail == null; // true : insert, false : update
        TbOrderDetail tbOrderDetail;
        TbOrderDetail compareTbOrderDetail = null;
        // 공급사 주문 (scmNo가 63,64)인 경우 data 생성하지 않음
        if(ifOrderDetail.getScmNo().equals(StringFactory.getScmNo1()) || ifOrderDetail.getScmNo().equals(StringFactory.getScmNo1())){
            log.debug("공급사 (scmNo : " + ifOrderDetail.getScmNo() + ") 주문입니다.");
            return null;
        }

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
            tbOrderDetail = new TbOrderDetail(orderId, orderSeq);
            ifOrderDetail.setOrderId(tbOrderDetail.getOrderId());
            ifOrderDetail.setOrderSeq(tbOrderDetail.getOrderSeq());
            jpaIfOrderDetailRepository.save(ifOrderDetail);
        }
        else { // update
            compareTbOrderDetail = new TbOrderDetail(outTbOrderDetail);
            tbOrderDetail = outTbOrderDetail;

            // trdstOrderStatus를 가지고 있으면 update 하지 않음.
            if(this.isTrdstOrderStatus(compareTbOrderDetail.getStatusCd())){
                log.debug("trdst의 orderStatus를 탄 주문은 update 할 수 없습니다. orderId : " + compareTbOrderDetail.getOrderId() + ", orderSeq : " + compareTbOrderDetail.getSetOrderSeq());
                return null;
            }
        }
        if(ititmm != null){
            tbOrderDetail.setItemId(ititmm.getItemId());
            tbOrderDetail.setAssortId(ititmm.getAssortId());
        }
        tbOrderDetail.setGoodsNm(ifOrderDetail.getChannelGoodsNm());
        tbOrderDetail.setStorageId(StringUtils.leftPad(StringFactory.getStrOne(),6,'0')); // 고도몰 주문(주문자 받는 곳 - 한국창고) : 000001

//        System.out.println("----------------------- : " + tbOrderDetail.getOrderId() + ", " + tbOrderDetail.getOrderSeq());
        String orderStatus = StringFactory.getStrPOne().equals(ifOrderDetail.getChannelOrderStatus())? StringFactory.getStrA01():ifOrderDetail.getChannelOrderStatus();
        tbOrderDetail.setStatusCd(orderStatus); // 고도몰에서는 A01 상태만 가져옴.
        tbOrderDetail.setOptionInfo(ifOrderDetail.getChannelOptionInfo());
        tbOrderDetail.setQty(ifOrderDetail.getGoodsCnt());
//        tbOrderDetail.setGoodsPrice(ifOrderDetail.getGoodsPrice()); // fixedPrice
        float goodsDcPrice = ifOrderDetail.getGoodsDcPrice() == null? 0 : ifOrderDetail.getGoodsDcPrice();
        float memberDcPrice = ifOrderDetail.getMemberDcPrice() == null? 0 : ifOrderDetail.getMemberDcPrice();
        float couponDcPrice = ifOrderDetail.getCouponDcPrice() == null? 0 : ifOrderDetail.getCouponDcPrice();
        float adminDcPrice = ifOrderDetail.getAdminDcPrice() == null? 0 : ifOrderDetail.getAdminDcPrice();
        tbOrderDetail.setGoodsDcPrice(ifOrderDetail.getGoodsDcPrice());
        tbOrderDetail.setMemberDcPrice(ifOrderDetail.getMemberDcPrice());
        tbOrderDetail.setCouponDcPrice(ifOrderDetail.getCouponDcPrice());
//        tbOrderDetail.setAdminDcPrice(ifOrderDetail.getAdminDcPrice()); // 일단 사용안함
        tbOrderDetail.setDcSumPrice(goodsDcPrice + memberDcPrice + couponDcPrice + adminDcPrice);
//        tbOrderDetail.setSalePrice(tbOrderDetail.getGoodsPrice() - tbOrderDetail.getDcSumPrice()); // goodsPrice
        tbOrderDetail.setDeliMethod(ifOrderDetail.getDeliveryMethodGb()); // 추후 수정
        tbOrderDetail.setDeliPrice(ifOrderDetail.getDeliPrice());
        tbOrderDetail.setChannelOrderNo(ifOrderDetail.getChannelOrderNo());
        tbOrderDetail.setChannelOrderSeq(ifOrderDetail.getChannelOrderSeq());

        tbOrderDetail.setLastGb(StringUtils.leftPad(StringFactory.getStrOne(),2,'0')); // 01 하드코딩
        tbOrderDetail.setLastCategoryId(StringUtils.leftPad(StringFactory.getStrOne(),2,'0')); // 01 하드코딩


        // 21-09-28 추가
        tbOrderDetail.setGoodsPrice(ifOrderDetail.getFixedPrice());
        tbOrderDetail.setSalePrice(ifOrderDetail.getGoodsPrice());
        tbOrderDetail.setOptionTextInfo(ifOrderDetail.getOptionTextInfo());
        tbOrderDetail.setListImageData(ifOrderDetail.getListImageData());
        tbOrderDetail.setOptionPrice(ifOrderDetail.getOptionPrice());
        tbOrderDetail.setOptionTextPrice(ifOrderDetail.getOptionTextPrice());
        tbOrderDetail.setDeliveryInfo(ifOrderDetail.getDeliveryInfo());
        
        // 21-10-01 추가
        tbOrderDetail.setStorageId(StringUtils.leftPad(StringFactory.getStrOne(),6,'0')); // 채널별 하드코딩. 고도몰(channelGb='01')의 경우 '000001'
        tbOrderDetail.setAssortGb(ifOrderDetail.getChannelGoodsType()); // 001 : 상품, 002 : 추가상품 (ifOrderDetail.channelGoodsType)

        // 21-10-06 추가
        tbOrderDetail.setScmNo(ifOrderDetail.getScmNo());
        IfOrderDetail ifOrderDetailParent = jpaIfOrderDetailRepository.findByIfNoAndChannelOrderNoAndChannelOrderSeq(ifOrderDetail.getIfNo(), ifOrderDetail.getChannelOrderNo(), ifOrderDetail.getParentChannelOrderSeq());
        if(ifOrderDetailParent == null){
            tbOrderDetail.setParentOrderSeq(ifOrderDetail.getOrderSeq());
        }
        else {
            tbOrderDetail.setParentOrderSeq(StringUtils.leftPad(ifOrderDetailParent.getIfNoSeq(), 4,'0'));
        }

        // TbOrderDetail가 기존 대비 변한 값이 있는지 확인하고 변하지 않았으면 null을 return 해준다. (history 쪽 함수에서 null을 받으면 업데이트하지 않도록)
        em.persist(tbOrderDetail);
        if(!flag && compareTbOrderDetail.equals(tbOrderDetail)){
            tbOrderDetail = null;
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
//        System.out.println("------ + " + ifOrderMaster.toString());
//        int effectIfOrderDetailListNum = ifOrderMaster.getIfOrderDetail().stream().filter(x->x.getChannelOrderStatus().equals(StringFactory.getStrPOne())).collect(Collectors.toList()).size();

        TbOrderMaster tbOrderMaster = jpaTbOrderMasterRepository.findByChannelOrderNo(ifOrderMaster.getChannelOrderNo());
        if(tbOrderMaster == null){
//            String ordId = jpaTbOrderMasterRepository.findMaxOrderId();
//            if(ordId == null){
//                ordId = StringUtils.leftPad(StringFactory.getStrOne(), 9, '0');
//            }
//            else{
//                ordId = Utilities.plusOne(ordId, 9);
//            }
//            tbOrderMaster = new TbOrderMaster(ordId);
//            String orderId = Utilities.getStringNo('O',ifOrderMaster.getIfNo(),9);
            tbOrderMaster = new TbOrderMaster(tbOrderDetail.getOrderId());
            ifOrderMaster.setOrderId(tbOrderMaster.getOrderId());
            jpaIfOrderMasterRepository.save(ifOrderMaster);
        }
        tbOrderMaster.setChannelOrderNo(ifOrderMaster.getChannelOrderNo());
        tbOrderMaster.setFirstOrderId(ifOrderMaster.getChannelOrderNo());
        tbOrderMaster.setOrderStatus(ifOrderMaster.getChannelOrderStatus());
        tbOrderMaster.setChannelGb(ifOrderMaster.getChannelGb());
        tbOrderMaster.setCustId(Long.parseLong(ifOrderMaster.getMemNo()));
        tbOrderMaster.setReceiptAmt(ifOrderMaster.getPayAmt());
        tbOrderMaster.setCustPcode(ifOrderMaster.getCustomerId());
        tbOrderMaster.setOrderMemo(ifOrderMaster.getOrderMemo());
        tbOrderMaster.setOrderDate(ifOrderMaster.getOrderDate());

        tbOrderMaster.setCustId(tbMember.getCustId());
        tbOrderMaster.setDeliId(tbMemberAddress.getDeliId());
        tbOrderMaster.setOrderAmt(ifOrderMaster.getPayAmt());
        tbOrderMaster.setPayGb(ifOrderMaster.getPayGb());
        tbOrderMaster.setPayDt(Utilities.dateToLocalDateTime(ifOrderMaster.getPayDt()));
//        tbOrderMaster.setOrderId(ifOrderMaster.getOrderId());
        tbOrderMaster.setFirstOrderGb(StringFactory.getGbOne()); // 첫주문 01 그다음 02
        tbOrderMaster.setOrderGb(StringFactory.getGbOne()); // 01 : 주문, 02 : 반품, 03 : 교환

//        if(effectIfOrderDetailListNum > 0){
//            em.persist(tbOrderMaster);
//        }

        return tbOrderMaster;
    }

    private void saveTbOrderHistory(IfOrderDetail ifOrderDetail, TbOrderDetail tbOrderDetail) {
        if(tbOrderDetail == null){
            log.debug("tbOrderDetail의 값이 변화 없음.");
            return;
        }
        TbOrderHistory tbOrderHistory = jpaTbOrderHistoryRepository.findByOrderIdAndOrderSeqAndEffEndDt(tbOrderDetail.getOrderId(), tbOrderDetail.getOrderSeq(), Utilities.getStringToDate(StringFactory.getDoomDay()));
        if(tbOrderHistory == null){ // insert
            tbOrderHistory = new TbOrderHistory(tbOrderDetail);
        }
        else{ // update
            tbOrderHistory.setEffEndDt(new Date());
            tbOrderHistory.setLastYn(StringUtils.leftPad(StringFactory.getStrOne(), 3,'0')); // 001 하드코딩

            TbOrderHistory newTbOrderHistory = new TbOrderHistory(tbOrderDetail);
//            newTbOrderHistory.setOrderSeq(tbOrderHistory.getOrderSeq());
            newTbOrderHistory.setStatusCd(tbOrderDetail.getStatusCd()); // 추후 수정
            em.persist(newTbOrderHistory);
        }
        tbOrderHistory.setStatusCd(tbOrderDetail.getStatusCd()); // 추후 수정
        em.persist(tbOrderHistory);
    }

    private TbMember saveTbMember(IfOrderMaster ifOrderMaster) {
        TbMember tbMember = jpaTbMemberRepository.findByCustId(Long.parseLong(ifOrderMaster.getMemNo()));
        if(tbMember == null){
            tbMember = new TbMember(ifOrderMaster);
        }
        tbMember.setCustTel(ifOrderMaster.getOrderTel());
        tbMember.setCustHp(ifOrderMaster.getOrderTel());
        tbMember.setCustZipcode(ifOrderMaster.getOrderZipcode());
        tbMember.setCustAddr1(ifOrderMaster.getOrderAddr1());
        tbMember.setCustAddr2(ifOrderMaster.getOrderAddr2());
        em.persist(tbMember);

        return tbMember;
    }

    private TbMemberAddress saveTbMemberAddress(IfOrderMaster ifOrderMaster, TbMember tbMember) {
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
        IfOrderMaster ifOrderMaster1 = tbOrderDetail.getIfOrderMaster();
        if(!tbOrderDetail.getIfOrderMaster().getChannelOrderStatus().equals(StringFactory.getStrPOne())){
            log.debug("해당 주문의 orderStatus가 p1이 아닙니다.");
            ifOrderMaster1.setIfStatus(StringFactory.getGbFour());
            jpaIfOrderMasterRepository.save(ifOrderMaster1);
            return null;
        }
        List<TbOrderDetail> tbOrderDetailList = jpaTbOrderDetailRepository.findByOrderId(tbOrderDetail.getOrderId());
        List<Integer> resList = new ArrayList<>();
        for(TbOrderDetail td : tbOrderDetailList){
            if(!td.getStatusCd().equals(StringFactory.getStrA01())){
              log.debug("이미 status 변경 과정을 거친 주문입니다.");
              continue;
            }
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
                return -1;
            }
        } catch (Exception e){
            System.err.println(e.toString());
        }
        return -1;
    }
}
