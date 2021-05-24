package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="es_goodsOption")
@Getter
@Setter
public class EsGoodsOption {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long sno;
    private Long goodsNo = 0l;
    private Long optionNo = 1l;
    private String optionValue1;
    private String optionValue2;
    private String optionValue3;
    private String optionValue4;
    private String optionValue5;
    private Float optionPrice = 0f;
    private Float optionCostPrice;
    private String optionViewFl;
    private String optionSellFl;
    private String optionSellCode ="";
    private String optionDeliveryFl = StringFactory.getStrNormal();
    private String optionDeliveryCode = "";
    private String optionCode;
    private Long stockCnt = 0l;
    private String sellStopFl = StringFactory.getStrN();
    private Float sellStopStock;
    private String confirmRequestFl = StringFactory.getStrN();
    private Float confirmRequestStock;
    private String optionMemo;
    private String deliverySmsSent = StringFactory.getStrN();
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date regDt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date modDt;
    private Float sPrice;
}
