package io.spring.main.model.goods.entity;

import io.spring.main.util.StringFactory;
import io.spring.main.model.goods.idclass.ItitmmId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ititmm")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ItitmmId.class)
public class Ititmm extends CommonProps implements Serializable {
    public Ititmm(Itasrt itasrt, Itvari itvari){ // 옵션데이터 없을때(단품일때)
        this.assortId = itasrt.getAssortId();
        this.shortYn = itasrt.getShortageYn();
        variationGb1 = itvari.getVariationGb();
        variationSeq1 = itvari.getSeq();
        itemId = StringUtils.leftPad(StringFactory.getStrOne(), 4, '0');
        itemNm = itasrt.getAssortNm();
        addPrice = StringFactory.getStrZero();
    }

    public Ititmm(IfGoodsOption ifGoodsOption){
        super(ifGoodsOption.getRegDt(), ifGoodsOption.getModDt());
        this.assortId = ifGoodsOption.getAssortId();
        this.shortYn = ifGoodsOption.getSoldOutFl();
//        this.itemNm = ifGoodsOption.getItemId();
//        this.regDt = ifGoodsOption.getRegDt();
//        this.updDt = ifGoodsOption.getModDt();
    }
//    public Ititmm(String assortId, GoodsInsertRequestData.Items items){
//        this.assortId = assortId;
//        this.shortYn = items.getShortYn();
//        this.addPrice = items.getAddPrice();
//    }
    @Id
    private String assortId;
    @Id
    private String itemId;
    private String itemNm;
    private String shortYn;
    private String variationGb1;
    private String variationSeq1;
    private String variationGb2;
    private String variationSeq2;
    private String addPrice;
//    private Long regId;
//    private Long updId;
//    @CreationTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date regDt;
//    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date updDt;

    // itasrt 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private Itasrt itasrt;

    // itvari 연관 관계 (일단 단방향) - 색상
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Itvari.class)
    @JoinColumns({
            @JoinColumn(name = "assortId", referencedColumnName="assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
            @JoinColumn(name = "variationSeq1", referencedColumnName="seq", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
    })
    private Itvari itvari1;

    // itvari 연관 관계 (일단 단방향) - 사이즈
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Itvari.class)
    @JoinColumns({
            @JoinColumn(name = "assortId", referencedColumnName="assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
            @JoinColumn(name = "variationSeq2", referencedColumnName="seq", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
    })
    private Itvari itvari2;

    private String orderLmtYn;
    private String orderLmtCnt;
    private Long minCnt = 0l;
    private Long maxCnt = 0l;
    private String dayDeliCnt;
    private String totDeliCnt;
    private String setYn = StringFactory.getGbTwo(); // 02 하드코딩
}
