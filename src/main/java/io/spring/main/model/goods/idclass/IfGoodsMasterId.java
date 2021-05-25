package io.spring.main.model.goods.idclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
public class IfGoodsMasterId implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    private String channelGb;
    private String assortId;
}
