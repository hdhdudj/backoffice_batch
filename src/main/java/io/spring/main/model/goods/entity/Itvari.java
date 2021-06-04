package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.model.goods.idclass.ItvariId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "itvari")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ItvariId.class)
public class Itvari extends CommonProps{
    public Itvari(String assortId){ // 단품인 경우
        this.assortId = assortId;
        this.seq = StringUtils.leftPad(StringFactory.getStrOne(), 4, '0');
        this.optionGb = StringFactory.getGbOne();
        this.variationGb = optionGb;
        this.optionNm = StringFactory.getStrSingleGoods();
    }
    /**
     *
     * @param goodsInsertRequestData
     * {
     *    "color": [
     *         {
     *           "seq": "001",
     *           "value": "빨강"
     *         },        
     *         {
     *           "seq": "002",
     *           "value": "파랑"
     *         },
     *         {
     *           "seq": "003",
     *           "value": "노랑"
     *         }
     *             ]
     * }
     */
    public Itvari(IfGoodsOption ifGoodsOption){
        super(ifGoodsOption.getRegDt(), ifGoodsOption.getModDt());
        this.assortId = ifGoodsOption.getAssortId();
    }
//    public Itvari(GoodsInsertRequestData goodsInsertRequestData){
//        this.assortId = goodsInsertRequestData.getAssortId();
//        this.delYn = "02";
////        this.optionGb = goodsRequestData.getOptionGb();
////        this.imgYn = goodsRequestData.getImgYn();
////        this.optionNm = goodsRequestData.getOptionNm();
////        this.regId = "123"; // 추후 추가
////        this.updId = "123"; // 추후 추가
//    }

    @Id
    private String assortId;
    @Id
    private String seq;

    private String optionGb;
    private String imgYn;
    private String optionNm;
    private String variationGb;
    private String delYn = StringFactory.getGbTwo(); // n 하드코딩

//    @CreationTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date regDt;
//    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date updDt;
//    private Long regId;
//    private Long updId;

    // itasrt 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private Itasrt itasrt;

//    // ititmm 연관 관계
//    @OneToMany(fetch = FetchType.LAZY, targetEntity = Ititmm.class)
//    @JoinColumns({
//            @JoinColumn(name = "assortId", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
//            @JoinColumn(name = "seq", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
//    })
////    @JoinColumn(name="assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none"))
//    private List<Ititmm> ititmm1;
//
//    // ititmm 연관 관계
//    @OneToMany(fetch = FetchType.LAZY, targetEntity = Ititmm.class)
//    @JoinColumns({
//            @JoinColumn(name = "assortId", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
//            @JoinColumn(name = "seq", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
//    })
////    @JoinColumn(name="assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none"))
//    private List<Ititmm> ititmm2;
}
