package com.nnk.springboot.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Rating;

@SpringBootTest
public class RatingRepositoryTest {
	private Rating rating;
	
	@Autowired
	private IRatingRepository ratingRepository;

	@BeforeEach
	public void setUpPerTest() {
		rating = new Rating("Moodys Rating", "Sand Rating", "Fitch Rating", 10);
		ratingRepository.save(rating);
	}

	@DisplayName("test for save Rating operation")
	@Test
	public void givenRatingObject_whenSave_thenReturnSavedRating() throws Exception {
		try {
			Rating rating = new Rating("Moodys Rating", "Sand Rating", "Fitch Rating", 12);
			rating = ratingRepository.save(rating);
			assertNotNull(rating.getId());
			assertTrue(rating.getOrderNumber() == 12);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for update Rating operation")
	@Test
	public void givenRatingObject_whenUpdate_thenReturnUpdatedRating() throws Exception {
		try {
			rating.setOrderNumber(20);
			rating = ratingRepository.save(rating);
			assertTrue(rating.getOrderNumber() == 20);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get all Ratings operation")
	@Test
	public void givenRatingList_whenFindAll_thenReturnAllRatings() throws Exception {
		try {
			List<Rating> listResult = ratingRepository.findAll();
			assertTrue(listResult.size() > 0);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get Rating by id  operation")
	@Test
	public void givenRatingObject_whenFindById_thenReturnRating() throws Exception {
		try {
			rating = ratingRepository.save(rating);
			Integer id = rating.getId();
			Rating result = ratingRepository.findById(id).get();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for delete Rating operation")
	@Test
	public void givenRatingObject_whenDeleteById_thenRemoveRating() throws Exception {
		try {
			rating = ratingRepository.save(rating);
			Integer id = rating.getId();
			ratingRepository.deleteById(id);
			Optional<Rating> ratingList = ratingRepository.findById(id);
			assertFalse(ratingList.isPresent());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}	
}
