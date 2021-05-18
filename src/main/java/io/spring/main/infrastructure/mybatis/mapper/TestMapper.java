package io.spring.main.infrastructure.mybatis.mapper;

import io.spring.main.dao.user.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestMapper {
	void insert(@Param("test") Test test);

	Test findById(@Param("id") int i);

	void update(@Param("test") Test test);

	List<Test> findTests();

}
