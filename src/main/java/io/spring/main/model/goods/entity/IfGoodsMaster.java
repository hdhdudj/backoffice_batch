package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.idclass.IfGoodsMasterId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class IfGoodsMaster {
    @Id
    private String channelGb;
    @Id
    private String assortId;
    private String goodsNo;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private Date modDt;
    private Long regId;
    private Long updId;
    private String uploadStatus;
}
