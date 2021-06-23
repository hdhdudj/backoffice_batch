package io.spring.main.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.util.StringFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClassMapConfiguration {

    /**
     * 해당 class의 list를 가지고 있는 맵
     * @return
     */
    @Bean
    public Map<String, List<Object>> listMap() {
        Map<String, List<Object>> listMap = new HashMap() {{
            put(StringFactory.getStrOrderInfoData(), new ArrayList<OrderSearchData.OrderInfoData>());
            put(StringFactory.getStrOrderDeliveryData(), new ArrayList<OrderSearchData.OrderDeliveryData>());
            put(StringFactory.getStrGiftData(), new ArrayList<OrderSearchData.GiftData>());
            put(StringFactory.getStrOrderGoodsData(), new ArrayList<OrderSearchData.OrderGoodsData>());
            put(StringFactory.getStrAddGoodsData(), new ArrayList<OrderSearchData.AddGoodsData>());
            put(StringFactory.getStrClaimData(), new ArrayList<OrderSearchData.ClaimData>());
            put(StringFactory.getStrExchangeInfoData(), new ArrayList<OrderSearchData.ExchageInfoData>());
            put(StringFactory.getStrOrderConsultData(), new ArrayList<OrderSearchData.OrderConsultData>());
        }};

        log.debug("ClassMapConfiguration listMap 실행.");

        return listMap;
    }

    /**
     * 해당 클래스 정보를 가지고 있는 맵
     * @return
     */
    @Bean
    public Map<String, Class> classMap() {
        Map<String, Class> classmap = new HashMap() {{
            put(StringFactory.getStrOrderInfoData(), OrderSearchData.OrderInfoData.class);
            put(StringFactory.getStrOrderDeliveryData(), OrderSearchData.OrderDeliveryData.class);
            put(StringFactory.getStrGiftData(), OrderSearchData.GiftData.class);
            put(StringFactory.getStrOrderGoodsData(), OrderSearchData.OrderGoodsData.class);
            put(StringFactory.getStrAddGoodsData(), OrderSearchData.AddGoodsData.class);
            put(StringFactory.getStrClaimData(), OrderSearchData.ClaimData.class);
            put(StringFactory.getStrExchangeInfoData(), OrderSearchData.ExchageInfoData.class);
            put(StringFactory.getStrOrderConsultData(), OrderSearchData.OrderConsultData.class);
        }};

        log.debug("ClassMapConfiguration classMap 실행.");

        return classmap;
    }
}
