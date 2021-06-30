package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.TbOrderDetail;
import io.spring.main.model.order.idclass.TbOrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaTbOrderDetailRepository extends JpaRepository<TbOrderDetail, TbOrderDetailId> {
    TbOrderDetail findByOrderIdAndGoodsNm(String orderId, String goodsNm);

    @Query("select max(t.orderSeq) from TbOrderDetail t where t.orderId=?1")
    String findMaxOrderSeqWhereOrderId(String orderId);
}
