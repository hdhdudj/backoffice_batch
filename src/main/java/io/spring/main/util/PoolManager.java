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

	public SqlSession getSession() {
		SqlSession session = null;
		session = sqlSessionFactory.openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
		return session;
	}

}
