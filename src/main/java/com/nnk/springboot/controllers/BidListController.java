package com.nnk.springboot.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import jakarta.validation.Valid;

@Controller
@RequestMapping("bidList")
public class BidListController {

	private static final Logger log = LogManager.getLogger(BidListController.class);

	@Autowired
	BidListService bidListService;

	@PostMapping("/validate")
	public ModelAndView validateBidList(@Valid @ModelAttribute BidList bidCreated, Model model, Principal principal) {
		// TODO: check data valid and save to db, after saving return bid list
		try {
			//bidListService.addBid(bidCreated, principal.getName());
			bidListService.addBid(bidCreated);
			return new ModelAndView("redirect:/bidList/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/add")
	public String getBidFormPage(Model model) {
		BidList bidListToCreate = new BidList();
		try {
			model.addAttribute("bidList", bidListToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve sign up page " + e.getMessage());
			// return Constants.ERROR_PAGE;
		}
		
		log.info(" Bid  form creation page successfully retrieved");
		return "bidList/add";
	}

	@GetMapping("/list")
	public String getBidListPage(HttpServletRequest httpServletRequest,Model model) {
		// TODO: call service find all bids to show to the view
		List<BidListDTO> bidLists = new ArrayList<>();
		try {
			bidLists = bidListService.getAllBids();

		} catch (NullPointerException e) {
			log.error(e.getMessage());		
		}
	
		model.addAttribute("bidLists", bidLists);
		model.addAttribute("remoteUser", httpServletRequest.getRemoteUser());
		return "bidList/list";
	}

	@PostMapping("/update/{id}")
	public ModelAndView updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute BidList bidList, Model model) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid
		try {

			bidListService.updateBidById(id, bidList);
			return new ModelAndView("redirect:/bidList/list");
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/update/{id}")
	public ModelAndView getUpdateFormBidListPage(@PathVariable("id") Integer id, Model model) {
		// TODO: get Bid by Id and to model then show to the form
		BidListDTO bidListToUpdate = new BidListDTO();
		try {
			bidListToUpdate  = bidListService.getBidById(id);
			if (bidListToUpdate != null) {
				model.addAttribute("bidList", bidListToUpdate);
			}
			log.info(" Bid  form update page successfully retrieved");
			return new ModelAndView("/bidList/update");
			//return "bidList/update";
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		try {
			 bidListService.deleteBidById(id);
				return new ModelAndView("redirect:/bidList/list");
			//return "bidList/list";
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/error-404");
		}
	
	}
}
