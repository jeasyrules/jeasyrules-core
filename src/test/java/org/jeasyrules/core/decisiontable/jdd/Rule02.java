package org.jeasyrules.core.decisiontable.jdd;

import java.util.Map;

import org.jeasyrules.core.decisiontable.impl.AbstractValidationRule;

/**
 * Validation rule implementation example.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class Rule02 extends AbstractValidationRule<ExampleVO> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean validate(ExampleVO object, Map<String, Object> ruleStorage) {
		return ruleStorage.containsKey("RESULT");
	}
}
