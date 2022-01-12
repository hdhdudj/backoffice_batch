package io.spring.main.model.order.idclass;

import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode
public class IfOrderDetailId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ifNo;
    private String ifNoSeq;
}
