package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Ititmc;
import io.spring.main.model.goods.idclass.ItitmcId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItitmcRepository extends JpaRepository<Ititmc, ItitmcId> {
}
