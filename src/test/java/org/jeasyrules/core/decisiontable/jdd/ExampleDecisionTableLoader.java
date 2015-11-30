package org.jeasyrules.core.decisiontable.jdd;

import java.io.InputStream;

import org.jeasyrules.core.decisiontable.impl.AbstractCSVDecisionLoader;

/**
 * Example of CSV decision table implementation.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class ExampleDecisionTableLoader extends AbstractCSVDecisionLoader<ExampleVO> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws Exception {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("example.csv");
		this.load(in);
	}
}
