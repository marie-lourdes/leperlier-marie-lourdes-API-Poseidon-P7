package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	private static final Logger log = LogManager.getLogger(RatingService.class);
	
	@Autowired
	private RatingRepository ratingRepository;

	public Rating addRating(Rating ratingCreated) throws NullPointerException {
		Rating ratingRegistered = new Rating();
		if (ratingCreated != null) {
			ratingRegistered.setMoodysRating(ratingCreated.getMoodysRating());
			ratingRegistered.setFitchRating(ratingCreated.getFitchRating());
			ratingRegistered.setSandRating(ratingCreated.getSandRating());
			ratingRegistered.setOrderNumber(ratingCreated.getOrderNumber());
		}

		ratingRegistered = ratingRepository.save(ratingRegistered);
		return ratingRegistered;
	}

	public Rating getRatingById(Integer id) throws NullPointerException {
		Rating ratingFoundById = ratingRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Rating" + id + " not found"));
		return ratingFoundById;
	}

	public List<Rating> getAllRatings() throws NullPointerException {
		List<Rating> allRatings = ratingRepository.findAll();
		
		if (allRatings.isEmpty()) {
			return new ArrayList<>();
		}
		return allRatings;
	}

	public Rating updateRatingById(Integer id, Rating ratingUpdated)
			throws NullPointerException, IllegalArgumentException {
		Rating ratingToUpdate = new Rating();
		ratingToUpdate = ratingRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Rating" + id + " not found for updating"));

		ratingToUpdate.setMoodysRating(ratingUpdated.getMoodysRating());
		ratingToUpdate.setFitchRating(ratingUpdated.getFitchRating());
		ratingToUpdate.setSandRating(ratingUpdated.getSandRating());
		ratingToUpdate.setOrderNumber(ratingUpdated.getOrderNumber());
		return ratingToUpdate;
	}

	public void deleteRatingById(Integer id) throws NullPointerException {
		ratingRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Rating" + id + " not found for deleting"));
		ratingRepository.deleteById(id);
	}
}
