package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Tmmapi;
import io.spring.main.model.goods.idclass.TmmapiId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTmmapiRepository extends JpaRepository<Tmmapi, TmmapiId> {
}
