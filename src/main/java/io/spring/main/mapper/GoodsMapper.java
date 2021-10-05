package io.spring.main.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface GoodsMapper {
    void insertTmitem();
    void insertTmmapi();
}
