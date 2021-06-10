package io.spring.main.jparepos.goods;

import io.spring.main.model.goods.entity.XmlTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaXmlTestRepository extends JpaRepository<XmlTest, String> {
}
