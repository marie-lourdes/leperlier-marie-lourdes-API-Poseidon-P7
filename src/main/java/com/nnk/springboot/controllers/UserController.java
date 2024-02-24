package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.BidListDTO;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/validate")
	public String validate(@Valid @ModelAttribute User userCreated, BindingResult result) {
		try {
			if (!result.hasErrors()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userCreated.setPassword(encoder.encode(userCreated.getPassword()));
				// model.addAttribute("userUpdateds", userService.findAll());
			}

			userService.addUser(userCreated);
			return "redirect:/user/list";
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of User created " + constraint.getMessageTemplate());
			}

			return "user/add";
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return "redirect:/error-404";
		}
	}

	@GetMapping("/add")
	public String getUserFormPage(Model model) {
		User userToCreate = new User();
		try {
			model.addAttribute("user", userToCreate);

		} catch (Exception e) {
			log.error("Failed to retrieve user form creation  page" + e.getMessage());
			// return Constants.ERROR_PAGE;
		}

		log.info(" User  form creation page successfully retrieved");
		return "user/add";
	}

	@GetMapping("/list")
	public String getUserListPage(HttpServletRequest httpServletRequest, Model model) {
		List<UserDTO> users = new ArrayList<UserDTO>();
		try {
			users = userService.getAllUsers();
			if (users.isEmpty()) {
				throw new NullPointerException("List of users not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

		model.addAttribute("users", users);
		model.addAttribute("remoteUser", httpServletRequest.getRemoteUser());
		return "user/list";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute User userUpdated,
			BindingResult result) {

		try {
			if (!result.hasErrors()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userUpdated.setPassword(encoder.encode(userUpdated.getPassword()));
				// model.addAttribute("userUpdateds", userService.findAll());
				return "redirect:/user/list";
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return "/error-404";
		}

		// userUpdated.setId(id);
		// userService.save(userUpdated);
		// model.addAttribute("userUpdateds", userService.findAll());
		userService.updateUserById(id, userUpdated);
		return "redirect:/user/list";
	}

	@GetMapping("/update/{id}")
	public ModelAndView getUpdateFormBidListPage(@PathVariable("id") Integer id, Model model) {
		UserDTO userToUpdate = new UserDTO();
		try {
			userToUpdate = userService.getUserById(id);
			if (userToUpdate != null) {
				model.addAttribute("user", userToUpdate);
			}

			log.info(" User  form update page successfully retrieved");
			return new ModelAndView("/user/update");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteUser(@PathVariable("id") Integer id, Model model) {
		try {
			userService.deleteUserById(id);
			return new ModelAndView("redirect:/user/list");
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/error-404");
		}
		/*
		 * User user= userService.findById(id).orElseThrow(() -> new
		 * IllegalArgumentException("Invalid userUpdated Id:" + id));
		 * userService.delete(user); model.addAttribute("users", userService.findAll());
		 */
		// return "redirect:/user/list";
	}
}
