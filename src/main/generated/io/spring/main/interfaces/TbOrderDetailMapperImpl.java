package io.spring.main.interfaces;

import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.TbOrderDetail;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-25T01:11:16+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.2 (AdoptOpenJDK)"
)
@Component
public class TbOrderDetailMapperImpl implements TbOrderDetailMapper {

    @Override
    public TbOrderDetail to(String orderId, String orderSeq, IfOrderDetail id, Ititmm it) {
        if ( orderId == null && orderSeq == null && id == null && it == null ) {
            return null;
        }

        TbOrderDetail tbOrderDetail = new TbOrderDetail();

        if ( orderId != null ) {
            tbOrderDetail.setOrderId( orderId );
        }
        if ( orderSeq != null ) {
            tbOrderDetail.setOrderSeq( orderSeq );
        }
        if ( id != null ) {
            tbOrderDetail.setAssortGb( id.getChannelGoodsType() );
            tbOrderDetail.setSalePrice( id.getGoodsPrice() );
            tbOrderDetail.setGoodsPrice( id.getFixedPrice() );
            tbOrderDetail.setDeliMethod( id.getDeliveryMethodGb() );
            tbOrderDetail.setQty( id.getGoodsCnt() );
            tbOrderDetail.setOptionInfo( id.getChannelOptionInfo() );
            tbOrderDetail.setGoodsNm( id.getChannelGoodsNm() );
            tbOrderDetail.setGoodsDcPrice( id.getGoodsDcPrice() );
            tbOrderDetail.setMemberDcPrice( id.getMemberDcPrice() );
            tbOrderDetail.setCouponDcPrice( id.getCouponDcPrice() );
            tbOrderDetail.setAdminDcPrice( id.getAdminDcPrice() );
            tbOrderDetail.setDeliPrice( id.getDeliPrice() );
            tbOrderDetail.setChannelOrderNo( id.getChannelOrderNo() );
            tbOrderDetail.setChannelOrderSeq( id.getChannelOrderSeq() );
            tbOrderDetail.setOptionTextInfo( id.getOptionTextInfo() );
            tbOrderDetail.setListImageData( id.getListImageData() );
            tbOrderDetail.setOptionPrice( id.getOptionPrice() );
            tbOrderDetail.setOptionTextPrice( id.getOptionTextPrice() );
            tbOrderDetail.setDeliveryInfo( id.getDeliveryInfo() );
            tbOrderDetail.setScmNo( id.getScmNo() );
            tbOrderDetail.setClaimHandleMode( id.getClaimHandleMode() );
            tbOrderDetail.setClaimHandleReason( id.getClaimHandleReason() );
            tbOrderDetail.setClaimHandleDetailReason( id.getClaimHandleDetailReason() );
        }
        if ( it != null ) {
            tbOrderDetail.setItemId( it.getItemId() );
            tbOrderDetail.setAssortId( it.getAssortId() );
            tbOrderDetail.setRegId( it.getRegId() );
            tbOrderDetail.setUpdId( it.getUpdId() );
            tbOrderDetail.setRegDt( it.getRegDt() );
            tbOrderDetail.setUpdDt( it.getUpdDt() );
        }
        tbOrderDetail.setLastCategoryId( "01" );
        tbOrderDetail.setLastGb( "01" );
        tbOrderDetail.setStorageId( "000002" );

        return tbOrderDetail;
    }

    @Override
    public TbOrderDetail copy(TbOrderDetail tbOrderDetail) {
        if ( tbOrderDetail == null ) {
            return null;
        }

        TbOrderDetail tbOrderDetail1 = new TbOrderDetail();

        tbOrderDetail1.setRegId( tbOrderDetail.getRegId() );
        tbOrderDetail1.setUpdId( tbOrderDetail.getUpdId() );
        tbOrderDetail1.setRegDt( tbOrderDetail.getRegDt() );
        tbOrderDetail1.setUpdDt( tbOrderDetail.getUpdDt() );
        tbOrderDetail1.setOrderId( tbOrderDetail.getOrderId() );
        tbOrderDetail1.setOrderSeq( tbOrderDetail.getOrderSeq() );
        tbOrderDetail1.setStatusCd( tbOrderDetail.getStatusCd() );
        tbOrderDetail1.setAssortGb( tbOrderDetail.getAssortGb() );
        tbOrderDetail1.setAssortId( tbOrderDetail.getAssortId() );
        tbOrderDetail1.setItemId( tbOrderDetail.getItemId() );
        tbOrderDetail1.setGoodsNm( tbOrderDetail.getGoodsNm() );
        tbOrderDetail1.setOptionInfo( tbOrderDetail.getOptionInfo() );
        tbOrderDetail1.setSetGb( tbOrderDetail.getSetGb() );
        tbOrderDetail1.setSetOrderId( tbOrderDetail.getSetOrderId() );
        tbOrderDetail1.setSetOrderSeq( tbOrderDetail.getSetOrderSeq() );
        tbOrderDetail1.setQty( tbOrderDetail.getQty() );
        tbOrderDetail1.setItemAmt( tbOrderDetail.getItemAmt() );
        tbOrderDetail1.setGoodsPrice( tbOrderDetail.getGoodsPrice() );
        tbOrderDetail1.setSalePrice( tbOrderDetail.getSalePrice() );
        tbOrderDetail1.setGoodsDcPrice( tbOrderDetail.getGoodsDcPrice() );
        tbOrderDetail1.setMemberDcPrice( tbOrderDetail.getMemberDcPrice() );
        tbOrderDetail1.setCouponDcPrice( tbOrderDetail.getCouponDcPrice() );
        tbOrderDetail1.setAdminDcPrice( tbOrderDetail.getAdminDcPrice() );
        tbOrderDetail1.setDcSumPrice( tbOrderDetail.getDcSumPrice() );
        tbOrderDetail1.setDeliPrice( tbOrderDetail.getDeliPrice() );
        tbOrderDetail1.setDeliMethod( tbOrderDetail.getDeliMethod() );
        tbOrderDetail1.setChannelOrderNo( tbOrderDetail.getChannelOrderNo() );
        tbOrderDetail1.setChannelOrderSeq( tbOrderDetail.getChannelOrderSeq() );
        tbOrderDetail1.setLastGb( tbOrderDetail.getLastGb() );
        tbOrderDetail1.setLastCategoryId( tbOrderDetail.getLastCategoryId() );
        tbOrderDetail1.setStorageId( tbOrderDetail.getStorageId() );
        tbOrderDetail1.setOptionTextInfo( tbOrderDetail.getOptionTextInfo() );
        tbOrderDetail1.setListImageData( tbOrderDetail.getListImageData() );
        tbOrderDetail1.setOptionPrice( tbOrderDetail.getOptionPrice() );
        tbOrderDetail1.setOptionTextPrice( tbOrderDetail.getOptionTextPrice() );
        tbOrderDetail1.setDeliveryInfo( tbOrderDetail.getDeliveryInfo() );
        tbOrderDetail1.setScmNo( tbOrderDetail.getScmNo() );
        tbOrderDetail1.setParentOrderSeq( tbOrderDetail.getParentOrderSeq() );
        tbOrderDetail1.setClaimHandleMode( tbOrderDetail.getClaimHandleMode() );
        tbOrderDetail1.setClaimHandleReason( tbOrderDetail.getClaimHandleReason() );
        tbOrderDetail1.setClaimHandleDetailReason( tbOrderDetail.getClaimHandleDetailReason() );
        tbOrderDetail1.setItitmm( tbOrderDetail.getItitmm() );
        tbOrderDetail1.setIfOrderMaster( tbOrderDetail.getIfOrderMaster() );
        tbOrderDetail1.setTbOrderMaster( tbOrderDetail.getTbOrderMaster() );

        return tbOrderDetail1;
    }
}
