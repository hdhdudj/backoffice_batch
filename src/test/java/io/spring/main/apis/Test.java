package io.spring.main.apis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
    @org.junit.Test
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
}
