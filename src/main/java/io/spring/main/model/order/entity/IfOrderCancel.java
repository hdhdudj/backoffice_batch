package io.spring.main.model.order.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "if_order_cancel")
public class IfOrderCancel extends CommonProps {

	public IfOrderCancel(IfOrderDetail iod) {
		this.ifNo = iod.getIfNo();
		this.ifNoSeq = iod.getIfNoSeq();
		this.channelOrderNo = iod.getChannelOrderNo();
		this.channelOrderSeq = iod.getChannelOrderSeq();
		this.channelOrderStatus = iod.getChannelOrderStatus();
		this.channelGoodsType = iod.getChannelGoodsType();
		this.channelGoodsNo = iod.getChannelGoodsNo();
		this.channelOptionsNo = iod.getChannelOptionsNo();
		this.channelParentGoodsNo = iod.getChannelParentGoodsNo();
		this.channelGoodsNm = iod.getChannelGoodsNm();
		this.channelOptionInfo = iod.getChannelOptionInfo();
		this.goodsCnt = iod.getGoodsCnt();
		this.goodsPrice = iod.getGoodsPrice();
		this.goodsDcPrice = iod.getGoodsDcPrice();
		this.memberDcPrice = iod.getMemberDcPrice();
		this.couponDcPrice = iod.getCouponDcPrice();
		this.adminDcPrice = iod.getAdminDcPrice();
		this.etcDcPrice = iod.getEtcDcPrice();
		this.deliveryMethodGb = iod.getDeliveryMethodGb();
		this.deliPrice = iod.getDeliPrice();
		this.orderId = iod.getOrderId();
		this.orderSeq = iod.getOrderSeq();
		this.channelGb = iod.getChannelGb();
		this.goodsModelNo = iod.getGoodsModelNo();
		this.divisionUseMileage = iod.getDivisionUseMileage();
		this.divisionGoodsDeliveryUseDeposit = iod.getDivisionGoodsDeliveryUseDeposit();
		this.divisionGoodsDeliveryUseMileage = iod.getDivisionGoodsDeliveryUseMileage();
		this.divisionCouponOrderDcPrice = iod.getDivisionCouponOrderDcPrice();
		this.divisionUseDeposit = iod.getDivisionUseDeposit();
		this.divisionCouponOrderMileage = iod.getDivisionCouponOrderMileage();
		this.addGoodsPrice = iod.getAddGoodsPrice();
		this.optionTextInfo = iod.getOptionTextInfo();
		this.listImageData = iod.getListImageData();
		this.optionPrice = iod.getOptionPrice();
		this.optionTextPrice = iod.getOptionTextPrice();
		this.fixedPrice = iod.getFixedPrice();
		this.deliveryInfo = iod.getDeliveryInfo();
		this.costPrice = iod.getCostPrice();
		this.memberOverlapDcPrice = iod.getMemberOverlapDcPrice();
		this.scmNo = iod.getScmNo();
		this.parentChannelOrderSeq = iod.getParentChannelOrderSeq();
		this.claimHandleMode = iod.getClaimHandleMode();
		this.claimHandleReason = iod.getClaimHandleReason();
		this.claimHandleDetailReason = iod.getClaimHandleDetailReason();

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String seq;

	private String ifNo;
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

	private String ifStatus;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date ifDt;
	private String ifMsg;
	private String ifCancelGb;

}
