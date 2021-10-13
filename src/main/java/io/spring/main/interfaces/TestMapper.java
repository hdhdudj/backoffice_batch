package io.spring.main.interfaces;

import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestMapper {//<OrderInfoData, IfOrderMaster> extends GenericMapper<OrderInfoData, IfOrderMaster>{
//    OrderInfoDataIfOrderMasterMapper INSTANCE = Mappers.getMapper(OrderInfoDataIfOrderMasterMapper.class);

    @Mapping(source = "a.asdf", target = "asdf")
    @Mapping(source = "a.asdf1", target = "asdf1")
    Bsdf to(Asdf a);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateFromDto(OrderSearchData.OrderInfoData dto, @MappingTarget IfOrderMaster entity);
}
