package io.spring.main.jparepos.category;

import io.spring.main.model.goods.entity.Itcatg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaItcatgRepository extends JpaRepository<Itcatg, String> {
    List<Itcatg> findByUpCategoryId(String categoryId);
}
