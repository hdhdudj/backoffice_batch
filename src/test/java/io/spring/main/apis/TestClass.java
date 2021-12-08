package io.spring.main.apis;

import io.spring.main.enums.DeliveryMethod;
import io.spring.main.enums.TrdstOrderStatus;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
public class TestClass {
    @Test
    public void DateTest() throws ParseException {
        //한달 전
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Calendar mon = Calendar.getInstance();
        Date startDt = form.parse("2017-01-01");
        mon.setTime(startDt);
        int n = 30;
        mon.add(Calendar.DATE , 59 * n);
        String beforeMonth = form.format(mon.getTime());
        System.out.println(beforeMonth);
    }

//    @Test
//    public void equalsTest(){
////        IfGoodsMaster if1 = new IfGoodsMaster();
////        IfGoodsMaster if2 = new IfGoodsMaster();
////        if2.setGoodsNo("2");
////
////        System.out.println(if1.toString());
////        System.out.println(if2.toString());
////        System.out.println(if1.equals(if2));
//    }

    @Test
    public void enumTest(){
        System.out.printf(DeliveryMethod.valueOf(DeliveryMethod.class, "delivery").toString());
//        for(DeliveryMethod val : DeliveryMethod.valueOf(DeliveryMethod.class, "001")){
//            System.out.println(val.toString());
//        }
    }

    @Test
    public void enumTest2(){
        System.out.println(EnumUtils.isValidEnum(TrdstOrderStatus.class, "a01"));
    }

//    @Test
//    public void builderTest(){
////        TbOrderDetail tbOrderDetail = new TbOrderDetail();
////        tbOrderDetail = tbOrderDetail.builder().orderId("aaaa").build();
////        System.out.println(tbOrderDetail.getOrderId() + ", " + tbOrderDetail.getOrderSeq());
//    }
}
