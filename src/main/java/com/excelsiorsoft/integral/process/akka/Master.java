package com.excelsiorsoft.integral.process.akka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.excelsiorsoft.integral.domain.Dto;


public class Master extends UntypedActor {

	private static final Log log = LogFactory.getLog(Master.class);
	
	private ActorRef aggregator = getContext().actorOf(
			new Props(Aggregator.class), "aggregator");
	
	@Override
	public void onReceive(Object message) throws Exception {
		log.debug("Handling message "+message+" in master");
		if (message instanceof Dto || message instanceof Result) {
			aggregator.tell(message);
		} else
			unhandled(message);
	}

	
}
