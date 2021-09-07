package io.spring.main.dao.order;

import io.spring.main.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl {
    private final OrderMapper orderMapper;

    public void insertTmmapi(){
        orderMapper.insertTmmapi();
    }
}
