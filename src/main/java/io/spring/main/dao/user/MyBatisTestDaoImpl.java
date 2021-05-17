package io.spring.main.dao.user;

import io.spring.main.infrastructure.mybatis.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MyBatisTestDaoImpl implements TestRepository {
	private final TestMapper testMapper;

	@Autowired
	public MyBatisTestDaoImpl(TestMapper testMapper) {
		this.testMapper = testMapper;
	}

	@Override
	public void save(Test test) {
		if (testMapper.findById(test.getId()) == null) {
			testMapper.insert(test);
		} else {
			testMapper.update(test);
		}
	}

	@Override
	public Optional<Test> findById(int id) {
		return Optional.ofNullable(testMapper.findById(id));
	}

	@Override
	public List<Test> findTests() {
		// TODO Auto-generated method stub
		return testMapper.findTests();
	}

}
