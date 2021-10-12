package io.spring.main.model.order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.spring.main.model.goods.entity.CommonProps;
import io.spring.main.util.StringFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
		// loginId = custEmail.split("@")[0];

		if (!ifOrderMaster.getMemNo().equals("0")) {
			if (!ifOrderMaster.getOrderEmail().equals("") && ifOrderMaster.getOrderEmail() != null) {
				loginId = ifOrderMaster.getOrderEmail(); // 아이디 그냥 이메일 사용
			} else {
				loginId = ifOrderMaster.getMemNo() + "@member";
			}

		} else {
			loginId = custId + "@guest";
			;
		}

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
