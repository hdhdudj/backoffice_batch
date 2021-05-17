package io.spring.main.dao.common;

import io.spring.main.infrastructure.mybatis.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class MyBatisCommonDaoImpl implements MyBatisCommonDao {
	private final CommonMapper commonMapper;

	@Autowired
	public MyBatisCommonDaoImpl(CommonMapper commonMapper) {
		this.commonMapper = commonMapper;
	}

	@Override
	public HashMap<String, Object> getSequence(HashMap<String, Object> param) {
		HashMap<String, Object> r = commonMapper.getSequence(param);
		System.out.println("aaacccc33444");
		return r;
	}

}
