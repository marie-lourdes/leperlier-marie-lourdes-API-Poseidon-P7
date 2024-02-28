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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("curvePoint")
public class CurveController {
	private static final Logger log = LogManager.getLogger(CurveController.class);

	@Autowired
	CurvePointService curvePointService;

	@PostMapping("/validate")
	public String validateCurvePoint(@Valid @ModelAttribute CurvePoint curvePointCreated, BindingResult result) {

		try {
			curvePointService.addCurvePoint(curvePointCreated);
			return Constants.REDIRECTION + Constants.CURVEPOINTLIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Curve Point created" + constraint.getMessageTemplate());
			}

			return Constants.CURVEPOINT_ADD_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.CURVEPOINT_ADD_PAGE;
		}
	}

	@GetMapping("/add")
	public String addCurvePointForm(Model model) {
		CurvePoint curvePointToCreate = new CurvePoint();
		try {
			model.addAttribute("curvePoint", curvePointToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve curve point form creation  page " + e.getMessage());
		}

		log.info(" CurvePoint form creation  page successfully retrieved");
		return Constants.CURVEPOINT_ADD_PAGE;
	}

	@GetMapping("/list")
	public String getCurvePointPage(HttpServletRequest httpServletRequest, Model model) {
	
		List<CurvePointDTO> curvePoints = new ArrayList<>();
		try {
			curvePoints = curvePointService.getAllCurvePoints();
			if (curvePoints.isEmpty()) {
				throw new NullPointerException("List of curve points not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}
		model.addAttribute("curvePoints", curvePoints);
		model.addAttribute("remoteUser", httpServletRequest.getRemoteUser());
		return Constants.CURVEPOINTLIST_PAGE;
	}

	@PostMapping("/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @Valid @ModelAttribute CurvePoint curvePointUpdated,
			BindingResult result) {
		
		try {
			if (result.hasErrors()) {
				return Constants.CURVEPOINT_UPDATE_PAGE;
			}
			curvePointService.updateCurvePointById(id, curvePointUpdated);
			return Constants.REDIRECTION + Constants.CURVEPOINTLIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Curve point updated " + constraint.getMessageTemplate());
			}
			return Constants.CURVEPOINT_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return Constants.CURVEPOINT_UPDATE_PAGE;
		}
	}

	@GetMapping("/update/{id}")
	public String getUpdateFormCurvePointListPage(@PathVariable("id") Integer id, Model model) {
		// TODO: get CurvePoint by Id and to model then show to the form
		CurvePoint curvePointToUpdate = new CurvePoint();
		try {
			curvePointToUpdate = curvePointService.getCurvePointById(id);
			if (curvePointToUpdate != null) {
				model.addAttribute("curvePoint", curvePointToUpdate);
			}

			log.info(" Curve Point  form update page successfully retrieved");
			return Constants.CURVEPOINT_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		try {
			curvePointService.deleteCurvePointById(id);
			return Constants.REDIRECTION +Constants.CURVEPOINTLIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}
}
