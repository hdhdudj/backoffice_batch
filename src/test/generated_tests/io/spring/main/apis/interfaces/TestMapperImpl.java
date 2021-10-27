package io.spring.main.apis.interfaces;

import io.spring.main.apis.model.TestAClass;
import io.spring.main.apis.model.TestBClass;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-25T01:11:21+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.2 (AdoptOpenJDK)"
)
@Component
public class TestMapperImpl implements TestMapper {

    @Override
    public TestBClass to(TestAClass a) {
        if ( a == null ) {
            return null;
        }

        TestBClass testBClass = new TestBClass();

        testBClass.setAsdf1( a.getAsdf1() );
        testBClass.setAsdf2( a.getAsdf2() );
        testBClass.setAsdf3( a.getAsdf3() );

        return testBClass;
    }
}
