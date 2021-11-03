package io.spring.backoffice_batch.job;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.OrderSearch;
import io.spring.main.model.order.IfItem;
import io.spring.main.model.order.OrderSearchData;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class TestJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;
	private final OrderSearch orderSearch;

	private static final int chunkSize = 1000;

	@Bean
	public Job testJob() {
		

		
		return jobBuilderFactory.get("testJob12").start(testStep1())
				// .incrementer(new UniqueRunIdIncrementer())
				.incrementer(new UniqueRunIdIncrementer())
				.build();

	}

	/**
	 * step1에서 저장한 if 테이블에서 한 줄씩 가져와 trdst DB에 쪼개 넣는 step
	 */
	@Bean
	public Step testStep1() {
		log.info("----- This is testStep1");

		return stepBuilderFactory.get("testStep1").<OrderSearchData, IfItem>chunk(chunkSize)
				.reader(xmlReader())
				// .processor(testProcessor())
				.processor(compositeItemProcessor())
				// .writer(testWriter11())
				.writer(itemWriter33())
				.build();
	}

	/*
	 * @Bean public JpaCustomPagingItemReader testReader() {
	 * JpaCustomPagingItemReader<IfOrderMaster> jpaPagingItemReader = new
	 * JpaCustomPagingItemReader<IfOrderMaster>() {
	 * 
	 * @Override public int getPage() { return 0; }
	 * 
	 * 
	 * 
	 * }; jpaPagingItemReader.setName("testReader");
	 * jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
	 * jpaPagingItemReader.setPageSize(chunkSize); jpaPagingItemReader.
	 * setQueryString("SELECT i FROM IfOrderMaster i where i.ifStatus='01'"); return
	 * jpaPagingItemReader; }
	 * 
	 * 
	 * 
	 * @Bean public JpaItemWriter testWriter() { JpaItemWriter jpaItemWriter = new
	 * JpaItemWriter(); jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
	 * return jpaItemWriter; }
	 * 
	 * 
	 * 
	 * @Bean public TestWriter1 testWriter11() { return new TestWriter1(); }
	 * 
	 * @Bean public TestProcessor testProcessor() { return new TestProcessor(); }
	 * 
	 */

	@Bean
	public ItemProcessor<OrderSearchData, IfItem> compositeItemProcessor() {
		CompositeItemProcessor<OrderSearchData, IfItem> compositeItemProcessor = new CompositeItemProcessor<>();
		// compositeItemProcessor.setDelegates(Arrays.asList(itemProcessor1(),
		// itemProcessor2()));
		compositeItemProcessor.setDelegates(Arrays.asList(itemProcessor1()));
		return compositeItemProcessor;
	}

	@Bean
	    public ItemProcessor<OrderSearchData, IfItem> itemProcessor1() {
	      
			return orderSearchData -> {
			  
				// orderSearch

				IfOrderMaster iom = orderSearch.getIfOrderMaster(orderSearchData);

				List<IfOrderDetail> iods = orderSearch.getIfOrderDetail(orderSearchData);

			  IfItem o = new IfItem();
			  

			  
				o.setIom(iom);
				o.setIods(iods);
				o.setOrderSearchData(orderSearchData);

				System.out.println("itemProcessor1");
				System.out.println(o);

			  return o;

			};
		  
		  
		  
	    }

		@Bean
		public JpaCustomItemWriter1 itemWriter33() {

			JpaCustomItemWriter1 jp = new JpaCustomItemWriter1(entityManagerFactory);

			// JpaItemWriter jpaItemWriter = new JpaItemWriter();
			jp.setEntityManagerFactory(entityManagerFactory);
			jp.setUsePersist(true);
			return jp;
		}
		/*
		 * @Bean public ItemProcessor<IfItem, IfItem> itemProcessor2() { return
		 * orderSearchData -> {
		 * 
		 * // IfItem o = new IfItem();
		 * 
		 * List<IfOrderDetail> iods = new ArrayList<IfOrderDetail>();
		 * 
		 * // IfOrderMaster iom = new IfOrderMaster(orderSearchData); IfOrderDetail iod
		 * = new IfOrderDetail("XX11");
		 * 
		 * iods.add(iod);
		 * 
		 * System.out.println("itemProcessor2");
		 * 
		 * 
		 * // iom.setChannelOrderNo(orderSearchData.getOrderNo().toString());
		 * 
		 * // o.setIom(iom); orderSearchData.setIods(iods);
		 * System.out.println(orderSearchData); return orderSearchData;
		 * 
		 * }; }
		 * 
		 */

	// private class TestWriter1 implements ItemWriter<HashMap<String, Object>> {
	private class TestWriter1 implements ItemWriter<IfItem> {

		@Override
		public void write(List<? extends IfItem> items) throws Exception {
			// TODO Auto-generated method stub


			System.out.println("----------------Start-------------------------");
			// for (HashMap<String, Object> item : items) {
			for (IfItem item : items) {

				System.out.println(item.getIom().getChannelOrderNo());
				System.out.println(item.getIods().get(0).getIfNo());
			}
			System.out.println("----------------end-------------------------");
		}

	}
/*
	private class TestProcessor implements ItemProcessor<OrderSearchData, HashMap<String, Object>> {

		@Override
		public HashMap<String, Object> process(OrderSearchData item) throws Exception {
			// TODO Auto-generated method stub

			System.out.println("process");

			HashMap<String, Object> m = new HashMap<String, Object>();

			System.out.println(item.getOrderNo());

			m.put("orderNo", item.getOrderNo());

			return m;
		}

	}
*/
	@Bean
	public ListItemReader<OrderSearchData> xmlReader() {

		
		List<OrderSearchData> orderSearchDataList = orderSearch.retrieveOrders("", "2021-11-01", "2021-11-03", "order");

		// List<HashMap<String, Object>> l= new ArrayList<HashMap<String, Object>>();

		// System.out.println(orderSearchDataList);

		// return new TestReader(orderSearchDataList);
		return new ListItemReader<OrderSearchData>(orderSearchDataList);
	}
	/*
	 * private class TestReader implements ItemReader<OrderSearchData> {
	 * 
	 * private List<OrderSearchData> list; private int nextIndex;
	 * 
	 * public TestReader(List<OrderSearchData> l) { this.list = l; nextIndex = 0;
	 * 
	 * }
	 * 
	 * @Override public OrderSearchData read() throws Exception,
	 * UnexpectedInputException, ParseException, NonTransientResourceException { //
	 * TODO Auto-generated method stub
	 * 
	 * OrderSearchData nextObj = null;
	 * 
	 * System.out.println("read ");
	 * 
	 * System.out.println(list);
	 * 
	 * if (nextIndex < list.size()) { nextObj = list.get(nextIndex); nextIndex++; }
	 * else { nextIndex = 0; }
	 * 
	 * return nextObj;
	 * 
	 * }
	 * 
	 * }
	 */
}
