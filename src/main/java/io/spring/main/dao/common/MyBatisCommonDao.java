package io.spring.main.dao.common;

import java.util.HashMap;

public interface MyBatisCommonDao {
	HashMap<String, Object> getSequence(HashMap<String, Object> param);
}
