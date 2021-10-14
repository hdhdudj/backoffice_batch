package io.spring.main.apis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestAClass extends ParentClass {
    public TestAClass(){
        this.asdf1 = "1번";
        this.asdf2 = "2번";
        this.asdf3 = "3번";
        super.setAsdf4("a의 asdf4");
        super.setAsdf5("a의 asdf5");
    }
    private String asdf1;
    private String asdf2;
    private String asdf3;
}
