package io.spring.main.apis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestBClass extends ParentClass{
    public TestBClass(){
        super.setAsdf4("b의 asdf4");
        super.setAsdf5("b의 asdf5");
    }
    private String asdf1;
    private String asdf2;
    private String asdf3;
}
