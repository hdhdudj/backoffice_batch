package io.spring.main.model.order.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.order.idclass.TbOrderDetailId;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tb_order_detail")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(TbOrderDetailId.class)
@EqualsAndHashCode(exclude = {"ititmm"}, callSuper = false)
public class TbOrderDetail extends CommonProps
{
//    public TbOrderDetail(TbOrderMaster tbOrderMaster, Ititmm ititmm){
//        this.orderId = tbOrderMaster.getOrderId();
//        this.orderSeq = StringFactory.getThreeStartCd(); // 001 하드코딩
//    }
    public TbOrderDetail(String orderId, String orderSeq){
        this.orderId = orderId;
        this.orderSeq = orderSeq;
    }
    public TbOrderDetail(TbOrderDetail tbOrderDetail){
        this.orderId = tbOrderDetail.getOrderId();
        this.orderSeq = tbOrderDetail.getOrderSeq();
        this.statusCd = tbOrderDetail.getStatusCd();
        this.assortGb = tbOrderDetail.getAssortGb();
        this.assortId = tbOrderDetail.getAssortId();
        this.itemId = tbOrderDetail.getItemId();
        this.goodsNm = tbOrderDetail.getGoodsNm();
        this.optionInfo = tbOrderDetail.getOptionInfo();
        this.setGb = tbOrderDetail.getSetGb();
        this.setOrderId = tbOrderDetail.getSetOrderId();
        this.setOrderSeq = tbOrderDetail.getSetOrderSeq();
        this.qty = tbOrderDetail.getQty();
        this.itemAmt = tbOrderDetail.getItemAmt();
        this.goodsPrice = tbOrderDetail.getGoodsPrice();
        this.salePrice = tbOrderDetail.getSalePrice();
        this.goodsDcPrice = tbOrderDetail.getGoodsDcPrice();
        this.memberDcPrice = tbOrderDetail.getMemberDcPrice();
        this.couponDcPrice = tbOrderDetail.getCouponDcPrice();
        this.adminDcPrice = tbOrderDetail.getAdminDcPrice();
        this.dcSumPrice = tbOrderDetail.getDcSumPrice();
        this.deliPrice = tbOrderDetail.getDeliPrice();
        this.deliMethod = tbOrderDetail.getDeliMethod();
        this.channelOrderNo = tbOrderDetail.getChannelOrderNo();
        this.channelOrderSeq = tbOrderDetail.getChannelOrderSeq();
        this.lastGb = tbOrderDetail.getLastGb();
        this.lastCategoryId = tbOrderDetail.getLastCategoryId();
        this.storageId = tbOrderDetail.getStorageId();
        this.ititmm = tbOrderDetail.getItitmm();
        // 21-10-06
        this.scmNo = tbOrderDetail.getScmNo();
        this.parentOrderSeq = tbOrderDetail.parentOrderSeq;

        super.setRegDt(tbOrderDetail.getRegDt());
        super.setRegId(tbOrderDetail.getRegId());
        super.setUpdDt(tbOrderDetail.getUpdDt());
        super.setUpdId(tbOrderDetail.getUpdId());

		// 21-10-13 추가
		this.claimHandleMode = tbOrderDetail.getClaimHandleMode();
		this.claimHandleReason = tbOrderDetail.getClaimHandleReason();
		this.claimHandleDetailReason = tbOrderDetail.getClaimHandleDetailReason();

    }
    @Id
    private String orderId;
    @Id
    private String orderSeq;

    private String statusCd;
    private String assortGb;
    private String assortId;
    private String itemId;
    private String goodsNm;
    private String optionInfo;
    private String setGb;
    private String setOrderId;
    private String setOrderSeq;
    private Long qty;
    private Float itemAmt;
    private Float goodsPrice;
    private Float salePrice;
    private Float goodsDcPrice;
    private Float memberDcPrice;
    private Float couponDcPrice;
    private Float adminDcPrice;
    private Float dcSumPrice;
    private Float deliPrice;
    private String deliMethod;
    private String channelOrderNo;
    private String channelOrderSeq; // order_status의 sno
    private String lastGb;
    private String lastCategoryId;
    private String storageId;

    // 21-09-28 새로 생긴 컬럼
    private String optionTextInfo;
    private String listImageData;
    private Float optionPrice;
    private Float optionTextPrice;
    private String deliveryInfo;

    // 21-10-06 새로 생긴 컬럼
    private Long scmNo;
    private String parentOrderSeq;

    // 21-10-07 새로 생긴 컬럼

	// 21-10-13 새로 생긴 컬럼
	private String claimHandleMode;
	private String claimHandleReason;
	private String claimHandleDetailReason;

    @JoinColumns(
    {
        @JoinColumn(name = "assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
        @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    })
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private Ititmm ititmm; // ititmm 연관관계

    @JoinColumn(name = "channelOrderNo", referencedColumnName = "channelOrderNo", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private IfOrderMaster ifOrderMaster; // ifOrderMaster 연관관계

    @JoinColumn(name = "orderId", referencedColumnName = "orderId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private TbOrderMaster tbOrderMaster; // tbOrderMaster 연관관계
}
