package io.spring.main.model.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class GoodsData {
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
    private String purchaseGoodsNm;
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
    private String goodsDescriptionSameFl;
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
    private String detailInfoAsFl;
    private String detailInfoAs;
    private String detailInfoAsDirectInput;
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


    private String allCateCd;
    private String detailInfoAS;
    private String magnifyImageData;
    private String detailImageData;
    private String listImageData;
    private String mainImageData;
    private String purchaseNm;
    private String purchaseCategory;
    private String purchasePhone;
    private String purchaseAddress;
    private String purchaseAddressSub;
    private String purchaseMemo;
    private String seoTagTitle;
    private String seoTagAuthor;
    private String seoTagDescription;
    private String seoTagKeyword;

    // 다른 객체들
    private List<GoodsMustInfoData> goodsMustInfoData = new ArrayList<>();
    private List<OptionData> optionData = new ArrayList<>();
    private List<AddGoodsData> addGoodsData= new ArrayList<>();
    private List<TextOptionData> textOptionData = new ArrayList<>();

    @Getter
    @Setter
    public static class GoodsMustInfoData {
        private List<StepData> stepDataListList;
    }
    @Getter
    @Setter
    private static class StepData{
        private String infoTitle;
        private String infoValue;
    }

    @Getter
    @Setter
    public static class OptionData {
        private Long sno;
        private Long optionNo;
        private Long goodsNo;
        private String optionImage;
        private String optionValue1;
        private String optionValue2;
        private String optionValue3;
        private String optionValue4;
        private String optionValue5;
        private Float optionPrice;
        private String optionViewFl;
        private String optionSellFl;
        private String optionCode;
        private Long stockCnt;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date regDt;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date modDt;
    }

    @Getter
    @Setter
    public static class TextOptionData {
        private Long sno;
        private Long goodsNo;
        private String optionName;
        private String mustFl;
        private Float addPrice;
        private Long inputLimit;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date regDt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date modDt;
    }

    @Getter
    @Setter
    public static class AddGoodsData {
        private String title;
        private String mustFl;
        private List<Long> goodsNoData;
    }
}
