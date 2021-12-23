package io.spring.main.model.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.entity.CommonProps;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="tb_order_master")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class TbOrderMaster extends CommonProps {
    public TbOrderMaster(String orderId){
        this.orderId = orderId;
    }
    @Id
    private String orderId;
    private String firstOrderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date orderDate;
    private String orderStatus;
    private String channelGb;
    private Long custId;
    private Long deliId;
    private Float orderAmt;
    private Float receiptAmt;
    private String firstOrderGb;
    private String orderGb;
    private String channelOrderNo;
    private String custPcode;
    private String orderMemo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime payDt;

    // 21-09-28 추가된 컬럼
    private String payGb;

    // 21-10-07 추가된 컬럼
    private Float totalGoodsPrice;
    private Float totalDeliveryCharge;
    private Float totalGoodsDcPrice;
    private Float totalMemberDcPrice;
    private Float totalMemberOverlapDcPrice;
    private Float totalCouponGoodsDcPrice;
    private Float totalCouponOrderDcPrice;
    private Float totalCouponDeliveryDcPrice;
    private Float totalMileage;
    private Float totalGoodsMileage;
    private Float totalMemberMileage;
    private Float totalCouponGoodsMileage;
    private Float totalCouponOrderMileage;

    // 21-12-17 추가된 컬럼
    private String orderName; // 주문자명
    private String orderAddr1; // 주문자 주소1
    private String orderAddr2; // 주문자 주소2 (상세주소)
    // 21-12-21 추가된 컬럼
    private String orderZipcode; // 주문자 구 우편번호
    private String orderZonecode; // 주문자 신 우편번호
    private String orderTel; // 주문자 전화
    private String orderHp; // 주문자 폰
}
