package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.idclass.TmmapiId;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tmmapi")
@IdClass(TmmapiId.class)
public class Tmmapi extends CommonProps{
    @Id
    private String channelGb;
    @Id
    private String assortId;
    private String assortNm;
    private String channelGoodsNo;
    private Float standardPrice;
    private Float salePrice;
    private String deliGb;
    private Float deliPrice;
    private Float deliMaxPrice;
    private String shortageYn;
    private String upJoinCategoryId;
    private String upAssortNm;
    private Float upStandardPrice;
    private Float upSalePrice;
    private String upDeliGb;
    private Float upDeliPrice;
    private Float upDeliMaxPrice;
    private String upShortageYn;
    private String upJoinMakerId;
    private String uploadType;
    private String uploadYn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date uploadDt;
    private String uploadRmk;
    private String joinStatus;
    private String errorMsg;
}
