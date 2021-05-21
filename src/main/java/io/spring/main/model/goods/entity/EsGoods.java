package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.GoodsData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="es_goods")
@Getter
@Setter
public class EsGoods {
    @Id
    private Long goodsNo;
    @Column(columnDefinition = "varchar(1) default 'd'")
    private String goodsNmFl;
    @Column(columnDefinition = "varchar(1) default ''")
    private String goodsNm;
    private String goodsNmMain;
    private String goodsNmList;
    private String goodsNmDetail;
    private String goodsNmPartner;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String goodsDisplayFl;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String goodsDisplayMobileFl;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String goodsSellFl;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String goodsSellMobileFl;
    @Column(columnDefinition = "integer default 1")
    private Long scmNo;
    private String purchaseGoodsNm;
    private String applyFl;
    private String applyType;
    private String applyMsg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date applyDt;
    @Column(columnDefinition = "float default 0.00")
    private Float commission;
    private String goodsCd;
    @Column(columnDefinition = "varchar(1) default ''")
    private String cateCd;
    private String goodsSearchWord;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date goodsOpenDt;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String goodsState;
    private String goodsColor;
    @Column(columnDefinition = "varchar(5) default 'local'")
    private String imageStorage;
    private String brandCd;
    private String makerNm;
    private String originNm;
    private String hscode;
    private String goodsModelNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date makeYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date launchYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effectiveStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effectiveEndYmd;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String qrCodeFl;
    @Column(columnDefinition = "varchar(3) default 'all'")
    private String goodsPermission;
    private String goodsPermissionGroup;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String goodsPermissionPriceStringFl;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String goodsPermissionPriceString;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String onlyAdultFl;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String onlyAdultDisplayFl;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String onlyAdultImageFl;
    @Column(columnDefinition = "varchar(3) default 'all'")
    private String goodsAccess;
    private String goodsAccessGroup;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String goodsAccessDisplayFl;
    private String kcmarkInfo;
    @Column(columnDefinition = "varchar(1) default 't'")
    private String taxFreeFl;
    @Column(columnDefinition = "float default 10.0")
    private Float taxPercent;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String cultureBenefitFl;
    @Column(columnDefinition = "float default 0.00")
    private Float goodsWeight;
    @Column(columnDefinition = "integer default 0")
    private Float totalStock;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String stockFl;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String soldOutFl;
    @Column(columnDefinition = "varchar(5) default 'option'")
    private String fixedSales;
    @Column(columnDefinition = "varchar(5) default 'option'")
    private String fixedOrderCnt;
    @Column(columnDefinition = "integer default 1")
    private Long salesUnit;
    @Column(columnDefinition = "integer default 1")
    private Long minOrderCnt;
    @Column(columnDefinition = "integer default 0")
    private Long maxOrderCnt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesEndYmd;
    private String restockFl;
    @Column(columnDefinition = "varchar(1) default 'c'")
    private String mileageFl;
    @Column(columnDefinition = "varchar(3) default 'all'")
    private String mileageGroup;
    @Column(columnDefinition = "float default 0.00")
    private Float mileageGoods;
    @Column(columnDefinition = "varchar(7) default 'percent'")
    private String mileageGoodsUnit;
    private String mileageGroupInfo;
    private String mileageGroupMemberInfo;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String goodsBenefitSetFl;
    private String benefitUseType;
    @Column(columnDefinition = "varchar(5) default 'regDt'")
    private String newGoodsRegFl;
    private Long newGoodsDate;
    @Column(columnDefinition = "varchar(3) default 'day'")
    private String newGoodsDateFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountEnd;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String goodsDiscountFl;
    @Column(columnDefinition = "float default 0.00")
    private Float goodsDiscount;
    @Column(columnDefinition = "varchar(7) default 'percent'")
    private String goodsDiscountUnit;
    private String fixedGoodsDiscount;
    @Column(columnDefinition = "varchar(3) default 'all'")
    private String goodsDiscountGroup;
    private String goodsDiscountGroupMemberInfo;
    private String exceptBenefit;
    private String exceptBenefitGroup;
    private String exceptBenefitGroupInfo;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String payLimitFl;
    private String payLimit;
    private String goodsPriceString;
    @Column(columnDefinition = "float default 0.00")
    private Float goodsPrice;
    @Column(columnDefinition = "float default 0.00")
    private Float fixedPrice;
    @Column(columnDefinition = "float default 0.00")
    private Float costPrice;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String optionFl;
    @Column(columnDefinition = "varchar(1) default 's'")
    private String optionDisplayFl;
    private String optionName;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String optionTextFl;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String optionImagePreviewFl;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String optionImageDisplayFl;
    private String addGoodsFl;
    private String shortDescription;
    private String eventDescription;
    private String goodsDescription;
    private String goodsDescriptionMobile;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String goodsDescriptionSameFl;
    @Column(columnDefinition = "integer default 1")
    private Long deliverySno;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String relationFl;
    private String relationSameFl;
    @Column(columnDefinition = "integer default 0")
    private Long relationCnt;
    private String relationGoodsNo;
    private String relationGoodsDate;
    private String relationGoodsEach;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date goodsIconStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date goodsIconEndYmd;
    private String goodsIconCdPeriod;
    private String goodsIconCd;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String imgDetailViewFl;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String externalVideoFl;
    private String externalVideoUrl;
    @Column(columnDefinition = "integer default 0")
    private Long externalVideoWidth;
    @Column(columnDefinition = "integer default 0")
    private Long externalVideoHeight;
    @Column(columnDefinition = "varchar(2) default 'no'")
    private String detailInfoDeliveryFl;
    private String detailInfoDelivery;
    private String detailInfoDeliveryDirectInput;
    @Column(columnDefinition = "varchar(2) default 'no'")
    private String detailInfoAsFl;
    private String detailInfoAs;
    private String detailInfoAsDirectInput;
    @Column(columnDefinition = "varchar(2) default 'no'")
    private String detailInfoRefundFl;
    private String detailInfoRefund;
    private String detailInfoRefundDirectInput;
    @Column(columnDefinition = "varchar(2) default 'no'")
    private String detailInfoExchangeFl;
    private String detailInfoExchange;
    private String detailInfoExchangeDirectInput;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String seoTagFl;
    private Long seoTagSno;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String naverFl;
    @Column(columnDefinition = "varchar(1) default 'y'")
    private String daumFl;
    private String paycoFl;
    private String naverImportFlag;
    private String naverProductFlag;
    private String naverAgeGroup;
    private String naverGender;
    private String naverTag;
    private String naverAttribute;
    private String naverCategory;
    private String naverProductId;
    private String memo;
    @Column(columnDefinition = "integer default 0")
    private Long orderCnt;
    private Long orderGoodsCnt;
    @Column(columnDefinition = "integer default 0")
    private Long hitCnt;
    private Long cartCnt;
    private Long wishCnt;
    private Long reviewCnt;
    private Long plusReviewCnt;
    private String excelFl;
    @Column(columnDefinition = "varchar(4) default 'n'")
    private String delFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date modDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date delDt;
    private String goodsCeleb;
    private String goodsMainListShot;
    private String goodsMainListLong;
    private String goodsCateListShot;
    private String goodsCateListLong;
    private Long prodInc;
    private String pUrl;
    private String mallKind;
    private String mallId;
    private Long customs;
    private Long addTax;
    private String pName;
    private Float lPrice;
    private Float sPrice;
    private String pImg;
    private String itemCode;
    private String logoImg;
    private Long stock;
    @Column(columnDefinition = "integer default 0")
    private Long fpGoodsOpen;
    @Column(columnDefinition = "integer default 0")
    private Long fpGoodsName;
    @Column(columnDefinition = "integer default 0")
    private Long fpGoodsDesc;
    @Column(columnDefinition = "integer default 1")
    private Long fsGoodsName;
    @Column(columnDefinition = "integer default 1")
    private Long fsGoodsImg;
    @Column(columnDefinition = "integer default 1")
    private Long fsGoodsDesc;
    @Column(columnDefinition = "integer default 0")
    private Long fsGoodsOpen;
    @Column(columnDefinition = "integer default 0")
    private Long fsGoodsBrand;
    @Column(columnDefinition = "integer default 0")
    private Long fsGoodsPrice;
    @Column(columnDefinition = "integer default 0")
    private Long fsGoodsDelivery;
    @Column(columnDefinition = "float default 0.00")
    private Float goodsVolume;
    @Column(columnDefinition = "integer default 0")
    private Long cremaReviewCnt;
    private Float mdRrp;
    @Column(columnDefinition = "varchar(4) default 'incl'")
    private String mdTax;
    private String mdYear;
    private Float mdMargin;
    private Float mdVatrate;
    private Float mdOfflinePrice;
    private Float mdOnlinePrice;
    private Float mdGoodsVatrate;
    private String buyWhere;
    @Column(columnDefinition = "varchar(4) default 'incl'")
    private String buyTax;
    private Float buySupplyDiscount;
    private Float buyRrpIncrement;
    private Float buyExchangeRate;
    private Long width;
    private Long height;
    private Long depth;
    private String sizeType;
    private Float mdDiscountRate;
    @Column(columnDefinition = "integer default 0")
    private Long naverReviewCnt;
    private String goodsMakeWeek;
    private String goodsSize;
}
