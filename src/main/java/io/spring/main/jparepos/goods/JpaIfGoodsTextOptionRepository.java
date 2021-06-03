package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfGoodsTextOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaIfGoodsTextOptionRepository extends JpaRepository<IfGoodsTextOption, String> {
    List<IfGoodsTextOption> findByGoodsNo(String goodsNo);
}
