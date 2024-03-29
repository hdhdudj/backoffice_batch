package io.spring.main.model.goods.entity;

import io.spring.main.util.StringFactory;
import io.spring.main.model.deposit.request.DepositInsertRequestData;
import io.spring.main.model.goods.idclass.ItitmcId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="ititmc")
@IdClass(ItitmcId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ititmc extends CommonProps{
    public Ititmc(DepositInsertRequestData depositInsertRequestData, DepositInsertRequestData.Item item){
        this.storageId = depositInsertRequestData.getStoreCd();
        this.assortId = item.getAssortId();
        this.itemId = item.getItemId();
        this.itemGrade = item.getItemGrade();
        this.effEndDt = depositInsertRequestData.getDepositDt();
        this.effStaDt = this.effEndDt;
        this.stockGb = StringFactory.getGbOne(); // 01 하드코딩
    }
    @Id
    private String storageId;
    @Id
    private String assortId;
    @Id
    private String itemId;
    @Id
    private String itemGrade;
    @Id
    private Date effEndDt;
    @Id
    private Date effStaDt;
    private String stockGb;
    private Long shipIndicateQty;
    private Long qty;
    private Float stockAmt;
    private String vendorId;
    private String ownerId;
    private String siteGb;
}
