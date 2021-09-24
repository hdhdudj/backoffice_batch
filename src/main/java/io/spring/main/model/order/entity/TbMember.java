package io.spring.main.model.order.entity;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
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
    public TbMember(IfOrderMaster ifOrderMaster){
        custId = Long.parseLong(ifOrderMaster.getMemNo());
        custNm = ifOrderMaster.getOrderName();
        custEmail = ifOrderMaster.getOrderEmail();
        loginId = custEmail.split("@")[0];
        loginPw = "";
    }
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custId;
    private String custNm;
    private String custEmail;
    private String loginId;
    private String loginPw;
    private String channelGb = StringFactory.getGbOne(); // 01 하드코딩
    private String custGb;
    private String custGrade;
    private String custStatus;
    private String custTel;
    private String custHp;
    private String custZipcode;
    private String custAddr1;
    private String custAddr2;
}
