package io.spring.main.model.goods.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "itaimg")
public class Itaimg extends CommonProps {
    @Id
    private Long imageSeq;
    private String imageGb;
    private String imageName;
    private String imaOriginalName;
    private String imagePath;
    private String imageStatus;
    private String imageSize;
    private String imageType;
    private String assortId;
}
