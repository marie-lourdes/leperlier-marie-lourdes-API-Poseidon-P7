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
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("ruleName")
public class RuleNameController {
	private static final Logger log = LogManager.getLogger(RuleNameController.class);
    // TODO: Inject RuleName service
	@Autowired
	RuleNameService ruleNameService;
	
	@PostMapping("/validate")
	public String validateRuleName(@Valid @ModelAttribute RuleName ruleNameCreated, BindingResult result) {
		try {
			ruleNameService.addRuleName(ruleNameCreated);
			return "redirect:/ruleName/list";
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of RuleName created" + constraint.getMessageTemplate());
			}

			return "ruleName/add";
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return "ruleName/add";
		}
	}

	@GetMapping("/add")
	public String addRuleNameForm(Model model) {
		RuleName ruleNameToCreate = new RuleName();
		try {
			model.addAttribute("ruleName", ruleNameToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve RuleName form creation  page " + e.getMessage());
			// return Constants.ERROR_PAGE;
		}

		log.info(" RuleName form creation  page successfully retrieved");
		return "ruleName/add";
	}

	@GetMapping("/list")
	public String getRuleNamePage(HttpServletRequest httpServletRequest, Model model) {	
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
		model.addAttribute("remoteUser", httpServletRequest.getRemoteUser());
		return "ruleName/list";
	}

	@PostMapping("/update/{id}")
	public ModelAndView updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute RuleName ruleNameUpdated,BindingResult result) {
		try {
			ruleNameService.updateRuleNameById(id, ruleNameUpdated);
			return new ModelAndView("redirect:/ruleName/list");
		}catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of RuleName updated " + constraint.getMessageTemplate());
			}
			return new ModelAndView("redirect:/ruleName/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
			
		}	

	}

	@GetMapping("/update/{id}")
	public ModelAndView getUpdateFormRuleNameListPage(@PathVariable("id") Integer id, Model model) {
		// TODO: get RuleName by Id and to model then show to the form
		RuleName ruleNameToUpdate = new RuleName();
		try {
			ruleNameToUpdate = ruleNameService.getRuleNameById(id);
			if (ruleNameToUpdate != null) {
				model.addAttribute("ruleName", ruleNameToUpdate);
			}

			log.info(" RuleName  form update page successfully retrieved");
			return new ModelAndView("/ruleName/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model) {
		try {
			ruleNameService.deleteRuleNameById(id);
			return new ModelAndView("redirect:/ruleName/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}
}
