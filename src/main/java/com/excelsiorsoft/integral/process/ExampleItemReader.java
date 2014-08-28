package com.excelsiorsoft.integral.process;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

/**
 * {@link ItemReader} with hard-coded input data.
 */

//@Component("cvsFileItemReader")
@Scope("step")
@Deprecated
public class ExampleItemReader implements ItemReader<String> {
	
	private String[] input = {"Hello world!", "Good bye, world!"};
	
	private int index = 0;
	
	private String fileName;
	
	@Value("#{jobParameters['fileName']}")
	  public void setFileName(final String name) {
		fileName = name;
	  }
	
	/**
	 * Reads next record from input
	 */
	public String read() throws Exception {
		
		
		
		if (index < input.length) {
			return input[index++];
		}
		else {
			return null;
		}
		
	}

}
