package io.spring.main.model.order.entity;

import io.spring.main.model.goods.entity.CommonProps;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLog extends CommonProps {
    public OrderLog(TbOrderDetail tbOrderDetail){
        this.orderId = tbOrderDetail.getOrderId();
        this.orderSeq = tbOrderDetail.getOrderSeq();
        this.prevStatus = tbOrderDetail.getStatusCd();
        this.currentStatus = tbOrderDetail.getStatusCd();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String orderId;
    private String orderSeq;
    private String prevStatus;
    private String currentStatus;
}
