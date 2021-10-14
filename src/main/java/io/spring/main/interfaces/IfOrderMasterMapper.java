package io.spring.main.interfaces;

import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderMaster;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IfOrderMasterMapper{//<OrderInfoData, IfOrderMaster> extends GenericMapper<OrderInfoData, IfOrderMaster>{
//    OrderInfoDataIfOrderMasterMapper INSTANCE = Mappers.getMapper(OrderInfoDataIfOrderMasterMapper.class);

    OrderSearchData.OrderInfoData toDto(IfOrderMaster e);
    @Mapping(source = "oi.orderCellPhone", target = "orderTel")
    @Mapping(source = "oi.orderAddress", target = "orderAddr1")
    @Mapping(source = "oi.orderAddressSub", target = "orderAddr2")
    @Mapping(source = "oi.receiverCellPhone", target = "receiverTel")
    @Mapping(source = "oi.receiverAddress", target = "receiverAddr1")
    @Mapping(source = "oi.receiverAddressSub", target = "receiverAddr2")
    @Mapping(source = "os.orderNo", target = "channelOrderNo")
    @Mapping(source = "os.orderStatus", target = "channelOrderStatus")
    @Mapping(source = "os.settleKind", target = "payGb")
    @Mapping(source = "os.settlePrice", target = "payAmt")
    @Mapping(source = "os.orderEmail", target = "orderEmail")
    @Mapping(source = "os.paymentDt", target = "payDt")
    @Mapping(ignore = true, target = "super.updDt")
    @Mapping(ignore = true, target = "super.regDt")
    IfOrderMaster to(OrderSearchData os, OrderSearchData.OrderInfoData oi);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateFromDto(OrderSearchData.OrderInfoData dto, @MappingTarget IfOrderMaster entity);
}
