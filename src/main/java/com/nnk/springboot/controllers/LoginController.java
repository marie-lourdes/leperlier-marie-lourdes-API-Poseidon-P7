package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.service.UserService;
import com.nnk.springboot.utils.Constants;

@Controller
@RequestMapping("app")
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/login");
		return mav;
	}

	@GetMapping("/secure/article-details")
	public ModelAndView getAllUserArticles() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userService.getAllUsers());
		mav.setViewName(Constants.USERLIST_PAGE);
		return mav;
	}

	@GetMapping("/error")
	public ModelAndView error(Model model) {
		ModelAndView mav = new ModelAndView();
		String errorMessage = "";
		model.addAttribute("errorMsg", errorMessage);
		mav.setViewName("403");
		return mav;
	}
}
