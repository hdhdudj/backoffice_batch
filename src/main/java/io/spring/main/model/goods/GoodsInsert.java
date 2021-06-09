package io.spring.main.model.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GoodsInsert {
    private GoodsData goodsData;
    private OptionData optionData;
    private TextOptionData textOptionData;
    private AddGoodsData addGoodsData;

    @Getter
    @Setter
    public static class GoodsData{
        private String goodsNmFl;
        private String goodsNm;
        private String goodsNmMain;
        private String goodsNmList;
        private String goodsNmDetail;
        private String goodsDisplayFl;
        private String goodsDisplayMobileFl;
        private String goodsSellFl;
        private String goodsSellMobileFl;
        private String scmNo;
        private String goodsCd;
        private String cateCd;
        private String goodsSearchWord;
        private String goodsOpenDt;
        private String goodsState;
        private String goodsColor;
        private Long brandCd;
        private String makerNm;
        private String originNm;
        private String goodsModelNo;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date makeYmd;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date launchYmd;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date effectiveStartYmd;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date effectiveEndYmd;
        private String goodsPermission;
        private String goodsPermissionGroup;
        private String onlyAdultFl;
        private String taxFreeFl;
        private Float taxPercent;
        private Long goodsWeight;
        private Long totalStock;
        private String stockFl;
        private String soldOutFl;
        private Long salesUnit;
        private Long minOrderCnt;
        private Long maxOrderCnt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date salesStartYmd;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date salesEndYmd;
        private String imageStorage;
        private String restockFl;
        private String mileageFl;
        private Float mileageGoods;
        private String mileageGoodsUnit;
        private String goodsDiscountFl;
        private Float goodsDiscount;
        private String goodsDiscountUnit;
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
        private String addGoodsFl;
        private String shortDescription;
        private String goodsDescription;
        private String goodsDescriptionMobile;
        private Long deliverySno;
        private String relationFl;
        private String relationSameFl;
        private Long relationCnt;
        private String relationGoodsNo;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private Date relationGoodsDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date goodsIconStartYmd;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date goodsIconEndYmd;
        private String goodsIconCdPeriod;
        private String goodsIconCd;
        private String imgDetailViewFl;
        private String externalVideoFl;
        private String externalVideoUrl;
        private String externalVideoWidth;
        private String externalVideoHeight;
        private String detailInfoDelivery;
        private String detailInfoAS;
        private String detailInfoRefund;
        private String detailInfoExchange;
        private String memo;
        private String allCateCd;
        private List<String> magnifyImageData;
        private List<String> detailImageData;
        private List<String> listImageData;
        private List<String> mainImageData;
        private String exTitle;
        // xml key : ex{n}
        private List<String> exList;
        private String purchaseGoodsNm;
        private String naverFl;
        private String naverImportFlag;
        private String naverProductFlag;
        private String naverAgeGroup;
        private String naverGender;
        private String naverTag;
        private String naverAttribute;
        private Long naverCategory;
        private String naverProductId;
        private String goodsNmPartner;
        private String cultureBenefitFl;
        private String eventDescription;
        private String seoTagFl;
        private String seoTagTitle;
        private String seoTagAuthor;
        private String seoTagDescription;
        private String seoTagKeyword;
        private String daumFl;
    }
    @Getter
    @Setter
    public static class OptionData{
        private Long optionNo;
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
    }
    @Getter
    @Setter
    public static class TextOptionData{
        private String optionName;
        private String mustFl;
        private Float addPrice;
        private Long inputLimit;
    }
    @Getter
    @Setter
    public static class AddGoodsData{
        private String title;
        private String mustFl;
        private List<String> goodsNoData;
    }
    @Getter
    @Setter
    public static class GoodsMustInfoData{
        private List<StepData> stepData;
    }
    @Getter
    @Setter
    private static class StepData{
        private String infoTitle;
        private String infoValue;
    }
}
