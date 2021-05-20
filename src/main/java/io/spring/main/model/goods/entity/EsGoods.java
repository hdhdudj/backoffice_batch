package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.GoodsData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.junit.jupiter.api.Tags;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="es_goods")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EsGoods {
    public EsGoods(GoodsData goodsData){
        this.goodsNo = goodsData.getGoodsNo();
        // not null에 default value 필요한 애들
        this.buyExchangeRate = 0F;
        this.buyRrpIncrement = 0F;
        this.buySupplyDiscount = 0F;
        this.width = 0L;
        this.height = 0L;
        this.depth = 0L;
        this.mdDiscountRate = 0F;
        this.newGoodsDate = 0L;
        this.seoTagSno = 0L;
        this.goodsCeleb = "";
        this.goodsMainListShot = "";
        this.goodsMainListLong = "";
        this.goodsCateListShot = "";
        this.goodsCateListLong = "";
        this.mdRrp = 0f;
        this.mdYear = "";
        this.mdMargin = 0f;
        this.mdVatrate = 0f;
        this.mdOfflinePrice = 0f;
        this.mdOnlinePrice = 0f;
        this.mdGoodsVatrate = 0f;
        this.buyWhere = "";
        this.buySupplyDiscount = 0f;
        this.buyExchangeRate = 0f;
        this.width = 0l;
        this.height = 0l;
        this.depth = 0l;
        this.mdDiscountRate = 0f;
    }
    @Id
    private Long goodsNo;
    @Generated(GenerationTime.INSERT)
    private String goodsNmFl;
    @Generated(GenerationTime.INSERT)
    private String goodsNm;
    @Generated(GenerationTime.INSERT)
    private String goodsNmMain;
    private String goodsNmList;
    private String goodsNmDetail;
    private String goodsNmPartner;
    @Generated(GenerationTime.INSERT)
    private String goodsDisplayFl;
    @Generated(GenerationTime.INSERT)
    private String goodsDisplayMobileFl;
    @Generated(GenerationTime.INSERT)
    private String goodsSellFl;
    @Generated(GenerationTime.INSERT)
    private String goodsSellMobileFl;
    @Generated(GenerationTime.INSERT)
    private Long scmNo;
    private Long purchaseGoodsNm;
    @Generated(GenerationTime.INSERT)
    private String applyFl;
    private String applyType;
    private String applyMsg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date applyDt;
    @Generated(GenerationTime.INSERT)
    private Float commission;
    private String goodsCd;
    @Generated(GenerationTime.INSERT)
    private String cateCd;
    private String goodsSearchWord;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date goodsOpenDt;
    @Generated(GenerationTime.INSERT)
    private String goodsState;
    private String goodsColor;
    @Generated(GenerationTime.INSERT)
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
    @Generated(GenerationTime.INSERT)
    private String qrCodeFl;
    @Generated(GenerationTime.INSERT)
    private String goodsPermission;
    private String goodsPermissionGroup;
    @Generated(GenerationTime.INSERT)
    private String goodsPermissionPriceStringFl;
    @Generated(GenerationTime.INSERT)
    private String goodsPermissionPriceString;
    @Generated(GenerationTime.INSERT)
    private String onlyAdultFl;
    @Generated(GenerationTime.INSERT)
    private String onlyAdultDisplayFl;
    @Generated(GenerationTime.INSERT)
    private String onlyAdultImageFl;
    @Generated(GenerationTime.INSERT)
    private String goodsAccess;
    private String goodsAccessGroup;
    @Generated(GenerationTime.INSERT)
    private String goodsAccessDisplayFl;
    private String kcmarkInfo;
    @Generated(GenerationTime.INSERT)
    private String taxFreeFl;
    @Generated(GenerationTime.INSERT)
    private Float taxPercent;
    @Generated(GenerationTime.INSERT)
    private String cultureBenefitFl;
    @Generated(GenerationTime.INSERT)
    private Float goodsWeight;
    @Generated(GenerationTime.INSERT)
    private Float totalStock;
    @Generated(GenerationTime.INSERT)
    private String stockFl;
    @Generated(GenerationTime.INSERT)
    private String soldOutFl;
    @Generated(GenerationTime.INSERT)
    private String fixedSales;
    @Generated(GenerationTime.INSERT)
    private String fixedOrderCnt;
    @Generated(GenerationTime.INSERT)
    private Long salesUnit;
    @Generated(GenerationTime.INSERT)
    private Long minOrderCnt;
    @Generated(GenerationTime.INSERT)
    private Long maxOrderCnt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesEndYmd;
    private String restockFl;
    @Generated(GenerationTime.INSERT)
    private String mileageFl;
    @Generated(GenerationTime.INSERT)
    private String mileageGroup;
    @Generated(GenerationTime.INSERT)
    private Float mileageGoods;
    @Generated(GenerationTime.INSERT)
    private String mileageGoodsUnit;
    private String mileageGroupInfo;
    private String mileageGroupMemberInfo;
    @Generated(GenerationTime.INSERT)
    private String goodsBenefitSetFl;
    private String benefitUseType;
    @Generated(GenerationTime.INSERT)
    private String newGoodsRegFl;
    private Long newGoodsDate;
    @Generated(GenerationTime.INSERT)
    private String newGoodsDateFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date periodDiscountEnd;
    @Generated(GenerationTime.INSERT)
    private String goodsDiscountFl;
    @Generated(GenerationTime.INSERT)
    private Float goodsDiscount;
    @Generated(GenerationTime.INSERT)
    private String goodsDiscountUnit;
    private String fixedGoodsDiscount;
    @Generated(GenerationTime.INSERT)
    private String goodsDiscountGroup;
    private String goodsDiscountGroupMemberInfo;
    private String exceptBenefit;
    private String exceptBenefitGroup;
    private String exceptBenefitGroupInfo;
    @Generated(GenerationTime.INSERT)
    private String payLimitFl;
    private String payLimit;
    private String goodsPriceString;
    @Generated(GenerationTime.INSERT)
    private Float goodsPrice;
    @Generated(GenerationTime.INSERT)
    private Float fixedPrice;
    @Generated(GenerationTime.INSERT)
    private Float costPrice;
    @Generated(GenerationTime.INSERT)
    private String optionFl;
    @Generated(GenerationTime.INSERT)
    private String optionDisplayFl;
    private String optionName;
    @Generated(GenerationTime.INSERT)
    private String optionTextFl;
    @Generated(GenerationTime.INSERT)
    private String optionImagePreviewFl;
    @Generated(GenerationTime.INSERT)
    private String optionImageDisplayFl;
    private String addGoodsFl;
    private String shortDescription;
    private String eventDescription;
    private String goodsDescription;
    private String goodsDescriptionMobile;
    @Generated(GenerationTime.INSERT)
    private String goodsDescriptionSameFl;
    @Generated(GenerationTime.INSERT)
    private Long deliverySno;
    @Generated(GenerationTime.INSERT)
    private String relationFl;
    private String relationSameFl;
    @Generated(GenerationTime.INSERT)
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
    @Generated(GenerationTime.INSERT)
    private String imgDetailViewFl;
    @Generated(GenerationTime.INSERT)
    private String externalVideoFl;
    private String externalVideoUrl;
    @Generated(GenerationTime.INSERT)
    private Long externalVideoWidth;
    @Generated(GenerationTime.INSERT)
    private Long externalVideoHeight;
    @Generated(GenerationTime.INSERT)
    private String detailInfoDeliveryFl;
    private String detailInfoDelivery;
    private String detailInfoDeliveryDirectInput;
    @Generated(GenerationTime.INSERT)
    private String detailInfoAsFl;
    private String detailInfoAs;
    private String detailInfoAsDirectInput;
    @Generated(GenerationTime.INSERT)
    private String detailInfoRefundFl;
    private String detailInfoRefund;
    private String detailInfoRefundDirectInput;
    @Generated(GenerationTime.INSERT)
    private String detailInfoExchangeFl;
    private String detailInfoExchange;
    private String detailInfoExchangeDirectInput;
    @Generated(GenerationTime.INSERT)
    private String seoTagFl;
    private Long seoTagSno;
    @Generated(GenerationTime.INSERT)
    private String naverFl;
    @Generated(GenerationTime.INSERT)
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
    @Generated(GenerationTime.INSERT)
    private Long orderCnt;
    private Long orderGoodsCnt;
    @Generated(GenerationTime.INSERT)
    private Long hitCnt;
    private Long cartCnt;
    private Long wishCnt;
    private Long reviewCnt;
    private Long plusReviewCnt;
    private String excelFl;
    @Generated(GenerationTime.INSERT)
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
    @Generated(GenerationTime.INSERT)
    private Long fpGoodsOpen;
    @Generated(GenerationTime.INSERT)
    private Long fpGoodsName;
    @Generated(GenerationTime.INSERT)
    private Long fpGoodsDesc;
    @Generated(GenerationTime.INSERT)
    private Long fsGoodsName;
    @Generated(GenerationTime.INSERT)
    private Long fsGoodsImg;
    @Generated(GenerationTime.INSERT)
    private Long fsGoodsDesc;
    @Generated(GenerationTime.INSERT)
    private Long fsGoodsOpen;
    @Generated(GenerationTime.INSERT)
    private Long fsGoodsBrand;
    @Generated(GenerationTime.INSERT)
    private Long fsGoodsPrice;
    @Generated(GenerationTime.INSERT)
    private Long fsGoodsDelivery;
    @Generated(GenerationTime.INSERT)
    private Float goodsVolume;
    @Generated(GenerationTime.INSERT)
    private Long cremaReviewCnt;
    private Float mdRrp;
    @Generated(GenerationTime.INSERT)
    private String mdTax;
    private String mdYear;
    private Float mdMargin;
    private Float mdVatrate;
    private Float mdOfflinePrice;
    private Float mdOnlinePrice;
    private Float mdGoodsVatrate;
    private String buyWhere;
    @Generated(GenerationTime.INSERT)
    private String buyTax;
    private Float buySupplyDiscount;
    private Float buyRrpIncrement;
    private Float buyExchangeRate;
    private Long width;
    private Long height;
    private Long depth;
    private String sizeType;
    private Float mdDiscountRate;
    @Generated(GenerationTime.INSERT)
    private Long naverReviewCnt;
    private String goodsMakeWeek;
    private String goodsSize;
}
