package io.spring.main.jparepos.goods;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.spring.main.model.goods.entity.IfGoodsOption;

public interface JpaIfGoodsOptionRepository extends JpaRepository<IfGoodsOption, String> {
    List<IfGoodsOption> findByGoodsNo(String goodsNo);

	IfGoodsOption findByAssortIdAndItemId(String assortId, String itemId);

}
