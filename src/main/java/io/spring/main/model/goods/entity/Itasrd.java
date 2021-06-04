package io.spring.main.model.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.model.goods.idclass.ItasrdId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "itasrd")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ItasrdId.class)
public class Itasrd extends CommonProps {
    public Itasrd(IfGoodsMaster ifGoodsMaster){
        super(ifGoodsMaster.getRegDt(), ifGoodsMaster.getModDt());
    }

//    public Itasrd(GoodsInsertRequestData goodsInsertRequestData){
//        this.assortId = goodsInsertRequestData.getAssortId();
////        this.ordDetCd = "01";
////        this.memo = goodsRequestData.get
//        this.delYn = "02";
////        this.textHtmlGb = goodsRequestData.getTextHtmlGb();
////        this.regId = "123"; // 추후 추가
////        this.updId = "123"; // 추후 추가
//    }

    @Id
    private String assortId;
    @Id
    private String seq;

    private String ordDetCd;
    private String memo;
    private String delYn = StringFactory.getGbTwo(); // 01 : 삭제, 02 : 사용  하드코딩
    private String textHtmlGb;
    private String memo2;

    // 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assortId", referencedColumnName = "assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private Itasrt itasrt;
}
