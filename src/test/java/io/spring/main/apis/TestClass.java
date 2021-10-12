package io.spring.main.apis;

import io.spring.main.model.goods.entity.IfGoodsMaster;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestClass {
    @Test
    public void DateTest() throws ParseException {
        //한달 전
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Calendar mon = Calendar.getInstance();
        Date startDt = form.parse("2017-01-01");
        mon.setTime(startDt);
        int n = 7;
        mon.add(Calendar.DATE , 250 * n);
        String beforeMonth = form.format(mon.getTime());
        System.out.println(beforeMonth);
    }

    @Test
    public void equalsTest(){
        IfGoodsMaster if1 = new IfGoodsMaster();
        IfGoodsMaster if2 = new IfGoodsMaster();
        if2.setGoodsNo("2");

        System.out.println(if1.toString());
        System.out.println(if2.toString());
        System.out.println(if1.equals(if2));
    }
}
