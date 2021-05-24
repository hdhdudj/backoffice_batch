package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="if_goods_text_option")
public class IfGoodsTextOption {
    @Id
    private String assortId;
    private String optionTextId;
    private String goodsNo;
    private String optionName;
    private String mustFl;
    private Float addPrice;
    private Long inputLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private Date modDt;
    private Long regId;
    private Long updId;
}
