package io.spring.backoffice_batch.job;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import io.spring.main.apis.GoodsSearch;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * searchGoodsJob 필수 parameter :
 * --job.name=searchGoodsJob (프로젝트 내 어떤 잡인지 알려주는 파라미터)
 * version=0 (스프링 배치에서 요구하는 필수 파라미터)
 * -dateParam=3 (며칠치 데이터를 긁어올지 필수. 단, goodsNo가 있으면 없어도 됨.)
 *
 * 필수는 아닌 option parameter :
 * -page=3 (고도몰에서 데이터를 페이지로 제공해줌. maxPage가 몇인지 보면 몇 페이지까지 제공되는지 알 수 있음.)
 * -goodsNo=1000063938 (고도몰의 상품 번호. 이거 있으면 dateParam 없어도 됨.)
 * -searchDateType=modDt (상품 수정한 것이 최근에 나오는 모드. 없으면 그냥 최근 등록대로 나옴.)
 * --spring.config.location=/var/jenkins_home/jar/order_search_config/application.properties,/var/jenkins_home/jar/order_search_config/godourl.yml (외부의 설정파일을 참조할 때)
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class GoodsSearchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final GoodsSearch goodsSearch;
	private static final int chunkSize = 1000;
	private static final int pageSize = 1000;
    private int cnt = 0;

    @Bean
    public Job searchGoodsJob(){
        return jobBuilderFactory.get("searchGoodsJob")
                .start(searchGoodsStep1(null, null, null, null))
                .next(searchGoodsStep2())
                .next(searchGoodsStep3())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step searchGoodsStep1(@Value("#{jobParameters[page]}") String page, @Value("#{jobParameters[goodsNo]}") String goodsNo,
                                 @Value("#{jobParameters[searchDateType]}") String searchDateType,
                                 @Value("#{jobParameters[dateParam]}")String dateParam){
        return stepBuilderFactory.get("searchGoodsStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchGoodsStep1");
                    // if table entity 리스트 생성
                    // 트랜잭션1. if table 저장 함수

//					goodsNo = "1000003553";

					goodsSearch.saveIfTables(goodsNo, searchDateType, dateParam, page); // , ifGoodsOptionList, ifGoodsTextOptionList,
																			// ifGoodsAddGoodsList);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step searchGoodsStep2(){
        log.info("----- This is searchGoodsStep2");
        return stepBuilderFactory.get("searchGoodsStep2")
                .<IfGoodsMaster, String>chunk(chunkSize)
                .reader(jpaGoodsSearchItemWriterReader(null))
                .processor(jpaGoodsSearchItemProcessor())
                .writer(jpaGoodsSearchItemWriter())
                .build();
    }

	@Bean
    @JobScope
	public Step searchGoodsStep4() {
		log.info("----- This is searchGoodsStep4");
		return stepBuilderFactory.get("searchGoodsStep4").<IfGoodsMaster, String>chunk(chunkSize)
				.reader(jpaGoodsSearchItemWriterReader(null))
                .processor(jpaGoodsSearchItemProcessor())
				.writer(jpaGoodsSearchItemWriter()).build();
	}

    @Bean
    @StepScope
    public JpaPagingItemReader jpaGoodsSearchItemWriterReader(@Value("#{jobParameters[goodsNo]}") String goodsNo) {
        log.debug("실행됨.");
        JpaPagingItemReader<IfGoodsMaster> jpaPagingItemReader = new JpaPagingItemReader<IfGoodsMaster>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        jpaPagingItemReader.setName("jpaGoodsSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(pageSize);
        jpaPagingItemReader.setQueryString("SELECT i FROM IfGoodsMaster i where i.uploadStatus='01' and ( :goodsNo is null or i.goodsNo=:goodsNo) order by i.goodsNo asc");
        Map<String, Object> map = new HashMap<>();
        map.put("goodsNo", goodsNo);
        jpaPagingItemReader.setParameterValues(map);
        return jpaPagingItemReader;
    }

    @Bean
    public ItemProcessor<IfGoodsMaster, IfGoodsMaster> jpaGoodsSearchItemProcessor() {
//        System.out.println("----- cnt : " + cnt);
//        cnt++;
        System.out.println("sdlkfjsdljfsdjkf");
        return ifGoodsMaster -> goodsSearch.saveOneGoodsNo(ifGoodsMaster.getGoodsNo(),ifGoodsMaster);
    }

    @Bean
    public JpaItemWriter<IfGoodsMaster> jpaGoodsSearchItemWriter() {
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    @JobScope
    public Step searchGoodsStep3(){
        return stepBuilderFactory.get("searchGoodsStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchGoodsStep3");
					goodsSearch.insertTms1(); // tmitem, tmmapi 삽입 query 실행
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
//    @Bean
//    public Step searchGoodsStep2(){
//        return stepBuilderFactory.get("searchGoodsStep2")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("----- This is searchGoodsStep2");
////                    goodsSearch.searchGoodsSeq("1999-01-01","2022-01-01");
//                    List<IfGoodsMaster> ifGoodsMasterList = goodsSearch.getIfGoodsMasterListWhereUploadStatus01(); // if_goods_master
//                    // 트랜잭션2. if table의 한 줄을 자체 table에 저장할 때 goodsNo 하나 기준으로 어떤 if table에서 실패하면 주루룩 실패해야 함.
//                    for(IfGoodsMaster ifGoodsMaster : ifGoodsMasterList){
//                        goodsSearch.saveOneGoodsNo(ifGoodsMaster.getGoodsNo(), ifGoodsMaster);
//                    }
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
}
