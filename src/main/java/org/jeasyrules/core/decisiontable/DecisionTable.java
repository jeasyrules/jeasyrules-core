package org.jeasyrules.core.decisiontable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Decision table interface.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T>
 *            valueObject's type
 *
 */
public interface DecisionTable<T extends Serializable> {
	/**
	 * Getting decisions from a predicate's list.
	 * 
	 * @param predicates
	 * @param T
	 *            valueObject
	 * @param ruleStorage
	 * @return Map<String, String>
	 */
	public List<DecisionResult> getDecisions(Map<String, String> predicates, T valueObject, Map<String, Object> ruleStorage);

	/**
	 * Getting headers of decision table.
	 * 
	 * @return List<String>
	 */
	List<String> getHeaders();

	/**
	 * Getting all validation rules.
	 * 
	 * @return ValidationRule<T>
	 */
	List<ValidationRule<T>> getValidationRules();

	/**
	 * adding a list of validation rules.
	 * 
	 * @param validationRules
	 */
	void setValidationRules(List<ValidationRule<T>> validationRules);
}
