package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *  ITASRT table의 Entity
 *  ITASRT : 상품 정보 table
 */

@Entity
@Table(name = "itasrt")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Itasrt extends CommonProps{
	public Itasrt(IfGoodsMaster ifGoodsMaster){
		super(ifGoodsMaster.getRegDt(), ifGoodsMaster.getUpdDt());
		this.assortId = ifGoodsMaster.getAssortId();
		this.assortNm = ifGoodsMaster.getGoodsNm();
		this.assortState = ifGoodsMaster.getGoodsDisplayFl();
		this.assortGb = ifGoodsMaster.getSizeType();
		this.shortageYn = ifGoodsMaster.getGoodsSellFl();
		this.dispCategoryId = ifGoodsMaster.getCateCd();
		this.assortColor = ifGoodsMaster.getGoodsColor();
		this.margin = ifGoodsMaster.getCommission();
		this.brandId = ifGoodsMaster.getBrandCd();
		this.manufactureNm = ifGoodsMaster.getMakerNm();
//		this.origin = ifGoodsMaster.getOriginNm(); // 맵핑 데이블 필요 혹은 하드코딩
		this.assortModel = ifGoodsMaster.getGoodsModelNo();
		this.taxGb = ifGoodsMaster.getTaxFreeFl();
		this.sellStaDt = ifGoodsMaster.getSalesStartYmd();
		this.sellEndDt = ifGoodsMaster.getSalesEndYmd();
		this.localSale = ifGoodsMaster.getGoodsPrice();
		this.localPrice = ifGoodsMaster.getFixedPrice();
		this.deliPrice = ifGoodsMaster.getCostPrice();
		this.optionGbName = ifGoodsMaster.getOptionName();
		this.optionUseYn = ifGoodsMaster.getOptionFl();
		this.mdRrp = ifGoodsMaster.getMdRrp();
		this.mdTax = ifGoodsMaster.getMdTax();
		this.mdYear = ifGoodsMaster.getMdYear();
		this.mdMargin = ifGoodsMaster.getMdMargin();
		this.mdVatrate = ifGoodsMaster.getMdVatrate();
		this.mdOfflinePrice = ifGoodsMaster.getMdOfflinePrice();
		this.mdOnlinePrice = ifGoodsMaster.getMdOnlinePrice();
		this.mdGoodsVatrate = ifGoodsMaster.getMdGoodsVatrate();
		this.buyWhere = ifGoodsMaster.getBuyWhere();
		this.buySupplyDiscount = ifGoodsMaster.getBuySupplyDiscount();
		this.buyRrpIncrement = ifGoodsMaster.getBuyRrpIncrement();
		this.buyExchangeRate = ifGoodsMaster.getBuyExchangeRate();
		this.asWidth = ifGoodsMaster.getWidth();
		this.asHeight = ifGoodsMaster.getHeight();
		this.asLength = ifGoodsMaster.getDepth();
		this.weight = ifGoodsMaster.getGoodsWeight();

		// 21-10-05 추가
		this.ownerId = StringUtils.leftPad(Long.toString(ifGoodsMaster.getScmNo()),6,'0');
		// 21-10-06 추가
		this.addGoodsYn = StringFactory.getGbTwo(); // 02 하드코딩
	}

	public Itasrt(Itadgs itadgs){
		this.assortId = itadgs.getAddGoodsId();
		this.assortNm = itadgs.getAddGoodsNm();
		this.assortModel = itadgs.getAddGoodsModel();
		this.localPrice = itadgs.getLocalPrice();
		this.localSale = itadgs.getLocalSale();
		this.deliPrice = itadgs.getDeliPrice();
		this.assortState = itadgs.getAddGoodsState();
		this.shortageYn = itadgs.getShortYn();
		this.brandId = itadgs.getBrandId();
		this.manufactureNm = itadgs.getMakerNm();
		this.taxGb = itadgs.getTaxGb();
		this.addOptionNm = itadgs.getOptionNm();
		this.addGoodsYn = StringFactory.getGbOne(); // 01 하드코딩
		this.addImageUrl = itadgs.getImageUrl();
		this.stockCnt = itadgs.getStockCnt();
	}
	@Id
	private String assortId;

	private String assortNm;
	private String assortModel;
	private Float margin;
	private String taxGb;
	private String assortGb;
	private String assortState;
	private Float asWidth;
	private Float asLength;
	private Float asHeight;
	private Float weight;
	private String origin;
	private String shortageYn; // itasrn에도
	private String brandId;
	private String categoryId;
	private String dispCategoryId;
	private String siteGb = StringFactory.getGbOne(); // 01 하드코딩
	private String ownerId;// = StringFactory.getFourStartCd(); // 0001 하드코딩 // 21-10-05 : scmNo를 여섯자리 String으로 만들어 삽입
	private String manufactureNm;
	private Float deliPrice;
	private Float localPrice;
	private Float localDeliFee;
	private Float localSale; // itasrn에도 들어감
	private String assortColor;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date sellStaDt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date sellEndDt;
	private Float mdRrp;
	private String mdTax;
	private String mdYear;
	private Float mdMargin;
	private Float mdVatrate;
	private Float mdOfflinePrice;
	private Float mdOnlinePrice;
	private Float mdGoodsVatrate;
	private String buyWhere;
	private String buyTax;
	private Float buySupplyDiscount;
	private Float buyRrpIncrement;
	private Float buyExchangeRate;
//	private String sizeType;
	private Float mdDiscountRate;
	private String optionGbName;
	private String optionUseYn;
	private String storageId;

	// 21-10-06 추가된 컬럼
	private String addGoodsYn;
	private String addOptionNm;
	private String addImageUrl;
	private Long stockCnt;


	//// 다른 테이블과 엮으면 나오는 프로퍼티들
	@JoinColumn(name = "assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY) // itvari 연관관계
	private List<Itvari> itvariList;

	@JoinColumn(name = "assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY) // ititmm 연관관계
	private List<Ititmm> ititmmList;

	@JoinColumn(name="assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
	@OneToMany(fetch = FetchType.LAZY) // itasrd 연관관계
	@JsonIgnore
	private List<Itasrd> itasrdList;

	@JoinColumn(name="brandId", referencedColumnName = "brandId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Itbrnd itbrnd; // itbrnd 연관관계

	@JoinColumn(name="dispCategoryId", referencedColumnName = "categoryId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
	@ManyToOne
	@JsonIgnore
	@NotFound(action = NotFoundAction.IGNORE)
	private Itcatg itcatg; // itcatg 연관관계


}
