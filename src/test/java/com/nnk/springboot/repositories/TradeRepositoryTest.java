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

import com.nnk.springboot.domain.Trade;

@SpringBootTest
public class TradeRepositoryTest {
	private Trade trade;

	@Autowired
	private ITradeRepository tradeRepository;

	@BeforeEach
	public void setUpPerTest() {
		trade = new Trade("Trade Account", "Type");
		tradeRepository.save(trade);
	}

	@DisplayName("test for save Trade operation")
	@Test
	public void givenTradeObject_whenSave_thenReturnSavedTrade() throws Exception {
		try {
			Trade trade = new Trade("Trade Account test", "Type");
			trade = tradeRepository.save(trade);
			assertNotNull(trade.getTradeId());
			assertTrue(trade.getAccount().equals("Trade Account test"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for update Trade operation")
	@Test
	public void givenTradeObject_whenUpdate_thenReturnUpdatedTrade() throws Exception {
		try {
			trade.setAccount("Trade Account Update");
			trade = tradeRepository.save(trade);
			assertTrue(trade.getAccount().equals("Trade Account Update"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get all Trades operation")
	@Test
	public void givenTradeList_whenFindAll_thenReturnAllTrades() throws Exception {
		try {
			List<Trade> listResult = tradeRepository.findAll();
			assertTrue(listResult.size() > 0);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get Trade by id  operation")
	@Test
	public void givenTradeObject_whenFindById_thenReturnTrade() throws Exception {
		try {
			trade = tradeRepository.save(trade);
			Integer id = trade.getTradeId();
			Trade result = tradeRepository.findById(id).get();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for delete Trade operation")
	@Test
	public void givenTradeObject_whenDeleteById_thenRemoveTrade() throws Exception {
		try {
			trade = tradeRepository.save(trade);
			Integer id = trade.getTradeId();
			tradeRepository.deleteById(id);
			Optional<Trade> tradeList = tradeRepository.findById(id);
			assertFalse(tradeList.isPresent());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
