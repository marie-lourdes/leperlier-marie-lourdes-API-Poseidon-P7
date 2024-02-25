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

import com.nnk.springboot.domain.CurvePoint;

@SpringBootTest
public class CurvePointRepositoryTest {
	private CurvePoint curvePoint;
	@Autowired
	private ICurvePointRepository curvePointRepository;

	@BeforeEach
	public void setUpPerTest() {
		curvePoint = new CurvePoint(10, 10d, 30d);
	}

	@DisplayName("test for save curve point operation")
	@Test
	public void givenCurvePointObject_whenSave_thenReturnSavedCurvePoint() throws Exception {
		try {
			curvePoint = curvePointRepository.save(curvePoint);
			assertNotNull(curvePoint.getId());
			assertTrue(curvePoint.getCurveId() == 10);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for update curve point operation")
	@Test
	public void givenCurvePointObject_whenUpdate_thenReturnUpdatedCurvePoint() throws Exception {
		try {
			curvePoint.setCurveId(20);
			curvePoint = curvePointRepository.save(curvePoint);
			assertTrue(curvePoint.getCurveId() == 20);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get all Curve points operation")
	@Test
	public void givenCurvePointList_whenFindAll_thenReturnAllCurvePoints() throws Exception {
		try {
			List<CurvePoint> listResult = curvePointRepository.findAll();
			assertTrue(listResult.size() > 0);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get Curve point  by id  operation")
	@Test
	public void givenCurvePointObject_whenFindById_thenReturnCurvePoint() throws Exception {
		try {
			curvePoint = curvePointRepository.save(curvePoint);
			Integer id = curvePoint.getId();
			CurvePoint result = curvePointRepository.findById(id).get();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for delete Curve point operation")
	@Test
	public void givenCurvePointObject_whenDeleteById_thenRemoveCurvePoint() throws Exception {
		try {
			curvePoint = curvePointRepository.save(curvePoint);
			Integer id = curvePoint.getId();
			curvePointRepository.deleteById(id);
			Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
			assertFalse(curvePointList.isPresent());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
