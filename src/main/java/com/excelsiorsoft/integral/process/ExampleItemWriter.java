package com.excelsiorsoft.integral.process;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excelsiorsoft.integral.domain.Dto;


/**
 *  Workhorse {@link ItemWriter} which does the final calculation.
 */
@Component("writer")
@Scope("step")
public class ExampleItemWriter implements ItemWriter<Dto> {

	private static final Log log = LogFactory.getLog(ExampleItemWriter.class);

	@Autowired()
	private Accumulator accumulator;
	
	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	public void  write(List<? extends Dto> data) throws Exception {
			
		for (Dto item: data){

			log.info("processing item # "+item.getId());

			accumulator.accumulate(item.getPoints());

		}

	}
	
	
	
	

}
