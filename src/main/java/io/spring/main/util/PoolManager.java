package io.spring.main.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.io.IOException;
import java.io.InputStream;

public class PoolManager {

	private static PoolManager instance;
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;

	private PoolManager() {
		String resource = "sqlConfig/mybatis-config.xml";
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PoolManager getInstance() {
		if (instance == null) {
			instance = new PoolManager();
		}
		return instance;
	}

	// mybatis는 default commit 옵션이 false 이므로,
	// 쿼리문 수행 후 commit 해야한다.
	public SqlSession getSession() {
		SqlSession session = null;
		session = sqlSessionFactory.openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
		return session;
	}

}
