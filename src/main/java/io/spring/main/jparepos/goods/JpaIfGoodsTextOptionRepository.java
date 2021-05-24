package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfGoodsTextOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIfGoodsTextOptionRepository extends JpaRepository<IfGoodsTextOption, String> {
}
