package org.jeasyrules.core.decisiontable;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Interface wich aims is to implement a way to load a decision table from a
 * file (like reading CSV file for example).
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T>
 */
public interface DecisionTableLoaderFromFile<T extends Serializable> extends DecisionTableLoader<T> {
	/**
	 * Loading the decision table from file.
	 * 
	 * @param InputStream
	 *            .
	 * @throws Exception
	 */
	void load(InputStream is) throws Exception;
}
