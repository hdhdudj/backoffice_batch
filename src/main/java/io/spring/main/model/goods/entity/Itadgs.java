package io.spring.main.model.goods.entity;

import io.spring.main.util.StringFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name="itadgs")
@NoArgsConstructor
public class Itadgs extends CommonProps {
    public Itadgs(IfGoodsMaster ifGoodsMaster, IfGoodsAddGoods ifGoodsAddGoods){
        super(ifGoodsAddGoods.getRegDt(), ifGoodsAddGoods.getUpdDt());
        this.addGoodsId = ifGoodsAddGoods.getAddGoodsId();
        this.addGoodsNm = ifGoodsAddGoods.getGoodsNm();
        this.localSale = ifGoodsAddGoods.getGoodsPrice();
        this.shortYn = ifGoodsAddGoods.getSoldOutFl();
        this.optionNm = ifGoodsAddGoods.getOptionNm();
        this.addGoodsState = ifGoodsAddGoods.getViewFl();
        this.brandId = ifGoodsAddGoods.getBrandCd();
        this.makerNm = ifGoodsAddGoods.getMakerNm();
        this.stockCnt = ifGoodsAddGoods.getStockCnt();
        this.imageUrl = ifGoodsMaster.getMainImageData();
    }

    @Id
    private String addGoodsId;
    private String addGoodsNm;
    private String taxGb = StringFactory.getGbOne(); // 01 하드코딩 (01 : 과세, 02 : 면세)
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
