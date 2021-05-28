package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Itlkag;
import io.spring.main.model.goods.idclass.ItlkagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaItlkagRepository extends JpaRepository<Itlkag, ItlkagId> {
    @Query("select max(i.addGoodsId) as maxVal from Itlkag as i where i.assortId = ?1")
    String findMaxAddGoodsIdByAssortId(String assortId);
}
