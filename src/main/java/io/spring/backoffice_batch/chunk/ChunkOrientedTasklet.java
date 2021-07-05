package io.spring.backoffice_batch.chunk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.item.Chunk;
import org.springframework.batch.core.step.item.ChunkProcessor;
import org.springframework.batch.core.step.item.ChunkProvider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
@RequiredArgsConstructor
public class ChunkOrientedTasklet<T> implements Tasklet {
    private static final String INPUTS_KEY = "INPUTS";
    private final ChunkProcessor<T> chunkProcessor;
    private final ChunkProvider<T> chunkProvider;
    private boolean buffering = true;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception{
        Chunk<T> inputs = (Chunk<T>) chunkContext.getAttribute(INPUTS_KEY);
        if(inputs == null){
            inputs = chunkProvider.provide(contribution);
            if(buffering){
                chunkContext.setAttribute(INPUTS_KEY, inputs);
            }
        }
        chunkProcessor.process(contribution,inputs);
        chunkProvider.postProcess(contribution,inputs);
        if(inputs.isBusy()){
            log.debug("Inputs still busy.");
            return RepeatStatus.CONTINUABLE;
        }
        chunkContext.removeAttribute(INPUTS_KEY);
        chunkContext.setComplete();

        if(log.isDebugEnabled()){
            log.debug("Inputs not busy, ended : " + inputs.isEnd());
        }
        return RepeatStatus.continueIf(!inputs.isEnd());
    }
}
