package io.spring.main.jparepos.deposit;

import io.spring.main.model.deposit.entity.Lsdpsm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLsdpsmRepository extends JpaRepository<Lsdpsm, String> {
}
