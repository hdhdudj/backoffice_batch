package io.spring.main.model.code;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeData { 
	int Sno;
	String GroupNm;
	  @JacksonXmlProperty(
		      isAttribute = true, localName = "idx")
	int Idx;
	String Text;
}
