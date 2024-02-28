package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.IRatingRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class RatingServiceTest {
	@Autowired
	private RatingService ratingServiceUnderTest;

	@MockBean
	private IRatingRepository ratingRepository;

	private Rating rating;

	@BeforeEach
	public void init() {
		rating = new Rating();
		rating.setId(1);
		rating.setMoodysRating("moodys rating test");
		rating.setFitchRating("fitch rating test");
		rating.setSandRating("sand rating test");
		rating.setOrderNumber(10);
		List<Rating> allRatings = new ArrayList<Rating>();
		allRatings.add(rating);
		doThrow(new NullPointerException()).when(ratingRepository).deleteById(any(Integer.class));
	}

	@AfterEach
	public void reset() throws Exception {
		ratingServiceUnderTest.deleteAllRatings();
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
			when(ratingRepository.save(any(Rating.class))).thenReturn(ratingCreated);

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
			when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
			ratingServiceUnderTest.addRating(rating);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> ratingServiceUnderTest.addRating(rating));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddRating_WithEmptyData() throws Exception {
		try {
			rating = null;
			Rating result = ratingServiceUnderTest.addRating(rating);

			assertNull(result.getId());
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> ratingServiceUnderTest.addRating(rating));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> ratingServiceUnderTest.addRating(new Rating()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRating() throws Exception {
		try {
			rating.setOrderNumber(15);
			ratingServiceUnderTest.getRatingById(1).setOrderNumber(15);
			when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

			Rating result = ratingServiceUnderTest.updateRatingById(1, rating);
			assertAll("assertion data rating created", () -> {
				assertNotNull(result.getId());
				assertEquals("moodys rating test", result.getMoodysRating());
				assertEquals("fitch rating test", result.getFitchRating());
				assertEquals("sand rating test", result.getSandRating());
				assertEquals(15, result.getOrderNumber());
			});
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ratingServiceUnderTest.updateRatingById(1, rating));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateRating_WithRatingNotFound() throws Exception {
		try {
			doThrow(new NullPointerException()).when(ratingRepository).findById(15);
			Rating result = ratingServiceUnderTest.updateRatingById(15, rating);

			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> ratingServiceUnderTest.updateRatingById(15, rating));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ratingServiceUnderTest.updateRatingById(15, rating));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> ratingServiceUnderTest.updateRatingById(15, rating));
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
			doThrow(new NullPointerException()).when(ratingRepository).findAll();

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
			when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

			Rating result = ratingServiceUnderTest.getRatingById(1);
			assertNotNull(result);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> ratingServiceUnderTest.getRatingById(1));
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
