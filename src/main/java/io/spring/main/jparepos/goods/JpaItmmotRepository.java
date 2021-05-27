package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Itmmot;
import io.spring.main.model.goods.idclass.ItmmotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaItmmotRepository extends JpaRepository<Itmmot, ItmmotId> {
    @Query("select max(i.optionTextId) as maxVal from Itmmot as i where i.assortId = ?1")
    String findMaxSeqByAssortId(String assortId);
}
