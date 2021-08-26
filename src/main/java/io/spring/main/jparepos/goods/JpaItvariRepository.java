package io.spring.main.jparepos.goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.spring.main.model.goods.entity.Itvari;
import io.spring.main.model.goods.idclass.ItvariId;

import java.util.List;

public interface JpaItvariRepository extends JpaRepository<Itvari, ItvariId> {
//    List<Itvari> findByAssortId(String assortId);
    @Query("select max(i.seq) as maxVal from Itvari as i where i.assortId = ?1")
    String findMaxSeqByAssortId(String assortId);
//    Itvari findByAssortIdAndSeq(String assortId, String seq);
//    @Query("select i.seq, i.optionGb from Itvari as i where i.assortId = ?1 and i.optionNm = ?2")
    List<Itvari> findByAssortIdAndOptionNm(String assortId, String optionNm);

    Itvari findByOptionGbAndOptionNm(String optionGb, String optionNm);

	List<Itvari> findByAssortIdAndOptionGbAndOptionNm(String assortId, String optionGb, String optionNm);

//    @Query("update User set name = :#{#paramUser.name}, age = :#{#paramUser.age}, ssn = :#{#paramUser.ssn} where id = :#{#paramUser.id}")
//    int updateSpecificAttribute(@Param("paramUser") User user );
}
