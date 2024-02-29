package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nnk.springboot.utils.Constants;

@Controller
public class HomeController {
	private static final Logger log = LogManager.getLogger(HomeController.class);
	@GetMapping("/")
	public String home(Model model) {
		log.info("Home page successfully retrieved");
		return "home";
	}

	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		log.info("Home page admin successfully retrieved");
		return  Constants.BIDLIST_PAGE;
	}

}
