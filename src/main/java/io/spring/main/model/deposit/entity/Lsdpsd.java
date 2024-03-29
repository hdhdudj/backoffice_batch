package io.spring.main.model.deposit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import io.spring.main.model.deposit.idclass.LsdpsdId;
import io.spring.main.model.deposit.request.DepositInsertRequestData;
import io.spring.main.model.goods.entity.Itasrt;
import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.util.Utilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.flywaydb.core.internal.util.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="lsdpsd")
@IdClass(LsdpsdId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lsdpsd extends CommonProps {
    public Lsdpsd(String depositNo, DepositInsertRequestData.Item item){
        this.depositNo = depositNo;
        this.depositSeq = item.getDepositSeq();
        this.assortId = item.getAssortId();
        this.itemGrade = item.getItemGrade();
        this.itemId = item.getItemId();
        this.extraClsCd = StringFactory.getGbOne(); // 초기값 일단 하드코딩 '01'
        this.salePrice = (float) 0;
        this.depositQty = item.getDepositQty();
        this.extraUnitcost = item.getExtraUnitcost();
        this.deliPrice = this.depositQty * this.extraUnitcost;
        this.extraCost = this.deliPrice;
        this.extraQty = this.depositQty;
        this.finishYymm = Utilities.getStringToDate(StringFactory.getDoomDay());
        this.depositType = StringFactory.getGbOne(); // 초기값 일단 하드코딩 '01' 입고
        this.siteGb = StringFactory.getGbOne(); // 초기값 일단 하드코딩 '01'
        this.ownerId = Utilities.getStringNo('B', StringFactory.getStrOne(), 6);
        this.inputNo = item.getPurchaseNo();
        this.inputSeq = item.getPurchaseSeq();
    }
    @Id
    private String depositNo;
    @Id
    private String depositSeq;
    private String assortId;
    private String itemId;
    private String itemGrade;
    private String extraClsCd;
    private Long depositQty;
    private Float deliPrice;
    private Float salePrice;
    private Float extraUnitcost;
    private Float extraCost;
    private Long extraQty;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date finishYymm;
    private String depositType;
    private String siteGb;
    private String ownerId;
    private String sStorageCd;
    private String minDepositNo;
    private String minDepositSeq;
    private String inputNo;
    private String inputSeq;
    private Date excAppDt;


    // 연관 관계 lsdpsm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depositNo", referencedColumnName="depositNo", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private Lsdpsm lsdpsm;

    // 연관 관계 lsdpsp
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "inputNo", referencedColumnName="purchaseNo", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
            @JoinColumn(name = "inputSeq", referencedColumnName="purchaseSeq", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
    })
    private Lsdpsp lsdpsp;

    // 연관 관계 lsdpds
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "depositNo", referencedColumnName="depositNo", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
            @JoinColumn(name = "depositSeq", referencedColumnName="depositSeq", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
    })
    private Lsdpds lsdpds;

    // 연관 관계 itasrt
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assortId", referencedColumnName="assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private Itasrt itasrt;

    // 연관 관계 ititmm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "assortId", referencedColumnName="assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
            @JoinColumn(name = "itemId", referencedColumnName="itemId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
    })
    private Ititmm ititmm;

//    // 연관 관계 ititmc
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "inputNo", referencedColumnName="purchaseNo", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
//            @JoinColumn(name = "inputSeq", referencedColumnName="purchaseSeq", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
//    })
//    private Ititmc ititmc;
}
