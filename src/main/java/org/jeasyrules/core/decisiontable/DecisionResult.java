package org.jeasyrules.core.decisiontable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Object representation of decision result.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class DecisionResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean status;

	private Map<String, String> decisions;

	private Map<String, Boolean> validationStatus;

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the decisions
	 */
	public Map<String, String> getDecisions() {
		return decisions;
	}

	/**
	 * @param decisions
	 *            the decisions to set
	 */
	public void setDecisions(Map<String, String> decisions) {
		this.decisions = decisions;
	}

	/**
	 * @return the validationStatus
	 */
	public Map<String, Boolean> getValidationStatus() {
		return validationStatus;
	}

	/**
	 * @param validationStatus
	 *            the validationStatus to set
	 */
	public void setValidationStatus(Map<String, Boolean> validationStatus) {
		this.validationStatus = validationStatus;
	}

	/**
	 * private constructore => use newInstance methods.
	 */
	private DecisionResult() {

	}

	/**
	 * @return new instance of DecisionResult.
	 */
	public static DecisionResult newInstance() {
		return new DecisionResult();
	}

	/**
	 * Setting the status with fluent coding style.
	 * 
	 * @param status
	 *            the status to set
	 * @return the current instance of DecisionResult
	 */
	public DecisionResult status(Boolean status) {
		setStatus(status);
		return this;
	}

	/**
	 * Setting the decisions with fluent coding style.
	 * 
	 * @param decisions
	 *            the status to set
	 * @return the current instance of DecisionResult
	 */
	public DecisionResult decisions(Map<String, String> decisions) {
		setDecisions(decisions);
		return this;
	}

	/**
	 * Setting the validationStatus with fluent coding style.
	 * 
	 * @param validationStatus
	 *            the status to set
	 * @return the current instance of DecisionResult
	 */
	public DecisionResult validationStatus(Map<String, Boolean> validationStatus) {
		setValidationStatus(validationStatus);
		return this;
	}

	/**
	 * Pushing a decision with fluent coding style.
	 * 
	 * @param key
	 * @param value
	 * @return the current instance of DecisionResult
	 */
	public DecisionResult decision(String key, String value) {
		if (null == decisions) {
			decisions = new HashMap<>();
		}

		decisions.put(key, value);
		return this;
	}

	/**
	 * Pushing a validation status with fluent coding style.
	 * 
	 * @param key
	 * @param value
	 * @return the current instance of DecisionResult
	 */
	public DecisionResult validationStatus(String key, Boolean value) {
		if (null == validationStatus) {
			validationStatus = new HashMap<>();
		}

		validationStatus.put(key, value);
		return this;
	}
}
