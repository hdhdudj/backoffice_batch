package io.spring.main.model.goods.idclass;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TmitemId implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    private String channelGb;
    private String assortId;
    private String itemId;
    private Date effStaDt;
    private Date effEndDt;
}
