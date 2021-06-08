package io.spring.main.infrastructure.util;

import lombok.Getter;

public class StringFactory {
    // Godo Key
    @Getter
    private static String pKey ="JUJDJTExJTg4byUyNSUzQ1olRDE=";
    @Getter
    private static String key = "JURBaSU4OGU3LnMlMjElM0ElQUIlQzQ2NiU5RiVFQiUyOCVBOCUwNSVFMSUxQyU4RiVCM0klMTMlOEMlMkElN0QlQTIlQjJ4ZyVCRVMlOTglMDclMEFSQjYlODM=";//"JUMxJTAyJTExJUJBJUQwJTJBcyUzQSU5RVglMkZKJTEwRiVDNSVFQSUwRSVGQyU4NTYlQzUlRTV3MFhGJURESCVBMzElRDklRTRCJTEwJTE4diUyOCVGRCUwMHY=";

    // Godo api page
    @Getter
    private final static String godoUrl = "https://openhub.godo.co.kr/godomall5/";
    // 상품조회
    @Getter
    private final static String goodsSearch = "goods/Goods_Search.php";
    // 추가상품조회
    @Getter
    private final static String addGoodsSearch = "goods/Goods_Add_Search.php";
    @Getter
    private final static String[] goodsSearchParams = {"partner_key",
            "key","goodsNm","goodsNo","goodsCd","makerNm","originNm",
            "goodsSearchWord","goodsModelNo","companyNm","searchDateType",
            "startDate","endDate","page","size","scmNo","cateCd"};

    // GoodsController
    @Getter
    private final static String seqItasrtStr = "seq_ITASRT";

    //jpaCommonService
    @Getter
    private final static String cUpperStr = "C";

    // jpaGoodsService
    @Getter
    private final static String threeStartCd = "001";
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
    private final static String splitGb = "\\^\\|\\^";

    // jpaPurchaseService
    @Getter
    private final static String purchaseSeqStr = "seq_LSPCHM";
    @Getter
    private final static String depositPlanId = "seq_LSDPSP";
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
    private final static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    // deposit controller
    @Getter
    private final static String strDepositNo = "seq_LSDPSM";
    @Getter
    private final static String dUpperStr = "D";


    // DB table default values
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

    // xml 파싱 관련
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

    // Itvari
    @Getter
    private final static String strSingleGoods = "단품";

    // sizeType, 직구 or 수입
    @Getter
    private final static String strLight = "light";
    @Getter
    private final static String strFurn = "furn";

    @Getter
    private final static String strDataImage = "data:image";
}
