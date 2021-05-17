package io.spring.main.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface OrderMapper {

	List<HashMap<String, Object>> selectOrderListByCondition(HashMap<String, Object> param);

}
