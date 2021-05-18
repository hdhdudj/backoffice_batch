package io.spring.main.model.code;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Return { 

	  @JacksonXmlProperty(
		      localName = "code_data")	
	List<CodeData> CodeData;
}
