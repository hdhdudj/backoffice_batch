package io.spring.main.apis;

import io.spring.main.enums.TrdstOrderStatus;
import io.spring.main.interfaces.Asdf;
import io.spring.main.interfaces.Bsdf;
import io.spring.main.interfaces.TestMapper;
import io.spring.main.interfaces.TestMapperImpl;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.entity.TbOrderDetail;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Mapper;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestMapperImpl.class, TestMapper.class})
public class TestClass {
    @Autowired
    private TestMapper testMapper;

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

    @Test
    public void equalsTest(){
//        IfGoodsMaster if1 = new IfGoodsMaster();
//        IfGoodsMaster if2 = new IfGoodsMaster();
//        if2.setGoodsNo("2");
//
//        System.out.println(if1.toString());
//        System.out.println(if2.toString());
//        System.out.println(if1.equals(if2));
    }

    @Test
    public void matchTest(){
        Asdf a = new Asdf("아아");
        a.setAsdf1(1223l);
        Bsdf b = testMapper.to(a);
        System.out.println(b.getAsdf1());
        System.out.println(b.getAsdf());
    }

    @Test
    public void enumTest(){
        for(TrdstOrderStatus val : TrdstOrderStatus.values()){
            System.out.println(val.toString());
        }
    }

    @Test
    public void builderTest(){
        TbOrderDetail tbOrderDetail = new TbOrderDetail();
        tbOrderDetail = tbOrderDetail.builder().orderId("aaaa").build();
        System.out.println(tbOrderDetail.getOrderId() + ", " + tbOrderDetail.getOrderSeq());
    }
}
