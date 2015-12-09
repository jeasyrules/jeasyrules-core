package org.jeasyrules.core.decisiontable;

/**
 * Constants.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface DecisionConstants {
	/**
	 * Types of columns
	 */
	String PREFIX_PREDICATE = "P_";
	String PREFIX_DECISION = "D_";
	String PREFIX_VALIDATION = "V_";

	/**
	 * Operators
	 */
	String OPERATOR_OR = "or";
	String OPERATOR_AND = "and";

	/**
	 * String type of value
	 */
	String V_TRUE = "1";
	String V_FALSE = "0";
}
