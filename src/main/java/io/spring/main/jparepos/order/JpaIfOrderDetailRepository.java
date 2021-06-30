package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.idclass.IfOrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaIfOrderDetailRepository extends JpaRepository<IfOrderDetail, IfOrderDetailId> {
//    IfOrderMaster findByChannelOrderNo(String channelOrderNo);

    IfOrderDetail findByIfNoAndChannelGoodsNo(String ifNo, String goodsNo);

    @Query("select max(I.ifNoSeq) from IfOrderDetail I where I.ifNo = ?1")
    String findMaxIfNoSeq(String ifNo);
}
