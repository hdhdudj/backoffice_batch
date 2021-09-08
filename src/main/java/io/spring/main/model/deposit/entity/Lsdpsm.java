package io.spring.main.model.deposit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import io.spring.main.model.deposit.request.DepositInsertRequestData;
import io.spring.main.model.vendor.entity.Cmvdmr;
import io.spring.main.util.Utilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.flywaydb.core.internal.util.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="lsdpsm")
public class Lsdpsm extends CommonProps {
    public Lsdpsm(DepositInsertRequestData depositInsertRequestData){
        this.depositNo = depositInsertRequestData.getDepositNo();
        this.depositDt = depositInsertRequestData.getDepositDt();
        this.storeCd = depositInsertRequestData.getStoreCd();
        this.siteGb = StringFactory.getGbOne(); // 01 하드코딩
        this.depositGb = StringFactory.getGbOne(); // 01 하드코딩
        this.vendorId = StringUtils.leftPad("1",6,'0');
        this.finishYymm = Utilities.getStringToDate(StringFactory.getDoomDay());
        this.depositType = StringFactory.getGbOne(); // 01 하드코딩
        this.ownerId = depositInsertRequestData.getDepositVendorId();
    }

    @Id
    private String depositNo;
    private Date depositDt;
    private String depositGb;
    private String siteGb;
    private String vendorId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date finishYymm;
    private String depositType;
    private String storeCd;
    private String ownerId;

    // 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depositVendorId", referencedColumnName="id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private Cmvdmr cmvdmr;
}
