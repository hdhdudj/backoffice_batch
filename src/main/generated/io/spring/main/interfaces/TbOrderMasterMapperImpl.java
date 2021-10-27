package io.spring.main.interfaces;

import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.entity.TbMember;
import io.spring.main.model.order.entity.TbMemberAddress;
import io.spring.main.model.order.entity.TbOrderMaster;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-25T01:11:16+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.2 (AdoptOpenJDK)"
)
@Component
public class TbOrderMasterMapperImpl implements TbOrderMasterMapper {

    @Override
    public TbOrderMaster to(String orderId, IfOrderMaster im, TbMember tm, TbMemberAddress ta) {
        if ( orderId == null && im == null && tm == null && ta == null ) {
            return null;
        }

        String orderId1 = null;
        if ( orderId != null ) {
            orderId1 = orderId;
        }

        TbOrderMaster tbOrderMaster = new TbOrderMaster( orderId1 );

        if ( im != null ) {
            tbOrderMaster.setReceiptAmt( im.getPayAmt() );
            tbOrderMaster.setOrderAmt( im.getPayAmt() );
            tbOrderMaster.setOrderStatus( im.getChannelOrderStatus() );
            tbOrderMaster.setFirstOrderId( im.getChannelOrderNo() );
            tbOrderMaster.setChannelGb( im.getChannelGb() );
            tbOrderMaster.setRegId( im.getRegId() );
            tbOrderMaster.setUpdId( im.getUpdId() );
            tbOrderMaster.setOrderDate( im.getOrderDate() );
            tbOrderMaster.setChannelOrderNo( im.getChannelOrderNo() );
            tbOrderMaster.setOrderMemo( im.getOrderMemo() );
            if ( im.getPayDt() != null ) {
                tbOrderMaster.setPayDt( LocalDateTime.ofInstant( im.getPayDt().toInstant(), ZoneId.of( "UTC" ) ) );
            }
            tbOrderMaster.setPayGb( im.getPayGb() );
            tbOrderMaster.setTotalGoodsPrice( im.getTotalGoodsPrice() );
            tbOrderMaster.setTotalDeliveryCharge( im.getTotalDeliveryCharge() );
            tbOrderMaster.setTotalGoodsDcPrice( im.getTotalGoodsDcPrice() );
            tbOrderMaster.setTotalMemberDcPrice( im.getTotalMemberDcPrice() );
            tbOrderMaster.setTotalMemberOverlapDcPrice( im.getTotalMemberOverlapDcPrice() );
            tbOrderMaster.setTotalCouponGoodsDcPrice( im.getTotalCouponGoodsDcPrice() );
            tbOrderMaster.setTotalCouponOrderDcPrice( im.getTotalCouponOrderDcPrice() );
            tbOrderMaster.setTotalCouponDeliveryDcPrice( im.getTotalCouponDeliveryDcPrice() );
            tbOrderMaster.setTotalMileage( im.getTotalMileage() );
            tbOrderMaster.setTotalGoodsMileage( im.getTotalGoodsMileage() );
            tbOrderMaster.setTotalMemberMileage( im.getTotalMemberMileage() );
            tbOrderMaster.setTotalCouponGoodsMileage( im.getTotalCouponGoodsMileage() );
            tbOrderMaster.setTotalCouponOrderMileage( im.getTotalCouponOrderMileage() );
        }
        if ( tm != null ) {
            tbOrderMaster.setCustId( tm.getCustId() );
        }
        if ( ta != null ) {
            tbOrderMaster.setDeliId( ta.getDeliId() );
        }
        tbOrderMaster.setOrderGb( "01" );
        tbOrderMaster.setFirstOrderGb( "01" );

        return tbOrderMaster;
    }

    @Override
    public TbOrderMaster copy(TbOrderMaster tbOrderMaster) {
        if ( tbOrderMaster == null ) {
            return null;
        }

        String orderId = null;

        orderId = tbOrderMaster.getOrderId();

        TbOrderMaster tbOrderMaster1 = new TbOrderMaster( orderId );

        tbOrderMaster1.setRegId( tbOrderMaster.getRegId() );
        tbOrderMaster1.setUpdId( tbOrderMaster.getUpdId() );
        tbOrderMaster1.setRegDt( tbOrderMaster.getRegDt() );
        tbOrderMaster1.setUpdDt( tbOrderMaster.getUpdDt() );
        tbOrderMaster1.setFirstOrderId( tbOrderMaster.getFirstOrderId() );
        tbOrderMaster1.setOrderDate( tbOrderMaster.getOrderDate() );
        tbOrderMaster1.setOrderStatus( tbOrderMaster.getOrderStatus() );
        tbOrderMaster1.setChannelGb( tbOrderMaster.getChannelGb() );
        tbOrderMaster1.setCustId( tbOrderMaster.getCustId() );
        tbOrderMaster1.setDeliId( tbOrderMaster.getDeliId() );
        tbOrderMaster1.setOrderAmt( tbOrderMaster.getOrderAmt() );
        tbOrderMaster1.setReceiptAmt( tbOrderMaster.getReceiptAmt() );
        tbOrderMaster1.setFirstOrderGb( tbOrderMaster.getFirstOrderGb() );
        tbOrderMaster1.setOrderGb( tbOrderMaster.getOrderGb() );
        tbOrderMaster1.setChannelOrderNo( tbOrderMaster.getChannelOrderNo() );
        tbOrderMaster1.setCustPcode( tbOrderMaster.getCustPcode() );
        tbOrderMaster1.setOrderMemo( tbOrderMaster.getOrderMemo() );
        tbOrderMaster1.setPayDt( tbOrderMaster.getPayDt() );
        tbOrderMaster1.setPayGb( tbOrderMaster.getPayGb() );
        tbOrderMaster1.setTotalGoodsPrice( tbOrderMaster.getTotalGoodsPrice() );
        tbOrderMaster1.setTotalDeliveryCharge( tbOrderMaster.getTotalDeliveryCharge() );
        tbOrderMaster1.setTotalGoodsDcPrice( tbOrderMaster.getTotalGoodsDcPrice() );
        tbOrderMaster1.setTotalMemberDcPrice( tbOrderMaster.getTotalMemberDcPrice() );
        tbOrderMaster1.setTotalMemberOverlapDcPrice( tbOrderMaster.getTotalMemberOverlapDcPrice() );
        tbOrderMaster1.setTotalCouponGoodsDcPrice( tbOrderMaster.getTotalCouponGoodsDcPrice() );
        tbOrderMaster1.setTotalCouponOrderDcPrice( tbOrderMaster.getTotalCouponOrderDcPrice() );
        tbOrderMaster1.setTotalCouponDeliveryDcPrice( tbOrderMaster.getTotalCouponDeliveryDcPrice() );
        tbOrderMaster1.setTotalMileage( tbOrderMaster.getTotalMileage() );
        tbOrderMaster1.setTotalGoodsMileage( tbOrderMaster.getTotalGoodsMileage() );
        tbOrderMaster1.setTotalMemberMileage( tbOrderMaster.getTotalMemberMileage() );
        tbOrderMaster1.setTotalCouponGoodsMileage( tbOrderMaster.getTotalCouponGoodsMileage() );
        tbOrderMaster1.setTotalCouponOrderMileage( tbOrderMaster.getTotalCouponOrderMileage() );

        return tbOrderMaster1;
    }
}
