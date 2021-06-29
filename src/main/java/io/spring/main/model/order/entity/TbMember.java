package io.spring.main.model.order.entity;

import io.spring.main.model.goods.entity.CommonProps;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="tb_member")
public class TbMember extends CommonProps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custId;
    private String custNm;
    private String custEmail;
    private String loginId;
    private String loginPw;
    private String channelGb;
    private String custGb;
    private String custGrade;
    private String custStatus;
    private String custTel;
    private String custHp;
    private String custZipcode;
    private String custAddr1;
    private String custAddr2;
}
