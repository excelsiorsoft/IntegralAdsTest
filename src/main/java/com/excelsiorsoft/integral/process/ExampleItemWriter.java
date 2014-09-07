package com.excelsiorsoft.integral.process;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.excelsiorsoft.integral.domain.Dto;
import com.excelsiorsoft.integral.process.akka.Master;

import static com.excelsiorsoft.integral.process.ExampleConfiguration.*;


/**
 *  Workhorse {@link ItemWriter} which does the final calculation.
 */
@Component("writer")
@Scope("step")
public class ExampleItemWriter implements ItemWriter<Dto> {

	private static final Log log = LogFactory.getLog(ExampleItemWriter.class);

	/*
	 * Akka initialization
	 */
	/*private ActorSystem _system;
	private ActorRef master; 
	
	@PostConstruct
	public void init(){
		_system = ActorSystem.create("Aggregator");
		
		master = _system.actorOf(new Props(Master.class),"master");
		log.info("Obtained master: "+master);
	}*/
	
	@Autowired()
	private Accumulator accumulator;
	
	@Autowired
    @Qualifier(/*ExampleConfiguration.*/MASTER_ACTOR)
    private ActorRef master;
	
	
	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	public void  write(List<? extends Dto> data) throws Exception {
			
		
		
		for (Dto item: data){

			log.info("processing item # "+item.getId());
			
			accumulator.accumulate(item.getPoints());

			master.tell(item);
		}

	}
	
	
	
	

}
