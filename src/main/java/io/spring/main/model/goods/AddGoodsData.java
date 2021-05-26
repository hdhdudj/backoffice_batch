package io.spring.main.model.goods;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddGoodsData {
    private Long addGoodsNo;
    private String goodsNm;
    private String optionNm;
    private String brandCd;
    private String makerNm;
    private Float goodsPrice;
    private Long stockCnt;
    private String viewFl;
    private String soldOutFl;
}
