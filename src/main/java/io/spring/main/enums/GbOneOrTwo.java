package io.spring.main.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GbOneOrTwo {
    y("01"),
    n("02"),
    light("01"),
    furn("02");
    private final String fieldName;
}
