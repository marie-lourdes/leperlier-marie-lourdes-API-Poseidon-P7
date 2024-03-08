package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.utils.Constants;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {
	private String authority;
	private static final Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/validate")
	public String validate(@Valid @ModelAttribute User userCreated, BindingResult result,
			Authentication Authentication) {
		log.debug("adding user");
		
		authority = "";
		try {
			if (!result.hasErrors()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userCreated.setPassword(encoder.encode(userCreated.getPassword()));
			}
			
			log.info("User added successfully {}", userCreated);
			userService.addUser(userCreated);
			Authentication.getAuthorities().forEach(authoritie -> authority = authoritie.getAuthority());
			if (authority.equals("ROLE_ADMIN")) {
				return Constants.REDIRECTION + Constants.USERLIST_PAGE;
			}
			return "home";
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of User created " + constraint.getMessageTemplate());
			}

			return Constants.USER_ADD_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.USER_ADD_PAGE;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return "error";
		}
	}

	@GetMapping("/add")
	public String getUserFormPage(Model model) {
		log.debug("getting user form page");
		User userToCreate = new User();
		try {
			model.addAttribute("user", userToCreate);

		} catch (Exception e) {
			log.error("Failed to retrieve user form creation  page" + e.getMessage());
		}

		log.info(" User  form creation page successfully retrieved");
		return Constants.USER_ADD_PAGE;
	}

	@GetMapping("/list")
	public String getUserListPage(Model model) {
		log.debug("getting user list page");
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
		log.info("User list page successfully retrieved");
		return Constants.USERLIST_PAGE;
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute User userUpdated,
			BindingResult result) {
		log.debug("updating User {}, id: {}", userUpdated, id);
		try {
			if (!result.hasErrors()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userUpdated.setPassword(encoder.encode(userUpdated.getPassword()));

			} else {
				return Constants.USER_UPDATE_PAGE;
			}
			userService.updateUserById(id, userUpdated);
			log.info("User updated sucessfully{}, id: {}", userUpdated, id);
			return Constants.REDIRECTION + Constants.USERLIST_PAGE;
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violationsException = e.getConstraintViolations();
			for (ConstraintViolation<?> constraint : violationsException) {
				log.error("Errors fields of User updated " + constraint.getMessageTemplate());
			}

			return Constants.USER_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.USER_UPDATE_PAGE;
		}

	}

	@GetMapping("/update/{id}")
	public String getUpdateFormBidListPage(@PathVariable("id") Integer id, Model model) {
		log.debug("getting user update form page");
		User userToUpdate = new User();
		try {
			userToUpdate = userService.getUserEntityById(id);
			if (userToUpdate != null) {
				model.addAttribute("user", userToUpdate);
			}

			log.info(" User  form update page successfully retrieved");
			return Constants.USER_UPDATE_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		log.debug("deleting User {}, id: {}", id);
		try {
			userService.deleteUserById(id);
			return Constants.REDIRECTION + Constants.USERLIST_PAGE;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return Constants.ERROR_404_PAGE;
		}
	}
}
