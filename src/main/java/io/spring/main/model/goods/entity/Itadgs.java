package io.spring.main.model.goods.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name="itadgs")
public class Itadgs {
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
    private Long regId;
    private String regDt;
    private Long updId;
    private String updDt;
}
