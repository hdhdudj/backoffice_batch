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
     * OrderSearch의 list를 가지고 있는 맵
     * @return
     */
    @Bean
    public Map<String, List<Object>> orderSearchListMap() {
        Map<String, List<Object>> orderSearchListMap = new HashMap() {{
            put(StringFactory.getStrOrderInfoData(), new ArrayList<OrderSearchData.OrderInfoData>());
            put(StringFactory.getStrOrderDeliveryData(), new ArrayList<OrderSearchData.OrderDeliveryData>());
            put(StringFactory.getStrGiftData(), new ArrayList<OrderSearchData.GiftData>());
            put(StringFactory.getStrOrderGoodsData(), new ArrayList<OrderSearchData.OrderGoodsData>());
            put(StringFactory.getStrAddGoodsData(), new ArrayList<OrderSearchData.AddGoodsData>());
            put(StringFactory.getStrClaimData(), new ArrayList<OrderSearchData.ClaimData>());
            put(StringFactory.getStrExchangeInfoData(), new ArrayList<OrderSearchData.ExchageInfoData>());
            put(StringFactory.getStrOrderConsultData(), new ArrayList<OrderSearchData.OrderConsultData>());
        }};

        log.debug("ClassMapConfiguration orderSearchListMap 실행.");

        return orderSearchListMap;
    }

    /**
     * OrderSearch 클래스 정보를 가지고 있는 맵
     * @return
     */
    @Bean
    public Map<String, Class> orderSearchClassMap() {
        Map<String, Class> orderSearchClassMap = new HashMap() {{
            put(StringFactory.getStrOrderInfoData(), OrderSearchData.OrderInfoData.class);
            put(StringFactory.getStrOrderDeliveryData(), OrderSearchData.OrderDeliveryData.class);
            put(StringFactory.getStrGiftData(), OrderSearchData.GiftData.class);
            put(StringFactory.getStrOrderGoodsData(), OrderSearchData.OrderGoodsData.class);
            put(StringFactory.getStrAddGoodsData(), OrderSearchData.AddGoodsData.class);
            put(StringFactory.getStrClaimData(), OrderSearchData.ClaimData.class);
            put(StringFactory.getStrExchangeInfoData(), OrderSearchData.ExchageInfoData.class);
            put(StringFactory.getStrOrderConsultData(), OrderSearchData.OrderConsultData.class);
        }};

        log.debug("ClassMapConfiguration orderSearchClassMap 실행.");

        return orderSearchClassMap;
    }

    /**
     * GoodsSearch의 list를 가지고 있는 맵
     * @return
     */
    @Bean
    public Map<String, List<Object>> goodsSearchListMap() {
        Map<String, List<Object>> goodsSearchListMap = new HashMap() {{
            put(StringFactory.getStrOptionData(), new ArrayList<GoodsSearchData.OptionData>());
            put(StringFactory.getStrTextOptionData(), new ArrayList<GoodsSearchData.TextOptionData>());
            put(StringFactory.getStrAddGoodsData(), new ArrayList<GoodsSearchData.AddGoodsData>());
            put(StringFactory.getStrGoodsMustInfoData(), new ArrayList<GoodsSearchData.GoodsMustInfoData>());
            put(StringFactory.getStrStepData(), new ArrayList<GoodsSearchData.StepData>());
        }};

        log.debug("ClassMapConfiguration goodsSearchListMap 실행.");

        return goodsSearchListMap;
    }

    /**
     * GoodSearch 클래스 정보를 가지고 있는 맵
     * @return
     */
    @Bean
    public Map<String, Class> goodsSearchClassMap() {
        Map<String, Class> goodsSearchClassMap = new HashMap() {{
            put(StringFactory.getStrGoodsMustInfoData(), GoodsSearchData.GoodsMustInfoData.class);
            put(StringFactory.getStrStepData(), GoodsSearchData.StepData.class);
            put(StringFactory.getStrOption(), GoodsSearchData.OptionData.class);
            put(StringFactory.getStrTextOptionData(), GoodsSearchData.TextOptionData.class);
            put(StringFactory.getStrAddGoodsData(), GoodsSearchData.AddGoodsData.class);
        }};

        log.debug("ClassMapConfiguration goodsSearchClassMap 실행.");

        return goodsSearchClassMap;
    }

    /**
     * GoodSearch list를 가진 props의 정보를 가지고 있는 맵
     * @return
     */
    @Bean
    public List<String> goodsSearchGotListPropsMap() {
        List<String> goodsSearchGotListPropsMap = Arrays.asList(StringFactory.getStrGoodsNoData());

        log.debug("ClassMapConfiguration goodsSearchClassMap 실행.");

        return goodsSearchGotListPropsMap;
    }
}
