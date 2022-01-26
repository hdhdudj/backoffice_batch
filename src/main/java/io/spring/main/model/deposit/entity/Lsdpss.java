package io.spring.main.model.deposit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import io.spring.main.model.deposit.request.DepositInsertRequestData;
import io.spring.main.util.Utilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="lsdpss")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lsdpss extends CommonProps {
    public Lsdpss(DepositInsertRequestData depositInsertRequestData){
        this.depositNo = depositInsertRequestData.getDepositNo();
        this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay());
        this.depositStatus = depositInsertRequestData.getDepositStatus();
        this.effStaDt = new Date();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String depositNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effEndDt;
    private String depositStatus;
    private Date effStaDt;
}
