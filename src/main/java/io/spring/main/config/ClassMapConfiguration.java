package io.spring.main.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.util.StringFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClassMapConfiguration {

    /**
     * GoodSearch에서 list를 가진 props의 정보를 가지고 있는 맵
     * @return
     */
    @Bean
    public List<String> goodsSearchGotListPropsMap() {
        List<String> goodsSearchGotListPropsMap = Arrays.asList("magnifyImageData",
                "detailImageData", "listImageData", "mainImageData"
                , "goodsMustInfoData", "optionData", "addGoodsData", "textOptionData"
        , "stepData", "goodsNoData");

        log.debug("ClassMapConfiguration goodsSearchClassMap 실행.");

        return goodsSearchGotListPropsMap;
    }

    /**
     * OrderSearch에서 list를 가진 props의 정보를 가지고 있는 맵
     * @return
     */
    @Bean
    public List<String> orderSearchGotListPropsMap() {
        List<String> goodsSearchGotListPropsMap = Arrays.asList("orderDeliveryData",
                "orderInfoData", "addGoodsData", "giftData"
                , "orderGoodsData", "exchageInfoData", "claimData");

        log.debug("ClassMapConfiguration orderSearchGotListPropsMap 실행.");

        return goodsSearchGotListPropsMap;
    }
}
