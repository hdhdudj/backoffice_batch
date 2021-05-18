package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.EsGoods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEsGoodsRepository extends JpaRepository<EsGoods, Long> {
}
