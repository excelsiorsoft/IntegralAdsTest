package com.excelsiorsoft.integral.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;



public class AggregatingStepListener implements StepExecutionListener  {
	
	private static final Log log = LogFactory.getLog(AggregatingStepListener.class); 
	
	@Autowired()
	private Accumulator accumulator;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("StepExecutionListener - beforeStep");
	}
 
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("StepExecutionListener - afterStep");
		log.info("Grand Total Points: "+accumulator.getState());
		
		return null;
	}
 
}