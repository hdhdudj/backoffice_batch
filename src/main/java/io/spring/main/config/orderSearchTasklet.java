package io.spring.main.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class orderSearchTasklet implements Tasklet {
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.debug(chunkContext.getStepContext().getStepExecution().getId() + " is run!");
        //이곳에 batch 로직 구현
        return RepeatStatus.FINISHED;
    }
}

