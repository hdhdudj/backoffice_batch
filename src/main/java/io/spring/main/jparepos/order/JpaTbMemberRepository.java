package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.TbMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTbMemberRepository extends JpaRepository<TbMember, Long> {
    TbMember findByLoginId(String loginId);

    TbMember findByCustId(long memNo);
}
