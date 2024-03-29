package io.spring.main.model.goods.idclass;

import io.spring.main.model.deposit.request.DepositInsertRequestData;
import io.spring.main.model.purchase.request.PurchaseInsertRequestData;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItitmtId implements Serializable {
    //default serial version id, required for serializable classes.
    public ItitmtId(PurchaseInsertRequestData purchaseInsertRequestData, PurchaseInsertRequestData.Items items){
        this.storageId = purchaseInsertRequestData.getStoreCd();
        this.assortId = items.getAssortId();
        this.itemId = items.getItemId();
        this.itemGrade = items.getItemGrade();
        this.effStaDt = purchaseInsertRequestData.getPurchaseDt();
        this.effEndDt = this.effStaDt;
    }

    public ItitmtId(DepositInsertRequestData depositInsertRequestData, DepositInsertRequestData.Item item){
        this.storageId = depositInsertRequestData.getStoreCd();
        this.assortId = item.getAssortId();
        this.itemId = item.getItemId();
        this.itemGrade = item.getItemGrade();
        this.effStaDt = depositInsertRequestData.getDepositDt();
        this.effEndDt = depositInsertRequestData.getDepositDt();
    }

    private static final long serialVersionUID = 1L;
    private String storageId;
    private String assortId;
    private String itemId;
    private String itemGrade;
    private Date effEndDt;
    private Date effStaDt;
}
