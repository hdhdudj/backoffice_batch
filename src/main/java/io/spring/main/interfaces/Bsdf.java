package io.spring.main.interfaces;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bsdf {
    public Bsdf(String asdf){
        this.asdf = asdf;
    }
    private String asdf;
    private String asdf1;
}
