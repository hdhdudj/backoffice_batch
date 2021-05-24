package io.spring.main.model.goods.idclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItmmotId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String assortId;
    private String optionTextId;
}
