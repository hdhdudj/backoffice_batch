package io.spring.main.dao.goods;

import io.spring.main.mapper.goods.GoodsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GoodsDaoImpl {
    private final GoodsMapper goodsMapper;

    public void insertTmitem(){
        goodsMapper.insertTmitem();
    };
}
