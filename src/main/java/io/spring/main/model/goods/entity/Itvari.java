package io.spring.main.model.goods.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import io.spring.main.model.goods.idclass.ItvariId;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public Itvari(String assortId, String Seq) { // 단품인 경우
		this.assortId = assortId;
		this.seq = Seq;
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
    private String imgYn = StringFactory.getGbTwo(); // 02 하드코딩
    private String optionNm;
    private String variationGb;
    private String delYn = StringFactory.getGbTwo(); // 02 하드코딩

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
