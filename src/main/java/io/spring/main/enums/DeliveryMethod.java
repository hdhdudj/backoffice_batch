package io.spring.main.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryMethod {
    delivery("001"),
    air("002"),
    ship("003"),
    quick("004"),
    기타("005"),
    etc("005");
    private final String fieldName;
}
