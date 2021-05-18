package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.model.goods.AddGoodsData;
import io.spring.main.model.goods.GoodsMustInfoData;
import io.spring.main.model.goods.OptionData;
import io.spring.main.model.goods.TextOptionData;
import io.spring.main.model.goods.entity.EsGoods;
import io.spring.main.util.PoolManager;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsSearch {
//    private static PoolManager poolManager = null;
//    private static SqlSession session = null;

    public static List<EsGoods> retrieveOrder(String fromDt, String toDt) {

        // TODO Auto-generated method stub
        BufferedReader br = null;
        // DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        List<EsGoods> goodsDatas = new ArrayList<>();

        try {
            //OpenApi호출
            String urlstr = StringFactory.getGodoUrl() + StringFactory.getGoodsSearch() + "?" + StringFactory.getGoodsSearchParams()[0] + "=" +
                    StringFactory.getPKey() + "&" +StringFactory.getGoodsSearchParams()[1]
                    + "=" + StringFactory.getKey();
            System.out.println(urlstr);
            //+ "&orderStatus=p1";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

            // 응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }

            System.out.println(result);
//
//            // xml 파싱하기
//            InputSource is = new InputSource(new StringReader(result));
//
//            builder = factory.newDocumentBuilder();
//            doc = builder.parse(is);
//            XPathFactory xpathFactory = XPathFactory.newInstance();
//            XPath xpath = xpathFactory.newXPath();
//            // XPathExpression expr = xpath.compile("/response/body/items/item");
//            XPathExpression expr = xpath.compile("//data");
//            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                NodeList child = nodeList.item(i).getChildNodes();
//                for (int y = 0; y < child.getLength(); y++) {
//                    Node lNode = child.item(y);
//
//                    // printTree(lNode, 1);
//
//                    if (lNode.getNodeName() == "return") {
//
//                        NodeList mNodes = lNode.getChildNodes();
//
//                        // List<OrderData> orderMasterDatas = new ArrayList<OrderData>();
//
//                        for (int mi = 0; mi < mNodes.getLength(); mi++) {
////								for (int mi = 0; mi < 1; mi++) {
//                            Node mNode = mNodes.item(mi);
//
//                            if (mNode.getNodeName() == "goods_data") {
////                                EsGoods map = makeGoodsmaster(mNode);
//                                makeGoodsmaster(mNode);
//
////                                goodsDatas.add(map);
//                                // order master array append
//                            }
//
//                        }
//
//                        System.out.println("-----------------------------------------------------------------");
////                        System.out.println(goodsDatas.size());
//                        System.out.println("-----------------------------------------------------------------");
//
//                    }
//
//                }
//
//            }

            return goodsDatas;


        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    private static void makeGoodsmaster(Node root) {
        Map<String, Object> map = new HashMap<String, Object>();
        // List<Map<String, Object>> deliveryDatas = new ArrayList<Map<String,
        // Object>>();
        List<OptionData> optionDataList = new ArrayList<>();

        List<TextOptionData> textOptionDataList = new ArrayList<>();
        // List<Map<String, Object>> goodsDatas = new ArrayList<Map<String, Object>>();
        List<AddGoodsData> adGoodsDataList = new ArrayList<>();
        // List<Map<String, Object>> addGoodsDatas = new ArrayList<Map<String,
        // Object>>();
        List<GoodsMustInfoData> goodsMustInfoDataList = new ArrayList<>();

        NodeList cNodes = root.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node cNode = cNodes.item(i);
            //String idx = cNode.getAttributes().getNamedItem("idx").getNodeValue();
            // System.out.println(cNode.getAttributes().getNamedItem("idx").getNodeValue());
            if (cNode.getNodeName() == "optionData") {
                OptionData optionData = makeOptionData(cNode);
                optionDataList.add(optionData);
            } else if (cNode.getNodeName() == "textOptionData") {
                TextOptionData textOptionData = makeTextOptionData(cNode);
                textOptionDataList.add(textOptionData);
            } else if (cNode.getNodeName() == "addGoodsData") {
                // Map<String, Object> goodsData = makeOrderGoodsData(cNode);
                AddGoodsData addGoodsData = makeAddGoodsData(cNode);
                adGoodsDataList.add(addGoodsData);
            } else if (cNode.getNodeName() == "goodsMustInfoData ") {
                // Map<String, Object> addGoodsData = makeAddGoodsData(cNode);
                GoodsMustInfoData goodsMustInfoData = makeGoodsMustInfoData(cNode);
                goodsMustInfoDataList.add(goodsMustInfoData);
            } else {

                if (cNode.getNodeName() == "claimData") {
                    System.out.println("claimData 데이타 이상 - 확인필요");
                }

                if ("확인필요한값!".equals((String) getNodeValue(cNode))) {
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("데이타 이상 - 확인필요");
                    System.out.println("-----------------------------------------------------------------");
                } else {
                    map.put(cNode.getNodeName(), getNodeValue(cNode));
                }

            }

            map.put("optionData",optionDataList);
            map.put("textOptionData",textOptionDataList);
            map.put("addGoodsData",adGoodsDataList);
            map.put("goodsMustInfoData",goodsMustInfoDataList);
        }

//        ObjectMapper mapper = new ObjectMapper();
//
//        OrderData o = mapper.convertValue(map, OrderData.class);

//        return o;
//
        // return map;

    }

    private static AddGoodsData makeAddGoodsData(Node cNode) {
        return null;
    }

    private static GoodsMustInfoData makeGoodsMustInfoData(Node cNode) {
        return null;
    }

    private static TextOptionData makeTextOptionData(Node cNode) {
        return null;
    }

    private static OptionData makeOptionData(Node cNode) {
        return null;
    }

    public static Object getNodeValue(Node n) {


        if (n.getChildNodes().getLength() == 0) {
            return n.getNodeValue();
        } else {
            if (n.getNodeValue() == null && n.getFirstChild().getNodeType() == 3) {

                return n.getFirstChild().getNodeValue();
            } else if (n.getNodeValue() == null && n.getFirstChild().getNodeType() == 4) {

                return n.getFirstChild().getNodeValue();

            } else {
                return "확인필요한값!";
            }

        }



    }

    public static void printTree(Node root, int level) {

        System.out.println("name : " + root.getNodeName() + " level - " + level);
        // System.out.println(root.getChildNodes().getLength());

        if (root.getChildNodes().getLength() > 0) {

            NodeList cNodes = root.getChildNodes();

            for (int i = 0; i < cNodes.getLength(); i++) {
                Node cNode = cNodes.item(i);
                if (cNode.getNodeType() == 1) {
                    printTree(cNode, level + 1);
                } else {
                    System.out.println("==level=== " + level);
                    System.out.println("====" + cNode.getNodeValue());
                }
            }

        } else {

            System.out.println("-------" + level);
            System.out.println("-------" + root.getNodeValue());
        }

    }
}
