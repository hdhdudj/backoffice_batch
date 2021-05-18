package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.infrastructure.util.StringFactory;
import io.spring.main.model.goods.entity.EsGoods;
import io.spring.main.util.PoolManager;
import org.apache.ibatis.session.SqlSession;
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
    private static PoolManager poolManager = null;
    private static SqlSession session = null;

    public static List<EsGoods> retrieveOrder(String fromDt, String toDt) {

        // TODO Auto-generated method stub
        BufferedReader br = null;
        // DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        List<EsGoods> orderMasterDatas = new ArrayList<>();

        try {
            //OpenApi호출
            String urlstr = StringFactory.getGodoUrl() + "?" + StringFactory.getGoodsSearchParams()[0] + "=" +
                    StringFactory.getPKey() + "&" +StringFactory.getGoodsSearchParams()[1]
                    + "=" + StringFactory.getKey();
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

            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));

            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            // XPathExpression expr = xpath.compile("/response/body/items/item");
            XPathExpression expr = xpath.compile("//data");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int y = 0; y < child.getLength(); y++) {
                    Node lNode = child.item(y);

                    // printTree(lNode, 1);

                    if (lNode.getNodeName() == "return") {

                        NodeList mNodes = lNode.getChildNodes();

                        // List<OrderData> orderMasterDatas = new ArrayList<OrderData>();

                        for (int mi = 0; mi < mNodes.getLength(); mi++) {
//								for (int mi = 0; mi < 1; mi++) {
                            Node mNode = mNodes.item(mi);


                            if (mNode.getNodeName() == "goods_data") {
                                EsGoods map = makeGoodsmaster(mNode);



                                orderMasterDatas.add(map);
                                // order master array append
                            }

                        }

                        System.out.println("-----------------------------------------------------------------");
                        System.out.println(orderMasterDatas.size());
                        System.out.println("-----------------------------------------------------------------");





                    }

                }

            }

            return orderMasterDatas;


        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    private static EsGoods makeGoodsmaster(Node root) {
        Map<String, Object> map = new HashMap<String, Object>();
        // List<Map<String, Object>> deliveryDatas = new ArrayList<Map<String,
        // Object>>();
        List<OrderDeliveryData> deliveryDatas = new ArrayList<OrderDeliveryData>();

        List<OrderInfoData> infoDatas = new ArrayList<OrderInfoData>();
        // List<Map<String, Object>> goodsDatas = new ArrayList<Map<String, Object>>();
        List<OrderGoodsData> goodsDatas = new ArrayList<OrderGoodsData>();
        // List<Map<String, Object>> addGoodsDatas = new ArrayList<Map<String,
        // Object>>();
        List<AddGoodsData> addGoodsDatas = new ArrayList<AddGoodsData>();

        NodeList cNodes = root.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node cNode = cNodes.item(i);



            //String idx = cNode.getAttributes().getNamedItem("idx").getNodeValue();



            // System.out.println(cNode.getAttributes().getNamedItem("idx").getNodeValue());


            if (cNode.getNodeName() == "orderDeliveryData") {
                OrderDeliveryData deliveryData = makeOrderDeliveryData(cNode);
                deliveryDatas.add(deliveryData);
            } else if (cNode.getNodeName() == "orderInfoData") {
                OrderInfoData infoData = makeOrderInfoData(cNode);
                infoDatas.add(infoData);
            } else if (cNode.getNodeName() == "orderGoodsData") {
                // Map<String, Object> goodsData = makeOrderGoodsData(cNode);
                OrderGoodsData goodsData = makeOrderGoodsData(cNode);
                goodsDatas.add(goodsData);
            } else if (cNode.getNodeName() == "addGoodsData") {
                // Map<String, Object> addGoodsData = makeAddGoodsData(cNode);
                AddGoodsData addGoodsData = makeAddGoodsData(cNode);
                addGoodsDatas.add(addGoodsData);
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

            map.put("orderDeliveryData",deliveryDatas);
            map.put("orderInfoData",infoDatas);
            map.put("orderGoodsData",goodsDatas);
            map.put("addGoodsData",addGoodsDatas);



        }

        ObjectMapper mapper = new ObjectMapper();

        OrderData o = mapper.convertValue(map, OrderData.class);

        return o;
//
        // return map;

    }
}
