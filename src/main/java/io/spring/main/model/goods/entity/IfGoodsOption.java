package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "if_goods_option")
@Getter
@Setter
public class IfGoodsOption {
    @Id
    private String assortId;
    private String itemId;
    private String goodsNm;
    private String goodsNo;
    private String optionValue1;
    private String optionValue2;
    private String optionValue3;
    private String optionValue4;
    private String optionValue5;
    private Float optionPrice;
    private String optionViewFl;
    private String optionCode;
    private String soldOutFl;
    private Long minOrderCnt;
    private Long maxOrderCnt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private Date modDt;
    private Long regId;
    private Long updId;
}
