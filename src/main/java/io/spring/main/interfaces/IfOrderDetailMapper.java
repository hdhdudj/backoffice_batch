package io.spring.main.interfaces;

import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IfOrderDetailMapper {//<OrderInfoData, IfOrderMaster> extends GenericMapper<OrderInfoData, IfOrderMaster>{
//    OrderInfoDataIfOrderMasterMapper INSTANCE = Mappers.getMapper(OrderInfoDataIfOrderMasterMapper.class);

    @Mapping(source = "os.orderNo", target = "channelOrderNo")
    @Mapping(source = "og.sno", target = "channelOrderSeq")
    @Mapping(source = "og.orderStatus", target = "channelOrderStatus")
    @Mapping(source = "og.goodsNo", target = "channelGoodsNo")
    @Mapping(source = "og.optionSno", target = "channelOptionsNo")
    @Mapping(source = "og.optionInfo", target = "channelOptionInfo")
    @Mapping(source = "og.goodsNm", target = "channelGoodsNm")
    @Mapping(source = "og.goodsType", target = "channelGoodsType")
    @Mapping(source = "og.parentGoodsNo", target = "channelParentGoodsNo")
    @Mapping(source = "og.couponGoodsDcPrice", target = "couponDcPrice")
    @Mapping(source = "og.goodsDeliveryCollectPrice", target = "deliPrice")
    @Mapping(source = "og.deliveryCond", target = "deliveryInfo")
    @Mapping(source = "og.deliveryMethodFl", target = "deliveryMethodGb")
    IfOrderDetail to(OrderSearchData os, OrderSearchData.OrderGoodsData og);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateFromDto(OrderSearchData.OrderInfoData dto, @MappingTarget IfOrderMaster entity);
}
