package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfGoodsOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaIfGoodsOptionRepository extends JpaRepository<IfGoodsOption, String> {
    List<IfGoodsOption> findByGoodsNo(String goodsNo);
}
