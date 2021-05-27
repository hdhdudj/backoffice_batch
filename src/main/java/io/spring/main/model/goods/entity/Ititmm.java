package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.model.goods.idclass.ItitmmId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ititmm")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ItitmmId.class)
public class Ititmm {
    public Ititmm(IfGoodsOption ifGoodsOption){
        this.assortId = ifGoodsOption.getAssortId();
        this.shortYn = ifGoodsOption.getSoldOutFl();
        this.regDt = ifGoodsOption.getRegDt();
        this.updDt = ifGoodsOption.getModDt();
        this.regId = ifGoodsOption.getRegId();
        this.updId = ifGoodsOption.getUpdId();
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
    @Column(nullable = true)
    private Long regId;
    @Column(nullable = true)
    private Long updId;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date regDt;
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date updDt;

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
//    private String minCnt;
//    private String maxCnt;
    private String dayDeliCnt;
    private String totDeliCnt;
    private String setYn = StringFactory.getGbTwo(); // 02 하드코딩
}
