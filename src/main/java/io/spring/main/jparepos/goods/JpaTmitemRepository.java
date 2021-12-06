package io.spring.main.jparepos.goods;

import org.springframework.data.jpa.repository.JpaRepository;

import io.spring.main.model.goods.entity.Tmitem;
import io.spring.main.model.goods.idclass.TmitemId;

public interface JpaTmitemRepository extends JpaRepository<Tmitem, TmitemId> {
    Tmitem findByChannelGbAndChannelGoodsNo(String gbOne, String goodsNm);

    Tmitem findByChannelGbAndChannelGoodsNoAndChannelOptionsNo(String gbOne, String channelGoodsNo, String channelOptionsNo);

	Tmitem findByAssortIdAndItemIdAndChannelGbAndChannelGoodsNoAndChannelOptionsNo(String assortId, String itemId,
			String gbOne, String channelGoodsNo, String channelOptionsNo);
}
