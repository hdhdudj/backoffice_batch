package io.spring.main.interfaces;

import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.OrderSearchData.OrderInfoData;
import io.spring.main.model.order.entity.IfOrderMaster;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-25T01:11:17+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.2 (AdoptOpenJDK)"
)
@Component
public class IfOrderMasterMapperImpl implements IfOrderMasterMapper {

    @Override
    public OrderInfoData toDto(IfOrderMaster e) {
        if ( e == null ) {
            return null;
        }

        OrderInfoData orderInfoData = new OrderInfoData();

        orderInfoData.setOrderName( e.getOrderName() );
        orderInfoData.setOrderEmail( e.getOrderEmail() );
        orderInfoData.setOrderZipcode( e.getOrderZipcode() );
        orderInfoData.setReceiverName( e.getReceiverName() );
        orderInfoData.setReceiverZipcode( e.getReceiverZipcode() );
        orderInfoData.setOrderMemo( e.getOrderMemo() );

        return orderInfoData;
    }

    @Override
    public IfOrderMaster to(OrderSearchData os, OrderInfoData oi) {
        if ( os == null && oi == null ) {
            return null;
        }

        OrderSearchData orderSearchData = null;

        IfOrderMaster ifOrderMaster = new IfOrderMaster( orderSearchData );

        if ( os != null ) {
            if ( os.getOrderNo() != null ) {
                ifOrderMaster.setChannelOrderNo( String.valueOf( os.getOrderNo() ) );
            }
            ifOrderMaster.setChannelOrderStatus( os.getOrderStatus() );
            ifOrderMaster.setPayGb( os.getSettleKind() );
            ifOrderMaster.setPayAmt( os.getSettlePrice() );
            ifOrderMaster.setOrderEmail( os.getOrderEmail() );
            ifOrderMaster.setPayDt( os.getPaymentDt() );
            ifOrderMaster.setIfNo( os.getIfNo() );
            if ( os.getMemNo() != null ) {
                ifOrderMaster.setMemNo( String.valueOf( os.getMemNo() ) );
            }
            ifOrderMaster.setOrderDate( os.getOrderDate() );
            ifOrderMaster.setTotalGoodsPrice( os.getTotalGoodsPrice() );
            ifOrderMaster.setTotalDeliveryCharge( os.getTotalDeliveryCharge() );
            ifOrderMaster.setTotalGoodsDcPrice( os.getTotalGoodsDcPrice() );
            ifOrderMaster.setTotalMemberDcPrice( os.getTotalMemberDcPrice() );
            ifOrderMaster.setTotalMemberOverlapDcPrice( os.getTotalMemberOverlapDcPrice() );
            ifOrderMaster.setTotalCouponGoodsDcPrice( os.getTotalCouponGoodsDcPrice() );
            ifOrderMaster.setTotalCouponOrderDcPrice( os.getTotalCouponOrderDcPrice() );
            ifOrderMaster.setTotalCouponDeliveryDcPrice( os.getTotalCouponDeliveryDcPrice() );
            ifOrderMaster.setTotalMileage( os.getTotalMileage() );
            ifOrderMaster.setTotalGoodsMileage( os.getTotalGoodsMileage() );
            ifOrderMaster.setTotalMemberMileage( os.getTotalMemberMileage() );
            ifOrderMaster.setTotalCouponGoodsMileage( os.getTotalCouponGoodsMileage() );
            ifOrderMaster.setTotalCouponOrderMileage( os.getTotalCouponOrderMileage() );
        }
        if ( oi != null ) {
            ifOrderMaster.setOrderTel( oi.getOrderCellPhone() );
            ifOrderMaster.setOrderAddr1( oi.getOrderAddress() );
            ifOrderMaster.setOrderAddr2( oi.getOrderAddressSub() );
            ifOrderMaster.setReceiverTel( oi.getReceiverCellPhone() );
            ifOrderMaster.setReceiverAddr1( oi.getReceiverAddress() );
            ifOrderMaster.setReceiverAddr2( oi.getReceiverAddressSub() );
            ifOrderMaster.setOrderName( oi.getOrderName() );
            ifOrderMaster.setOrderZipcode( oi.getOrderZipcode() );
            ifOrderMaster.setReceiverName( oi.getReceiverName() );
            ifOrderMaster.setReceiverZipcode( oi.getReceiverZipcode() );
            ifOrderMaster.setOrderMemo( oi.getOrderMemo() );
        }

        return ifOrderMaster;
    }
}
