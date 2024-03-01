package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.utils.Constants;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("ruleName")
public class RuleNameController {
	private static final Logger log = LogManager.getLogger(RuleNameController.class);

	@Autowired
	RuleNameService ruleNameService;

	@PostMapping("/validate")
	public String validateRuleName(@Valid @ModelAttribute RuleName ruleNameCreated, BindingResult result) {
		log.debug("adding RuleName");
		try {
			ruleNameService.addRuleName(ruleNameCreated);
			log.info("RuleName added successfully {}", ruleNameCreated);
			return Constants.REDIRECTION + Constants.RULENAMELIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of RuleName created" + constraint.getMessageTemplate());
			}

			return Constants.RULENAME_ADD_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.RULENAME_ADD_PAGE;
		}
	}

	@GetMapping("/add")
	public String addRuleNameForm(Model model) {
		log.debug("getting rule name form page");
		RuleName ruleNameToCreate = new RuleName();
		try {
			model.addAttribute("ruleName", ruleNameToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve RuleName form creation  page " + e.getMessage());
		}

		log.info(" RuleName form creation  page successfully retrieved");
		return Constants.RULENAME_ADD_PAGE;
	}

	@GetMapping("/list")
	public String getRuleNamePage(Model model) {
		log.debug("getting rule name list page");
		List<RuleName> ruleNames = new ArrayList<>();
		try {
			ruleNames = ruleNameService.getAllRuleNames();
			if (ruleNames.isEmpty()) {
				throw new NullPointerException("List of RuleNames not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

		model.addAttribute("ruleNames", ruleNames);
		log.info(" Rule name list page successfully retrieved");
		return Constants.RULENAMELIST_PAGE;
	}

	@PostMapping("/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute RuleName ruleNameUpdated,
			BindingResult result) {
		log.debug("updating RuleName{}, id: {}", ruleNameUpdated, id);
		try {
			if (result.hasErrors()) {
				return Constants.RULENAME_UPDATE_PAGE;
			}
			ruleNameService.updateRuleNameById(id, ruleNameUpdated);
			log.info(" RuleName updated sucessfully{}, id: {}", ruleNameUpdated, id);
			return Constants.REDIRECTION + Constants.RULENAMELIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of RuleName updated " + constraint.getMessageTemplate());
			}
			return Constants.RULENAME_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.RULENAME_UPDATE_PAGE;
		}
	}

	@GetMapping("/update/{id}")
	public String getUpdateFormRuleNameListPage(@PathVariable("id") Integer id, Model model) {
		log.debug("getting rule name update form page");
		RuleName ruleNameToUpdate = new RuleName();
		try {
			ruleNameToUpdate = ruleNameService.getRuleNameById(id);
			if (ruleNameToUpdate != null) {
				model.addAttribute("ruleName", ruleNameToUpdate);
			}

			log.info(" RuleName  form update page successfully retrieved");
			return Constants.RULENAME_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		log.debug("deleting RuleName {}, id: {}", id);
		try {
			ruleNameService.deleteRuleNameById(id);
			log.info("RuleName successfully deleted");
			return Constants.REDIRECTION + Constants.RULENAMELIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}
}
