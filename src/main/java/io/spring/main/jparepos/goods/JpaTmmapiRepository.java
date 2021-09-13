package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Tmmapi;
import io.spring.main.model.goods.idclass.TmmapiId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTmmapiRepository extends JpaRepository<Tmmapi, TmmapiId> {
    List<Tmmapi> findByJoinStatus(String jobStatus);

    Tmmapi findByChannelGbAndChannelGoodsNo(String gbOne, String toString);
}
