package io.spring.main.apis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.JpaItitmmRepository;
import io.spring.main.jparepos.order.*;
import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.*;
import io.spring.main.model.order.idclass.TbOrderDetailId;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;
import javax.rmi.CORBA.Util;
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
    @Value("${url.orderSearch}")
    private String orderSearchUrl;

    private final GoodsSearch goodsSearch;
    private final JpaIfOrderMasterRepository jpaIfOrderMasterRepository;
    private final JpaIfOrderDetailRepository jpaIfOrderDetailRepository;
    private final JpaSequenceDataRepository jpaSequenceDataRepository;
    private final JpaTbOrderMasterRepository jpaTbOrderMasterRepository;
    private final JpaTbOrderHistoryRepository jpaTbOrderHistoryRepository;
    private final JpaTbMemberRepository jpaTbMemberRepository;
    private final JpaTbMemberAddressRepository jpaTbMemberAddressRepository;
    private final JpaTbOrderDetailRepository jpaTbOrderDetailRepository;
    private final JpaItitmmRepository jpaItitmmRepository;
    private final EntityManager em;
    private final ObjectMapper objectMapper;
    private final CommonXmlParse commonXmlParse;
    private final List<String> orderSearchGotListPropsMap;


    // 고도몰에서 일주일치 주문을 땡겨와서 if_order_master, if_order_detail에 저장하는 함수
    @Transactional
    public void saveIfTables(String startDt, String endDt){
        List<OrderSearchData> orderSearchDataList = retrieveOrders("2106301555509122", startDt, endDt);

        // 1. if table 저장
        for(OrderSearchData orderSearchData : orderSearchDataList){
            this.saveIfOrderMaster(orderSearchData); // if_order_master : tb_member, tb_member_address, tb_order_master  * 여기서 ifNo 생성
            this.saveIfOrderDetail(orderSearchData); // if_order_detail : tb_order_detail, tb_order_history
        }
    }

    public List<IfOrderMaster> getIfOrderMasterListWhereIfStatus01(){
        List<IfOrderMaster> ifOrderMasterList = jpaIfOrderMasterRepository.findByIfStatus(StringFactory.getGbOne()); // if_order_master에서 if_status가 01인 애 전부 가져옴
        return ifOrderMasterList;
    }

    private void saveIfOrderMaster(OrderSearchData orderSearchData) {
        String ifNo;
        // ifNo 채번
        IfOrderMaster ioMaster = jpaIfOrderMasterRepository.findByChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
        IfOrderMaster ifOrderMaster = null;
        if(ioMaster == null){ // insert
            ifNo = StringUtils.leftPad(jpaSequenceDataRepository.nextVal(StringFactory.getSeqIforderMaster()), 9, '0');
        }
        else { // update
            ifNo = ioMaster.getIfNo();
            ifOrderMaster = ioMaster;
        }
        orderSearchData.setIfNo(ifNo);
        IfOrderMaster ifOrderMaster2 = null;
        if(ifOrderMaster == null){ // insert
            ifOrderMaster = objectMapper.convertValue(orderSearchData, IfOrderMaster.class);
        }
        // not null 컬럼들 설정
        ifOrderMaster.setChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
        ifOrderMaster.setChannelOrderStatus(orderSearchData.getOrderStatus());
        ifOrderMaster.setCustomerId((String)(((Map<String, Object>)(Utilities.makeStringToMap(orderSearchData.getAddField()).get("1"))).get("data")));
        ifOrderMaster.setIfStatus(StringFactory.getGbOne());
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
        ifOrderMaster.setPayDt(orderSearchData.getOrderGoodsData().get(0).getPaymentDt());
        ifOrderMaster.setOrderId(orderSearchData.getMemId().split(StringFactory.getStrAt())[0]);
        em.persist(ifOrderMaster);
    }

    private void saveIfOrderDetail(OrderSearchData orderSearchData) {
        for(OrderSearchData.OrderGoodsData orderGoodsData : orderSearchData.getOrderGoodsData()){
            IfOrderDetail ifOrderDetail = jpaIfOrderDetailRepository.findByIfNoAndChannelGoodsNo(orderSearchData.getIfNo(), orderGoodsData.getGoodsNo());
            if(ifOrderDetail == null){
                ifOrderDetail = new IfOrderDetail(orderSearchData);
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
            ifOrderDetail.setChannelOrderStatus(orderSearchData.getOrderStatus());
            ifOrderDetail.setChannelGoodsType(changeGoodsAddGoodsToCode(orderGoodsData.getGoodsType()));
            ifOrderDetail.setChannelGoodsNo(orderGoodsData.getGoodsNo());
            ifOrderDetail.setChannelOptionsNo(Long.toString(orderGoodsData.getOptionSno()));
            ifOrderDetail.setChannelOptionInfo(orderGoodsData.getOptionInfo());
            // goodsNm 가져오기
            ifOrderDetail.setChannelGoodsNm(goodsSearch.retrieveGoods(orderGoodsData.getGoodsNo(), "", "").get(0).getGoodsNm());
            //
            ifOrderDetail.setChannelParentGoodsNo(Long.toString(orderGoodsData.getParentGoodsNo()));
            ifOrderDetail.setGoodsCnt(orderGoodsData.getGoodsCnt());
            ifOrderDetail.setGoodsPrice(orderGoodsData.getGoodsPrice());
            ifOrderDetail.setGoodsDcPrice(orderGoodsData.getGoodsDcPrice());
            ifOrderDetail.setCouponDcPrice(orderGoodsData.getCouponGoodsDcPrice());
            ifOrderDetail.setMemberDcPrice(orderGoodsData.getMemberDcPrice());
            ifOrderDetail.setDeliveryMethodGb(changeDeliMethodToCode(orderGoodsData.getDeliveryMethodFl()));
            ifOrderDetail.setDeliPrice(orderGoodsData.getGoodsDeliveryCollectPrice());
            ifOrderDetail.setOrderId(orderSearchData.getMemId().split(StringFactory.getStrAt())[0]);

            jpaIfOrderDetailRepository.save(ifOrderDetail);
        }
    }

    private String changeDeliMethodToCode(String deliveryMethodFl) {
        String code;
        if(deliveryMethodFl.equals(StringFactory.getStrDelivery())){
            code = StringFactory.getThreeStartCd(); // 001
        }
        else if(deliveryMethodFl.equals(StringFactory.getStrAir())){
            code = StringUtils.leftPad(StringFactory.getStrTwo(),3,'0');
        }
        else if(deliveryMethodFl.equals(StringFactory.getStrShip())){
            code = StringUtils.leftPad(StringFactory.getStrThree(),3,'0');
        }
        else if(deliveryMethodFl.equals(StringFactory.getStrQuick())){
            code = StringUtils.leftPad(StringFactory.getStrFour(),3,'0');
        }
        else{
            code = StringUtils.leftPad(StringFactory.getStrFive(),3,'0');
        }
        return code;
    }

    // goods -> 001, add_goods -> 002로 반환
    private String changeGoodsAddGoodsToCode(String goodsType) {
        String code = "";
        if(goodsType.equals(StringFactory.getStrGoods())){
            code = StringFactory.getThreeStartCd(); // 001
        }
        else if(goodsType.equals(StringFactory.getStrAddGoods())) {
            code = StringUtils.leftPad(StringFactory.getStrTwo(),3,'0');
        }
        return code;
    }

    // goods xml 받아오는 함수
    public List<OrderSearchData> retrieveOrders(String orderNo, String fromDt, String toDt) {
        //OpenApi호출
        String urlstr = orderSearchUrl + StringFactory.getStrQuestion() + StringFactory.getOrderSearchParams()[0] + StringFactory.getStrEqual() +
                pKey + StringFactory.getStrAnd() +StringFactory.getOrderSearchParams()[1]
                + StringFactory.getStrEqual() + key
                + StringFactory.getStrAnd() + StringFactory.getOrderSearchParams()[3]
                + StringFactory.getStrEqual() + fromDt
                + StringFactory.getStrAnd() + StringFactory.getOrderSearchParams()[4]
                + StringFactory.getStrEqual() + toDt
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
    public void saveOneIfNo(String ifNo, IfOrderMaster ifOrderMaster) {
        // tb_order_master, tb_member, tb_member_address 저장
        TbOrderMaster tbOrderMaster = saveTbOrderMaster(ifOrderMaster);
        TbMember tbMember = saveTbMember(ifOrderMaster);
        saveTbMemberAddress(ifOrderMaster, tbMember);
        
        // tb_order_detail, tb_order_history
        for(IfOrderDetail ifOrderDetail : ifOrderMaster.getIfOrderDetail()){
            TbOrderDetail tbOrderDetail = saveTbOrderDetail(tbOrderMaster, ifOrderDetail);
            saveTbOrderHistory(ifOrderDetail, tbOrderDetail);
        }
        ifOrderMaster.setIfStatus(StringFactory.getGbTwo()); // ifStatus 02로 변경
        em.persist(ifOrderMaster);
    }

    private TbOrderDetail saveTbOrderDetail(TbOrderMaster tbOrderMaster, IfOrderDetail ifOrderDetail) {
        GoodsSearchData goodsSearchData = goodsSearch.retrieveGoods(ifOrderDetail.getChannelGoodsNo(),"","").get(0);
//        System.out.println("----------------------- : " + tbOrderMaster.getOrderId() + " " + goodsSearchData.getGoodsNm());
        TbOrderDetail tbOrderDetail = jpaTbOrderDetailRepository.findByOrderIdAndGoodsNm(tbOrderMaster.getOrderId(), goodsSearchData.getGoodsNm());
        Ititmm ititmm = tbOrderDetail == null? jpaItitmmRepository.findByItemNm(goodsSearchData.getGoodsNm()) : tbOrderDetail.getItitmm();
        if(tbOrderDetail == null){ // insert
            tbOrderDetail = new TbOrderDetail(tbOrderMaster, ititmm);
            String orderSeq = Utilities.plusOne(jpaTbOrderDetailRepository.findMaxOrderSeqWhereOrderId(tbOrderDetail.getOrderId()),3);
            tbOrderDetail.setOrderSeq(orderSeq == null? tbOrderDetail.getOrderSeq() : orderSeq);
        }
        tbOrderDetail.setAssortId(ititmm.getAssortId());
        TbOrderDetail compareTbOrderDetail = new TbOrderDetail(tbOrderDetail);
//        System.out.println("----------------------- : " + tbOrderDetail.getOrderId() + ", " + tbOrderDetail.getOrderSeq());
        tbOrderDetail.setStatusCd(StringFactory.getStrAOne()); // 고도몰에서는 A01 상태만 가져옴.
        tbOrderDetail.setAssortGb(ifOrderDetail.getChannelGoodsType()); // 001 : goods, 002 : add_goods
        tbOrderDetail.setItemId(ititmm.getItemId());
        tbOrderDetail.setGoodsNm(ititmm.getItemNm());
        tbOrderDetail.setOptionInfo(ifOrderDetail.getChannelOptionInfo());
        tbOrderDetail.setQty(ifOrderDetail.getGoodsCnt());
        tbOrderDetail.setGoodsPrice(ifOrderDetail.getGoodsPrice());
        float goodsDcPrice = ifOrderDetail.getGoodsDcPrice() == null? 0 : ifOrderDetail.getGoodsDcPrice();
        float memberDcPrice = ifOrderDetail.getMemberDcPrice() == null? 0 : ifOrderDetail.getMemberDcPrice();
        float couponDcPrice = ifOrderDetail.getCouponDcPrice() == null? 0 : ifOrderDetail.getCouponDcPrice();
        float adminDcPrice = ifOrderDetail.getAdminDcPrice() == null? 0 : ifOrderDetail.getAdminDcPrice();
        tbOrderDetail.setGoodsDcPrice(ifOrderDetail.getGoodsDcPrice());
        tbOrderDetail.setMemberDcPrice(ifOrderDetail.getMemberDcPrice());
        tbOrderDetail.setCouponDcPrice(ifOrderDetail.getCouponDcPrice());
        tbOrderDetail.setAdminDcPrice(ifOrderDetail.getAdminDcPrice());
        tbOrderDetail.setDcSumPrice(goodsDcPrice + memberDcPrice + couponDcPrice + adminDcPrice);
        tbOrderDetail.setSalePrice(tbOrderDetail.getGoodsPrice() - tbOrderDetail.getDcSumPrice());
        tbOrderDetail.setDeliMethod(ifOrderDetail.getDeliveryMethodGb()); // 추후 수정
        tbOrderDetail.setDeliPrice(ifOrderDetail.getDeliPrice());
        tbOrderDetail.setChannelOrderNo(ifOrderDetail.getChannelOrderNo());
        tbOrderDetail.setChannelOrderSeq(ifOrderDetail.getChannelOrderSeq());

        tbOrderDetail.setLastGb(StringUtils.leftPad(StringFactory.getStrOne(),2,'0')); // 01 하드코딩
        tbOrderDetail.setLastCategoryId(StringUtils.leftPad(StringFactory.getStrOne(),2,'0')); // 01 하드코딩
        tbOrderDetail.setStorageId(StringUtils.leftPad(StringFactory.getStrOne(),6,'0'));

        jpaTbOrderDetailRepository.save(tbOrderDetail);
        // TbOrderDetail가 기존 대비 변한 값이 있는지 확인하고 변하지 않았으면 null을 return 해준다. (history 쪽 함수에서 null을 받으면 업데이트하지 않도록)
        if(compareTbOrderDetail.equals(tbOrderDetail)){
            tbOrderDetail = null;
        }
        return tbOrderDetail;
    }

    private TbOrderMaster saveTbOrderMaster(IfOrderMaster ifOrderMaster) {
//        System.out.println("------ + " + ifOrderMaster.toString());
        TbOrderMaster tbOrderMaster = jpaTbOrderMasterRepository.findByChannelOrderNo(ifOrderMaster.getChannelOrderNo());
        if(tbOrderMaster == null){
            String ordId = jpaTbOrderMasterRepository.findMaxOrderId();
            if(ordId == null){
                ordId = StringUtils.leftPad(StringFactory.getStrOne(), 9, '0');
            }
            else{
                ordId = Utilities.plusOne(ordId, 9);
            }
            tbOrderMaster = new TbOrderMaster(ordId);
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

        em.persist(tbOrderMaster);

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
        TbMember tbMember = jpaTbMemberRepository.findByLoginId(ifOrderMaster.getOrderEmail().split(StringFactory.getStrAt())[0]);
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

    private void saveTbMemberAddress(IfOrderMaster ifOrderMaster, TbMember tbMember) {
        TbMemberAddress tbMemberAddress = jpaTbMemberAddressRepository.findByCustId(tbMember.getCustId());
        if(tbMemberAddress == null){
            tbMemberAddress = new TbMemberAddress(ifOrderMaster, tbMember);
        }
        tbMemberAddress.setDeliTel(ifOrderMaster.getReceiverTel());
        tbMemberAddress.setDeliHp(ifOrderMaster.getReceiverTel());
        tbMemberAddress.setDeliZipcode(ifOrderMaster.getReceiverZipcode());
        tbMemberAddress.setDeliAddr1(ifOrderMaster.getReceiverAddr1());
        tbMemberAddress.setDeliAddr2(ifOrderMaster.getReceiverAddr2());

        em.persist(tbMemberAddress);
    }
}
