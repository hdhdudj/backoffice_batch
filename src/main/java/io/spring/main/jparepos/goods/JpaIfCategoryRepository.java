package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.IfCategory;
import io.spring.main.model.goods.idclass.IfCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIfCategoryRepository extends JpaRepository<IfCategory, IfCategoryId> {
    IfCategory findByChannelCategoryId(String channelCategoryId);
}
