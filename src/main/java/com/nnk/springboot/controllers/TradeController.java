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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.utils.Constants;

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

		try {
			tradeService.addTrade(tradeCreated);
			return Constants.REDIRECTION + Constants.TRADELIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Trade created " + constraint.getMessageTemplate());
			}
			return Constants.TRADE_ADD_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.TRADE_ADD_PAGE;
		}
	}

	@GetMapping("/add")
	public String getTradeFormPage(Model model) {
		Trade tradeToCreate = new Trade();
		try {
			model.addAttribute("trade", tradeToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve bid form creation  page" + e.getMessage());
		}

		log.info(" Trade  form creation page successfully retrieved");
		return Constants.TRADE_ADD_PAGE;
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
		return Constants.TRADELIST_PAGE;
	}

	@PostMapping("/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute Trade tradeUpdated,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				return Constants.TRADE_UPDATE_PAGE;
			}
			tradeService.updateTradeById(id, tradeUpdated);
			return Constants.REDIRECTION + Constants.TRADELIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Trade updated " + constraint.getMessageTemplate());
			}
			return Constants.TRADE_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.TRADE_UPDATE_PAGE;
		}
	}

	@GetMapping("/update/{id}")
	public String getUpdateFormTradeListPage(@PathVariable("id") Integer id, Model model) {

		Trade tradeToUpdate = new Trade();
		try {
			tradeToUpdate = tradeService.getTradeById(id);
			if (tradeToUpdate != null) {
				model.addAttribute("trade", tradeToUpdate);
			}
			log.info(" Trade  form update page successfully retrieved");
			return Constants.TRADE_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {

		try {
			tradeService.deleteTradeById(id);
			return Constants.REDIRECTION + Constants.TRADELIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}

	}
}
