package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.IfOrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaIfOrderMasterRepository extends JpaRepository<IfOrderMaster, String> {
    IfOrderMaster findByChannelGbAndChannelOrderNo(String channelGb, String channelOrderNo);

//    @Query("select i from IfOrderMaster i")
//    List<IfOrderMaster> findAll();

    List<IfOrderMaster> findByIfStatus(String ifStatus);

    IfOrderMaster findByIfNo(String orderId);
}
