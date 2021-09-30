package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.TbOrderDetail;
import io.spring.main.model.order.idclass.TbOrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface JpaTbOrderDetailRepository extends JpaRepository<TbOrderDetail, TbOrderDetailId> {
//    @Query("select t from TbOrderDetail t left join fetch t.ititmm where t.orderId=?1 and t.goodsNm=?2")
//    TbOrderDetail findByOrderIdAndGoodsNm(String orderId, String goodsNm);

    @Query("select max(t.orderSeq) from TbOrderDetail t where t.orderId=?1")
    String findMaxOrderSeqWhereOrderId(String orderId);

    @Query("select t from TbOrderDetail t left join fetch t.ititmm where t.orderId=?1 and t.orderSeq=?2")
    TbOrderDetail findByOrderIdAndOrderSeq(String orderId, String orderSeq);

    TbOrderDetail findByChannelOrderNoAndChannelOrderSeq(String channelOrderNo, String channelOrderSeq);

    List<TbOrderDetail> findByOrderId(String setOrderSeq);

    List<TbOrderDetail> findByChannelOrderNo(String channelOrderNo);
}
