package io.spring.main.model.goods.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.util.StringFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "if_add_goods")
public class IfAddGoods{ // extends CommonProps {
    @Id
    private String addGoodsNo;
    private String addGoodsId;
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

    private String regId = StringFactory.getBatchId();
    private String updId = StringFactory.getBatchId();
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date regDt;
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date updDt;
}
