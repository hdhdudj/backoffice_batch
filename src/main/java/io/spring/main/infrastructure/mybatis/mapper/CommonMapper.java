package io.spring.main.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface CommonMapper {

	HashMap<String, Object> getSequence(HashMap<String, Object> param);

}
