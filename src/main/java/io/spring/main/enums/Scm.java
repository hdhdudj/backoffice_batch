package io.spring.main.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Scm {
    씨디에프브로스(1),
    atempo(63),
    mohd(67),
    본사재고(74),
    추가금(75),
    sevokorea(64);
    private final int fieldName;
}