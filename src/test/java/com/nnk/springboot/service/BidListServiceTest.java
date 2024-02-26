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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDTO;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class BidListServiceTest {
	@Autowired
	private BidListService bidListServiceUnderTest;

	private BidList bidList;

	@BeforeEach
	public void init() {
		bidList = new BidList();
		bidList.setBidListId(1);
		bidList.setType("type test");
		bidList.setAccount("account test");
		bidList.setBidQuantity(14.0);
		bidListServiceUnderTest.addBid(bidList);
	}

	@Test
	void testAddBid() throws Exception {
		try {
			BidList bidListCreated = new BidList();
			bidListCreated.setType("type test");
			bidListCreated.setAccount("account test");
			bidListCreated.setBidQuantity(14.0);
			BidList result = bidListServiceUnderTest.addBid(bidListCreated);

			assertAll("assertion data bidlist created", () -> {
				assertNotNull(result.getBidListId());
				assertEquals("account test", result.getAccount());
				assertEquals("type test", result.getType());
				assertEquals(14.0, result.getBidQuantity());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddBid_WithInvalidData() throws Exception {
		try {
			bidList.setType("");
			BidList result = bidListServiceUnderTest.addBid(bidList);
			
			assertEquals(result.getBidListId(), bidListServiceUnderTest.getBidById(result.getBidListId()));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> bidListServiceUnderTest.addBid(bidList));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddBid_WithEmptyData() throws Exception {
		try {
			bidList.setType("");
			BidList result = bidListServiceUnderTest.addBid(bidList);
			
			assertNull(result.getBidListId());
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> bidListServiceUnderTest.addBid(bidList));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> bidListServiceUnderTest.addBid(bidList));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBid() throws Exception {
		try {
			bidList.setAccount("account updated");
			BidList BidListToUpdateTest = bidListServiceUnderTest.addBid(bidList);
			
			BidList result = bidListServiceUnderTest.updateBidById(BidListToUpdateTest.getBidListId(), bidList);
			assertAll("assertion data bidlist created", () -> {
				assertNotNull(result.getBidListId());
				assertEquals("account updated", result.getAccount());
				assertEquals("type test", result.getType());
				assertEquals(14.0, result.getBidQuantity());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBid_WithBidNotFound() throws Exception {
		try {
			BidList result = bidListServiceUnderTest.updateBidById(15, bidList);
			
			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> bidListServiceUnderTest.updateBidById(15, bidList));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> bidListServiceUnderTest.updateBidById(15, bidList));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> bidListServiceUnderTest.updateBidById(15, bidList));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllBids() throws Exception {
		try {
			bidListServiceUnderTest.addBid(bidList);
			
			List<BidListDTO> result = bidListServiceUnderTest.getAllBids();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllBids_WithListOfBidsNotFound() throws Exception {
		try {
			bidListServiceUnderTest.deleteAllBids();
			
			List<BidListDTO> result = bidListServiceUnderTest.getAllBids();
			assertTrue(result.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> bidListServiceUnderTest.getAllBids());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetBidById() throws Exception {
		try {
			BidList result = bidListServiceUnderTest.addBid(bidList);
			result = bidListServiceUnderTest.getBidById(result.getBidListId());
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void tesGetBidById_WithBidNotFound() throws Exception {
		try {
			bidListServiceUnderTest.deleteAllBids();
			
			BidList result = bidListServiceUnderTest.getBidById(1);
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteBidById() throws Exception {
		try {
			BidList bidListCreated = bidListServiceUnderTest.addBid(bidList);
			bidListServiceUnderTest.deleteBidById(bidListCreated.getBidListId());
			
			BidList result = bidListServiceUnderTest.getBidById(bidListCreated.getBidListId());
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteBidById_WithNoExistingBid() throws Exception {
		try {
			bidListServiceUnderTest.deleteBidById(15);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> bidListServiceUnderTest.deleteBidById(15));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
