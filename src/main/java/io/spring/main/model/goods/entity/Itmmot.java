package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.model.goods.idclass.ItmmotId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "itmmot")
@IdClass(ItmmotId.class)
@NoArgsConstructor
public class Itmmot extends CommonProps{
    public Itmmot(IfGoodsTextOption ifGoodsTextOption){
        super(ifGoodsTextOption.getRegDt(), ifGoodsTextOption.getModDt());
        this.assortId = ifGoodsTextOption.getAssortId();
        this.optionNm = ifGoodsTextOption.getOptionName();
        this.addPrice = ifGoodsTextOption.getAddPrice();
        this.mustFl = ifGoodsTextOption.getMustFl();
        this.inputLimit = ifGoodsTextOption.getInputLimit();
    }
    @Id
    private String assortId;
    @Id
    private String optionTextId;
    private String optionNm;
    private Float addPrice;
    private String delYn = StringFactory.getGbTwo(); // n 하드코딩
    private String mustFl;
    private Long inputLimit;
//    private Long regId;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date regDt;
//    private Long updId;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
//    private Date updDt;
}
