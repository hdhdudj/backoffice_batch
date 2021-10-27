package io.spring.main.interfaces;

import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfGoodsAddGoods;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-25T01:11:17+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.2 (AdoptOpenJDK)"
)
@Component
public class IfGoodsAddGoodsMapperImpl implements IfGoodsAddGoodsMapper {

    @Override
    public IfGoodsAddGoods to(String channelGb, long goodsNo, GoodsSearchData gd) {
        if ( channelGb == null && gd == null ) {
            return null;
        }

        IfGoodsAddGoods ifGoodsAddGoods = new IfGoodsAddGoods();

        if ( channelGb != null ) {
            ifGoodsAddGoods.setChannelGb( channelGb );
        }
        if ( gd != null ) {
            ifGoodsAddGoods.setRegDt( gd.getRegDt() );
            ifGoodsAddGoods.setAssortId( gd.getAssortId() );
            if ( gd.getScmNo() != null ) {
                ifGoodsAddGoods.setScmNo( String.valueOf( gd.getScmNo() ) );
            }
            ifGoodsAddGoods.setGoodsNm( gd.getGoodsNm() );
            ifGoodsAddGoods.setBrandCd( gd.getBrandCd() );
            ifGoodsAddGoods.setMakerNm( gd.getMakerNm() );
            ifGoodsAddGoods.setGoodsPrice( gd.getGoodsPrice() );
            ifGoodsAddGoods.setSoldOutFl( gd.getSoldOutFl() );
        }
        ifGoodsAddGoods.setGoodsNo( String.valueOf( goodsNo ) );

        return ifGoodsAddGoods;
    }

    @Override
    public IfGoodsAddGoods copy(IfGoodsAddGoods ig) {
        if ( ig == null ) {
            return null;
        }

        IfGoodsAddGoods ifGoodsAddGoods = new IfGoodsAddGoods();

        ifGoodsAddGoods.setRegId( ig.getRegId() );
        ifGoodsAddGoods.setUpdId( ig.getUpdId() );
        ifGoodsAddGoods.setRegDt( ig.getRegDt() );
        ifGoodsAddGoods.setUpdDt( ig.getUpdDt() );
        ifGoodsAddGoods.setChannelGb( ig.getChannelGb() );
        ifGoodsAddGoods.setGoodsNo( ig.getGoodsNo() );
        ifGoodsAddGoods.setAddGoodsNo( ig.getAddGoodsNo() );
        ifGoodsAddGoods.setAddGoodsId( ig.getAddGoodsId() );
        ifGoodsAddGoods.setAssortId( ig.getAssortId() );
        ifGoodsAddGoods.setScmNo( ig.getScmNo() );
        ifGoodsAddGoods.setTitle( ig.getTitle() );
        ifGoodsAddGoods.setGoodsNm( ig.getGoodsNm() );
        ifGoodsAddGoods.setBrandCd( ig.getBrandCd() );
        ifGoodsAddGoods.setOptionNm( ig.getOptionNm() );
        ifGoodsAddGoods.setMakerNm( ig.getMakerNm() );
        ifGoodsAddGoods.setGoodsPrice( ig.getGoodsPrice() );
        ifGoodsAddGoods.setStockCnt( ig.getStockCnt() );
        ifGoodsAddGoods.setViewFl( ig.getViewFl() );
        ifGoodsAddGoods.setSoldOutFl( ig.getSoldOutFl() );
        ifGoodsAddGoods.setUploadStatus( ig.getUploadStatus() );

        return ifGoodsAddGoods;
    }
}
