package io.spring.main.model.goods.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "xml_test")
@Entity
@NoArgsConstructor
public class XmlTest {
    public XmlTest(String assortId){
        this.assortId = assortId;
    }
    @Id
    private String assortId;
    private String xml;
}
