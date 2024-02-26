package com.nnk.springboot.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nnk.springboot.domain.BidList;

@SpringBootTest
public class BidRepositoryTest {

	private BidList bid;

	@Autowired
	private IBidListRepository bidListRepository;

	@BeforeEach
	public void setUpPerTest() {
		bid = new BidList("Account Test", "Type Test", 10d);
		bidListRepository.save(bid);
	}

	@DisplayName("test for save bid operation")
	@Test
	public void givenBidObject_whenSave_thenReturnSavedBid() throws Exception {
		try {
			BidList bid = new BidList("Account Test", "Type Test", 12d);
			bid = bidListRepository.save(bid);
			assertNotNull(bid.getBidListId());
			assertEquals(bid.getBidQuantity(), 12d);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for update bid operation")
	@Test
	public void givenBidObject_whenUpdate_thenReturnUpdatedBid() throws Exception {
		try {
			bid.setBidQuantity(20d);
			bid = bidListRepository.save(bid);
			assertEquals(bid.getBidQuantity(), 20d);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get all bids operation")
	@Test
	public void givenBidList_whenFindAll_thenReturnAllBids() throws Exception {
		try {
			List<BidList> listResult = bidListRepository.findAll();
			assertTrue(listResult.size() > 0);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get bid by id  operation")
	@Test
	public void givenBidObject_whenFindById_thenReturnBid() throws Exception {
		try {
			bid = bidListRepository.save(bid);
			Integer id = bid.getBidListId();
			BidList result = bidListRepository.findById(id).get();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for delete bid operation")
	@Test
	public void givenBidObject_whenDeleteById_thenRemoveBid() throws Exception {
		try {
			bid = bidListRepository.save(bid);
			Integer id = bid.getBidListId();
			bidListRepository.deleteById(id);
			Optional<BidList> bidList = bidListRepository.findById(id);
			assertFalse(bidList.isPresent());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}