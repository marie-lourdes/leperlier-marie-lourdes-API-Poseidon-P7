package com.nnk.springboot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService {
	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
}
