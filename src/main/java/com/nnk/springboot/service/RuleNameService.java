package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.IRuleNameRepository;

@Service
public class RuleNameService {
	@Autowired
	private IRuleNameRepository ruleNameRepository;

	public RuleName addRuleName(RuleName ruleNameCreated) throws NullPointerException {
		RuleName ruleNameRegistered = new RuleName();
		if (ruleNameCreated != null) {
			ruleNameRegistered.setName(ruleNameCreated.getName());
			ruleNameRegistered.setDescription(ruleNameCreated.getDescription());
			ruleNameRegistered.setJson(ruleNameCreated.getJson());
			ruleNameRegistered.setTemplate(ruleNameCreated.getTemplate());
			ruleNameRegistered.setSqlStr(ruleNameCreated.getSqlStr());
			ruleNameRegistered.setSqlPart(ruleNameCreated.getSqlPart());
		}
		ruleNameRegistered = ruleNameRepository.save(ruleNameRegistered);
		return ruleNameRegistered;
	}

	public RuleName getRuleNameById(Integer id) throws NullPointerException {
		RuleName ruleNameFoundById = ruleNameRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("RuleName" + id + " not found"));
		return ruleNameFoundById;
	}

	public List<RuleName> getAllRuleNames() throws NullPointerException {
		List<RuleName> allRuleNames = ruleNameRepository.findAll();
		if (allRuleNames.isEmpty()) {
			return new ArrayList<>();
		}
		return allRuleNames;
	}

	public RuleName updateRuleNameById(Integer id, RuleName ruleNameUpdated)
			throws NullPointerException, IllegalArgumentException {
		RuleName ruleNameToUpdate = new RuleName();
		ruleNameToUpdate = ruleNameRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("RuleName" + id + " not found for updating"));
		ruleNameToUpdate.setName(ruleNameUpdated.getName());
		ruleNameToUpdate.setDescription(ruleNameUpdated.getDescription());
		ruleNameToUpdate.setJson(ruleNameUpdated.getJson());
		ruleNameToUpdate.setTemplate(ruleNameUpdated.getTemplate());
		ruleNameToUpdate.setSqlStr(ruleNameUpdated.getSqlStr());
		ruleNameToUpdate.setSqlPart(ruleNameUpdated.getSqlPart());
		ruleNameToUpdate = ruleNameRepository.save(ruleNameToUpdate);
		return ruleNameToUpdate;
	}

	public void deleteRuleNameById(Integer id) throws NullPointerException {
		ruleNameRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("RuleName" + id + " not found for deleting"));
		ruleNameRepository.deleteById(id);
	}

	public void deleteAllRuleNames() throws Exception {
		ruleNameRepository.deleteAll();
	}
}
