package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.jparepos.order.JpaIfOrderDetailRepository;
import io.spring.main.jparepos.order.JpaIfOrderMasterRepository;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.util.StringFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final JpaIfOrderMasterRepository jpaIfOrderMasterRepository;
    private final JpaIfOrderDetailRepository jpaIfOrderDetailRepository;
    private final ObjectMapper objectMapper;
    private final CommonXmlParse commonXmlParse;
    private final List<String> orderSearchGotListPropsMap;


    // 고도몰에서 일주일치 주문을 땡겨와서 if_order_master, if_order_detail에 저장하는 함수
    @Transactional
    public void saveIfTables(String startDt, String endDt){
        List<OrderSearchData> orderSearchDataList = retrieveOrders(null, startDt, endDt);

        for(OrderSearchData orderSearchData : orderSearchDataList){
            System.out.println(orderSearchData.getOrderGoodsData().get(0).getOrderNo());

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
                + "&orderNo=2106231208001241";
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
