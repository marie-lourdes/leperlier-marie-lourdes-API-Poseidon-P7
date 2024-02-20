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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

import jakarta.validation.Valid;

@Controller
public class BidListController {

	private static final Logger log = LogManager.getLogger(BidListController.class);

	@Autowired
	BidListService bidListService;

	@PostMapping("/bidList/validate")
	public ModelAndView validateBidList(@Valid @ModelAttribute BidList bid, Model model, Principal principal) {
		// TODO: check data valid and save to db, after saving return bid list
		try {
			bidListService.addbid(bid, principal.getName());
			return new ModelAndView("redirect:/bidList/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error");
		}
	}

	@PostMapping("/bidList/update/{id}")
	public ModelAndView updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute BidList bidList, Model model) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid
		try {
		bidListService.updateBidById(id, bidList);
		return new ModelAndView("redirect:/bidList/list");
		}catch (NullPointerException e) {
			return new ModelAndView("redirect:/error");
		}
		
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/list")
	public String getBidListPage(Model model) {
		// TODO: call service find all bids to show to the view
		List<BidList> bidLists = new ArrayList<>();
		try {
			bidLists = bidListService.getAllbids();

		} catch (NullPointerException e) {
			log.error(e.getMessage());
			//return new ModelAndVie:/w("redirecterror");
		}
		model.addAttribute("bidLists", bidLists);
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String getBidFormPage(Model model) {
		BidList bidListCreated = new BidList();
		try {
			model.addAttribute("bidList", bidListCreated);
		} catch (Exception e) {
			log.error("Failed to retrieve sign up page " + e.getMessage());
			// return Constants.ERROR_PAGE;
		}
		log.info(" Bid Form page successfully retrieved");

		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String getUpdateFormBidListPage(@PathVariable("id") Integer id, Model model) {
		// TODO: get Bid by Id and to model then show to the form
		return "bidList/update";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		return "redirect:/bidList/list";
	}
}
