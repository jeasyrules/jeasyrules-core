package org.jeasyrules.core.decisiontable;

import static org.jeasyrules.core.decisiontable.DecisionConstants.V_FALSE;
import static org.jeasyrules.core.decisiontable.DecisionConstants.V_TRUE;

import java.util.HashMap;
import java.util.Map;

/**
 * Predicates class (fluent coding style).
 * 
 * @author idrissneumann
 *
 */
public class Predicates {
	private Map<String, String> mapPredicates;

	/**
	 * Private constructor : use the newInstance methode.
	 */
	private Predicates() {
		mapPredicates = new HashMap<>();
	}

	/**
	 * New instance.
	 * 
	 * @return Predicates
	 */
	public static Predicates newInstance() {
		return new Predicates();
	}

	/**
	 * Adding predicate.
	 * 
	 * @param key
	 * @param value
	 * @return Predicates
	 */
	public Predicates addPredicate(String key, String value) {
		mapPredicates.put(key, value);
		return this;
	}

	/**
	 * Adding true predicate.
	 * 
	 * @param keys
	 * @return Predicates
	 */
	public Predicates addTrue(String... keys) {
		if (null == keys || keys.length <= 0) {
			return this;
		}

		for (String key : keys) {
			addPredicate(key, V_TRUE);
		}
		return this;
	}

	/**
	 * Adding true predicate.
	 * 
	 * @param keys
	 * @return Predicates
	 */
	public Predicates addFalse(String... keys) {
		if (null == keys || keys.length <= 0) {
			return this;
		}

		for (String key : keys) {
			addPredicate(key, V_FALSE);
		}
		return this;
	}

	/**
	 * Return a map of predicates.
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> toMap() {
		return new HashMap<>(mapPredicates);
	}
}
