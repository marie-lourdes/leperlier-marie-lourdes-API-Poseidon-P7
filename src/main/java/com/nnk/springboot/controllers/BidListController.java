package com.nnk.springboot.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("bidList")
public class BidListController {
	private static final Logger log = LogManager.getLogger(BidListController.class);

	@Autowired
	BidListService bidListService;

	@PostMapping("/validate")
	public String validate(@Valid @ModelAttribute BidList bidCreated, BindingResult result, Principal principal) {
		try {
			bidListService.addBid(bidCreated);
			return Constants.REDIRECTION + Constants.BIDLIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Bid created " + constraint.getMessageTemplate());
			}

			return Constants.BID_ADD_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.BID_ADD_PAGE;
		}
	}

	@GetMapping("/add")
	public String getBidFormPage(Model model) {
		BidList bidListToCreate = new BidList();
		try {
			model.addAttribute("bidList", bidListToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve bid form creation  page" + e.getMessage());
		}

		log.info(" Bid  form creation page successfully retrieved");
		return Constants.BID_ADD_PAGE;
	}

	@GetMapping("/list")
	public String getBidListPage(HttpServletRequest httpServletRequest, Model model, Authentication authentication) {

		List<BidListDTO> bidLists = new ArrayList<BidListDTO>();
		try {
			bidLists = bidListService.getAllBids();
			if (bidLists.isEmpty()) {
				throw new NullPointerException("List of bids not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

		model.addAttribute("bidLists", bidLists);
		model.addAttribute("remoteUser", httpServletRequest.getRemoteUser());
		return Constants.BIDLIST_PAGE;
	}

	@PostMapping("/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute BidList bidListUpdated,
			BindingResult result) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid
		try {
			if (result.hasErrors()) {
				return   Constants.BID_UPDATE_PAGE;
			}
			bidListService.updateBidById(id, bidListUpdated);
			return Constants.BIDLIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.BID_UPDATE_PAGE;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return Constants.BID_UPDATE_PAGE;
		}
	}

	@GetMapping("/update/{id}")
	public String getUpdateFormBidListPage(@PathVariable("id") Integer id, Model model) {
		
		BidList bidListToUpdate = new BidList();
		try {
			bidListToUpdate = bidListService.getBidById(id);
			if (bidListToUpdate != null) {
				model.addAttribute("bidList", bidListToUpdate);
			}

			log.info(" Bid  form update page successfully retrieved");
			return Constants.BID_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return  Constants.ERROR_404_PAGE;
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		try {
			bidListService.deleteBidById(id);
			return Constants.REDIRECTION + Constants.BIDLIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}

	}
}
