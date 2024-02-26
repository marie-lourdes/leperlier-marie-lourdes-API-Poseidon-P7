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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

import jakarta.validation.ConstraintViolationException;
@SpringBootTest
class TradeServiceTest {
	@Autowired
	private TradeService tradeServiceUnderTest;

	private Trade rating;

	@BeforeEach
	public void init() {
		rating = new Trade();
		rating.setTradeId(1);
		rating.setAccount("account trade");
		rating.setType("type trade ");
		rating.setBuyQuantity(14.0);
		tradeServiceUnderTest.addTrade(rating);
	}

	@Test
	void testAddTrade() throws Exception {
		try {
			Trade ratingCreated = new Trade();
			ratingCreated.setTradeId(2);
			ratingCreated.setAccount("account trade test");
			ratingCreated.setType("type trade test");
			ratingCreated.setBuyQuantity(10.0);
			
			Trade result = tradeServiceUnderTest.addTrade(ratingCreated);
			assertAll("assertion data trade created", () -> {
				assertNotNull(result.getTradeId());
				assertEquals("account trade test", result.getAccount());
				assertEquals("type trade test", result.getType());
				assertEquals(10.0, result.getBuyQuantity());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddTrade_WithInvalidData() throws Exception {
		try {
			rating.setAccount("too long account test invalid with size validation jakarta" );
			tradeServiceUnderTest.addTrade(rating);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> tradeServiceUnderTest.addTrade(rating));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddTrade_WithEmptyData() throws Exception {
		try {
			rating.setType("");
			Trade result = tradeServiceUnderTest.addTrade(rating);

			assertNull(result.getTradeId());
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> tradeServiceUnderTest.addTrade(rating));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> tradeServiceUnderTest.addTrade(rating));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateTrade() throws Exception {
		try {
			rating.setType("type trade updated");
			Trade TradeToUpdateTest = tradeServiceUnderTest.addTrade(rating);

			Trade result = tradeServiceUnderTest.updateTradeById(TradeToUpdateTest.getTradeId(),
					rating);
			assertAll("assertion data rating created", () -> {
				assertNotNull(result.getTradeId());
				assertEquals("account trade", result.getAccount());
				assertEquals(14.0, result.getBuyQuantity());
				assertEquals("type trade updated", result.getType());		
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateTrade_WithTradeNotFound() throws Exception {
		try {
			Trade result = tradeServiceUnderTest.updateTradeById(15, rating);

			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class,
					() -> tradeServiceUnderTest.updateTradeById(15, rating));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> tradeServiceUnderTest.updateTradeById(15, rating));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> tradeServiceUnderTest.updateTradeById(15, rating));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllTrades() throws Exception {
		try {
			tradeServiceUnderTest.addTrade(rating);

			List<TradeDTO> result = tradeServiceUnderTest.getAllTrades();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllTrades_WithListOfTradesNotFound() throws Exception {
		try {
			tradeServiceUnderTest.deleteAllTrades();

			List<TradeDTO> result = tradeServiceUnderTest.getAllTrades();
			assertTrue(result.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> tradeServiceUnderTest.getAllTrades());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetTradeById() throws Exception {
		try {
			Trade result = tradeServiceUnderTest.addTrade(rating);
			result = tradeServiceUnderTest.getTradeById(result.getTradeId());
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void tesGetTradeById_WithTradeNotFound() throws Exception {
		try {
			tradeServiceUnderTest.deleteAllTrades();

			Trade result = tradeServiceUnderTest.getTradeById(1);
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteTradeById() throws Exception {
		try {
			Trade ratingCreated = tradeServiceUnderTest.addTrade(rating);
			tradeServiceUnderTest.deleteTradeById(ratingCreated.getTradeId());

			Trade result = tradeServiceUnderTest.getTradeById(ratingCreated.getTradeId());
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteTradeById_WithNoExistingTrade() throws Exception {
		try {
			tradeServiceUnderTest.deleteTradeById(15);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> tradeServiceUnderTest.deleteTradeById(15));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
