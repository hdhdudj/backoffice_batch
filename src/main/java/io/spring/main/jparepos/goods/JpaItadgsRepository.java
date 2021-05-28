package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Itadgs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItadgsRepository extends JpaRepository<Itadgs, String> {
}
