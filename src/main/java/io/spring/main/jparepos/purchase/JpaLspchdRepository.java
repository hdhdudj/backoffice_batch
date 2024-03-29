package io.spring.main.jparepos.purchase;

import io.spring.main.model.purchase.entity.Lspchd;
import io.spring.main.model.purchase.entity.Lspchs;
import io.spring.main.model.purchase.idclass.LspchdId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaLspchdRepository extends JpaRepository<Lspchd, LspchdId> {
    List<Lspchs> findByPurchaseNo(String purchaseNo);
    @Query("select max(l.purchaseSeq) as maxVal from Lspchd as l where l.purchaseNo = ?1")
    String findMaxPurchaseSeqByPurchaseNo(String purchaseNo);
    Lspchd findByPurchaseNoAndPurchaseSeq(String purchaseNo, String purchaseSeq);
}
