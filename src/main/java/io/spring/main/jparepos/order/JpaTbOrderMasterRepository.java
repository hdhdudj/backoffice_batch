package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.TbOrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaTbOrderMasterRepository extends JpaRepository<TbOrderMaster, String> {
    @Query("select max(t.orderId) from TbOrderMaster t")
    String findMaxOrderId();

    TbOrderMaster findByChannelOrderNo(String ifNo);
}
