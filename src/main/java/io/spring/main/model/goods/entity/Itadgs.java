package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="itadgs")
@NoArgsConstructor
public class Itadgs extends CommonProps {
    public Itadgs(IfGoodsAddGoods ifGoodsAddGoods){
        super(ifGoodsAddGoods.getRegDt(), ifGoodsAddGoods.getModDt());
        this.addGoodsId = ifGoodsAddGoods.getAddGoodsId();
        this.addGoodsNm = ifGoodsAddGoods.getGoodsNm();
        this.localSale = ifGoodsAddGoods.getGoodsPrice();
        this.shortYn = ifGoodsAddGoods.getSoldOutFl();
        this.optionNm = ifGoodsAddGoods.getOptionNm();
        this.addGoodsState = ifGoodsAddGoods.getViewFl();
        this.brandId = ifGoodsAddGoods.getBrandCd();
        this.makerNm = ifGoodsAddGoods.getMakerNm();
        this.stockCnt = ifGoodsAddGoods.getStockCnt();
    }

    @Id
    private String addGoodsId;
    private String addGoodsNm;
    private String taxGb = StringFactory.getGbTwo(); // 02 하드코딩
    private String addGoodsModel;
    private String optionNm;
    private Float localPrice = 0f;
    private Float localSale;
    private Float deliPrice = 0f;
    private String imageUrl;
    private String addGoodsState;
    private String shortYn;
    private String brandId;
    private String makerNm;
    private Long stockCnt;
}
