package com.excelsiorsoft.integral.process;

public class Accumulator {
	
	private int state = 0;
	
	public synchronized int getState() {
		return state;
	}

	public synchronized void accumulate(int current){
		state += current;
	}

}
