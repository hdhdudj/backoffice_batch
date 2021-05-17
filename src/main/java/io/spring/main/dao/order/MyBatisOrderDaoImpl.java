package io.spring.main.dao.order;

import io.spring.main.infrastructure.mybatis.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MyBatisOrderDaoImpl implements MyBatisOrderDao {
	private final OrderMapper orderMapper;

	@Autowired
	public MyBatisOrderDaoImpl(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	@Override
	public List<HashMap<String, Object>> selectOrderListByCondition(HashMap<String, Object> param) {
		return orderMapper.selectOrderListByCondition(param);
	}

}
