package io.spring.backoffice_batch;

import io.spring.main.util.StringFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class BackofficeBatchApplicationTests {

    @Test
    void contextLoads() {

    }

//    @Test
//    void getPastMonthDate(){
//        //한달 전
//        Calendar mon = Calendar.getInstance();
//        mon.add(Calendar.MONTH , -1);
//        String beforeMonth = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mon.getTime());
//        System.out.println(getStringToDate(beforeMonth));
//    }
//    @Test
//    Date getStringToDate(String strDate){
//        Date getDate = null;
//        try{
//            getDate = new SimpleDateFormat(StringFactory.getDateFormat()).parse(strDate);
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        return getDate;
//    }

}
