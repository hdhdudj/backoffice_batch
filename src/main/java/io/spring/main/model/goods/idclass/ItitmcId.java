package io.spring.main.model.goods.idclass;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode
public class ItitmcId implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    private String storageId;
    private String assortId;
    private String itemId;
    private String itemGrade;
    private Date effEndDt;
    private Date effStaDt;
}
