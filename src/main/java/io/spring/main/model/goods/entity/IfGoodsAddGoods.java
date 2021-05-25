package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.idclass.IfGoodsAddGoodsId;
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
@Getter
@Setter
@Table(name="if_goods_add_goods")
@IdClass(IfGoodsAddGoodsId.class)
public class IfGoodsAddGoods {
    @Id
    private String channelGb;
    @Id
    private String goodsNo;
    @Id
    private String goodsNoData;
    private String addGoodsId;
    private String addGoodsNo;
    private String scmNo;
    private String title;
    private String goodsNm;
    private String brandCd;
    private String optionNm;
    private String makerNm;
    private Float goodsPrice;
    private Long stockCnt;
    private String viewFl;
    private String soldOutFl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date regDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date modDt;
    private Long regId;
    private Long updId;
    private String uploadStatus;
}
