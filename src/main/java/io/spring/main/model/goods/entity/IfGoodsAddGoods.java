package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.util.StringFactory;
import io.spring.main.model.goods.idclass.IfGoodsAddGoodsId;
import lombok.Getter;
import lombok.Setter;

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
public class IfGoodsAddGoods extends CommonProps{
    @Id
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩
    @Id
    private String goodsNo; // 원본상품번호
    @Id 
    private String addGoodsNo; // 연관 상품번호
    private String addGoodsId; // itadgs의 id. insert시 필요
    private String assortId;
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
    private String uploadStatus = StringFactory.getGbOne(); // 01 하드코딩
}
