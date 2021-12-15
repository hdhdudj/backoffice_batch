package io.spring.main.jparepos.goods;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.goods.idclass.ItitmmId;

public interface JpaItitmmRepository extends JpaRepository<Ititmm, ItitmmId> {
    @Query("select max(i.itemId) as maxVal from Ititmm as i where i.assortId = ?1")
    String findMaxItemIdByAssortId(String assortId);
    List<Ititmm> findByAssortId(String assortId);

	List<Ititmm> findByAssortIdAndDelYn(String assortId, String delYn);

    Ititmm findByAssortIdAndItemId(String assortId, String itemId);

    Ititmm findByItemNm(String channelGoodsNm);

	Ititmm findByAssortIdAndVariationSeq1AndVariationSeq2(String assortId, String variationSeq1, String variationSeq2);

    Ititmm findByAssortIdAndVariationSeq1AndVariationSeq2AndVariationSeq3(String assortId, String variationSeq1, String variationSeq2, String variationSeq3);

	List<Ititmm> findByAssortIdAndVariationSeq1AndVariationSeq2AndVariationSeq3AndDelYn(String assortId,
			String variationSeq1, String variationSeq2, String variationSeq3, String delYn);
}
