package io.spring.main.model.order.entity;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.model.goods.idclass.IfBrandId;
import io.spring.main.model.order.idclass.IfOrderDetailId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "if_order_detail")
@IdClass(value = IfOrderDetailId.class)
public class ifOrderDetail extends CommonProps {
    @Id
    private String ifNo;
    @Id
    private String ifNoSeq;
    private String channelOrderNo;
    private String channelOrderSeq;
    private String channelOrderStatus;
    private String channelGoodsType;
    private String channelGoodsNo;
    private String channelOptionsNo;
    private String channelParentGoodsNo;
    private String channelGoodsNm;
    private String channelOptionInfo;
    private Long goodsCnt;
    private Float goodsPrice;
    private Float goodsDcPrice;
    private Float memberDcPrice;
    private Float couponDcPrice;
    private Float adminDcPrice;
    private Float etcDcPrice;
    private String deliveryMethodGb;
    private String deliveryInfo;
    private String orderId;
    private String orderSeq;
}
