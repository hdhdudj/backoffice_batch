package io.spring.main.interfaces;

import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.TbOrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TbOrderDetailMapper {
    @Mapping(target = "lastCategoryId", constant = "01")
    @Mapping(target = "lastGb", constant = "01")
    @Mapping(target = "storageId", constant = "000002") // 현재는 외국으로 입고되므로 일단 000002 하드코딩
    @Mapping(source = "id.channelGoodsType", target = "assortGb")
    @Mapping(source = "id.goodsPrice", target = "salePrice")
    @Mapping(source = "id.fixedPrice", target = "goodsPrice")
    @Mapping(source = "id.deliveryMethodGb", target = "deliMethod")
    @Mapping(source = "id.goodsCnt", target = "qty")
    @Mapping(source = "id.channelOptionInfo", target = "optionInfo")
    @Mapping(source = "id.channelGoodsNm", target = "goodsNm")
    @Mapping(source = "it.itemId", target = "itemId")
    @Mapping(source = "it.assortId", target = "assortId")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderSeq", target = "orderSeq")
    @Mapping(ignore = true, target = "super.updDt")
    @Mapping(ignore = true, target = "super.regDt")
    TbOrderDetail to(String orderId, String orderSeq, IfOrderDetail id, Ititmm it);

    TbOrderDetail copy(TbOrderDetail tbOrderDetail);
}
