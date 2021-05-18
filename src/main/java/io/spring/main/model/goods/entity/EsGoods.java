package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Tags;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="es_goods")
@Getter
@Setter
public class EsGoods {
    public EsGoods(){

    }
    @Id
    private Long goodsNo;
    private String goodsNmFl;
    private String goodsNm;
    private String goodsNmMain;
    private String goodsNmList;
    private String goodsNmDetail;
    private String goodsNmPartner;
    private String goodsDisplayFl;
    private String goodsDisplayMobileFl;
    private String goodsSellFl;
    private String goodsSellMobileFl;
    private Long scmNo;
    private Long purchaseGoodsNm;
    private String applyFl;
    private String applyType;
    private String applyMsg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date applyDt;
    private Float commission;
    private String goodsCd;
    private String cateCd;
    private String goodsSearchWord;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date goodsOpenDt;
    private String goodsState;
    private String goodsColor;
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
    private String qrCodeFl;
    private String goodsPermission;
    private String goodsPermissionGroup;
    private String goodsPermissionPriceStringFl;
    private String goodsPermissionPriceString;
    private String onlyAdultFl;
    private String onlyAdultDisplayFl;
    private String onlyAdultImageFl;
    private String goodsAccess;
    private String goodsAccessGroup;
    private String goodsAccessDisplayFl;
    private String kcmarkInfo;
    private String taxFreeFl;
    private Float taxPercent;
    private String cultureBenefitFl;
    private Float goodsWeight;
    private Float totalStock;
    private String stockFl;
    private String soldOutFl;
    private String fixedSales;
    private String fixedOrderCnt;
    private Long salesUnit;
    private Long minOrderCnt;
    private Long maxOrderCnt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesEndYmd;
    private String restockFl;
    private String mileageFl;
    private String mileageGroup;
    private Float mileageGoods;
    private String mileageGoodsUnit;
    private String mileageGroupInfo;
    private String mileageGroupMemberInfo;
    private String goodsBenefitSetFl;
    private String benefitUseType;
    private String newGoodsRegFl;
    private Long newGoodsDate;
    private String newGoodsDateFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountEnd;
    private String goodsDiscountFl;
    private Float goodsDiscount;
    private String goodsDiscountUnit;
    private String fixedGoodsDiscount;
    private String goodsDiscountGroup;
    private String goodsDiscountGroupMemberInfo;
    private String exceptBenefit;
    private String exceptBenefitGroup;
    private String exceptBenefitGroupInfo;
    private String payLimitFl;
    private String payLimit;
    private String goodsPriceString;
    private Float goodsPrice;
    private Float fixedPrice;
    private Float costPrice;
    private String optionFl;
    private String optionDisplayFl;
    private String optionName;
    private String optionTextFl;
    private String optionImagePreviewFl;
    private String optionImageDisplayFl;
    private String addGoodsFl;
    private String shortDescription;
    private String eventDescription;
    private String goodsDescription;
    private String goodsDescriptionMobile;
    private Long deliverySno;
    private String relationFl;
    private String relationSameFl;
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
    private String imgDetailViewFl;
    private String externalVideoFl;
    private String externalVideoUrl;
    private Long externalVideoWidth;
    private Long externalVideoHeight;
    private String detailInfoDeliveryFl;
    private String detailInfoDelivery;
    private String detailInfoDeliveryDirectInput;
    private String detailInfoASFl;
    private String detailInfoAS;
    private String detailInfoASDirectInput;
    private String detailInfoRefundFl;
    private String detailInfoRefund;
    private String detailInfoRefundDirectInput;
    private String detailInfoExchangeFl;
    private String detailInfoExchange;
    private String detailInfoExchangeDirectInput;
    private String seoTagFl;
    private Long seoTagSno;
    private String naverFl;
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
    private Long orderCnt;
    private Long orderGoodsCnt;
    private Long hitCnt;
    private Long cartCnt;
    private Long wishCnt;
    private Long reviewCnt;
    private Long plusReviewCnt;
    private String excelFl;
    private String delFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
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
    private String mallID;
    private Long customs;
    private Long addTax;
    private String pName;
    private Float lPrice;
    private Float sPrice;
    private String pImg;
    private String itemCode;
    private String logoImg;
    private Long stock;
    private Long fpGoodsOpen;
    private Long fpGoodsName;
    private Long fpGoodsDesc;
    private Long fsGoodsName;
    private Long fsGoodsImg;
    private Long fsGoodsDesc;
    private Long fsGoodsOpen;
    private Long fsGoodsBrand;
    private Long fsGoodsPrice;
    private Long fsGoodsDelivery;
    private Float goodsVolume;
    private Long cremaReviewCnt;
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
    private Long width;
    private Long height;
    private Long depth;
    private String sizeType;
    private Float mdDiscountRate;
    private Long naverReviewCnt;
    private String goodsMakeWeek;
    private String goodsSize;
}
