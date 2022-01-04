package io.spring.main.interfaces.mapper;

import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfGoodsOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IfGoodsOptionMapper {
    @Mapping(source = "od.goodsNo", target = "goodsNo")
    @Mapping(source = "gs.assortId", target = "assortId")
    @Mapping(source = "gs.optionName", target = "optionName")
    @Mapping(target = "uploadStatus", constant = "01")
    @Mapping(ignore = true, target = "regDt")
    @Mapping(ignore = true, target = "modDt")
    IfGoodsOption to(GoodsSearchData.OptionData od, GoodsSearchData gs);
}
