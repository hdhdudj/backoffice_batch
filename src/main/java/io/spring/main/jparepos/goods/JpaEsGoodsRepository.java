package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.EsGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEsGoodsRepository extends JpaRepository<EsGoods, Long> {
}
