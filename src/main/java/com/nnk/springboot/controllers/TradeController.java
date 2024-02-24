package com.nnk.springboot.controllers;

import java.security.Principal;
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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("trade")
public class TradeController {
	private static final Logger log = LogManager.getLogger(TradeController.class);

	@Autowired
	TradeService tradeService;

	@PostMapping("/validate")
	public String validate(@Valid @ModelAttribute Trade tradeCreated, BindingResult result, Principal principal) {
		// TODO: check data valid and save to db, after saving return bid list

		/*
		 * if(result.hasErrors()) { return new ModelAndView("redirect:/trade/add"); }
		 */
		try {
			// tradeService.addTrade(tradeCreated, principal.getName());
			tradeService.addTrade(tradeCreated);
			return "redirect:/trade/list";
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Trade created " + constraint.getMessageTemplate());
			}

			return "trade/add";
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return "redirect:/error-404";
		}
	}

	@GetMapping("/add")
	public String getTradeFormPage(Model model) {
		Trade tradeToCreate = new Trade();
		try {
			model.addAttribute("trade", tradeToCreate);

		} catch (Exception e) {
			log.error("Failed to retrieve bid form creation  page" + e.getMessage());
			// return Constants.ERROR_PAGE;
		}

		log.info(" Trade  form creation page successfully retrieved");
		return "trade/add";
	}

	@GetMapping("/list")
	public String getTradeListPage(HttpServletRequest httpServletRequest, Model model) {
		// TODO: call service find all bids to show to the view
		List<TradeDTO> trades = new ArrayList<TradeDTO>();
		try {
			trades = tradeService.getAllTrades();
			if (trades.isEmpty()) {
				throw new NullPointerException("List of bids not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

		model.addAttribute("trades", trades);
		model.addAttribute("remoteUser", httpServletRequest.getRemoteUser());
		return "trade/list";
	}

	@PostMapping("/update/{id}")
	public ModelAndView updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute Trade tradeUpdated,BindingResult result) {
		// TODO: check required fields, if valid call service to update Trade and return
		// list Trade
		try {
			tradeService.updateTradeById(id, tradeUpdated);
			return new ModelAndView("redirect:/trade/list");
		}catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Trade updated " + constraint.getMessageTemplate());
			}
			return new ModelAndView("redirect:/trade/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
			
		}	
	}

	@GetMapping("/update/{id}")
	public ModelAndView getUpdateFormTradeListPage(@PathVariable("id") Integer id, Model model) {
		// TODO: get Trade by Id and to model then show to the form
		TradeDTO tradeToUpdate = new TradeDTO();
		try {
			tradeToUpdate = tradeService.getTradeById(id);
			if (tradeToUpdate != null) {
				model.addAttribute("trade", tradeToUpdate);
			}

			log.info(" Trade  form update page successfully retrieved");
			return new ModelAndView("/trade/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteTrade(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Trade by Id and delete the bid, return to Trade list
		try {
			tradeService.deleteTradeById(id);
			return new ModelAndView("redirect:/trade/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}

	}
}
