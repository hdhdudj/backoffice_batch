package io.spring.main.model.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.model.goods.entity.Itasrd;
import io.spring.main.model.goods.entity.Itasrt;
import io.spring.main.model.goods.entity.Tmitem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class GoodsInsertData {
    public GoodsInsertData(GoodsData goodsData, OptionData[] optionDataList){
        this.goodsData = new GoodsData[1];
        this.goodsData[0] = goodsData;
        this.goodsData[0].setOptionData(optionDataList);
    }
    @XmlElement(name = "goods_data")
    private GoodsData[] goodsData;
//    private OptionData optionData;
//    private TextOptionData textOptionData;
//    private AddGoodsData addGoodsData;

    @Getter
    @Setter
    @NoArgsConstructor
    @XmlRootElement(name = "goods_data")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GoodsData{

        public GoodsData(Itasrt itasrt, Itasrd itasrd1, Itasrd itasrd2){
            /**
             *  Itasrt 로부터 값을 얻는 부분
             */
            this.goodsCd = itasrt.getAssortId();
            this.goodsNm = itasrt.getAssortNm();
            this.goodsDisplayFl = itasrt.getAssortState().equals(StringFactory.getGbOne())? StringFactory.getStrY():StringFactory.getStrN();
            this.sizeType = itasrt.getAssortGb();
            this.goodsSellFl = itasrt.getShortageYn().equals(StringFactory.getGbOne())? StringFactory.getStrY():StringFactory.getStrN();
            this.cateCd = itasrt.getDispCategoryId();
            this.goodsColor = itasrt.getAssortColor();
            this.commission = itasrt.getMargin();
//            this.brandCd = itasrt.getBrandId(); // 변환 필요
            this.makerNm = itasrt.getManufactureNm();
            this.goodsModelNo = itasrt.getAssortModel();
            this.taxFreeFl = itasrt.getTaxGb().equals(StringFactory.getGbOne())? StringFactory.getStrY():StringFactory.getStrN();
            this.salesStartYmd = itasrt.getSellStaDt();
            this.salesEndYmd = itasrt.getSellEndDt();
            this.goodsPrice = itasrt.getLocalSale();
            this.fixedPrice = itasrt.getLocalPrice();
            this.costPrice = itasrt.getDeliPrice();
            this.optionName = itasrt.getOptionGbName();
            this.optionFl = itasrt.getOptionUseYn().equals(StringFactory.getGbOne())? StringFactory.getStrY():StringFactory.getStrN();
            this.mdRrp = itasrt.getMdRrp();
            this.mdRrpTax = itasrt.getMdTax();
            this.mdYear = itasrt.getMdYear();
            this.mdMargin = itasrt.getMdMargin();
            this.mdVatrate = itasrt.getMdVatrate();
            this.mdOfflinePrice = itasrt.getMdOfflinePrice();
            this.mdOnlinePrice = itasrt.getMdOnlinePrice();
            this.mdGoodsVatrate = itasrt.getMdGoodsVatrate();
            this.buyWhere = itasrt.getBuyWhere();
            this.buySupplyDiscount = itasrt.getBuySupplyDiscount();
            this.buyRrpIncrement = itasrt.getBuyRrpIncrement();
            this.buyExchangeRate = itasrt.getBuyExchangeRate();
            this.width = itasrt.getAsWidth();
            this.height = itasrt.getAsHeight();
            this.depth = itasrt.getAsLength();
            this.goodsWeight = itasrt.getWeight();

            /**
             * Itasrd로부터 값을 얻는 부분
             */
            this.shortDescription = itasrd1.getMemo();
            this.goodsDescription = itasrd2.getMemo();
        }
//        @Expose
//        private String assortId;
        private Long deliverySno = 310l;
        private String goodsNmFl;
        private String goodsNm;
        private String goodsNmMain;
        private String goodsNmList;
        private String goodsNmDetail;
        private String goodsDisplayFl;
        private String goodsDisplayMobileFl;
        private String goodsSellFl;
        private String goodsSellMobileFl;
        private String scmNo = StringFactory.getStrOne();
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
        private Float goodsWeight;
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
        private String optionDisplayFl = StringFactory.getStrD(); // d 하드코딩
        private String optionName;
        private String optionTextFl = StringFactory.getStrY(); // y 하드코딩
        private String addGoodsFl = StringFactory.getStrD(); // d 하드코딩
        private String shortDescription; // 짧은 설명
        private String goodsDescription; // 상세 설명
        private String goodsDescriptionMobile;
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
        // 원래 고도몰 api에는 없던거
        private Float mdRrp;
        private String mdTax;
        private String mdRrpTax;
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
        private Float commission;
        private String sizeType;
        private Float width;
        private Float height;
        private Float depth;

        @XmlElement(name = "optionData")
        private OptionData[] optionData;
        @XmlElement(name = "textOptionData")
        private List<TextOptionData> textOptionData;
        @XmlElement(name = "addGoodsData")
        private List<AddGoodsData> addGoodsData;
    }
    @Getter
    @Setter
    @XmlRootElement(name = "optionData")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OptionData{
        public OptionData(Tmitem tmitem){
            this.optionCode = tmitem.getItemId();
            this.optionNo = Long.parseLong(tmitem.getItemId());
            this.optionPrice = tmitem.getOptionPrice();
            this.optionSellFl = tmitem.getShortYn().equals(StringFactory.getGbOne())? StringFactory.getStrY():StringFactory.getStrN();
            this.optionValue1 = tmitem.getItvari1() == null? null: tmitem.getItvari1().getOptionNm();
            this.optionValue2 = tmitem.getItvari2() == null? null:tmitem.getItvari2().getOptionNm();
        }

        @XmlAttribute(name="idx")
        int idx;
        private Long optionNo;
        private String optionValue1;
        private String optionValue2;
        private String optionValue3;
        private String optionValue4;
        private String optionValue5;
        private Float optionPrice;
        private String optionViewFl = StringFactory.getStrY(); // y 하드코딩
        private String optionSellFl;
        private String optionCode;
        private Long stockCnt = 999l; // 999 하드코딩
    }

    @Getter
    @Setter
    @XmlRootElement(name = "textOptionData")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TextOptionData{
        private String optionName;
        private String mustFl;
        private Float addPrice;
        private Long inputLimit;
    }
    @Getter
    @Setter
    @XmlRootElement(name = "addGoodsData")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class AddGoodsData{
        private String title;
        private String mustFl;
        private List<String> goodsNoData;
    }
    @Getter
    @Setter
    @XmlRootElement(name = "goodsMustInfoData")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GoodsMustInfoData{
        private List<StepData> stepData;
    }
    @Getter
    @Setter
    @XmlRootElement(name = "goods_data")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class StepData{
        private String infoTitle;
        private String infoValue;
    }
}
