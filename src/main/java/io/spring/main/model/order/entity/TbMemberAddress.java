package io.spring.main.model.order.entity;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 * 수취자 (receiver) 정보 저장 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "tb_member_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TbMemberAddress extends CommonProps {
    public TbMemberAddress(IfOrderMaster ifOrderMaster, TbMember tbMember){
        this.custId = tbMember.getCustId();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliId;
    private Long custId;
    private String deliNm;
    private String deliGb = StringFactory.getThreeStartCd();// 001 하드코딩
    private String deliTel;
    private String deliHp;
    private String deliZipcode;
    private String deliAddr1;
    private String deliAddr2;

    // 21-11-24 추가
    private String receiverZonecode;
}
