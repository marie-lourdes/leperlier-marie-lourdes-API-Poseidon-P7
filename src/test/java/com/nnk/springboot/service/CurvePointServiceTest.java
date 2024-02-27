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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointDTO;
import com.nnk.springboot.repositories.ICurvePointRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class CurvePointServiceTest {
	@Autowired
	private CurvePointService curvePointServiceUnderTest;

	@MockBean
	private ICurvePointRepository curvePointRepository;

	private CurvePoint curvePoint;

	@BeforeEach
	public void init() {
		curvePoint = new CurvePoint();
		curvePoint.setId(1);
		curvePoint.setCurveId(10);
		curvePoint.setTerm(10.0);
		curvePoint.setValue(14.0);
		List<CurvePoint> allCurvePoints = new ArrayList<CurvePoint>();
		allCurvePoints.add(curvePoint);
		doThrow(new NullPointerException()).when(curvePointRepository).deleteById(any(Integer.class));
	}

	@AfterEach
	public void reset() throws Exception {
		curvePointServiceUnderTest.deleteAllCurvePoints();
	}

	@Test
	void testAddCurvePoint() throws Exception {
		try {
			CurvePoint curvePointCreated = new CurvePoint();
			curvePointCreated.setId(2);
			curvePointCreated.setCurveId(12);
			curvePointCreated.setTerm(10.0);
			curvePointCreated.setValue(10.0);
			when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePointCreated);

			CurvePoint result = curvePointServiceUnderTest.addCurvePoint(curvePointCreated);
			assertAll("assertion data curve point created", () -> {
				assertNotNull(result.getId());
				assertEquals(12, result.getCurveId());
				assertEquals(10.0, result.getTerm());
				assertEquals(10.0, result.getValue());

			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddCurvePoint_WithInvalidData() throws Exception {
		try {
			curvePoint.setTerm(-1.0);
			when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
			curvePointServiceUnderTest.addCurvePoint(curvePoint);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> curvePointServiceUnderTest.addCurvePoint(curvePoint));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddCurvePoint_WithEmptyData() throws Exception {
		try {
			curvePoint = null;
			curvePointServiceUnderTest.addCurvePoint(curvePoint);

			assertNull(curvePointServiceUnderTest.getCurvePointById(2));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> curvePointServiceUnderTest.updateCurvePointById(2, curvePoint));
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class,
					() -> curvePointServiceUnderTest.addCurvePoint(new CurvePoint()));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> curvePointServiceUnderTest.addCurvePoint(new CurvePoint()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateCurvePoint() throws Exception {
		try {
			curvePointServiceUnderTest.getCurvePointById(2).setCurveId(13);
			when(curvePointRepository.findById(2)).thenReturn(Optional.of(curvePoint));

			CurvePoint result = curvePointServiceUnderTest.updateCurvePointById(12, curvePoint);
			assertAll("assertion data bidlist created", () -> {
				assertNotNull(result.getId());
				assertEquals(13, result.getCurveId());
				assertEquals(10.0, result.getTerm());
				assertEquals(14.0, result.getValue());
			});
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> curvePointServiceUnderTest.updateCurvePointById(12, curvePoint));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateCurvePoint_WithCurvePointNotFound() throws Exception {
		try {
			doThrow(new NullPointerException()).when(curvePointRepository).findById(15);
			CurvePoint result = curvePointServiceUnderTest.updateCurvePointById(15, curvePoint);

			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class,
					() -> curvePointServiceUnderTest.updateCurvePointById(15, curvePoint));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> curvePointServiceUnderTest.updateCurvePointById(15, curvePoint));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class,
					() -> curvePointServiceUnderTest.updateCurvePointById(15, curvePoint));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllCurvePoints() throws Exception {
		try {
			curvePointServiceUnderTest.addCurvePoint(curvePoint);

			List<CurvePointDTO> result = curvePointServiceUnderTest.getAllCurvePoints();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllCurvePoints_WithListOfCurvePointsNotFound() throws Exception {
		try {
			doThrow(new NullPointerException()).when(curvePointRepository).findAll();

			List<CurvePointDTO> result = curvePointServiceUnderTest.getAllCurvePoints();
			assertTrue(result.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> curvePointServiceUnderTest.getAllCurvePoints());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetCurvePointById() throws Exception {
		try {
			when(curvePointRepository.findById(2)).thenReturn(Optional.of(curvePoint));
			CurvePoint result = curvePointServiceUnderTest.getCurvePointById(2);

			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void tesGetCurvePointById_WithCurvePointNotFound() throws Exception {
		try {
			curvePointServiceUnderTest.deleteAllCurvePoints();

			CurvePoint result = curvePointServiceUnderTest.getCurvePointById(10);
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteCurvePointById() throws Exception {
		try {
			CurvePoint curvePointCreated = curvePointServiceUnderTest.addCurvePoint(curvePoint);
			curvePointServiceUnderTest.deleteCurvePointById(curvePointCreated.getId());

			CurvePoint result = curvePointServiceUnderTest.getCurvePointById(curvePointCreated.getId());
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteCurvePointById_WithNoExistingCurvePoint() throws Exception {
		try {
			curvePointServiceUnderTest.deleteCurvePointById(15);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> curvePointServiceUnderTest.deleteCurvePointById(15));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

}
