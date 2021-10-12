package io.spring.main.interfaces;

import io.spring.main.model.order.OrderSearchData;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderInfoDataIfOrderMasterMapper<OrderInfoData, IfOrderMaster> extends GenericMapper<OrderInfoData, IfOrderMaster>{

    OrderInfoData toDto(IfOrderMaster e);
    @Mapping(source = "oi.orderCellPhone", target = "orderTel")
    @Mapping(source = "oi.orderAddress", target = "orderAddr2")
    @Mapping(source = "oi.orderAddressSub", target = "orderAddressSub")
    @Mapping(source = "oi.receiverCellPhone", target = "receiverTel")
    @Mapping(source = "oi.receiverAddress", target = "receiverAddr1")
    @Mapping(source = "oi.receiverAddressSub", target = "receiverAddr2")
    @Mapping(source = "os.orderNo", target = "channelOrderNo")
    @Mapping(source = "os.orderStatus", target = "channelOrderStatus")
    IfOrderMaster to(OrderSearchData os, OrderInfoData oi);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(OrderInfoData dto, @MappingTarget IfOrderMaster entity);
}
