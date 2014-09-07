package com.excelsiorsoft.integral.process.akka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.UntypedActor;

import com.excelsiorsoft.integral.domain.Dto;

public class Aggregator extends UntypedActor{
	private static final Log log = LogFactory.getLog(Aggregator.class);
	private double accumulator;
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Dto) {
			accumulator += ((Dto)message).getPoints();
			log.info("Accumulator currently: "+accumulator);
		} else if (message instanceof Result) {
			log.info("Result from Akka: "+accumulator);
		} else
			unhandled(message);
		
	}

}
