package io.spring.main.model.order.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "if_order_master")
public class IfOrderMaster extends CommonProps implements Serializable {
    public IfOrderMaster(OrderSearchData orderSearchData){
//        ifNo = orderSearchData.getIfNo();
//        channelOrderNo = Long.toString(orderSearchData.getOrderNo());
//        ifStatus = StringFactory.getGbOne(); // 01 하드코딩
//        memNo = Long.toString(orderSearchData.getMemNo());
//        orderName = orderSearchData.getOrderInfoData().get(0).getOrderName();
    }
    @Id
    private String ifNo;
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩
    private String channelOrderNo;
    private String ifStatus; // 01 신규 02 수정
    private String channelOrderStatus;
    private String memNo;
    private String orderName;
    private String orderTel;
    private String orderEmail;
    private String orderZipcode;
    private String orderAddr1;
    private String orderAddr2;
    private String receiverName;
    private String receiverTel;
    private String receiverZipcode;
    private String receiverAddr1;
    private String receiverAddr2;
    private String channelInfo;
    private String customerId;
    private String payGb;
    private Float payAmt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime payDt;
    private String orderId;
    private String orderMemo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDate;

    // 21-09-28 추가된 컬럼
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

    // 21-11-24
    private String orderZonecode;
    private String receiverZonecode;

    // 21-12-21
    private String receiverHp; // 수취자 폰
    private String orderHp; // 주문자 폰

	private String xmlMessage;

    @Override
    public String toString(){
        return "ifNo : " + ifNo + ", channelOrderNo : " + channelOrderNo + ", ifStatus : " + ifStatus;
    }

    // ifOrderDetail 연관 관계
    @JoinColumn(name="ifNo", referencedColumnName = "ifNo", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<IfOrderDetail> ifOrderDetail;
}
