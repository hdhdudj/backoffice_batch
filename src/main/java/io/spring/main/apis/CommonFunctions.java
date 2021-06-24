package io.spring.main.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.util.StringFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.apache.http.HttpEntity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommonFunctions {
    private final ObjectMapper objectMapper;
    private final List<String> goodsSearchGotListPropsMap;

    /**
     * get xml by open api and return node list of xml.
     * @param urlstr
     * @return
     */
    public static NodeList getXmlNodes(String urlstr){
        // TODO Auto-generated method stub
        BufferedReader br = null;
        // DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        List<GoodsSearchData> goodsSearchData = new ArrayList<>();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        Long start = System.currentTimeMillis();
        try {
            //OpenApi호출
            log.debug("godomall open api url : " + urlstr);
            HttpGet request = new HttpGet(urlstr);
            CloseableHttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            String result = null;

            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
//                System.out.println(result);
            }

            log.debug("get xml time : " + (System.currentTimeMillis() - start)/1000);
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));

            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            // XPathExpression expr = xpath.compile("/response/body/items/item");
            XPathExpression expr = xpath.compile("//data");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            return nodeList;
        }catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    /**
     * xml node를 object와 매핑해주는 함수
     * @param cNode
     * @return
     */
    public <T> T makeSimpleNodeToObj(Node cNode, Class<T> clss) {
        Map<String, Object> map = makeSimpleNodToMap(cNode);

        T o = objectMapper.convertValue(map, clss);
        return o;
    }

    /**
     * xml node를 map과 매핑해주는 함수
     * @param cNode
     * @return
     */
    private Map<String, Object> makeSimpleNodToMap(Node cNode){
        Map<String, Object> map = new HashMap<String, Object>();
        NodeList cNodes = cNode.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node node = cNodes.item(i);
            if(node.getChildNodes().getLength() > 1){
                map.put(node.getNodeName(), makeSimpleNodToMap(node));
            }
            else{
                System.out.println("------ node.getNodeName() : " + node.getNodeName());
                System.out.println("------ node.getNodeValue() : " + getNodeValue(node));
                map.put(node.getNodeName(), getNodeValue(node));
            }
        }
        return map;
    }

    /**
     * get node value of one xml node.
     * @param n
     * @return
     */
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

    // 고도몰 table column명이 camleCase로 돼있는데 몇 개만 snake로 돼있어서 걔네 처리용
    public static String controlSnakeCaseException(String nodeNm){
        String[] splitStrs = nodeNm.split("_");
        if(splitStrs.length > 2){
            nodeNm = snakeToCamel(nodeNm);
        }
        return nodeNm;
    }
    private static String snakeToCamel(String str){
        String[] miniStrs = str.split("_");
        str = miniStrs[0];
        for(int j = 1 ; j < miniStrs.length; j++){
            str += miniStrs[j].substring(0,1).toUpperCase() + miniStrs[j].substring(1);
        }
        return str;
    }

    // xml 받아오는 함수
    public List<Map<String, Object>> retrieveNodeMaps(String dataName, NodeList nodeList, Map<String, Class> classMap, Map<String, List<Object>> listMap) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList child = nodeList.item(i).getChildNodes();
            for (int y = 0; y < child.getLength(); y++) {
                Node lNode = child.item(y);
//                 printTree(lNode, 1);
                if (lNode.getNodeName() == StringFactory.getStrReturn()) {
                    NodeList mNodes = lNode.getChildNodes();
                    for (int mi = 0; mi < mNodes.getLength(); mi++) {
//								for (int mi = 0; mi < 1; mi++) {
                        Node mNode = mNodes.item(mi);
                        if (mNode.getNodeName() == dataName) {
                            Map<String, Object> map = makeMasterNode(mNode, classMap, listMap);
                            dataList.add(map);
                            // order master array append
                        }
                    }
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println(dataList.size());
                    System.out.println("-----------------------------------------------------------------");
                }
            }
        }
        return dataList;
    }

    private Map<String, Object> makeMasterNode(Node root, Map<String, Class> classMap, Map<String, List<Object>> listMap) {
        Map<String, Object> map = new HashMap<String, Object>();

        NodeList cNodes = root.getChildNodes();
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node cNode = cNodes.item(i);

            for(String key : classMap.keySet()){
                if(key.equals(cNode.getNodeName())){
                    listMap.get(key).add(makeSimpleNodeToObj(cNode, classMap.get(key)));
                }
            }
            if (cNode.getNodeName() == StringFactory.getStrClaimData()) {
                System.out.println("claimData 데이타 이상 - 확인필요");
            }

            if ("확인필요한값!".equals((String) CommonFunctions.getNodeValue(cNode))) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("데이타 이상 - 확인필요");
                System.out.println("-----------------------------------------------------------------");
            } else {
                map.put(CommonFunctions.controlSnakeCaseException(cNode.getNodeName()), CommonFunctions.getNodeValue(cNode));
            }
            for(String key : listMap.keySet()){
                map.put(key,listMap.get(key));
            }
        }

        return map;
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
