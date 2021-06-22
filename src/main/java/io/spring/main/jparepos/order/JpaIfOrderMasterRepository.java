package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.IfOrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaIfOrderMasterRepository extends JpaRepository<IfOrderMaster, String> {

    List<IfOrderMaster> findByIfStatus(String ifStatus);
}