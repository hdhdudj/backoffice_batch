package io.spring.main.model.goods.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.spring.main.model.goods.idclass.TmitemId;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tmitem")
@IdClass(TmitemId.class)
@NoArgsConstructor
public class Tmitem extends CommonProps{

	public Tmitem(IfGoodsMaster igm, Ititmm im,IfGoodsOption igo) {
		super(new Date(), new Date());
		

			this.channelGb = igm.getChannelGb();
			this.assortId = igm.getAssortId();
			this.itemId = im.getItemId();
			this.channelGoodsNo = igm.getGoodsNo();
			this.effStaDt = new Date();

			this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay());
			this.shortYn = im.getShortYn();
			this.variationGb1 = im.getVariationGb1();
			this.variationSeq1 = im.getVariationSeq1();
			this.variationGb2 = im.getVariationGb2();
			this.variationSeq2 = im.getVariationSeq2();
			this.variationGb3 = im.getVariationGb3();
			this.variationSeq3 = im.getVariationSeq3();

			this.channelGoodsNo = igo.getGoodsNo();
			this.channelOptionsNo = igo.getSno();
			
			
			
		

	}

    @Id
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩
    @Id
    private String assortId;
    @Id
    private String itemId;
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effStaDt;
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effEndDt;
    private String shortYn;
    private String variationGb1;
    private String variationSeq1;
    private String variationGb2;
    private String variationSeq2;
	private String variationGb3;
	private String variationSeq3;
    private String channelGoodsNo;
    private String channelOptionsNo;
    private Float optionPrice;


    // itvari 연관관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "assortId", referencedColumnName="assortId", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
            @JoinColumn(name = "variationSeq1", referencedColumnName="seq", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
    })
    private Itvari itvari1;

    // itvari 연관관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "assortId", referencedColumnName="assortId", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
            @JoinColumn(name = "variationSeq2", referencedColumnName="seq", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
    })
    private Itvari itvari2;

	// itvari 연관관계
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")),
			@JoinColumn(name = "variationSeq3", referencedColumnName = "seq", insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none")), })
	private Itvari itvari3;
}
