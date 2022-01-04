package io.spring.main.interfaces.mapper;

import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfGoodsTextOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IfGoodsTextOptionMapper {
    IfGoodsTextOption to(GoodsSearchData.TextOptionData t);
}
