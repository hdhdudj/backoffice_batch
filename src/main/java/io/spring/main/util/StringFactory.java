package io.spring.main.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

public class StringFactory {
    @Getter
    private final static String strUtf8 = "UTF-8";

    @Getter
    private final static String[] goodsSearchParams = {"partner_key",
            "key","goodsNm","goodsNo","goodsCd","makerNm","originNm",
            "goodsSearchWord","goodsModelNo","companyNm","searchDateType",
            "startDate","endDate","page","size","scmNo","cateCd"};
    @Getter
    private final static String[] orderSearchParams = {"partner_key",
            "key","dateType","startDate","endDate","orderNo","orderStatus",
            "orderChannel","searchType","searchKeyword","sort"};
    // request param 관련, 기호
    @Getter
    private final static String strQuestion = "?";
    @Getter
    private final static String strEqual = "=";
    @Getter
    private final static String strAnd = "&";
    @Getter
    private final static String strStar = "*";

    // seq keyword
    @Getter
    private final static String seqItasrtStr = "seq_ITASRT";
    @Getter
    private final static String purchaseSeqStr = "seq_LSPCHM";
    @Getter
    private final static String depositPlanId = "seq_LSDPSP";
    @Getter
    private final static String seqIforderMaster = "seq_IFORDERMASTER";
    @Getter
    private final static String seqTbOrderDetail = "seq_TBORDERDETAIL";
    @Getter
    private final static String seqTbMember = "seq_TBMEMBER";

    // alphabet
    @Getter
    private final static String cUpperStr = "C";
    @Getter
    private final static String strUpperO = "O";

    // jpaGoodsService
    @Getter
    private final static String threeStartCd = "001";
    @Getter
    private final static String threeSecondCd = "002";
    @Getter
    private final static String fourStartCd = "0001";
    @Getter
    private final static String fiveStartCd = "00001";
    //    private final String nineStartCd = "000000001";
    @Getter
    private final static String gbOne = "01";
    @Getter
    private final static String gbTwo = "02";
    @Getter
    private final static String gbThree = "03";
    @Getter
    private final static String gbFour = "04";
    // 공급사 scmNo
    @Getter
    private final static String scmNo63 = "000063";
    @Getter
    private final static String scmNo64 = "000064";

    // jpaPurchaseService
    @Getter
    private final static String ninetyNine = "99";
    @Getter
    private final static int intNine = 9;
    @Getter
    private final static int intEight = 8;
    @Getter
    private final static String strZero = "0";
    @Getter
    private final static String strOne = "1";
    @Getter
    private final static String strTwo = "2";
    @Getter
    private final static String strThree = "3";
    @Getter
    private final static String strFour = "4";
    @Getter
    private final static String strFive = "5";
    @Getter
    private final static String strEleven = "11";
    @Getter
    private final static String strStartDt = "startDt";
    @Getter
    private final static String strEndDt = "endDt";
    @Getter
    private final static String strPurchaseVendorId = "purchaseVendorId";
    @Getter
    private final static String strAssortId = "assortId";
    @Getter
    private final static String strPurchaseStatus = "purchaseStatus";


    // controller
    @Getter
    private final static String strOk = "ok";
    @Getter
    private final static String strSuccess = "success";

    @Getter
    private final static String doomDay = "9999-12-31 23:59:59";
    @Getter
    private final static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    @Getter
    private final static String dateFormat = "yyyy-MM-dd";

    // deposit controller
    @Getter
    private final static String strDepositNo = "seq_LSDPSM";
    @Getter
    private final static String dUpperStr = "D";


    // DB table default values
    @Getter
    private final static String strB = "b";
    @Getter
    private final static String strY = "y";
    @Getter
    private final static String strN = "n";
    @Getter
    private final static String strD = "d";
    @Getter
    private final static String strE = "e";
    @Getter
    private final static Float fZero = 0f;
    @Getter
    private final static String strLocal = "local";
    @Getter
    private final static String strAll = "all";
    @Getter
    private final static String strOption = "option";
    @Getter
    private final static String strC = "c";
    @Getter
    private final static String strPercent = "percent";
    @Getter
    private final static String strDay = "day";
    @Getter
    private final static String strS = "s";
    @Getter
    private final static String strT = "t";
    @Getter
    private final static String strNo = "no";
    @Getter
    private final static String strIncl = "incl";
    @Getter
    private final static String strRegDt = "regDt";
    @Getter
    private final static String strNormal = "normal";

    // xml 파싱 관련 - goods_search
    @Getter
    private final static String strReturn = "return";
    @Getter
    private final static String strGoodsData = "goods_data";
    @Getter
    private final static String strOptionData = "optionData";
    @Getter
    private final static String strTextOptionData = "textOptionData";
    @Getter
    private final static String strAddGoodsData = "addGoodsData";
    @Getter
    private final static String strGoodsMustInfoData = "goodsMustInfoData";
    @Getter
    private final static String strClaimData = "claimData";
    @Getter
    private final static String strStepData = "stepData";
    @Getter
    private final static String strGoodsNoData = "goodsNoData"; // 예외
    @Getter
    private final static String strDataUrl = "data_url";

    // xml 파싱 관련 - order_search 관련
    @Getter
    private final static String strOrderData = "order_data";
    @Getter
    private final static String strOrderInfoData = "orderInfoData";
    @Getter
    private final static String strOrderDeliveryData = "orderDeliveryData";
    @Getter
    private final static String strGiftData = "giftData";
    @Getter
    private final static String strOrderGoodsData = "orderGoodsData";
    @Getter
    private final static String strExchangeInfoData = "exchageInfoData";
    @Getter
    private final static String strOrderConsultData = "orderConsultData";

    // Itvari
    @Getter
    private final static String strSingleGoods = "단품";

    // sizeType, 직구 or 수입
    @Getter
    private final static String strLight = "light";
    @Getter
    private final static String strFurn = "furn";

    // 잘못된 상세 메모 split 지표 문구
    @Getter
    private final static String strDataImage = "data:image";

    // http 방법
    @Getter
    private final static String strPost = "POST";

    // xml 코드
    @Getter
    private final static String strSuccessCode = "000";

    // 상태 코드 관련
    @Getter
    private final static String strAOne = "A01";
    @Getter
    private final static String strGoods = "goods";
    @Getter
    private final static String strAddGoods = "add_goods";
    @Getter
    private final static String strDelivery = "delivery";
    @Getter
    private final static String strAir = "air";
    @Getter
    private final static String strShip = "ship";
    @Getter
    private final static String strQuick = "quick";
    @Getter
    private final static String strPOne = "p1"; // 결제 완료

    // 기호
    @Getter
    private final static String strAt = "@";
    @Getter
    private final static String splitGb = "\\^\\|\\^";

    // 주문 상태코드
    @Getter
    private final static String strA01 = "A01"; // 주문접수
    @Getter
    private final static String strA02 = "A02"; // 주문확인
    @Getter
    private final static String strB01 = "B01"; // 발주대기
    @Getter
    private final static String strB02 = "B02"; // 발주완료
    @Getter
    private final static String strC01 = "C01"; // 해외입고완료 (수입에만 존재)
    @Getter
    private final static String strC02 = "C02"; // 이동지시 (수입에만 존재)
    @Getter
    private final static String strC03 = "C03"; // 이동지시완료 (수입에만 존재)
    @Getter
    private final static String strC04 = "C04"; // 국내(현지)입고완료
    @Getter
    private final static String strD01 = "D01"; // 출고지시
    @Getter
    private final static String strD02 = "D02"; // 출고
    @Getter
    private final static String strD03 = "D03"; // 국제운송
    @Getter
    private final static String strD04 = "D04"; // 통관
    @Getter
    private final static String strD05 = "D05"; // 국내운송
    @Getter
    private final static String strD06 = "D06"; // 배송완료
    @Getter
    private final static String strE01 = "E01"; // 구매확정
}
