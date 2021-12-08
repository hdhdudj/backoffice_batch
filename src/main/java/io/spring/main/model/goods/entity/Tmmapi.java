package io.spring.main.model.goods.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.spring.main.model.goods.idclass.TmmapiId;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tmmapi")
@IdClass(TmmapiId.class)
public class Tmmapi extends CommonProps{

	public Tmmapi(IfGoodsMaster ifGoodsMaster) {
		super(new Date(), new Date());

		this.channelGb = ifGoodsMaster.getChannelGb();
		this.assortId = ifGoodsMaster.getAssortId();
		this.channelGoodsNo = ifGoodsMaster.getGoodsNo();
		this.uploadType = "01";
		this.uploadYn = "01";
		this.uploadDt = new Date();
		this.uploadRmk = "배치생성";
		this.joinStatus = "01";
		this.errorMsg = "성공";

	}

    @Id
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩
    @Id
    private String assortId;
    private String assortNm;
    private String channelGoodsNo;
    private Float standardPrice;
    private Float salePrice;
    private String deliGb;
    private Float deliPrice;
    private Float deliMaxPrice;
    private String shortageYn;
    private String upJoinCategoryId;
    private String upAssortNm;
    private Float upStandardPrice;
    private Float upSalePrice;
    private String upDeliGb;
    private Float upDeliPrice;
    private Float upDeliMaxPrice;
    private String upShortageYn;
    private String upJoinMakerId;
    private String uploadType;
    private String uploadYn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date uploadDt;
    private String uploadRmk;
    private String joinStatus;
    private String errorMsg;

    // tmitem 연관관계
	@JoinColumns({
        @JoinColumn(name = "assortId", referencedColumnName="assortId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none")),
			@JoinColumn(name = "channelGb", referencedColumnName = "channelGb", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    })
	@OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
	@NotFound(action = NotFoundAction.IGNORE)
    private List<Tmitem> tmitemList;
}
