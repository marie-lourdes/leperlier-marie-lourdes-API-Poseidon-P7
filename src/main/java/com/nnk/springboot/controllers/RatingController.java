package com.nnk.springboot.controllers;

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
			return "redirect:/error-404";
		}
	}

	@GetMapping("/add")
	public String addRatingForm(Rating rating) {
		return "rating/add";
	}

	@GetMapping("/list")
	public String home(Model model) {
		// TODO: find all Rating, add to model
		return "rating/list";
	}

	@PostMapping("/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating ratingUpdated, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Rating and
		// return Rating list
		return "redirect:/rating/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get Rating by Id and to model then show to the form
		return "rating/update";
	}

	@GetMapping("/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Rating by Id and delete the Rating, return to Rating list
		return "redirect:/rating/list";
	}
}
