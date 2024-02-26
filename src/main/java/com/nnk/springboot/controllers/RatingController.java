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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("rating")
public class RatingController {
	private static final Logger log = LogManager.getLogger(RatingController.class);
	// TODO: Inject Rating service
	@Autowired
	RatingService ratingService;

	@PostMapping("/validate")
	public String validateRating(@Valid @ModelAttribute Rating ratingCreated, BindingResult result) {
		// TODO: check data valid and save to db, after saving return Rating list
		try {
			ratingService.addRating(ratingCreated);
			return "redirect:/rating/list";

		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of rating created " + constraint.getMessageTemplate());
			}

			return "rating/add";
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return "rating/add";
		}
	}

	@GetMapping("/add")
	public String addRatingForm(Model model) {
		Rating ratingToCreate = new Rating();
		try {
			model.addAttribute("rating", ratingToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve rating form creation  page " + e.getMessage());
			// return Constants.ERROR_PAGE;
		}

		log.info(" Rating form creation  page successfully retrieved");
		return "rating/add";	
	}

	@GetMapping("/list")
	public String getRatingPage(HttpServletRequest httpServletRequest, Model model) {
		// TODO: find all Rating, add to model
		List<Rating> ratings = new ArrayList<>();
		try {
			ratings = ratingService.getAllRatings();
			if (ratings.isEmpty()) {
				throw new NullPointerException("List of ratings not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}
		model.addAttribute("ratings", ratings);
		model.addAttribute("remoteUser", httpServletRequest.getRemoteUser());
		return "rating/list";
	}

	@PostMapping("/update/{id}")
	public ModelAndView updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute Rating ratingUpdated,BindingResult result) {
		// TODO: check required fields, if valid call service to update Rating and
		// return Rating list
		try {
			ratingService.updateRatingById(id, ratingUpdated);
			return new ModelAndView("redirect:/rating/list");
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Rating updated " + constraint.getMessageTemplate());
			}
			return new ModelAndView("redirect:/rating/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
			
		}	
	}

	@GetMapping("/update/{id}")
	public ModelAndView getUpdateFormRatingListPage(@PathVariable("id") Integer id, Model model) {
		// TODO: get Rating by Id and to model then show to the form
		Rating ratingToUpdate = new Rating();
		try {
			ratingToUpdate = ratingService.getRatingById(id);
			if (ratingToUpdate != null) {
				model.addAttribute("rating", ratingToUpdate);
			}

			log.info(" Curve Point  form update page successfully retrieved");
			return new ModelAndView("/rating/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteRating(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Rating by Id and delete the Rating, return to Rating list
		try {
			ratingService.deleteRatingById(id);
			return new ModelAndView("redirect:/rating/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}
}
