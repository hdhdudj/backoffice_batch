package io.spring.main.interfaces;

import io.spring.main.model.goods.entity.IfGoodsMaster;
import io.spring.main.model.goods.entity.Itadgs;
import io.spring.main.model.goods.entity.Itasrt;
import io.spring.main.model.goods.entity.Ititmm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItasrtMapper {
    @Mapping(source = "ia.addGoodsId", target = "assortId")
    @Mapping(source = "ia.addGoodsNm", target = "assortNm")
    @Mapping(source = "ia.addGoodsModel", target = "assortModel")
    @Mapping(source = "ia.shortYn", target = "shortageYn")
    @Mapping(source = "ia.makerNm", target = "manufactureNm")
    @Mapping(target = "addGoodsYn", constant = "01") // 01 하드코딩, 01 : 추가상품 02 : 추가상품아님
    @Mapping(target = "assortState", constant = "01") // 01 하드코딩, 01 : 직구 02 : 수입
    @Mapping(ignore = true, target = "super.regDt")
    @Mapping(ignore = true, target = "super.updDt")
    Itasrt to(String assortId, Itadgs ia);

    Itasrt copy(Itasrt it);
}
