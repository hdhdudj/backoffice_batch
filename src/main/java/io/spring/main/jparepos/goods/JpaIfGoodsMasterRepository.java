package io.spring.main.jparepos.goods;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.spring.main.model.goods.entity.IfGoodsMaster;

public interface JpaIfGoodsMasterRepository extends JpaRepository<IfGoodsMaster, String> {
    IfGoodsMaster findByGoodsNo(String goodsNo);

    List<IfGoodsMaster> findByUploadStatus(String gbOne);

    IfGoodsMaster findByChannelGbAndGoodsNo(String gbOne, String toString);

	IfGoodsMaster findByChannelGbAndGoodsNoAndModDt(String gbOne, String goodsNo, Date modDt);

}
