package io.spring.main.dao.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository {
	void save(Test test);

	Optional<Test> findById(int id);

	List<Test> findTests();

}
