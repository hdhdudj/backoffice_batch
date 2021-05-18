package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Ititmd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface JpaItitmdRepository extends JpaRepository<Ititmd, Long> {
    Ititmd findByItemId(String itemId);
    Ititmd findByAssortIdAndItemIdAndEffEndDt(String assortId, String itemId, Date effEndDt);
}
