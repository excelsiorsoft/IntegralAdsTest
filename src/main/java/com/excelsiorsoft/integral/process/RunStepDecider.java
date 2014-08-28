package com.excelsiorsoft.integral.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component("decider")
public class RunStepDecider implements JobExecutionDecider {
	  
	private static final Log log = LogFactory.getLog(RunStepDecider.class);
	
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
	    final String format = jobExecution.getJobInstance().getJobParameters().getString("fileFormat");
		
	    FlowExecutionStatus result = null;
	    
		if ("csv".equalsIgnoreCase(format)) {
			result = new FlowExecutionStatus("csv");
		} else if ("tab".equalsIgnoreCase(format)) {
			result = new FlowExecutionStatus("tab");
		}
	   
	   return result;
	  }
	}
