package io.spring.main.jparepos.purchase;


import io.spring.main.model.purchase.entity.Lspchs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface JpaLspchsRepository extends JpaRepository<Lspchs, Long> {
    Lspchs findByPurchaseNoAndEffEndDt(String purchaseNo, Date effEndDt);
}
