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
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="lsdpds")
public class Lsdpds extends CommonProps implements Serializable {
    public Lsdpds(String depositNo, DepositInsertRequestData.Item item){
        this.depositNo = depositNo;
        this.depositSeq = item.getDepositSeq();
        this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay());
        this.effStaDt = new Date();
        this.depositStatus = item.getDepositStatus();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String depositNo;
    private String depositSeq;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effEndDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effStaDt;
    private String depositStatus;
}
