package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.idclass.IfOrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaIfOrderDetailRepository extends JpaRepository<IfOrderDetail, IfOrderDetailId> {
//    IfOrderMaster findByChannelOrderNo(String channelOrderNo);

    IfOrderDetail findByIfNoAndChannelGoodsNo(String ifNo, String goodsNo);

    @Query("select max(I.ifNoSeq) from IfOrderDetail I where I.ifNo = ?1")
    String findMaxIfNoSeq(String ifNo);

    @Query("select max(I.ifNo) from IfOrderDetail I")
    String findMaxIfNo();

    IfOrderDetail findByIfNoAndChannelOrderNoAndChannelOrderSeq(String ifNo, String orderNo, String sno);

    IfOrderDetail findByChannelGbAndChannelOrderNoAndChannelOrderSeq(String gbOne, String channelOrderNo, String channelOrderSeq);

    List<IfOrderDetail> findByChannelGbAndChannelOrderNo(String gbOne, String channelOrderNo);
}
