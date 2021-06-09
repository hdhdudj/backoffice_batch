package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.jparepos.common.JpaSequenceDataRepository;
import io.spring.main.jparepos.goods.*;
import io.spring.main.model.goods.entity.Itasrt;
import io.spring.main.model.goods.entity.Tmmapi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:godourl.yml")
public class GoodsInsert {
    private final JpaIfGoodsMasterRepository jpaIfGoodsMasterRepository;
    private final JpaIfGoodsOptionRepository jpaIfGoodsOptionRepository;
    private final JpaIfGoodsTextOptionRepository jpaIfGoodsTextOptionRepository;
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
    private final JpaTmmapiRepository jpaTmmapiRepository;
    private final JpaTmitemRepository jpaTmitemRepository;
    private final ObjectMapper objectMapper;

    // 고도몰 관련 값들
    @Value("${pKey}")
    private String pKey;
    @Value("${key}")
    private String key;
    @Value("${url.goodsInsert}")
    private String goodsInsertUrl;

    // tmmapi, tmitem을 뒤져서 jobStatus가 01인 애들을 훑어서 고도몰에 보낸 후 joinStatus를 02로 바꿈.
    public void insertGoods() {
        // tmmapi에서 joinStatus가 01인 애들 찾아오기 (tmitem도 엮여서 옴)
        List<Tmmapi> tmmapiList = jpaTmmapiRepository.findByJoinStatus(StringFactory.getGbOne()); // 02
        System.out.println("+++++ tmmapiList.size() : " + tmmapiList.size());
        // tmmapi에서 해당 상품정보 불러서 고도몰 api 모양으로 만들기
        for(Tmmapi tmmapi : tmmapiList){
            makeGoodsSearchApi(tmmapi);
        }
        // tmitem에서 해당 상품정보 불러서 고도몰 api 모양으로 만들기
        // api 전송
    }

    private void makeGoodsSearchApi(Tmmapi tmmapi) {
    }
}
