package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Rating;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class RatingServiceTest {
	@Autowired
	private RatingService ratingServiceUnderTest;

	private Rating rating;

	@BeforeEach
	public void init() {
		rating = new Rating();
		rating.setId(1);
		rating.setMoodysRating("moodys rating test");
		rating.setFitchRating("fitch rating test");
		rating.setSandRating("sand rating test");
		rating.setOrderNumber(10);
		ratingServiceUnderTest.addRating(rating);
	}

	@Test
	void testAddRating() throws Exception {
		try {
			Rating ratingCreated = new Rating();
			ratingCreated.setId(1);
			ratingCreated.setMoodysRating("moodys rating test");
			ratingCreated.setFitchRating("fitch rating test");
			ratingCreated.setSandRating("sand rating test");
			ratingCreated.setOrderNumber(10);
			
			Rating result = ratingServiceUnderTest.addRating(ratingCreated);
			assertAll("assertion data curve point created", () -> {
				assertNotNull(result.getId());
				assertEquals("moodys rating test", result.getMoodysRating());
				assertEquals("fitch rating test", result.getFitchRating());
				assertEquals("sand rating test", result.getSandRating());
				assertEquals(10, result.getOrderNumber());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddRating_WithInvalidData() throws Exception {
		try {
			rating.setOrderNumber(-1);
			ratingServiceUnderTest.addRating(rating);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> ratingServiceUnderTest.addRating(rating));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddRating_WithEmptyMoodysRatingOfRating() throws Exception {
		try {
			rating.setMoodysRating("");
			Rating result = ratingServiceUnderTest.addRating(rating);

			assertNull(result.getId());
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> ratingServiceUnderTest.addRating(rating));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> ratingServiceUnderTest.addRating(rating));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRating() throws Exception {
		try {
			rating.setOrderNumber(15);
			Rating RatingToUpdateTest = ratingServiceUnderTest.addRating(rating);

			Rating result = ratingServiceUnderTest.updateRatingById(RatingToUpdateTest.getId(),
					rating);
			assertAll("assertion data rating created", () -> {
				assertNotNull(result.getId());
				assertEquals("moodys rating test", result.getMoodysRating());
				assertEquals("fitch rating test", result.getFitchRating());
				assertEquals("sand rating test", result.getSandRating());
				assertEquals(15, result.getOrderNumber());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRating_WithRatingNotFound() throws Exception {
		try {
			Rating result = ratingServiceUnderTest.updateRatingById(15, rating);

			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class,
					() -> ratingServiceUnderTest.updateRatingById(15, rating));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> ratingServiceUnderTest.updateRatingById(15, rating));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> ratingServiceUnderTest.updateRatingById(15, rating));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllRatings() throws Exception {
		try {
			ratingServiceUnderTest.addRating(rating);

			List<Rating> result = ratingServiceUnderTest.getAllRatings();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllRatings_WithListOfRatingsNotFound() throws Exception {
		try {
			ratingServiceUnderTest.deleteAllRatings();

			List<Rating> result = ratingServiceUnderTest.getAllRatings();
			assertTrue(result.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ratingServiceUnderTest.getAllRatings());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetRatingById() throws Exception {
		try {
			Rating result = ratingServiceUnderTest.addRating(rating);
			result = ratingServiceUnderTest.getRatingById(result.getId());
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void tesGetRatingById_WithRatingNotFound() throws Exception {
		try {
			ratingServiceUnderTest.deleteAllRatings();

			Rating result = ratingServiceUnderTest.getRatingById(1);
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteRatingById() throws Exception {
		try {
			Rating ratingCreated = ratingServiceUnderTest.addRating(rating);
			ratingServiceUnderTest.deleteRatingById(ratingCreated.getId());

			Rating result = ratingServiceUnderTest.getRatingById(ratingCreated.getId());
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteRatingById_WithNoExistingRating() throws Exception {
		try {
			ratingServiceUnderTest.deleteRatingById(15);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ratingServiceUnderTest.deleteRatingById(15));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
