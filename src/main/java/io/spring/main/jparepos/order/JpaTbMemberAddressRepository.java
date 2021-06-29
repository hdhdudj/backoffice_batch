package io.spring.main.jparepos.order;

import io.spring.main.model.order.entity.TbMemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTbMemberAddressRepository extends JpaRepository<TbMemberAddress, Long> {
    TbMemberAddress findByCustId(Long custId);
}
