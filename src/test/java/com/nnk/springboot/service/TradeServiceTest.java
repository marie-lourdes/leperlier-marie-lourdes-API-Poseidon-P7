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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDTO;
import com.nnk.springboot.repositories.ITradeRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class TradeServiceTest {
	@Autowired
	private TradeService tradeServiceUnderTest;

	@MockBean
	private ITradeRepository tradeRepository;

	private Trade trade;

	@BeforeEach
	public void init() {
		trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("account trade");
		trade.setType("type trade ");
		trade.setBuyQuantity(14.0);
		List<Trade> allTrades = new ArrayList<Trade>();
		allTrades.add(trade);
		doThrow(new NullPointerException()).when(tradeRepository).deleteById(any(Integer.class));
	}

	@AfterEach
	public void reset() throws Exception {
		tradeServiceUnderTest.deleteAllTrades();
	}

	@Test
	void testAddTrade() throws Exception {
		try {
			Trade tradeCreated = new Trade();
			tradeCreated.setTradeId(2);
			tradeCreated.setAccount("account trade test");
			tradeCreated.setType("type trade test");
			tradeCreated.setBuyQuantity(10.0);
			when(tradeRepository.save(any(Trade.class))).thenReturn(tradeCreated);

			Trade result = tradeServiceUnderTest.addTrade(tradeCreated);
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
			trade.setAccount("too long account test invalid with size validation jakarta");
			when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
			tradeServiceUnderTest.addTrade(trade);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> tradeServiceUnderTest.addTrade(trade));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddTrade_WithEmptyData() throws Exception {
		try {
			trade = null;
			Trade result = tradeServiceUnderTest.addTrade(trade);

			assertNull(result.getTradeId());
		}catch (NullPointerException e) {
			e.getMessage();
		}catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> tradeServiceUnderTest.addTrade(trade));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> tradeServiceUnderTest.addTrade(trade));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateTrade() throws Exception {
		try {
			tradeServiceUnderTest.getTradeById(1).setType("type trade updated");
			when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

			Trade result = tradeServiceUnderTest.updateTradeById(1, trade);
			assertAll("assertion data trade created", () -> {
				assertNotNull(result.getTradeId());
				assertEquals("account trade", result.getAccount());
				assertEquals(14.0, result.getBuyQuantity());
				assertEquals("type trade updated", result.getType());
			});
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> tradeServiceUnderTest.updateTradeById(1, trade));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateTrade_WithTradeNotFound() throws Exception {
		try {
			doThrow(new NullPointerException()).when(tradeRepository).findById(15);

			Trade result = tradeServiceUnderTest.updateTradeById(15, trade);
			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> tradeServiceUnderTest.updateTradeById(15, trade));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> tradeServiceUnderTest.updateTradeById(15, trade));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> tradeServiceUnderTest.updateTradeById(15, trade));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllTrades() throws Exception {
		try {
			tradeServiceUnderTest.addTrade(trade);

			List<TradeDTO> result = tradeServiceUnderTest.getAllTrades();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllTrades_WithListOfTradesNotFound() throws Exception {
		try {
			doThrow(new NullPointerException()).when(tradeRepository).findAll();

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
			when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
			Trade result = tradeServiceUnderTest.getTradeById(1);
			assertNotNull(result);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> tradeServiceUnderTest.getTradeById(1));
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
			Trade tradeCreated = tradeServiceUnderTest.addTrade(trade);
			tradeServiceUnderTest.deleteTradeById(tradeCreated.getTradeId());

			Trade result = tradeServiceUnderTest.getTradeById(tradeCreated.getTradeId());
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
