package io.spring.main.interfaces;

import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IfGoodsMasterMapper {
    @Mapping(ignore = true, target = "super.updDt")
    @Mapping(ignore = true, target = "super.regDt")
    @Mapping(ignore = true, target = "mainImageData")
    @Mapping(ignore = true, target = "listImageData")
    @Mapping(ignore = true, target = "detailImageData")
    @Mapping(ignore = true, target = "magnifyImageData")
    IfGoodsMaster to(GoodsSearchData gs);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateFromDto(OrderSearchData.OrderInfoData dto, @MappingTarget IfOrderMaster entity);
}