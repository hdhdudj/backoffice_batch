package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfGoodsMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaIfGoodsMasterRepository extends JpaRepository<IfGoodsMaster, String> {
    IfGoodsMaster findByGoodsNo(String goodsNo);

    List<IfGoodsMaster> findByUploadStatus(String gbOne);

    IfGoodsMaster findByChannelGbAndGoodsNo(String gbOne, String toString);
}
