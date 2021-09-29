package io.spring.main.apis;

import io.spring.main.DataShareBean;
import io.spring.main.util.StringFactory;
import io.spring.main.jparepos.goods.JpaTmmapiRepository;
import io.spring.main.model.goods.GoodsInsertData;
import io.spring.main.model.goods.entity.Tmmapi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:godourl.yml")
@ComponentScan(basePackages={"io.spring.main"})
public class XmlSave {
    private final JpaTmmapiRepository jpaTmmapiRepository;
    private final GoodsInsert goodsInsert;
    private final DataShareBean<Tmmapi> dataShareBean;

    // 고도몰 관련 값들
    @Value("${pKey}")
    private String pKey;
    @Value("${key}")
    private String key;
    @Value("${url.godo.goodsInsert}")
    private String goodsInsertUrl;
    // xml 저장 주소
    @Value("${url.server.xmlUrl}")
    private String xmlSaveUrl;

    @Transactional
    public void getXmlMap(){
        // tmmapi에서 joinStatus가 02인 애들 찾아오기 (tmitem도 엮여서 옴)
        List<Tmmapi> tmmapiList = jpaTmmapiRepository.findByJoinStatus(StringFactory.getGbTwo()); // 02

        GoodsInsertData goodsInsertData = null;
        for(Tmmapi tmmapi : tmmapiList){
            // tmmapi, tmitem에서 해당 상품정보 불러서 전달용 객체로 만들기
            goodsInsertData = goodsInsert.makeGoodsSearchObject(tmmapi);
            // 객체를 고도몰 api 모양으로 만들기

            String xmlTest = goodsInsert.makeGoodsSearchXml(goodsInsertData, tmmapi.getAssortId());

            // map에 저장
            dataShareBean.putData(xmlTest,tmmapi);
        }
    }
}
