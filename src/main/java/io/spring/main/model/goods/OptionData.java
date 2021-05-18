package io.spring.main.model.goods;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OptionData {
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
