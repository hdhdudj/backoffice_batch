package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "itasrn")
public class Itasrn extends CommonProps{
    private final static Logger logger = LoggerFactory.getLogger(Itasrn.class);
    public Itasrn(Itasrn itasrn){
        this.historyGb = itasrn.getHistoryGb();
        this.vendorId = itasrn.getVendorId();
        this.assortId = itasrn.getAssortId();
        this.localSale = itasrn.getLocalSale();
        this.shortageYn = itasrn.getShortageYn();
        try
        {
            this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        }
        catch (Exception e){
            logger.debug(e.getMessage());
        }
    }
    public Itasrn(IfGoodsMaster ifGoodsMaster){
        super(ifGoodsMaster.getRegDt(), ifGoodsMaster.getUpdDt());
        this.shortageYn = ifGoodsMaster.getGoodsSellFl();
        this.localSale = ifGoodsMaster.getGoodsPrice();
        try
        {
            this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        }
        catch (Exception e){
            logger.debug(e.getMessage());
        }
    }


//    public Itasrn(GoodsInsertRequestData goodsInsertRequestData){
//        this.historyGb = "01"; // default 값
//        this.vendorId = "000001";
//
//        this.assortId = goodsInsertRequestData.getAssortId();
//        this.localSale = goodsInsertRequestData.getLocalSale();
//        try
//        {
//            this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
//        }
//        catch (Exception e){
//            logger.debug(e.getMessage());
//        }
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String historyGb = StringFactory.getGbOne(); // 하드코딩
    private String vendorId = StringFactory.getGbOne(); // 하드코딩
    private String assortId;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effStaDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effEndDt;

    @Column(nullable = true)
    private Float localSale;
    private String shortageYn;
//    private String addDeliGb;
//    private String bonusReserve;
//    private String callDisLimit;
//    private String cardFee;
//    private String couponYn;
//    private String delayRewardYn;
//    private String deliCharge;
//    private String deliInterval;
//    private String deliMth;
//    private String deliPrice;
//    private String disGb;
//    private String disRate;
//    private String dispCategoryId;
//    private String divideMth;
//    private String drtDeliMargin;
//    private String excluChargeYn;
//    private String freeGiftYn;
//    private String handlingCharge;
//    private String handlingChargeYn;
//    private String hsCode;
//    private String leadTime;
//    private String localDeliFee;
//    private String localPrice;
//    private String localTaxRt;
//    private String margin;
//    private String marginCd;
//    private String payMthCd;
//    private String payType;
//    private String plFromDt;
//    private String plGbn;
//    private String plToDt;
//    private String preorderYn;
//    private String reserveGive;
//    private String salePrice;
//    private String standardPrice;
//    private String taxDeliYn;
//    private String taxGb;
//    private String templateId;
//    private String vendorTrGb;
//    private String weight;
}
