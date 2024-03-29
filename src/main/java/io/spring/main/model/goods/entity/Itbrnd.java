package io.spring.main.model.goods.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="itbrnd")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Itbrnd extends CommonProps{
    @Id
    private String brandId;
    private String brandNm;
    private String brandEnm;

    @Override
    public String toString() {
        return "Itbrnd [brandId=" + brandId + ", brandNm=" + brandNm + ", brandEnm=" + brandEnm + ", regId=" + getRegId()
                + ", updId=" + getUpdId() + ", regDt=" + getRegDt() + ", updDt=" + getUpdDt() + "]";
    }
}
