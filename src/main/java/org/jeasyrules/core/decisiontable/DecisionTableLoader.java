package org.jeasyrules.core.decisiontable;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Interface wich aims is to implement a way to load a decision table (like
 * reading CSV file or a table in a database for example).
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface DecisionTableLoader<T extends Serializable> {
	/**
	 * Getting loaded decision table.
	 * 
	 * @return
	 */
	DecisionTable<T> getDecisionTable();

	/**
	 * Initialization of decision table.
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	void init() throws Exception;

	/**
	 * Getting all validation rules.
	 * 
	 * @return ValidationRule<T>
	 */
	List<ValidationRule<T>> getValidationRules();

	/**
	 * Adding validation rules.
	 * 
	 * @param validationRules
	 */
	void setValidationRules(List<ValidationRule<T>> validationRules);
}
