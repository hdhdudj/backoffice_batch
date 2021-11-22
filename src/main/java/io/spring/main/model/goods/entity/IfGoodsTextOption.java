package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.idclass.IfGoodsTextOptionId;
import lombok.EqualsAndHashCode;
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
@Table(name="if_goods_text_option")
@IdClass(IfGoodsTextOptionId.class)
@EqualsAndHashCode(exclude = {"uploadStatus, regDt, modDt, regId, updId"})
public class IfGoodsTextOption {
    @Id
    private String channelGb;
    @Id
    private String goodsNo;
    private String assortId;
    private String optionTextId;
    private String optionName;
    private String mustFl;
    private Float addPrice;
    private Long inputLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date regDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date modDt;
    private Long regId;
    private Long updId;
    private String uploadStatus;
}
