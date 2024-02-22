package com.nnk.springboot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	private static final Logger log = LogManager.getLogger(RatingService.class);
	@Autowired
	private RatingRepository ratingRepository; 

}
