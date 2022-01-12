package io.spring.main.model.goods.idclass;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItmmotId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String assortId;
    private String optionTextId;
}
