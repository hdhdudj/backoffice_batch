package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.util.StringFactory;
import io.spring.main.model.goods.idclass.IfGoodsMasterId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "if_goods_master")
@Getter
@Setter
@IdClass(IfGoodsMasterId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IfGoodsMaster extends CommonProps {
    public IfGoodsMaster(Itasrt itasrt, Itasrn itasrn, Itasrd itasrd){
        // itasrt
        this.goodsNm = itasrt.getAssortNm();
        this.goodsDisplayFl = itasrt.getAssortState();
        this.sizeType = itasrt.getAssortGb();
        this.goodsSellFl = itasrt.getShortageYn();
        this.cateCd = itasrt.getDispCategoryId();
        this.goodsColor = itasrt.getAssortColor();
        this.commission = itasrt.getMargin();
        this.brandCd = itasrt.getBrandId();
        this.makerNm = itasrt.getManufactureNm();
        this.goodsModelNo = itasrt.getAssortModel();
        this.taxFreeFl = itasrt.getTaxGb();
        this.salesStartYmd = itasrt.getSellStaDt();
        this.salesEndYmd = itasrt.getSellEndDt();
        this.goodsPrice = itasrt.getLocalSale();
        this.fixedPrice = itasrt.getLocalPrice();
        this.costPrice = itasrt.getDeliPrice();
        this.optionName = itasrt.getOptionGbName();
        this.optionFl = itasrt.getOptionUseYn();
        this.mdRrp = itasrt.getMdRrp();
        this.mdTax = itasrt.getMdTax();
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
        // itasrn
        this.goodsSellFl = itasrn.getShortageYn();
        this.goodsPrice = itasrn.getLocalSale();
        // itasrd
    }
    @Id
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩
    @Id
    private String goodsNo;
    private String assortId;
    private String goodsNm;
    private String goodsNmDetail;
    private String goodsDisplayFl;
    private String goodsSellFl;
    private String cateCd;
    private String goodsColor;
    private Float commission;
    private String brandCd;
    private String makerNm;
    private String originNm;
    private String goodsModelNo;
    private String onlyAdultFl;
    private String taxFreeFl;
    private String stockFl;
    private String soldOutFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesStartYmd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date salesEndYmd;
    private Float goodsPrice;
    private Float fixedPrice;
    private Float costPrice;
    private String optionMemo;
    private String shortDescription;
    private String goodsDescription;
    private String optionName;
    private String optionFl;
    private Float mdRrp;
    private String mdTax;
    private String mdYear;
    private Float width;
    private Float height;
    private Float depth;
    private Float goodsWeight;
    private Float mdMargin;
    private Float mdVatrate;
    private Float mdOfflinePrice;
    private Float mdOnlinePrice;
    private Float mdGoodsVatrate;
    private String buyWhere;
    private Float buySupplyDiscount;
    private Float buyRrpIncrement;
    private Float buyExchangeRate;
    private String sizeType;
    private String uploadStatus = StringFactory.getGbOne(); // 01 하드코딩

    // 이미지 경로
    private String mainImageUrl;
    private String listImageUrl;
    private String detailImageUrl;
    private String magnifyImageUrl;
}
