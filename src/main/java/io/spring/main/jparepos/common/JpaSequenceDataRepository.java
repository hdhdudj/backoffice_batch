package io.spring.main.jparepos.common;

import io.spring.main.model.common.entity.SequenceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSequenceDataRepository extends JpaRepository<SequenceData, String> {
//    @Procedure(procedureName = "nextval")
//    String nextVal(String input);
}
