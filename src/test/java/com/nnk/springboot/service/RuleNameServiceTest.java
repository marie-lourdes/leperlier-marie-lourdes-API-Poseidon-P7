package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.IRuleNameRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class RuleNameServiceTest {
	@Autowired
	private RuleNameService ruleNameServiceUnderTest;

	@MockBean
	private IRuleNameRepository ruleNameRepository;

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
		List<RuleName> allRuleNames = new ArrayList<RuleName>();
		allRuleNames.add(ruleName);
		doThrow(new NullPointerException()).when(ruleNameRepository).deleteById(any(Integer.class));
	}

	@AfterEach
	public void reset() throws Exception {
		ruleNameServiceUnderTest.deleteAllRuleNames();
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
			when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

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
			when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);
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
			ruleName = null;
			RuleName result = ruleNameServiceUnderTest.addRuleName(ruleName);

			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> ruleNameServiceUnderTest.addRuleName(new RuleName()));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> ruleNameServiceUnderTest.addRuleName(new RuleName()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRuleName() throws Exception {
		try {
			ruleNameServiceUnderTest.getRuleNameById(2).setName("name updated");
			when(ruleNameRepository.findById(2)).thenReturn(Optional.of(ruleName));

			RuleName result = ruleNameServiceUnderTest.updateRuleNameById(2, ruleName);
			assertAll("assertion data ruleName created", () -> {
				assertNotNull(result.getId());
				assertEquals("Json test", result.getJson());
				assertEquals("sql part test", result.getSqlPart());
				assertEquals("sql str test", result.getSqlStr());
				assertEquals("name updated", result.getName());
			});
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ruleNameServiceUnderTest.updateRuleNameById(2, ruleName));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRuleName_WithRuleNameNotFound() throws Exception {
		try {
			doThrow(new NullPointerException()).when(ruleNameRepository).findById(15);
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
			doThrow(new NullPointerException()).when(ruleNameRepository).findAll();
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
			when(ruleNameRepository.findById(2)).thenReturn(Optional.of(ruleName));

			RuleName result = ruleNameServiceUnderTest.getRuleNameById(2);
			assertNotNull(result);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ruleNameServiceUnderTest.getRuleNameById(2));
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
