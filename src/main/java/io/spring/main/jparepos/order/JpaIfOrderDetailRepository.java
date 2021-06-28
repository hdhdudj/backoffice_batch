package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.idclass.IfOrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIfOrderDetailRepository extends JpaRepository<IfOrderDetail, IfOrderDetailId> {
//    IfOrderMaster findByChannelOrderNo(String channelOrderNo);
}
