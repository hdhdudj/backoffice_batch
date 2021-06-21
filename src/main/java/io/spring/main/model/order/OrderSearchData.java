package io.spring.main.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class OrderSearchData {
    private Long orderNo;
    private Long memNo;
    private Long apiOrderGoodsNo;
    private String orderStatus;
    private String orderIp;
    private String orderChannelFl;
    private String orderTypeFl;
    private String orderEmail;
    private String orderGoodsNm;
    private Long orderGoodsCnt;
    private Float settlePrice;
    private Float taxSupplyPrice;
    private Float taxVatPrice;
    private Float taxFreePrice;
    private Float realTaxSupplyPrice;
    private Float realTaxVatPrice;
    private Float realTaxFreePrice;
    private Float useMileage;
    private Float useDeposit;
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
    private String firstSaleFl;
    private String settleKind;
    private String multiShippingFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date paymentDt;
    private String addField; // json 주문추가정보. name : 추가항목명, data : 추가항목 입력값 (입력방법이 order인 경우 Text 타입, goods인 경우 Array 타입), goodsNm : 상품명(goods인 경우 존재함), process : 입력방법(order=공통입력, goods=상품별입력)
    private OrderDeliveryData orderDeliveryData;
    private OrderInfoData orderInfoData;
    private AddGoodsData addGoodsData;
    private GiftData giftData;
    private OrderGoodsData orderGoodsData;
    private String bankName;
    private Long accountNumber;
    private String depositor;
    private String memId;
    private String memGroupNm;

    @Getter
    @Setter
    @NoArgsConstructor
    private class OrderDeliveryData {
        private Long scmNo;
        private Float commission;
        private Long scmAdjustNo;
        private Float deliveryCharge;
        private Float deliveryPolicyCharge;
        private Float deliveryAreaCharge;
        private String deliveryFixFl;
        private String deliveryCollectFl;
        private Long orderInfoSno;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class OrderInfoData {
        private Long sno;
        private Long orderInfoCd;
        private String orderName;
        private String orderEmail;
        private String orderPhone;
        private String orderCellPhone;
        private String orderZipcode;
        private String orderZonecode;
        private String orderAddress;
        private String orderAddressSub;
        private String receiverName;
        private String receiverPhone;
        private String receiverCellPhone;
        private String receiverZipcode;
        private String receiverZonecode;
        private String receiverAddress;
        private String receiverAddressSub;
        private String customIdNumber;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date orderMemo;
        private String receiverUseSafeNumberFl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class AddGoodsData {

    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class GiftData {
        private Long sno;
        private String presentTitle;
        private Long giftNo;
        private String giftCd;
        private String giftNm;
        private Long giftCnt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class OrderGoodsData {

    }
}
