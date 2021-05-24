package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfGoodsMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIfGoodsMasterRepository extends JpaRepository<IfGoodsMaster, String> {
}
