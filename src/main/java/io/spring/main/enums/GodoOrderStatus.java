package io.spring.main.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GodoOrderStatus {
    o1("o1"), // 입금대기
    p1("p1"), // 결제완료
    g1("g1"), // 상품준비중
    g2("g2"), // 구매발주
    g3("g3"), // 상품입고
    g4("g4"), // 상품출고
    d1("d1"), // 배송중
    d2("d2"), // 배송완료
    s1("s1"), // 구매확정
    c1("c1"), // 자동취소
    c2("c2"), // 품절취소
    c3("c3"), // 관리자취소
    c4("c4"), // 고객취소요청
    f1("f1"), // 결제시도
    f2("f2"), // 고객결제중단
    f3("f3"), // 결제실패
    b1("b1"), // 반품접수
    b2("b2"), // 반송중
    b3("b3"), // 반품보류
    b4("b4"), // 반품회수완료
    e1("e1"), // 교환접수
    e2("e2"), // 반송중
    e3("e3"), // 재배송중
    e4("e4"), // 교환보류
    e5("e5"), // 교환완료
    r1("r1"), // 환불접수
    r2("r2"), // 환불보류
    r3("r3"), // 환불완료
    z1("z1"), // 추가입금대기
    z2("z2"), // 추가결제완료
    z3("z3"), // 추가배송중
    z4("z4"), // 추가배송완료
    z5("z5"); // 교환추가완료
    private final String fieldName;
}
