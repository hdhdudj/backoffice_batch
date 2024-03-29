package io.spring.main.model.purchase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import io.spring.main.model.purchase.request.PurchaseInsertRequestData;
import io.spring.main.util.Utilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Slf4j
@Entity
@Table(name="lspchm")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lspchm extends CommonProps {
    public Lspchm(PurchaseInsertRequestData purchaseInsertRequestData){
        this.purchaseNo = purchaseInsertRequestData.getPurchaseNo();
        this.purchaseDt = purchaseInsertRequestData.getPurchaseDt();
        this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay());
        this.purchaseStatus = purchaseInsertRequestData.getPurchaseStatus(); // 01 : 발주, 05 : 취소
        this.purchaseRemark = purchaseInsertRequestData.getPurchaseRemark();
        this.siteGb = StringFactory.getGbOne(); // "01"
        this.vendorId = StringFactory.getFourStartCd(); // "0001"
        this.siteOrderNo = purchaseInsertRequestData.getSiteOrderNo();
        this.siteTrackNo = purchaseInsertRequestData.getSiteTrackNo();
        this.localPrice = purchaseInsertRequestData.getLocalPrice();
        this.newLocalPrice = this.localPrice;
        this.localDeliFee = purchaseInsertRequestData.getLocalDeliFee();
        this.newLocalDeliFee = this.localDeliFee;
        this.localTax = purchaseInsertRequestData.getLocalTax();
        this.newLocalTax = this.localTax;
        this.disPrice = purchaseInsertRequestData.getDisPrice();
        this.newDisPrice = this.disPrice;
        this.purchaseGb = StringFactory.getGbOne(); // "01" : 일반발주
        this.ownerId = purchaseInsertRequestData.getPurchaseVendorId();
        this.storeCd = purchaseInsertRequestData.getStoreCd(); // "00001"
        this.oStoreCd = purchaseInsertRequestData.getOStoreCd();
        this.terms = purchaseInsertRequestData.getTerms();
        this.delivery = purchaseInsertRequestData.getDelivery();
        this.payment = purchaseInsertRequestData.getPayment();
        this.carrier = purchaseInsertRequestData.getCarrier();

    }
    @Id
    private String purchaseNo;
    private Date purchaseDt;
    private Date effEndDt;
    private String purchaseStatus;
    private String purchaseRemark;
    private String siteGb;
    private String vendorId;
    private String dealtypeCd;
    private String siteOrderNo;
    private String siteTrackNo;
    private String purchaseCustNm;
    private Long localPrice;
    private Long newLocalPrice;
    private Long localDeliFee;
    private Long newLocalDeliFee;
    private Long localTax;
    private Long newLocalTax;
    private Long disPrice;
    private Long newDisPrice;
    private String cardId;
    private String purchaseGb;
    private String ownerId;
    private String affilVdId;
    private String storeCd;
    private String oStoreCd;
    private String terms;
    private String delivery;
    private String payment;
    private String carrier;

    // 연관관계 : lspchd
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "purchaseNo", referencedColumnName = "purchaseNo", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private List<Lspchd> lspchdList;
}
