package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Itmmot;
import io.spring.main.model.goods.idclass.ItmmotId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItmmotRepository extends JpaRepository<Itmmot, ItmmotId> {
}
