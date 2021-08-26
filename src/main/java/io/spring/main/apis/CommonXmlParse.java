package io.spring.main.apis;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.spring.main.model.goods.GoodsSearchData;
import io.spring.main.util.StringFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommonXmlParse {
    private final ObjectMapper objectMapper;

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
				result = EntityUtils.toString(entity).replace(new Character((char) 3).toString(), "");
//                System.out.println(result);
            }

			// String str = new Character((char) 3).toString();

			// ascii(3)

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

//    /**
//     * xml node를 object와 매핑해주는 함수
//     * @param cNode
//     * @return
//     */
//    public <T> T makeSimpleNodeToObj(Node cNode, Class<T> clss) {
//        Map<String, Object> map = makeSimpleNodToMap(cNode);
//
//        T o = objectMapper.convertValue(map, clss);
//        return o;
//    }

    /**
     * xml node를 map과 매핑해주는 함수
     * @param cNode
     * @return
     */
    private Object makeSimpleNodToMap(Node cNode, List<String> goodsSearchGotListPropsMap){ // cNode : orderNo, claimData, addGoodsData, ...
        Map<String, Object> map = new HashMap<String, Object>();
        NodeList cNodes = cNode.getChildNodes(); // cNodes : sno, goodsNo, ....
        int nodeNum = cNodes.getLength();

        for (int i = 0; i < nodeNum; i++) {
            Node node = cNodes.item(i); // goodsNo, ...
            if(nodeNum > 1) {
                NodeList dNodes = node.getChildNodes();
                if(map.get(node.getNodeName()) == null){ // 아예 key가 없는 경우 (단일 값으로 생성함)
                    // list로 존재해야만 하는 key일 경우 (단, value가 string일 경우 더 이상의 재귀는 x..)
                    if(goodsSearchGotListPropsMap.contains(node.getNodeName()) && !(node.getNodeValue() instanceof String)){
                        map.put(node.getNodeName(), singleValToList(makeSimpleNodToMap(node, goodsSearchGotListPropsMap)));
                    }
                    else{ // 단일 값으로 존재하는 key인 경우
                        map.put(node.getNodeName(), makeSimpleNodToMap(node, goodsSearchGotListPropsMap));
                    }
                }
                else{
                    if(map.get(node.getNodeName()) instanceof List){ // 이미 list로 들어있는 경우 (이미 존재하는 list에 이번 값을 넣어줌)
                       ((List<Object>)map.get(node.getNodeName())).add(makeSimpleNodToMap(node, goodsSearchGotListPropsMap));
                    }
                    else{ // 값이 있지만 list가 아닌 단일값으로 들어가 있는 경우 (새로 list를 만들어 기존 값을 넣고 이번 값도 넣어줌)
                        List<Object> newList = singleValToList(map.get(node.getNodeName()));
                        newList.add(makeSimpleNodToMap(node, goodsSearchGotListPropsMap));
                        map.put(node.getNodeName(), newList);
                    }
                }
            }
            else {
                // list로 존재해야만 하는 key일 경우 (단, value가 string일 경우 더 이상의 재귀는 x..)
                if(goodsSearchGotListPropsMap.contains(cNode.getNodeName()) && !(node.getNodeValue() instanceof String)){
                    return singleValToList(makeSimpleNodToMap(node, goodsSearchGotListPropsMap));
                }
                else{ // 단일 값으로 존재하는 key인 경우
                    return getNodeValue(node);
                }
            }
        }
        return map;
    }

    /**
     * return list that has input value
     * @param val
     * @return
     */
    private List<Object> singleValToList(Object val){
        List<Object> newList = new ArrayList<>();
        newList.add(val);
        return newList;
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

	public HashMap<String, Object> getPagination(NodeList nodeList) {
		System.out.println("getPagination");
		for (int i = 0; i < nodeList.getLength(); i++) {
			NodeList child = nodeList.item(i).getChildNodes();
			for (int y = 0; y < child.getLength(); y++) {

				Node lNode = child.item(y);
				if (lNode.getNodeName().equals("header")) {
					System.out.println("header");

					NodeList mNodes = lNode.getChildNodes();
					for (int mi = 0; mi < mNodes.getLength(); mi++) {


						Node mNode = mNodes.item(mi);
						if (mNode.getNodeName().equals("max_page")) {

							System.out.println("max_page");
							System.out.println(mNode.getTextContent());

							// Map<String, Object> map = makeMasterNode(mNode, goodsSearchGotListPropsMap);
							// dataList.add(map);
							// order master array append
						}

					}
				}
			}
		}
		

		HashMap<String, Object> r = new HashMap<String, Object>();

		return r;
	}

    // xml 받아오는 함수
    public List<Map<String, Object>> retrieveNodeMaps(String dataName, NodeList nodeList, List<String> goodsSearchGotListPropsMap) {
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
                            Map<String, Object> map = makeMasterNode(mNode, goodsSearchGotListPropsMap);
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

    private Map<String, Object> makeMasterNode(Node root, List<String> goodsSearchGotListPropsMap){//, Map<String, Class> classMap, Map<String, List<Object>> listMap) {
        Map<String, Object> map = new HashMap<>();
        NodeList cNodes = root.getChildNodes(); // root : order_search 등 최상위
        for (int i = 0; i < cNodes.getLength(); i++) {
            Node cNode = cNodes.item(i); // cNode : orderNo, claimData 등 바로 밑

            if (cNode.getNodeName() == StringFactory.getStrClaimData()) {
                System.out.println("claimData 데이타 이상 - 확인필요");
            }

            if ("확인필요한값!".equals((String) CommonXmlParse.getNodeValue(cNode))) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("데이타 이상 - 확인필요");
                System.out.println("-----------------------------------------------------------------");
            }
            else {
                String key = controlSnakeCaseException(cNode.getNodeName()); // claimData, orderNo, ...
                if(map.get(cNode.getNodeName()) == null){ // key가 없는 경우
                    // list로 들어가야 하는 key 인 경우
                    // list로 들어가야 하는 key인지 미리 알고 있어야 하는 이유 :
                    // list로 들어가야 하는 값이지만 xml에서 해당 값을 한 개만 보냈을 경우 그 값은 map에 list가 아닌 단일 value로 들어가게 되는데, 그러면 objectMapper에서 변환시 에러가 난다.
                    if(goodsSearchGotListPropsMap.contains(cNode.getNodeName())){
                        map.put(key, singleValToList(makeSimpleNodToMap(cNode, goodsSearchGotListPropsMap)));
                    }
                    else{ // 단일값으로 들어가야 하는 key인 경우
                        map.put(key, makeSimpleNodToMap(cNode, goodsSearchGotListPropsMap));
                    }
                }
                else if(map.get(cNode.getNodeName()) instanceof List){
                    ((List<Object>)map.get(cNode.getNodeName())).add(makeSimpleNodToMap(cNode, goodsSearchGotListPropsMap));
                }
                else { // key가 이미 존재 -> 무조건 list에 넣어줘야 함
                    List<Object> newList = new ArrayList<>();
                    newList.add(map.get(cNode.getNodeName()));
                    newList.add(makeSimpleNodToMap(cNode, goodsSearchGotListPropsMap));
                    map.put(key, newList);
                }
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
