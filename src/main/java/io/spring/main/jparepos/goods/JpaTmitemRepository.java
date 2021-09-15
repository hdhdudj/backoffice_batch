package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.goods.entity.Tmitem;
import io.spring.main.model.goods.idclass.TmitemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTmitemRepository extends JpaRepository<Tmitem, TmitemId> {
    Tmitem findByChannelGbAndChannelGoodsNo(String gbOne, String goodsNm);

    Tmitem findByChannelGbAndChannelGoodsNoAndChannelOptionsNo(String gbOne, String channelGoodsNo, String channelOptionsNo);
}
