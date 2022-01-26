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
@Table(name="lspchs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lspchs extends CommonProps {
    private final static Logger logger = LoggerFactory.getLogger(Lspchs.class);
    public Lspchs(PurchaseInsertRequestData purchaseInsertRequestData){
        this.purchaseNo = purchaseInsertRequestData.getPurchaseNo();
        this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay());
        this.purchaseStatus = purchaseInsertRequestData.getPurchaseStatus();
    }
    public Lspchs(Lspchs lspchs){
        this.purchaseNo = lspchs.getPurchaseNo();
        this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay());
        this.purchaseStatus = lspchs.getPurchaseStatus();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String purchaseNo;
    private Date effEndDt;
    private String purchaseStatus;
    @CreationTimestamp
    private Date effStaDt;
}
