package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDTO;
import com.nnk.springboot.repositories.IBidListRepository;
import com.nnk.springboot.service.BidListService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class BidListServiceTest {
	@Autowired
	private BidListService bidListServiceUnderTest;

	private BidList bidList;
	private BidListDTO bidListDTO;

	@BeforeEach
	public void init() {
		// bidListServiceUnderTest = new BidListService();
		bidList = new BidList();
		bidList.setType("type test");
		bidList.setAccount("account test");
		bidList.setBidQuantity(14.0);
		bidListDTO = new BidListDTO();
		bidListDTO.setAccount("account test");
		bidListDTO.setType("type test");
		bidListDTO.setBidQuantity(14.0);
	}

	@Test
	void testAddBid() throws Exception {
		try {
			BidList result = bidListServiceUnderTest.addBid(bidList);

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

	/*
	 * @Test void testUpdateBid() throws Exception{ try { bidList.setType("");
	 * BidList result = bidListServiceUnderTest.addBid(bidList);
	 * assertNull(result.getBidListId()); } catch (IllegalArgumentException e) {
	 * assertThrows(IllegalArgumentException.class, ()
	 * ->bidListServiceUnderTest.addBid(bidList)); } catch
	 * (ConstraintViolationException e) {
	 * assertThrows(ConstraintViolationException.class, ()
	 * ->bidListServiceUnderTest.addBid(bidList));
	 * 
	 * } catch (AssertionError e) { fail(e.getMessage()); } }
	 */
}
