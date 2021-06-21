package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfBrand;
import io.spring.main.model.goods.idclass.IfBrandId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIfBrandRepository extends JpaRepository<IfBrand, IfBrandId> {
    IfBrand findByChannelGbAndChannelBrandId(String channelGb, String channelBrandId);
    IfBrand findByChannelGbAndBrandId(String channelGb, String BrandId);
}
