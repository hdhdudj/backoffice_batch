package io.spring.main.dao.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TestDataList {

	@JsonProperty("tests")
	private final List<Test> testDatas;
	@JsonProperty("testsCount")
	private final int count;

	public TestDataList(List<Test> testDatas, int count) {

		this.testDatas = testDatas;
		this.count = count;
	}

}
