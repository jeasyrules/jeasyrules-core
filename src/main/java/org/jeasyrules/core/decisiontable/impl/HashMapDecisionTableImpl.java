package org.jeasyrules.core.decisiontable.impl;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.jeasyrules.core.decisiontable.DecisionConstants.OPERATOR_OR;
import static org.jeasyrules.core.decisiontable.DecisionConstants.PREFIX_DECISION;
import static org.jeasyrules.core.decisiontable.DecisionConstants.PREFIX_VALIDATION;
import static org.jeasyrules.core.decisiontable.DecisionConstants.V_FALSE;
import static org.jeasyrules.core.decisiontable.DecisionConstants.V_TRUE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.jeasyrules.core.decisiontable.DecisionConstants;
import org.jeasyrules.core.decisiontable.DecisionResult;
import org.jeasyrules.core.decisiontable.DecisionTable;
import org.jeasyrules.core.decisiontable.Predicates;
import org.jeasyrules.core.decisiontable.ValidationRule;

/**
 * HashMap implementation of decision table.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class HashMapDecisionTableImpl<T extends Serializable> implements DecisionTable<T> {
	private List<String> headers;
	private List<ValidationRule<T>> validationRules;
	private List<Map<String, String>> rows;
	private String validationOperator;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DecisionResult> getDecisions(Predicates predicates, T valueObject, Map<String, Object> ruleStorage) {
		return getDecisions(predicates.toMap(), valueObject, ruleStorage);
	}

	/**
	 * Getting decisions from a predicate's list.
	 * 
	 * @param predicates
	 * @param T
	 *            valueObject
	 * @param ruleStorage
	 * @return Map<String, String>
	 */
	private List<DecisionResult> getDecisions(Map<String, String> predicates, T valueObject,
			Map<String, Object> ruleStorage) {
		List<Map<String, String>> decisions = new ArrayList<>();
		if (isNotEmpty(rows) && isNotEmpty(predicates)) {
			for (Map<String, String> row : rows) {
				Boolean select = true;

				for (String kQuery : predicates.keySet()) {
					if (isRowNotSelectable(row, predicates, kQuery)) {
						select = false;
						break;
					}
				}

				// Getting the decision
				if (select) {
					Map<String, String> decision = new HashMap<>();
					for (String kRow : row.keySet()) {
						if (!kRow.toUpperCase().startsWith(DecisionConstants.PREFIX_PREDICATE)) {
							decision.put(kRow, row.get(kRow));
						}
					}

					decisions.add(decision);
				}
			}
		}

		return transformDecisionResult(decisions, valueObject, ruleStorage);
	}

	/**
	 * Checking if the row is not selectable.
	 * 
	 * @param row
	 * @param predicates
	 * @param kQuery
	 * @return boolean
	 */
	private boolean isRowNotSelectable(Map<String, String> row, Map<String, String> predicates, String kQuery) {
		return row.containsKey(kQuery) //
				&& isNotEmpty(row.get(kQuery)) //
				&& isNotEmpty(predicates.get(kQuery)) //
				&& (V_TRUE.equals(row.get(kQuery).trim()) || V_FALSE.equals(row.get(kQuery).trim())) //
				&& (V_TRUE.equals(predicates.get(kQuery).trim()) || V_FALSE.equals(predicates.get(kQuery).trim())) //
				&& !predicates.get(kQuery).trim().equalsIgnoreCase(row.get(kQuery).trim());
	}

	/**
	 * Getting validation rule from id.
	 * 
	 * @param id
	 * @return ValidationRule
	 */
	@SuppressWarnings("unchecked")
	private ValidationRule<T> getValidationRulesFromId(String id) {
		if (isEmpty(validationRules)) {
			return null;
		}

		return (ValidationRule<T>) CollectionUtils.find(validationRules, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ValidationRule<T> v = (ValidationRule<T>) object;
				return id.equalsIgnoreCase(v.getId());
			}
		});
	}

	/**
	 * Building the return stream.
	 * 
	 * @param decisions
	 * @param valueObject
	 * @param ruleStorage
	 * @return List<DecisionResult>
	 */
	private List<DecisionResult> transformDecisionResult(List<Map<String, String>> decisions, T valueObject,
			Map<String, Object> ruleStorage) {
		if (isEmpty(decisions)) {
			return null;
		}

		if (null == ruleStorage) {
			ruleStorage = new HashMap<>();
		}

		List<DecisionResult> rtn = new ArrayList<>();

		for (Map<String, String> item : decisions) {
			DecisionResult result = DecisionResult.newInstance()
					.status(OPERATOR_OR.equalsIgnoreCase(validationOperator));

			// Step 1 : decisions
			for (String key : item.keySet()) {
				if (key.toUpperCase().startsWith(PREFIX_DECISION)) {
					result.decision(key, item.get(key));
				}

				// We also add all of decision table columns value in the
				// ruleStorage
				ruleStorage.put(key, item.get(key));
			}

			// Step 2 : validation rules
			for (String key : item.keySet()) {
				if (key.toUpperCase().startsWith(PREFIX_VALIDATION)) {
					if (isEmpty(item.get(key))) {
						continue;
					}

					ValidationRule<T> rule = getValidationRulesFromId(item.get(key));
					if (null == rule) {
						result.validationStatus(item.get(key), false);
					} else {
						Boolean status = rule.validate(valueObject, ruleStorage);
						result.validationStatus(item.get(key), status).status(result.getStatus() || status);
					}
				}
			}

			rtn.add(result);
		}

		return rtn;
	}

	/**
	 * @return the headers
	 */
	@Override
	public List<String> getHeaders() {
		return headers;
	}

	/**
	 * @return the validationRules
	 */
	@Override
	public List<ValidationRule<T>> getValidationRules() {
		return validationRules;
	}

	/**
	 * @param validationRules
	 *            the validationRules to set
	 */
	@Override
	public void setValidationRules(List<ValidationRule<T>> validationRules) {
		this.validationRules = validationRules;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	/**
	 * @return the rows
	 */
	public List<Map<String, String>> getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(List<Map<String, String>> rows) {
		this.rows = rows;
	}

	/**
	 * @return the validationOperator
	 */
	public String getValidationOperator() {
		return validationOperator;
	}

	/**
	 * @param validationOperator
	 *            the validationOperator to set
	 */
	public void setValidationOperator(String validationOperator) {
		this.validationOperator = validationOperator;
	}
}
