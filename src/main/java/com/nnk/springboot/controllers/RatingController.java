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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("rating")
public class RatingController {
	private static final Logger log = LogManager.getLogger(RatingController.class);
	
	@Autowired
	RatingService ratingService;

	@PostMapping("/validate")
	public String validateRating(@Valid @ModelAttribute Rating ratingCreated, BindingResult result) {

		try {
			ratingService.addRating(ratingCreated);
			return Constants.REDIRECTION + Constants.RATINGLIST_PAGE;

		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of rating created " + constraint.getMessageTemplate());
			}

			return Constants.RATING_ADD_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.RATING_ADD_PAGE;
		}
	}

	@GetMapping("/add")
	public String addRatingForm(Model model) {
		Rating ratingToCreate = new Rating();
		try {
			model.addAttribute("rating", ratingToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve rating form creation  page " + e.getMessage());
		}

		log.info(" Rating form creation  page successfully retrieved");
		return Constants.RATING_ADD_PAGE;
	}

	@GetMapping("/list")
	public String getRatingPage(HttpServletRequest httpServletRequest, Model model) {
	
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
		return Constants.RATINGLIST_PAGE;
	}

	@PostMapping("/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute Rating ratingUpdated,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				return Constants.RATING_UPDATE_PAGE;
			}
			ratingService.updateRatingById(id, ratingUpdated);
			return Constants.REDIRECTION + Constants.RATINGLIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return Constants.RATING_UPDATE_PAGE;
		}
	}

	@GetMapping("/update/{id}")
	public String getUpdateFormRatingListPage(@PathVariable("id") Integer id, Model model) {
		
		Rating ratingToUpdate = new Rating();
		try {
			ratingToUpdate = ratingService.getRatingById(id);
			if (ratingToUpdate != null) {
				model.addAttribute("rating", ratingToUpdate);
			}

			log.info(" Rating  form update page successfully retrieved");
			return Constants.RATING_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
	
		try {
			ratingService.deleteRatingById(id);
			return Constants.REDIRECTION + Constants.RATINGLIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}
}
