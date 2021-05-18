package io.spring.main.model.goods;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsMustInfoData {
    private List<StepData> stepDataListList;

    @Getter
    @Setter
    public static class StepData{
        private String infoTitle;
        private String infoValue;
    }
}
