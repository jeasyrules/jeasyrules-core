package org.jeasyrules.core.decisiontable.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jeasyrules.core.decisiontable.DecisionResult;
import org.jeasyrules.core.decisiontable.ValidationRule;
import org.jeasyrules.core.decisiontable.jdd.ExampleDecisionTableLoader;
import org.jeasyrules.core.decisiontable.jdd.ExampleVO;
import org.jeasyrules.core.decisiontable.jdd.Rule02;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test on CSV implementation of decision table.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class ExampleDecisionTableTest {
	private ExampleDecisionTableLoader dtLoader;
	private ExampleVO exampleVO;
	private ValidationRule<ExampleVO> rule02;

	/**
	 * Decision table loading.
	 * 
	 * @throws Exception
	 */
	@Before
	public void init() throws Exception {
		dtLoader = new ExampleDecisionTableLoader();

		exampleVO = new ExampleVO();
		rule02 = new Rule02();
		rule02.setId("Rule02");
		List<ValidationRule<ExampleVO>> lstRules = new ArrayList<ValidationRule<ExampleVO>>();
		lstRules.add(rule02);
		dtLoader.setValidationRules(lstRules);

		dtLoader.init();
	}

	/**
	 * Test with single predicate and single decision<br />
	 * P_USERCASE1;P_USERCASE2;P_COND;D_MSG;V_RULE<br />
	 * Only one expected decision : 1;;0;MSG02;Rule01
	 */
	@Test
	public void testWithSinglePredicateAndSingleDecision() {
		Map<String, String> predicates = new HashMap<String, String>();
		predicates.put("P_COND", "0");
		List<DecisionResult> decisions = dtLoader.getDecisionTable().getDecisions(predicates, exampleVO, null);
		assertNotNull(decisions);
		assertEquals(1, decisions.size());

		assertNotNull(decisions.get(0).getDecisions());
		assertEquals(1, decisions.get(0).getDecisions().size());
		assertEquals("MSG02", decisions.get(0).getDecisions().get("D_MSG"));

		assertNotNull(decisions.get(0).getValidationStatus());
		assertEquals(1, decisions.get(0).getValidationStatus().size());

		Set<Entry<String, Boolean>> entrySet = decisions.get(0).getValidationStatus().entrySet();
		List<Entry<String, Boolean>> entries = new ArrayList<Entry<String, Boolean>>(entrySet);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		assertEquals("Rule01", entries.get(0).getKey());
		assertEquals(false, entries.get(0).getValue());
	}

	/**
	 * Test with single predicate and multiple decisions<br />
	 * P_CREE_RUS;P_ASSOC;P_DATE_MEP;D_TYPE_NOTIF;V_RGM<br />
	 * 1;0;1;MSG01;Rule01<br />
	 * 1;0;1;MSG03;Rule02
	 */
	@Test
	public void testWithSinglePredicateAndMultipleDecisions() {
		Map<String, String> predicates = new HashMap<String, String>();
		predicates.put("P_COND", "1");
		Map<String, Object> ruleStorage = new HashMap<String, Object>();
		ruleStorage.put("RESULT", true);
		List<DecisionResult> decisions = dtLoader.getDecisionTable().getDecisions(predicates, exampleVO, ruleStorage);
		assertNotNull(decisions);
		assertEquals(2, decisions.size());

		assertNotNull(decisions.get(0).getDecisions());
		assertEquals(1, decisions.get(0).getDecisions().size());
		assertEquals("MSG01", decisions.get(0).getDecisions().get("D_MSG"));

		assertNotNull(decisions.get(0).getValidationStatus());
		assertEquals(1, decisions.get(0).getValidationStatus().size());

		Set<Entry<String, Boolean>> entrySet = decisions.get(0).getValidationStatus().entrySet();
		List<Entry<String, Boolean>> entries = new ArrayList<Entry<String, Boolean>>(entrySet);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		assertEquals("Rule01", entries.get(0).getKey());
		assertEquals(false, entries.get(0).getValue());

		assertNotNull(decisions.get(1).getDecisions());
		assertEquals(1, decisions.get(1).getDecisions().size());
		assertEquals("MSG03", decisions.get(1).getDecisions().get("D_MSG"));

		entrySet = decisions.get(1).getValidationStatus().entrySet();
		entries = new ArrayList<Entry<String, Boolean>>(entrySet);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		assertEquals("Rule02", entries.get(0).getKey());
		assertEquals(true, entries.get(0).getValue());
	}

	/**
	 * Test with multiple predicate and single decision<br />
	 * P_USERCASE1;P_USERCASE2;P_COND;D_MSG;V_RULE<br />
	 * Only one expected decision : 1;;0;MSG02;Rule01
	 */
	@Test
	public void testWithMultiplePredicateAndSingleDecision() {
		Map<String, String> predicates = new HashMap<String, String>();
		predicates.put("P_COND", "0");
		predicates.put("P_USERCASE2", "0");
		List<DecisionResult> decisions = dtLoader.getDecisionTable().getDecisions(predicates, exampleVO, null);
		assertNotNull(decisions);
		assertEquals(1, decisions.size());

		assertNotNull(decisions.get(0).getDecisions());
		assertEquals(1, decisions.get(0).getDecisions().size());
		assertEquals("MSG02", decisions.get(0).getDecisions().get("D_MSG"));

		assertNotNull(decisions.get(0).getValidationStatus());
		assertEquals(1, decisions.get(0).getValidationStatus().size());

		Set<Entry<String, Boolean>> entrySet = decisions.get(0).getValidationStatus().entrySet();
		List<Entry<String, Boolean>> entries = new ArrayList<Entry<String, Boolean>>(entrySet);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		assertEquals("Rule01", entries.get(0).getKey());
		assertEquals(false, entries.get(0).getValue());

		// Changing one predicate
		predicates.put("P_USERCASE2", "1");
		decisions = dtLoader.getDecisionTable().getDecisions(predicates, exampleVO, null);

		assertNotNull(decisions);
		assertEquals(1, decisions.size());

		assertNotNull(decisions.get(0).getDecisions());
		assertEquals(1, decisions.get(0).getDecisions().size());
		assertEquals("MSG02", decisions.get(0).getDecisions().get("D_MSG"));

		assertNotNull(decisions.get(0).getValidationStatus());
		assertEquals(1, decisions.get(0).getValidationStatus().size());

		entrySet = decisions.get(0).getValidationStatus().entrySet();
		entries = new ArrayList<Entry<String, Boolean>>(entrySet);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		assertEquals("Rule01", entries.get(0).getKey());
		assertEquals(false, entries.get(0).getValue());
	}
}
