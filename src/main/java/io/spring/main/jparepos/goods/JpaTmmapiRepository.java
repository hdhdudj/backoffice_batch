package io.spring.main.jparepos.goods;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.spring.main.model.goods.entity.Tmmapi;
import io.spring.main.model.goods.idclass.TmmapiId;

public interface JpaTmmapiRepository extends JpaRepository<Tmmapi, TmmapiId> {

    List<Tmmapi> findByJoinStatus(String jobStatus);

	Tmmapi findByChannelGbAndChannelGoodsNo(String gbOne, String toString);

}
