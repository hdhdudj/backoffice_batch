package io.spring.main.interfaces.mapper;

import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfGoodsAddGoods;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IfGoodsAddGoodsMapper {
    @Mapping(source = "goodsNo", target = "goodsNo")
    @Mapping(source = "channelGb", target = "channelGb")
    @Mapping(ignore = true, target = "super.regDt")
    @Mapping(ignore = true, target = "super.updDt")
    IfGoodsAddGoods to(String channelGb, long goodsNo, GoodsSearchData gd);

    IfGoodsAddGoods copy(IfGoodsAddGoods ig);
}
