package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.TbOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTbOrderHistoryRepository extends JpaRepository<TbOrderHistory, String> {
}
