package org.jeasyrules.core.decisiontable.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

import org.jeasyrules.core.decisiontable.ValidationRule;

/**
 * Validation rule abstract implementation.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T>
 */
public abstract class AbstractValidationRule<T extends Serializable> implements ValidationRule<T> {
	String id;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Checking if a flag is enabled in the ruleStorage.
	 * 
	 * @param flag
	 * @param ruleStorage
	 * @return boolean
	 */
	protected boolean isFlagEnabled(String flag, Map<String, Object> ruleStorage) {
		return ruleStorage.containsKey(flag) //
		        && null != ruleStorage.get(flag) //
		        && ruleStorage.get(flag) instanceof Boolean //
		        && (Boolean) ruleStorage.get(flag);
	}

	/**
	 * Extract a Calendar from the ruleStorage
	 * 
	 * @param ruleStorage
	 * @param key
	 * @return extractDate
	 */
	protected Calendar extractDate(Map<String, Object> ruleStorage, String key) {
		Calendar date = null;
		if (ruleStorage.containsKey(key)) {
			date = (Calendar) ruleStorage.get(key);
		}

		return date;
	}
}
