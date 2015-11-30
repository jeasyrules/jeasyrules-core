package org.jeasyrules.core.decisiontable;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface pour les règles de validations complémentaires.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T>
 *            value object type.
 */
public interface ValidationRule<T extends Serializable> {
	/**
	 * Checking from value object and ruleStorage.
	 * 
	 * @param object
	 * @param ruleStorage
	 * @return Boolean
	 */
	Boolean validate(T object, Map<String, Object> ruleStorage);

	/**
	 * Getting the id of rule.
	 * 
	 * @return String.
	 */
	String getId();

	/**
	 * Setting the id of rule.
	 * 
	 * @param id
	 */
	void setId(String id);
}
