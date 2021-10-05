package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderLogRepository extends JpaRepository<OrderLog, Long> {
}
