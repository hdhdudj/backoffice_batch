package io.spring.main.apis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.order.JpaIfOrderDetailRepository;
import io.spring.main.jparepos.order.JpaIfOrderMasterRepository;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.util.StringFactory;
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
    private final ObjectMapper objectMapper;
    private final CommonXmlParse commonXmlParse;
    private final List<String> orderSearchGotListPropsMap;


    // 고도몰에서 일주일치 주문을 땡겨와서 if_order_master, if_order_detail에 저장하는 함수
    @Transactional
    public void saveIfTables(String startDt, String endDt){
        List<OrderSearchData> orderSearchDataList = retrieveOrders("2106281109256643", startDt, endDt);

        // 1. if table 저장
        for(OrderSearchData orderSearchData : orderSearchDataList){
            this.saveIfOrderMaster(orderSearchData); // if_order_master : tb_member, tb_member_address, tb_order_master, tb_order_history  * 여기서 ifNo 생성
            this.saveIfOrderDetail(orderSearchData); // if_order_detail : tb_order_detail
//            if(jpaIfOrderDetailRepository.findByChannelOrderNo(Long.toString(orderSearchData.getGoodsNo())).size() == 0){
//                this.saveIfGoodsOption(goodsSearchData); // if_goods_option : itvari, ititmm
//            }
        }
    }

    private void saveIfOrderMaster(OrderSearchData orderSearchData) {
        String ifNo;
        // ifNo 채번
        IfOrderMaster ioMaster = jpaIfOrderMasterRepository.findByChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
        if(ioMaster == null){
            ifNo = StringUtils.leftPad(jpaSequenceDataRepository.nextVal(StringFactory.getSeqItasrtStr()), 9, '0');
        }
        else {
            ifNo = ioMaster.getIfNo();
        }
        orderSearchData.setIfNo(ifNo);
        IfOrderMaster ifOrderMaster = jpaIfOrderMasterRepository.findByChannelOrderNo(Long.toString(orderSearchData.getOrderNo()));
        if(ifOrderMaster == null){
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
        ifOrderMaster.setOrderId(orderSearchData.getMemId().split("@")[0]);
        System.out.println("----- "+ifOrderMaster.toString());
        jpaIfOrderMasterRepository.save(ifOrderMaster);
    }

    private void saveIfOrderDetail(OrderSearchData orderSearchData) {
        for(OrderSearchData.OrderGoodsData orderGoodsData : orderSearchData.getOrderGoodsData()){
            IfOrderDetail ifOrderDetail = jpaIfOrderDetailRepository.findByIfNoAndChannelGoodsNo(orderSearchData.getIfNo(), orderGoodsData.getGoodsNo());
            if(ifOrderDetail == null){
                ifOrderDetail = new IfOrderDetail(orderSearchData);
                String seq = jpaIfOrderDetailRepository.findMaxIfNoSeq();
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
            ifOrderDetail.setChannelGoodsType(orderGoodsData.getGoodsType());
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
            ifOrderDetail.setDeliveryMethodGb(orderGoodsData.getDeliveryMethodFl());
            ifOrderDetail.setDeliPrice(orderGoodsData.getGoodsDeliveryCollectPrice());
            ifOrderDetail.setOrderId(orderSearchData.getMemId().split("@")[0]);

            jpaIfOrderDetailRepository.save(ifOrderDetail);
        }
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

}
