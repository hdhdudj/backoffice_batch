package io.spring.main.mapper.goods;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {
    void insertTmitem();
}
