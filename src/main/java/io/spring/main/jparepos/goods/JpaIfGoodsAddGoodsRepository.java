package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfGoodsAddGoods;
import io.spring.main.model.goods.idclass.IfGoodsAddGoodsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIfGoodsAddGoodsRepository extends JpaRepository<IfGoodsAddGoods, IfGoodsAddGoodsId> {
}
