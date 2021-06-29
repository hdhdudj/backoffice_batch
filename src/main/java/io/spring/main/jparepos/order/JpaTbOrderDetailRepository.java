package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.TbOrderDetail;
import io.spring.main.model.order.idclass.TbOrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTbOrderDetailRepository extends JpaRepository<TbOrderDetail, TbOrderDetailId> {
    TbOrderDetail findByOrderIdAndGoodsNm(String orderId, String goodsNm);
}
