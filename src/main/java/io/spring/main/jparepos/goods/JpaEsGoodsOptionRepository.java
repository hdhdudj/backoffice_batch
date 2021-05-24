package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.EsGoodsOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEsGoodsOptionRepository extends JpaRepository<EsGoodsOption, Long> {
}
