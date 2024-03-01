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
		log.debug("adding Trade");
		try {
			tradeService.addTrade(tradeCreated);
			log.info("Trade added successfully {}", tradeCreated);
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
		log.debug("getting trade form page");
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
	public String getTradeListPage(Model model) {
		log.debug("getting trade list page");
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
		log.info("Trade list page successfully retrieved");
		return Constants.TRADELIST_PAGE;
	}

	@PostMapping("/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute Trade tradeUpdated,
			BindingResult result) {
		log.debug("updating Trade {}, id: {}", tradeUpdated, id);
		try {
			if (result.hasErrors()) {
				return Constants.TRADE_UPDATE_PAGE;
			}
			tradeService.updateTradeById(id, tradeUpdated);
			log.info(" Trade updated sucessfully{}, id: {}", tradeUpdated, id);
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
		log.debug("getting trade update form page");
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
		log.debug("deleting Trade {}, id: {}", id);
		try {
			tradeService.deleteTradeById(id);
			log.info("Trade successfully deleted");
			return Constants.REDIRECTION + Constants.TRADELIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}
}
