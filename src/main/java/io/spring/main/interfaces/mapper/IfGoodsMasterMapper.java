package io.spring.main.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.goods.entity.IfGoodsMaster;

@Mapper(componentModel = "spring")
public interface IfGoodsMasterMapper {
    @Mapping(ignore = true, target = "super.updDt")
    @Mapping(ignore = true, target = "super.regDt")
    @Mapping(ignore = true, target = "mainImageData")
    @Mapping(ignore = true, target = "listImageData")
    @Mapping(ignore = true, target = "detailImageData")
    @Mapping(ignore = true, target = "magnifyImageData")
	@Mapping(ignore = true, target = "modDt")
    IfGoodsMaster to(GoodsSearchData gs);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateFromDto(OrderSearchData.OrderInfoData dto, @MappingTarget IfOrderMaster entity);
}