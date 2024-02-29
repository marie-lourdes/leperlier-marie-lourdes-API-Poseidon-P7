package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.service.UserService;
import com.nnk.springboot.utils.Constants;

@Controller
@RequestMapping("app")
public class LoginController {
	private static final Logger log = LogManager.getLogger(LoginController .class);
	
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/login");
		log.info(" Login page successfully retrieved");
		return mav;
	}

	@GetMapping("/secure/article-details")
	public ModelAndView getAllUserArticles() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userService.getAllUsers());
		mav.setViewName(Constants.USERLIST_PAGE);
		log.info("UserArticles page successfully retrieved");
		return mav;
	}
}
