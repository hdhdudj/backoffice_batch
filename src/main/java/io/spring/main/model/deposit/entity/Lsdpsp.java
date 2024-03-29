package io.spring.main.model.deposit.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.model.purchase.request.PurchaseInsertRequestData;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Setter
@Table(name = "lsdpsp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lsdpsp extends CommonProps implements Serializable {
	public Lsdpsp(PurchaseInsertRequestData purchaseInsertRequestData, PurchaseInsertRequestData.Items items) {
		this.depositPlanId = purchaseInsertRequestData.getDepositPlanId();
		try {
			this.smReservationDt = new SimpleDateFormat(StringFactory.getDateTimeFormat())
					.parse(StringFactory.getDoomDay());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		this.purchasePlanQty = items.getPurchaseQty();
		this.purchaseTakeQty = 0L;
		this.assortId = items.getAssortId();
		this.itemId = items.getItemId();
		this.planStatus = purchaseInsertRequestData.getPlanStatus();
//        this.orderId = purchaseInsertRequest.getOrderId();
//        this.orderSeq = purchaseInsertRequest.getOrderSeq();
		this.purchaseNo = purchaseInsertRequestData.getPurchaseNo();
		this.purchaseSeq = items.getPurchaseSeq();
		this.claimItemYn = StringFactory.getGbTwo(); // 02
	}

	@Id
	private String depositPlanId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date smReservationDt;
	private Long purchasePlanQty;
	private Long purchaseTakeQty;
	private String assortId;
	private String itemId;
	private String planStatus;
	private String orderId;
	private String orderSeq;
	private String purchaseNo;
	private String purchaseSeq;
	private String planChgReason;
	private String claimItemYn;
	private String purchaseGb;
	private String dealTypeCd;
}
