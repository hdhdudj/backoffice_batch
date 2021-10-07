package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Itadgs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaItadgsRepository extends JpaRepository<Itadgs, String> {
    @Query("select max(i.addGoodsId) as maxVal from Itadgs as i")
    String findMaxAddGoodsId();
}
