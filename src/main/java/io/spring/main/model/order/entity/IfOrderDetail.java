package io.spring.main.model.order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.model.order.idclass.IfOrderDetailId;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "if_order_detail")
@IdClass(value = IfOrderDetailId.class)
@EqualsAndHashCode
public class IfOrderDetail extends CommonProps {
    public IfOrderDetail(String ifNo){
        this.ifNo = ifNo;
    }
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
    private Float deliPrice;
    private String orderId;
    private String orderSeq;
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩

    // 21-09-28 추가된 컬럼
    private String goodsModelNo;
    private Float divisionUseMileage;
    private Float divisionGoodsDeliveryUseDeposit;
    private Float divisionGoodsDeliveryUseMileage;
    private Float divisionCouponOrderDcPrice;
    private Float divisionUseDeposit;
    private Float divisionCouponOrderMileage;
    private Float addGoodsPrice;
    private String optionTextInfo;
    private String listImageData;
    private Float optionPrice;
    private Float optionTextPrice;
    private Float fixedPrice;
    private String deliveryInfo;
    private Float costPrice;
    private Float memberOverlapDcPrice;
    private Long scmNo;

    // 21-10-06 추가된 컬럼
    private String parentChannelOrderSeq;

    // 21-10-13 추가된 컬럼
    private String claimHandleMode;
    private String claimHandleReason;
    private String claimHandleDetailReason;
}
