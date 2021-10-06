package io.spring.main.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GoodsOrAddGoods {
    goods("001"),
    add_goods("002"),
    addGoods("002");
    private final String fieldName;
}