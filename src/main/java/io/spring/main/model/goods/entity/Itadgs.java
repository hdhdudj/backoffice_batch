package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
//        this.regDt = ifGoodsAddGoods.getRegDt();
//        this.updDt = ifGoodsAddGoods.getModDt();
//        this.regId = 1l;//ifGoodsAddGoods.getRegId();
//        this.updId = 1l;//ifGoodsAddGoods.getUpdId();
    }
    @Id
    private String addGoodsId;
    private String addGoodsNm;
    private String taxGb;
    private String addGoodsModel;
    private String optionNm;
    private Float localPrice;
    private Float localSale;
    private Float deliPrice;
    private String imageUrl;
    private String addGoodsState;
    private String shortYn;
//    private Long regId;
//    private Long updId;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date regDt;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date updDt;
}
