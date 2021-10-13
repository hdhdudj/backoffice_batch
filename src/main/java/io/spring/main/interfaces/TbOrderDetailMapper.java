package io.spring.main.interfaces;

import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.TbOrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//@Mapper(componentModel = "spring")
//public interface TbOrderDetailMapper {
////    @Mapping(source = "id.orderNo", target = "channelOrderNo")
////    @Mapping(source = "og.sno", target = "channelOrderSeq")
////    @Mapping(source = "og.orderStatus", target = "channelOrderStatus")
////    @Mapping(source = "og.goodsNo", target = "channelGoodsNo")
////    @Mapping(source = "og.optionSno", target = "channelOptionsNo")
////    @Mapping(source = "og.optionInfo", target = "channelOptionInfo")
////    @Mapping(source = "og.goodsNm", target = "channelGoodsNm")
////    @Mapping(source = "og.goodsType", target = "channelGoodsType")
////    @Mapping(target = "goodsModelNo", expression = "java(og.getGoodsModelNo().trim().equals(\"\") ? null : Float.parseFloat(og.getGoodsModelNo()))")
////    @Mapping(source = "og.parentGoodsNo", target = "channelParentGoodsNo")
////    @Mapping(source = "og.couponGoodsDcPrice", target = "couponDcPrice")
////    @Mapping(source = "og.goodsDeliveryCollectPrice", target = "deliPrice")
////    @Mapping(source = "og.deliveryCond", target = "deliveryInfo")
////    @Mapping(source = "og.deliveryMethodFl", target = "deliveryMethodGb")
//    TbOrderDetail to(IfOrderDetail id, Ititmm it);
//}
