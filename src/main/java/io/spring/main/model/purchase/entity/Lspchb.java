package io.spring.main.model.purchase.entity;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import io.spring.main.model.purchase.request.PurchaseInsertRequestData;
import io.spring.main.util.Utilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="lspchb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lspchb extends CommonProps {
    private final static Logger logger = LoggerFactory.getLogger(Lspchb.class);
    public Lspchb(PurchaseInsertRequestData purchaseInsertRequestData){
        try
        {
            this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        }
        catch (Exception e){
            logger.debug(e.getMessage());
        }
        this.purchaseNo = purchaseInsertRequestData.getPurchaseNo();
        this.purchaseSeq = purchaseInsertRequestData.getPurchaseSeq();
        this.purchaseStatus = purchaseInsertRequestData.getPurchaseStatus();
        this.cancelGb = StringFactory.getNinetyNine();
    }
    public Lspchb(Lspchb lspchb){
        try
        {
            this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        }
        catch (Exception e){
            logger.debug(e.getMessage());
        }
        this.purchaseNo = lspchb.getPurchaseNo();
        this.purchaseSeq = lspchb.getPurchaseSeq();
        this.purchaseStatus = lspchb.getPurchaseStatus();
        this.cancelGb = StringFactory.getNinetyNine();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String purchaseNo;
    private String purchaseSeq;
    private Date effEndDt;
    @CreationTimestamp
    private Date effStaDt;
    private String purchaseStatus;
    private String cancelGb;
}
