package io.spring.main.apis;

import io.spring.main.model.goods.GoodsSearchData;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommonFunctions {
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

            System.out.println("111111111111111111111111111111111111111111111111111111111111111111111111111 : " + System.currentTimeMillis());
            Long start = System.currentTimeMillis();
        try {
            //OpenApi호출
            System.out.println(urlstr);
            //+ "&orderStatus=p1";
//            URL url = new URL(urlstr);
            HttpGet request = new HttpGet(urlstr);
            CloseableHttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            String result = null;

            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
                System.out.println(result);
            }
//            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
//
//            // 응답 읽기
//            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
//            String result = "";
//            String line;
//            while ((line = br.readLine()) != null) {
//                result = result + line.trim();// result = URL로 XML을 읽은 값
//                System.out.println(line);
//            }


            System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222 : " + (System.currentTimeMillis() - start)/1000);



//            System.out.println(result);
//
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
            System.out.println(e.getMessage());
            return null;
        }
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
}
