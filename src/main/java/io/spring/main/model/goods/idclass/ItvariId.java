package io.spring.main.model.goods.idclass;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItvariId implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    public ItvariId(String assortId, String seq){
        this.assortId = assortId;
        this.seq = seq;
    }
    private String assortId;
    private String seq;
}

