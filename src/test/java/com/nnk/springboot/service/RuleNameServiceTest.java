package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.RuleName;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class RuleNameServiceTest {
	@Autowired
	private RuleNameService ruleNameServiceUnderTest;

	private RuleName ruleName;

	@BeforeEach
	public void init() {
		ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setName("RuleName Test");
		ruleName.setJson("Json test");
		ruleName.setSqlPart("sql part test");
		ruleName.setSqlStr("sql str test");
		ruleName.setTemplate("template test");
		ruleName.setDescription("description test");
		ruleNameServiceUnderTest.addRuleName(ruleName);
	}

	@Test
	void testAddRuleName() throws Exception {
		try {
			RuleName ruleName = new RuleName();
			ruleName.setId(2);
			ruleName.setName("RuleName");
			ruleName.setJson("Json");
			ruleName.setSqlPart("sql part");
			ruleName.setSqlStr("sql str");
			ruleName.setTemplate("template");
			ruleName.setDescription("description");

			RuleName result = ruleNameServiceUnderTest.addRuleName(ruleName);
			assertAll("assertion data rulename created", () -> {
				assertNotNull(result.getId());
				assertEquals("RuleName", result.getName());
				assertEquals("Json", result.getJson());
				assertEquals("sql part", result.getSqlPart());
				assertEquals("sql str", result.getSqlStr());
				assertEquals("template", result.getTemplate());
				assertEquals("description", result.getDescription());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddRuleName_WithInvalidData() throws Exception {
		try {
			ruleName.setName("574857");
			ruleNameServiceUnderTest.addRuleName(ruleName);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> ruleNameServiceUnderTest.addRuleName(ruleName));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddRuleName_WithEmptyData() throws Exception {
		try {
			ruleName.setJson("");
			RuleName result = ruleNameServiceUnderTest.addRuleName(ruleName);

			assertNull(result.getId());
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> ruleNameServiceUnderTest.addRuleName(ruleName));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> ruleNameServiceUnderTest.addRuleName(ruleName));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRuleName() throws Exception {
		try {
			ruleName.setName("name updated");
			RuleName RuleNameToUpdateTest = ruleNameServiceUnderTest.addRuleName(ruleName);

			RuleName result = ruleNameServiceUnderTest.updateRuleNameById(RuleNameToUpdateTest.getId(), ruleName);
			assertAll("assertion data ruleName created", () -> {
				assertNotNull(result.getId());
				assertEquals("Json test", result.getJson());
				assertEquals("sql part test", result.getSqlPart());
				assertEquals("sql str test", result.getSqlStr());
				assertEquals("name updated", result.getName());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRuleName_WithRuleNameNotFound() throws Exception {
		try {
			RuleName result = ruleNameServiceUnderTest.updateRuleNameById(15, ruleName);

			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class,
					() -> ruleNameServiceUnderTest.updateRuleNameById(15, ruleName));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ruleNameServiceUnderTest.updateRuleNameById(15, ruleName));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> ruleNameServiceUnderTest.updateRuleNameById(15, ruleName));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllRuleNames() throws Exception {
		try {
			ruleNameServiceUnderTest.addRuleName(ruleName);

			List<RuleName> result = ruleNameServiceUnderTest.getAllRuleNames();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllRuleNames_WithListOfRuleNamesNotFound() throws Exception {
		try {
			ruleNameServiceUnderTest.deleteAllRuleNames();

			List<RuleName> result = ruleNameServiceUnderTest.getAllRuleNames();
			assertTrue(result.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ruleNameServiceUnderTest.getAllRuleNames());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetRuleNameById() throws Exception {
		try {
			RuleName result = ruleNameServiceUnderTest.addRuleName(ruleName);
			result = ruleNameServiceUnderTest.getRuleNameById(result.getId());
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void tesGetRuleNameById_WithRuleNameNotFound() throws Exception {
		try {
			ruleNameServiceUnderTest.deleteAllRuleNames();

			RuleName result = ruleNameServiceUnderTest.getRuleNameById(1);
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteRuleNameById() throws Exception {
		try {
			RuleName ruleNameCreated = ruleNameServiceUnderTest.addRuleName(ruleName);
			ruleNameServiceUnderTest.deleteRuleNameById(ruleNameCreated.getId());

			RuleName result = ruleNameServiceUnderTest.getRuleNameById(ruleNameCreated.getId());
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteRuleNameById_WithNoExistingRuleName() throws Exception {
		try {
			ruleNameServiceUnderTest.deleteRuleNameById(15);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ruleNameServiceUnderTest.deleteRuleNameById(15));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

}
