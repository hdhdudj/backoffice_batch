package io.spring.main.interfaces;

import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.OrderSearchData.OrderGoodsData;
import io.spring.main.model.order.entity.IfOrderDetail;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-25T01:11:17+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.2 (AdoptOpenJDK)"
)
@Component
public class IfOrderDetailMapperImpl implements IfOrderDetailMapper {

    @Override
    public IfOrderDetail to(OrderSearchData os, OrderGoodsData og) {
        if ( os == null && og == null ) {
            return null;
        }

        String ifNo = null;
        if ( os != null ) {
            ifNo = os.getIfNo();
        }

        IfOrderDetail ifOrderDetail = new IfOrderDetail( ifNo );

        if ( os != null ) {
            if ( os.getOrderNo() != null ) {
                ifOrderDetail.setChannelOrderNo( String.valueOf( os.getOrderNo() ) );
            }
        }
        if ( og != null ) {
            if ( og.getSno() != null ) {
                ifOrderDetail.setChannelOrderSeq( String.valueOf( og.getSno() ) );
            }
            ifOrderDetail.setChannelOrderStatus( og.getOrderStatus() );
            ifOrderDetail.setChannelGoodsNo( og.getGoodsNo() );
            if ( og.getOptionSno() != null ) {
                ifOrderDetail.setChannelOptionsNo( String.valueOf( og.getOptionSno() ) );
            }
            ifOrderDetail.setChannelOptionInfo( og.getOptionInfo() );
            ifOrderDetail.setChannelGoodsNm( og.getGoodsNm() );
            ifOrderDetail.setChannelGoodsType( og.getGoodsType() );
            if ( og.getParentGoodsNo() != null ) {
                ifOrderDetail.setChannelParentGoodsNo( String.valueOf( og.getParentGoodsNo() ) );
            }
            ifOrderDetail.setCouponDcPrice( og.getCouponGoodsDcPrice() );
            ifOrderDetail.setDeliPrice( og.getGoodsDeliveryCollectPrice() );
            ifOrderDetail.setDeliveryInfo( og.getDeliveryCond() );
            ifOrderDetail.setDeliveryMethodGb( og.getDeliveryMethodFl() );
            ifOrderDetail.setGoodsCnt( og.getGoodsCnt() );
            ifOrderDetail.setGoodsPrice( og.getGoodsPrice() );
            ifOrderDetail.setGoodsDcPrice( og.getGoodsDcPrice() );
            ifOrderDetail.setMemberDcPrice( og.getMemberDcPrice() );
            ifOrderDetail.setGoodsModelNo( og.getGoodsModelNo() );
            ifOrderDetail.setDivisionUseMileage( og.getDivisionUseMileage() );
            ifOrderDetail.setDivisionGoodsDeliveryUseDeposit( og.getDivisionGoodsDeliveryUseDeposit() );
            ifOrderDetail.setDivisionGoodsDeliveryUseMileage( og.getDivisionGoodsDeliveryUseMileage() );
            ifOrderDetail.setDivisionCouponOrderDcPrice( og.getDivisionCouponOrderDcPrice() );
            ifOrderDetail.setDivisionUseDeposit( og.getDivisionUseDeposit() );
            ifOrderDetail.setDivisionCouponOrderMileage( og.getDivisionCouponOrderMileage() );
            ifOrderDetail.setAddGoodsPrice( og.getAddGoodsPrice() );
            ifOrderDetail.setOptionTextInfo( og.getOptionTextInfo() );
            ifOrderDetail.setListImageData( og.getListImageData() );
            ifOrderDetail.setOptionPrice( og.getOptionPrice() );
            ifOrderDetail.setOptionTextPrice( og.getOptionTextPrice() );
            ifOrderDetail.setFixedPrice( og.getFixedPrice() );
            ifOrderDetail.setCostPrice( og.getCostPrice() );
            ifOrderDetail.setMemberOverlapDcPrice( og.getMemberOverlapDcPrice() );
            ifOrderDetail.setScmNo( og.getScmNo() );
        }

        return ifOrderDetail;
    }
}
