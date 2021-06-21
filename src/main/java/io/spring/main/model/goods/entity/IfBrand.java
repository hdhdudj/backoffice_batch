package io.spring.main.model.goods.entity;

import io.spring.main.util.StringFactory;
import io.spring.main.model.goods.idclass.IfBrandId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "if_brand")
@IdClass(value = IfBrandId.class)
public class IfBrand extends CommonProps{
    @Id
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩
    @Id
    private String channelBrandId;
    private String channelBrandNm;
    private String brandId;
    private String brandNm;
}
