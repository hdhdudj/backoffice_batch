package io.spring.main.model.goods.idclass;

import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode
public class IfGoodsTextOptionId implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    private String channelGb;
    private String goodsNo;
}
