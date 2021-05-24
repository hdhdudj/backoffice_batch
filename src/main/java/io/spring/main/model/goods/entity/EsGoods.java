package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
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
    private String goodsNmFl = StringFactory.getStrD();
    private String goodsNm = "";
    private String goodsNmMain;
    private String goodsNmList;
    private String goodsNmDetail;
    private String goodsNmPartner;
    private String goodsDisplayFl = StringFactory.getStrY();
    private String goodsDisplayMobileFl = StringFactory.getStrY();
    private String goodsSellFl;
    private String goodsSellMobileFl = StringFactory.getStrY();
    private Long scmNo = 1l;
    private String purchaseGoodsNm;
    private String applyFl;
    private String applyType;
    private String applyMsg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date applyDt;
    private Float commission = 0f;
    private String goodsCd;
    private String cateCd = "";
    private String goodsSearchWord;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date goodsOpenDt;
    private String goodsState = StringFactory.getStrN();
    private String goodsColor;
    private String imageStorage = StringFactory.getStrLocal();
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
    private String qrCodeFl = StringFactory.getStrN();
    private String goodsPermission = StringFactory.getStrAll();
    private String goodsPermissionGroup;
    private String goodsPermissionPriceStringFl = StringFactory.getStrN();
    private String goodsPermissionPriceString = StringFactory.getStrN();
    private String onlyAdultFl = StringFactory.getStrN();
    private String onlyAdultDisplayFl = StringFactory.getStrY();
    private String onlyAdultImageFl = StringFactory.getStrN();
    private String goodsAccess = StringFactory.getStrAll();
    private String goodsAccessGroup;
    private String goodsAccessDisplayFl = StringFactory.getStrY();
    private String kcmarkInfo;
    private String taxFreeFl = StringFactory.getStrT();
    private Float taxPercent = 10f;
    private String cultureBenefitFl = StringFactory.getStrN();
    private Float goodsWeight = 0f;
    private Float totalStock = 0f;
    private String stockFl = StringFactory.getStrN();
    private String soldOutFl = StringFactory.getStrN();
    private String fixedSales = StringFactory.getStrOption();
    private String fixedOrderCnt = StringFactory.getStrOption();
    private Long salesUnit = 1l;
    private Long minOrderCnt = 1l;
    private Long maxOrderCnt = 0l;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesEndYmd;
    private String restockFl;
    private String mileageFl = StringFactory.getStrC();
    private String mileageGroup = StringFactory.getStrAll();
    private Float mileageGoods = 0f;
    private String mileageGoodsUnit = StringFactory.getStrPercent();
    private String mileageGroupInfo;
    private String mileageGroupMemberInfo;
    private String goodsBenefitSetFl = StringFactory.getStrN();
    private String benefitUseType;
    private String newGoodsRegFl = StringFactory.getStrRegDt();
    private Long newGoodsDate;
    private String newGoodsDateFl = StringFactory.getStrDay();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountEnd;
    private String goodsDiscountFl = StringFactory.getStrN();
    private Float goodsDiscount = 0f;
    private String goodsDiscountUnit = StringFactory.getStrPercent();
    private String fixedGoodsDiscount;
    private String goodsDiscountGroup = StringFactory.getStrAll();
    private String goodsDiscountGroupMemberInfo;
    private String exceptBenefit;
    private String exceptBenefitGroup;
    private String exceptBenefitGroupInfo;
    private String payLimitFl = StringFactory.getStrN();
    private String payLimit;
    private String goodsPriceString;
    private Float goodsPrice = 0f;
    private Float fixedPrice = 0f;
    private Float costPrice = 0f;
    private String optionFl = StringFactory.getStrN();
    private String optionDisplayFl = StringFactory.getStrS();
    private String optionName;
    private String optionTextFl = StringFactory.getStrN();
    private String optionImagePreviewFl = StringFactory.getStrN();
    private String optionImageDisplayFl = StringFactory.getStrN();
    private String addGoodsFl;
    private String shortDescription;
    private String eventDescription;
    private String goodsDescription;
    private String goodsDescriptionMobile;
    private String goodsDescriptionSameFl = StringFactory.getStrY();
    private Long deliverySno = 1l;
    private String relationFl = StringFactory.getStrN();
    private String relationSameFl;
    private Long relationCnt = 0l;
    private String relationGoodsNo;
    private String relationGoodsDate;
    private String relationGoodsEach;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date goodsIconStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date goodsIconEndYmd;
    private String goodsIconCdPeriod;
    private String goodsIconCd;
    private String imgDetailViewFl = StringFactory.getStrN();
    private String externalVideoFl = StringFactory.getStrN();
    private String externalVideoUrl;
    private Long externalVideoWidth = 0l;
    private Long externalVideoHeight = 0l;
    private String detailInfoDeliveryFl = StringFactory.getStrNo();
    private String detailInfoDelivery;
    private String detailInfoDeliveryDirectInput;
    private String detailInfoAsFl = StringFactory.getStrNo();
    private String detailInfoAs;
    private String detailInfoAsDirectInput;
    private String detailInfoRefundFl = StringFactory.getStrNo();
    private String detailInfoRefund;
    private String detailInfoRefundDirectInput;
    private String detailInfoExchangeFl = StringFactory.getStrNo();
    private String detailInfoExchange;
    private String detailInfoExchangeDirectInput;
    private String seoTagFl = StringFactory.getStrN();
    private Long seoTagSno;
    private String naverFl = StringFactory.getStrY();
    private String daumFl = StringFactory.getStrY();
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
    private Long orderCnt = 0l;
    private Long orderGoodsCnt;
    private Long hitCnt = 0l;
    private Long cartCnt;
    private Long wishCnt;
    private Long reviewCnt;
    private Long plusReviewCnt;
    private String excelFl;
    @Column(columnDefinition = "varchar(1) default 'n'")
    private String delFl = StringFactory.getStrN();
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
    private Long fpGoodsOpen = 0l;
    private Long fpGoodsName = 0l;
    private Long fpGoodsDesc = 0l;
    private Long fsGoodsName = 1l;
    private Long fsGoodsImg = 1l;
    private Long fsGoodsDesc = 1l;
    private Long fsGoodsOpen = 0l;
    private Long fsGoodsBrand = 0l;
    private Long fsGoodsPrice = 0l;
    private Long fsGoodsDelivery = 0l;
    private Float goodsVolume = 0f;
    private Long cremaReviewCnt = 0l;
    private Float mdRrp;
    private String mdTax = StringFactory.getStrIncl();
    private String mdYear;
    private Float mdMargin;
    private Float mdVatrate;
    private Float mdOfflinePrice;
    private Float mdOnlinePrice;
    private Float mdGoodsVatrate;
    private String buyWhere;
    private String buyTax = StringFactory.getStrIncl();
    private Float buySupplyDiscount;
    private Float buyRrpIncrement;
    private Float buyExchangeRate;
    private Long width;
    private Long height;
    private Long depth;
    private String sizeType;
    private Float mdDiscountRate;
    private Long naverReviewCnt = 0l;
    private String goodsMakeWeek;
    private String goodsSize;
}
