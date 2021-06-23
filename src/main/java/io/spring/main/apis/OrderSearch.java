package io.spring.main.apis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.jparepos.order.JpaIfOrderDetailRepository;
import io.spring.main.jparepos.order.JpaIfOrderMasterRepository;
import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.util.StringFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
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

    private final JpaIfOrderMasterRepository jpaIfOrderMasterRepository;
    private final JpaIfOrderDetailRepository jpaIfOrderDetailRepository;
    private final ObjectMapper objectMapper;
    private final CommonFunctions commonFunctions;
    private final Map<String, List<Object>> listMap;
    private final Map<String, Class> classMap;


    // 고도몰에서 일주일치 주문을 땡겨와서 if_order_master, if_order_detail에 저장하는 함수
    @Transactional
    public void saveIfTables(String startDt, String endDt){
        List<OrderSearchData> orderSearchDataList = retrieveOrders(null, startDt, endDt);

        for(OrderSearchData orderSearchData : orderSearchDataList){

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
                + StringFactory.getStrEqual() + toDt;
//                + "&orderNo=1000000040";
//        System.out.println("##### " + urlstr);

        NodeList nodeList =  CommonFunctions.getXmlNodes(urlstr);

        List<OrderSearchData> orderSearchData = new ArrayList<>();

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
                        if (mNode.getNodeName() == StringFactory.getStrOrderData()) {
                            OrderSearchData map = makeOrderMaster(mNode);
                            orderSearchData.add(map);
                            // order master array append
                        }
                    }
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println(orderSearchData.size());
                    System.out.println("-----------------------------------------------------------------");
                }
            }
        }
        return orderSearchData;
    }

    private OrderSearchData makeOrderMaster(Node root) {
        Map<String, Object> map = new HashMap<String, Object>();

//        List<OrderSearchData.OrderInfoData> orderInfoDataList = new ArrayList<>();
//        List<OrderSearchData.OrderDeliveryData> orderDeliveryDataList = new ArrayList<>();
//        List<OrderSearchData.GiftData> giftDataList = new ArrayList<>();
//        List<OrderSearchData.OrderGoodsData> orderGoodsDataList = new ArrayList<>();
//        List<OrderSearchData.AddGoodsData> addGoodsDataList = new ArrayList<>();
//        List<OrderSearchData.ClaimData> claimDataList = new ArrayList<>();
//        List<OrderSearchData.ExchageInfoData> exchageInfoDataList = new ArrayList<>();
//        List<OrderSearchData.OrderConsultData> orderConsultDataList = new ArrayList<>();

        NodeList cNodes = root.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node cNode = cNodes.item(i);
            /////////////
            for(String key : classMap.keySet()){
                if(key.equals(cNode.getNodeName())){
                    listMap.get(key).add(commonFunctions.makeSimpleNodeToObj(cNode, classMap.get(key)));
                }
            }
            if (cNode.getNodeName() == StringFactory.getStrClaimData()) {
                System.out.println("claimData 데이타 이상 - 확인필요");
            }

            if ("확인필요한값!".equals((String) CommonFunctions.getNodeValue(cNode))) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("데이타 이상 - 확인필요");
                System.out.println("-----------------------------------------------------------------");
            } else {
                map.put(CommonFunctions.controlSnakeCaseException(cNode.getNodeName()), CommonFunctions.getNodeValue(cNode));
            }
            //////////////
//            if (cNode.getNodeName().equals(StringFactory.getStrOrderInfoData())) { // orderInfoData
//                OrderSearchData.OrderInfoData orderSearchData = makeOrderInfoData(cNode);
//                orderInfoDataList.add(orderSearchData);
//            } else if (cNode.getNodeName().equals(StringFactory.getStrOrderDeliveryData())) { // orderDeliveryData
//                OrderSearchData.OrderDeliveryData orderDeliveryData = makeOrderDeliveryData(cNode);
//                orderDeliveryDataList.add(orderDeliveryData);
//            } else if (cNode.getNodeName().equals(StringFactory.getStrGiftData())) { // giftData
//                OrderSearchData.GiftData giftData = makeGiftData(cNode);
//                giftDataList.add(giftData);
//            } else if (cNode.getNodeName().equals(StringFactory.getStrOrderGoodsData())) { // orderGoodsData
//                OrderSearchData.OrderGoodsData orderGoodsData = makeOrderGoodsData(cNode);
//                orderGoodsDataList.add(orderGoodsData);
//            }
//            else if (cNode.getNodeName().equals(StringFactory.getStrAddGoodsData())) { // addGoodsData
//                // Map<String, Object> addGoodsData = makeAddGoodsData(cNode);
//                OrderSearchData.AddGoodsData addGoodsData = makeAddGoodsData(cNode);
//                addGoodsDataList.add(addGoodsData);
//            }
//            else if (cNode.getNodeName().equals(StringFactory.getStrClaimData())) { // claimData
//                OrderSearchData.ClaimData claimData = makeClaimData(cNode);
//                claimDataList.add(claimData);
//            }
//            else if (cNode.getNodeName().equals(StringFactory.getStrExchangeInfoData())) { // exchageInfoData
//                OrderSearchData.ExchageInfoData exchageInfoData = makeExchangeInfoData(cNode);
//                exchageInfoDataList.add(exchageInfoData);
//            }
//            else if (cNode.getNodeName().equals(StringFactory.getStrOrderConsultData())) { // orderConsultData
//                OrderSearchData.OrderConsultData orderConsultData = makeOrderConsultData(cNode);
//                orderConsultDataList.add(orderConsultData);
//            }
//            else {
//
//                if (cNode.getNodeName() == StringFactory.getStrClaimData()) {
//                    System.out.println("claimData 데이타 이상 - 확인필요");
//                }
//
//                if ("확인필요한값!".equals((String) CommonFunctions.getNodeValue(cNode))) {
//                    System.out.println("-----------------------------------------------------------------");
//                    System.out.println("데이타 이상 - 확인필요");
//                    System.out.println("-----------------------------------------------------------------");
//                } else {
////                    log.debug("----- " + cNode.getNodeName() + " : "+  CommonFunctions.getNodeValue(cNode));
//                    map.put(CommonFunctions.controlSnakeCaseException(cNode.getNodeName()), CommonFunctions.getNodeValue(cNode));
//                }
//
//            }

//            map.put(StringFactory.getStrOrderInfoData(), orderInfoDataList);
//            map.put(StringFactory.getStrOrderDeliveryData(), orderDeliveryDataList);
//            map.put(StringFactory.getStrOrderGoodsData(), orderGoodsDataList);
//            map.put(StringFactory.getStrAddGoodsData(), addGoodsDataList);
//            map.put(StringFactory.getStrClaimData(), claimDataList);
//            map.put(StringFactory.getStrExchangeInfoData(), exchageInfoDataList);
//            map.put(StringFactory.getStrOrderConsultData(), orderConsultDataList);

            for(String key : listMap.keySet()){
                map.put(key,listMap.get(key));
            }
        }

        OrderSearchData o = objectMapper.convertValue(map, OrderSearchData.class);
        return o;
    }

    private OrderSearchData.OrderGoodsData makeOrderGoodsData(Node cNode) {
        Map<String, Object> map = new HashMap<String, Object>();
        NodeList cNodes = cNode.getChildNodes();
        List<String> goodsNoDataList = new ArrayList<>();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node node = cNodes.item(i);
            map.put(CommonFunctions.getNodeValue(node).toString(),CommonFunctions.getNodeValue(node));
        }
        map.put("goodsNoData", goodsNoDataList);
        OrderSearchData.OrderGoodsData o = objectMapper.convertValue(map, OrderSearchData.OrderGoodsData.class);
        return o;
    }

    private OrderSearchData.GiftData makeGiftData(Node cNode) {
        return commonFunctions.makeSimpleNodeToObj(cNode, OrderSearchData.GiftData.class);
    }

    private OrderSearchData.AddGoodsData makeAddGoodsData(Node cNode) {

        return commonFunctions.makeSimpleNodeToObj(cNode, OrderSearchData.AddGoodsData.class);
    }

    private OrderSearchData.ClaimData makeClaimData(Node cNode) {

        return commonFunctions.makeSimpleNodeToObj(cNode, OrderSearchData.ClaimData.class);
    }

    private OrderSearchData.ExchageInfoData makeExchangeInfoData(Node cNode) {

        return commonFunctions.makeSimpleNodeToObj(cNode, OrderSearchData.ExchageInfoData.class);
    }

    private OrderSearchData.OrderConsultData makeOrderConsultData(Node cNode) {

        return commonFunctions.makeSimpleNodeToObj(cNode, OrderSearchData.OrderConsultData.class);
    }

    private OrderSearchData.OrderDeliveryData makeOrderDeliveryData(Node cNode) {

        return commonFunctions.makeSimpleNodeToObj(cNode, OrderSearchData.OrderDeliveryData.class);
    }

    private OrderSearchData.OrderInfoData makeOrderInfoData(Node cNode) {

        return commonFunctions.makeSimpleNodeToObj(cNode, OrderSearchData.OrderInfoData.class);
    }
}
