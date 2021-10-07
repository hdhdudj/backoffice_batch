package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfAddGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaIfAddGoodsRepository extends JpaRepository<IfAddGoods, String> {
    IfAddGoods findByAddGoodsNo(String addGoods);

    @Query("select max(i.addGoodsId) as maxVal from IfAddGoods as i")
    String findMaxAddGoodsId();
}
