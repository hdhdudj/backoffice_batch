package io.spring.main.model.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GoodsData {
    private Long goodsNo;
    private String goodsNmFl;
    private String goodsNm;
    private String goodsNmMain;
    private String goodsNmList;
    private String goodsNmDetail;
    private String goodsNmPartner;
    private String purchaseGoodsNm;
    private String goodsDisplayFl;
    private String goodsDisplayMobileFl;
    private String goodsSellFl;
    private String goodsSellMobileFl;
    private String scmNo;
    private String applyFl;
    private String applyType;
    private String applyMsg;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date applyDt;
    private Float commission;
    private String goodsCd;
    private String cateCd;
    private String allCateCd;
    private String goodsSearchWord;
    private String goodsOpenDt;
    private String goodsState;
    private String goodsColor;
    private Long brandCd;
    private String makerNm;
    private String originNm;
    private String goodsModelNo;
    private String makeYmd;
    private String launchYmd;
    private String effectiveStartYmd;
    private String effectiveEndYmd;
    private String goodsPermission;
    private String goodsPermissionGroup;
    private String onlyAdultFl;
    private String imageStorage;
    private String taxFreeFl;
    private Float taxPercent;
    private Float goodsWeight;
    private Long totalStock;
    private String stockFl;
    private String soldOutFl;
    private Long salesUnit;
    private Long minOrderCnt;
    private Long maxOrderCnt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesStartYmd;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesEndYmd;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date relationGoodsDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date goodsIconStartYmd;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date goodsIconEndYmd;
    private String goodsIconCdPeriod;
    private String goodsIconCd;
    private String imgDetailViewFl;
    private String externalVideoFl;
    private String externalVideoUrl;
    private Long externalVideoWidth;
    private Long externalVideoHeight;
    private String detailInfoDelivery;
    private String detailInfoAS;
    private String detailInfoRefund;
    private String detailInfoExchange;
    private String memo;
    private Long orderCnt;
    private Long hitCnt;
    private Long reviewCnt;
    private String excelFl;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date regDt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date modDt;
    private List<String> magnifyImageData;
    private List<String> detailImageData;
    private List<String> listImageData;
    private List<String> mainImageData;
    private String purchaseNm;
    private String purchaseCategory;
    private String purchasePhone;
    private String purchaseAddress;
    private String purchaseAddressSub;
    private String purchaseMemo;
    private String cultureBenefitFl;
    private String eventDescription;
    private String seoTagFl;
    private String seoTagTitle;
    private String seoTagAuthor;
    private String seoTagDescription;
    private String seoTagKeyword;
    private String daumFl;
}
