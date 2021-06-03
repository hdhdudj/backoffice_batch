package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.infrastructure.util.Utilities;
import io.spring.main.model.goods.idclass.ItlkagId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Slf4j
@Entity
@Getter
@Setter
@Table(name = "itlkag")
@IdClass(ItlkagId.class)
@NoArgsConstructor
public class Itlkag {
    public Itlkag(IfGoodsAddGoods ifGoodsAddGoods){
        this.assortId = ifGoodsAddGoods.getAssortId();
        try
        {
            this.effEndDt = Utilities.getStringToDate(StringFactory.getDoomDay()); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        }
        catch(Exception e){
            log.debug(e.getMessage());
        }
        try
        {
            this.effStaDt = new Date(); // 마지막 날짜(없을 경우 9999-12-31 23:59:59?)
        }
        catch(Exception e){
            log.debug(e.getMessage());
        }
        this.regId = ifGoodsAddGoods.getRegId();
        this.updId = ifGoodsAddGoods.getUpdId();
        this.regDt = ifGoodsAddGoods.getRegDt();
        this.updDt = ifGoodsAddGoods.getModDt();
    }
    @Id
    private String assortId;
    @Id
    private String addGoodsId;
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effStaDt;
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date effEndDt;
    private Long regId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDt;
    private Long updId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private Date updDt;
}