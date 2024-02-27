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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDTO;
import com.nnk.springboot.repositories.IBidListRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class BidListServiceTest {
	@Autowired
	private BidListService bidListServiceUnderTest;

	@MockBean
	private IBidListRepository bidListRepository;
	private BidList bidList;

	@BeforeEach
	public void init() {
		bidList = new BidList();
		bidList.setBidListId(1);
		bidList.setType("type test");
		bidList.setAccount("account test");
		bidList.setBidQuantity(14.0);
		List<BidList> allBidList = new ArrayList<BidList>();
		allBidList.add(bidList);
		doThrow(new NullPointerException()).when(bidListRepository).deleteById(any(Integer.class));
	}

	@AfterEach
	public void reset() throws Exception {
		bidListServiceUnderTest.deleteAllBids();
	}

	@Test
	void testAddBid() throws Exception {
		try {

			BidList bidListCreated = new BidList();
			bidListCreated.setBidListId(2);
			bidListCreated.setType("type test");
			bidListCreated.setAccount("account test");
			bidListCreated.setBidQuantity(14.0);
			when(bidListRepository.save(any(BidList.class))).thenReturn(bidListCreated);

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
			when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);
			bidListServiceUnderTest.addBid(bidList);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> bidListServiceUnderTest.addBid(bidList));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddBid_WithEmptyData() throws Exception {
		try {
			bidList = null;
			bidListServiceUnderTest.addBid(bidList);

			assertNull(bidListServiceUnderTest.getBidById(1));
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> bidListServiceUnderTest.addBid(new BidList()));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> bidListServiceUnderTest.addBid(new BidList()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBid() throws Exception {
		try {
			bidListServiceUnderTest.getBidById(1).setAccount("account updated");
			when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

			BidList result = bidListServiceUnderTest.updateBidById(1, bidList);
			assertAll("assertion data bidlist created", () -> {
				assertNotNull(result);
				assertEquals("account updated", result.getAccount());
				assertEquals("type test", result.getType());
				assertEquals(14.0, result.getBidQuantity());
			});
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> bidListServiceUnderTest.updateBidById(1, bidList));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateBid_WithBidNotFound() throws Exception {
		try {
			doThrow(new NullPointerException()).when(bidListRepository).findById(15);
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
			doThrow(new NullPointerException()).when(bidListRepository).findAll();

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
			when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

			BidList result = bidListServiceUnderTest.getBidById(1);
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void tesGetBidById_WithBidNotFound() throws Exception {
		try {
			BidList result = bidListServiceUnderTest.getBidById(10);
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
