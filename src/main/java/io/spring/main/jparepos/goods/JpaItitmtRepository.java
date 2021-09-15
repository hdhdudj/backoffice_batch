package io.spring.main.jparepos.goods;


import io.spring.main.model.goods.entity.Ititmt;
import io.spring.main.model.goods.idclass.ItitmtId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaItitmtRepository extends JpaRepository<Ititmt, ItitmtId> {
    List<Ititmt> findByAssortIdAndItemIdAndStorageIdAndItemGrade(String assortId, String itemId, String domesticStorageId, String strEleven);
}
