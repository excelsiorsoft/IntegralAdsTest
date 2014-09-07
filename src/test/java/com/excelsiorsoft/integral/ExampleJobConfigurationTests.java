package com.excelsiorsoft.integral;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.excelsiorsoft.integral.process.akka.Master;
import com.excelsiorsoft.integral.process.akka.Result;

@ContextConfiguration(locations={"/launch-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleJobConfigurationTests {
	private static final Log log = LogFactory.getLog(ExampleJobConfigurationTests.class);

	/*
	 * Akka initialization
	 */
	private static ActorSystem _system;
	private static ActorRef master; 
	
	@BeforeClass
	public static void init(){
		_system = ActorSystem.create("Aggregator");
		
		master = _system.actorOf(new Props(Master.class),"master");
		log.info("Obtained master: "+master);
	}
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Test
	public void testSimpleProperties() throws Exception {
		assertNotNull(jobLauncher);
	}
	
	@Test
	public void testLaunchJob() throws Exception {
		jobLauncher.run(job, new JobParametersBuilder().addString("fileName", "data.csv").addString("fileFormat", "csv").toJobParameters());
		//jobLauncher.run(job, new JobParametersBuilder().addString("fileName", "data.tab").addString("fileFormat", "tab").toJobParameters());
		
		Thread.sleep(500);
		
		master.tell(new Result());
		
		Thread.sleep(500);
		
		_system.shutdown();
	}
	
}
