package org.jeasyrules.core.decisiontable;

import java.io.Serializable;
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
}
