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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;

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
		// TODO: check data valid and save to db, after saving return bid list

		/*
		 * if(result.hasErrors()) { return new ModelAndView("redirect:/bidList/add"); }
		 */
		try {
			// bidListService.addBid(bidCreated, principal.getName());
			bidListService.addBid(bidCreated);
			return "redirect:/bidList/list";
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Bid created " + constraint.getMessageTemplate());
			}

			return "bidList/add";
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return "redirect:/error-404";
		}
	}

	@GetMapping("/add")
	public String getBidFormPage(Model model) {
		BidList bidListToCreate = new BidList();
		try {
			model.addAttribute("bidList", bidListToCreate);

		} catch (Exception e) {
			log.error("Failed to retrieve bid form creation  page" + e.getMessage());
			// return Constants.ERROR_PAGE;
		}

		log.info(" Bid  form creation page successfully retrieved");
		return "bidList/add";
	}

	@GetMapping("/list")
	public String getBidListPage(HttpServletRequest httpServletRequest, Model model) {
		// TODO: call service find all bids to show to the view
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
		return "bidList/list";
	}

	@PostMapping("/update/{id}")
	public ModelAndView updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute BidList bidListUpdated,BindingResult result) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid
		try {
			bidListService.updateBidById(id, bidListUpdated);
			return new ModelAndView("redirect:/bidList/list");
		}catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Bid updated " + constraint.getMessageTemplate());
			}
			return new ModelAndView("redirect:/bidList/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
			
		}	
	}

	@GetMapping("/update/{id}")
	public ModelAndView getUpdateFormBidListPage(@PathVariable("id") Integer id, Model model) {
		// TODO: get Bid by Id and to model then show to the form
		BidList bidListToUpdate = new BidList();
		try {
			bidListToUpdate = bidListService.getBidById(id);
			if (bidListToUpdate != null) {
				model.addAttribute("bidList", bidListToUpdate);
			}

			log.info(" Bid  form update page successfully retrieved");
			return new ModelAndView("/bidList/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		try {
			bidListService.deleteBidById(id);
			return new ModelAndView("redirect:/bidList/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}

	}
}
