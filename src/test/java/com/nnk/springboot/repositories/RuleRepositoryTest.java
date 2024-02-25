package com.nnk.springboot.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.RuleName;

@SpringBootTest
public class RuleRepositoryTest {
	private RuleName rule;

	@Autowired
	private IRuleNameRepository ruleNameRepository;

	@BeforeEach
	public void setUpPerTest() {
		rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
	}

	@DisplayName("test for save RuleName operation")
	@Test
	public void givenRuleNameObject_whenSave_thenReturnSavedRuleName() throws Exception {
		try {
			rule = ruleNameRepository.save(rule);
			assertNotNull(rule.getId());
			assertTrue(rule.getName().equals("Rule Name"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for update RuleName operation")
	@Test
	public void givenRuleNameObject_whenUpdate_thenReturnUpdatedRuleName() throws Exception {
		try {
			rule.setName("Rule Name Update");
			rule = ruleNameRepository.save(rule);
			assertTrue(rule.getName().equals("Rule Name Update"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get all RuleNames operation")
	@Test
	public void givenRuleNameList_whenFindAll_thenReturnAllRuleNames() throws Exception {
		try {
			List<RuleName> listResult = ruleNameRepository.findAll();
			assertTrue(listResult.size() > 0);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get RuleName by id  operation")
	@Test
	public void givenRuleNameObject_whenFindById_thenReturnRuleName() throws Exception {
		try {
			rule = ruleNameRepository.save(rule);
			Integer id = rule.getId();
			RuleName result = ruleNameRepository.findById(id).get();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for delete RuleName operation")
	@Test
	public void givenRuleNameObject_whenDeleteById_thenRemoveRuleName() throws Exception {
		try {
			rule = ruleNameRepository.save(rule);
			Integer id = rule.getId();
			ruleNameRepository.deleteById(id);
			Optional<RuleName> ruleList = ruleNameRepository.findById(id);
			assertFalse(ruleList.isPresent());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
