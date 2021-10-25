package io.spring.main.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.entity.TbMember;
import io.spring.main.model.order.entity.TbMemberAddress;
import io.spring.main.model.order.entity.TbOrderMaster;

@Mapper(componentModel = "spring")
public interface TbOrderMasterMapper {
    @Mapping(target = "orderGb", constant = "01") // // 01 : 주문, 02 : 반품, 03 : 교환
    @Mapping(target = "firstOrderGb", constant = "01") // 첫주문 01 그다음 02
    @Mapping(source = "tm.custId", target = "custId")
    @Mapping(source = "im.payAmt", target = "receiptAmt")
    @Mapping(source = "im.payAmt", target = "orderAmt")
    @Mapping(source = "im.channelOrderStatus", target = "orderStatus")
	@Mapping(source = "orderId", target = "firstOrderId")
    @Mapping(source = "im.channelGb", target = "channelGb")
    @Mapping(source = "im.regId", target = "regId")
    @Mapping(source = "im.updId", target = "updId")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(ignore = true, target = "regDt")
    @Mapping(ignore = true, target = "updDt")
    TbOrderMaster to(String orderId, IfOrderMaster im, TbMember tm, TbMemberAddress ta);

    TbOrderMaster copy(TbOrderMaster tbOrderMaster);
}
