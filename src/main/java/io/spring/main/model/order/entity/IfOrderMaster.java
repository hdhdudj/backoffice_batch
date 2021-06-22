package io.spring.main.model.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.spring.main.model.goods.idclass.IfBrandId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "if_order_master")
public class IfOrderMaster {
    @Id
    private String ifNo;
    private String channelGb;
    private String channelOrderNo;
    private String ifStatus;
    private String channelOrderStatus;
    private String memNo;
    private String orderName;
    private String orderTel;
    private String orderEmail;
    private String orderZipcode;
    private String orderAddr1;
    private String orderAddr2;
    private String receiverName;
    private String receiverTel;
    private String receiverZipcode;
    private String receiverAddr1;
    private String receiverAddr2;
    private String channelInfo;
    private String customerId;
    private String payGb;
    private Float payAmt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date payDt;
    private String orderId;
    private String orderMemo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date orderDate;
}
