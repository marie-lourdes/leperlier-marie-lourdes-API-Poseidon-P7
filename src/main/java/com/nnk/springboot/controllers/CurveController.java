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
    // TODO: Inject Curve Point service

    @PostMapping("/validate")
    public  String validateCurvePoint (@Valid @ModelAttribute  CurvePoint curvePointCreated,BindingResult result) {
        // TODO: check data valid and save to db, after saving return Curve list
    	try {
    		curvePointService.addCurvePoint(curvePointCreated);
			return "redirect:/curvePoint/list";
		}catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of Curve Point created" + constraint.getMessageTemplate());
			}
			
			return "curvePoint/add";
		}catch (NullPointerException e) {
			log.error(e.getMessage());
			return "redirect:/error-404";
		}  
    }
    
    @GetMapping("/add")
    public String addCurvePointForm(Model model) {
    	CurvePoint curvePointToCreate= new CurvePoint();
		try {
			model.addAttribute("curvePoint",curvePointToCreate);
		} catch (Exception e) {
			log.error("Failed to retrieve sign up page " + e.getMessage());
			// return Constants.ERROR_PAGE;
		}
		
		log.info(" CurvePoint form creation  page successfully retrieved");		
        return "curvePoint/add";
    }
    
   @GetMapping("/list")
    public String getCurvePointPage(HttpServletRequest httpServletRequest,Model model)
    {
        // TODO: find all Curve Point, add to model
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
        return "curvePoint/list";
    }

   @PostMapping("/update/{id}")
   public ModelAndView updateCurvePoint(@PathVariable("id") Integer id, @Valid @ModelAttribute CurvePoint curvePoint, Model model) {
       // TODO: check required fields, if valid call service to update Curve and return Curve list
		try {
			 curvePointService.updateCurvePointById(id, curvePoint);
			return new ModelAndView("redirect:/curvePoint/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
  
   }
   
    @GetMapping("/update/{id}")
    public ModelAndView getUpdateFormCurvePointListPage(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
    	CurvePointDTO curvePointToUpdate = new CurvePointDTO();
		try {
			curvePointToUpdate = curvePointService.getCurvePointById(id);
			if (curvePointToUpdate != null) {
				model.addAttribute("curvePoint", curvePointToUpdate);
			}
			
			 log.info(" Curve Point  form update page successfully retrieved");
			return new ModelAndView("/curvePoint/update");		
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		} 
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
    	try {
    		curvePointService.deleteCurvePointById(id);
				return new ModelAndView("redirect:/curvePoint/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
    }
}
