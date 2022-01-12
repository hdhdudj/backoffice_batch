package io.spring.main.model.goods.idclass;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TmitemId implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    private String channelGb;
    private String assortId;
    private String itemId;
    private LocalDateTime effStaDt;
    private LocalDateTime effEndDt;
}
