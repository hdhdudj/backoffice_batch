package io.spring.main.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SearchDateType {
    regDt("등록일기준"),
    modDt("수정일기준");
    private final String fieldName;
}
