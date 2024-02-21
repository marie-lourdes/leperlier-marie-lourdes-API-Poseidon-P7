package com.nnk.springboot.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointDTO;
import com.nnk.springboot.domain.dto.CurvePointMapperImpl;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	private static final Logger log = LogManager.getLogger(CurvePointService.class);

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Autowired
	private CurvePointMapperImpl mapper;

	public CurvePoint addCurvePoint(CurvePoint curvePointCreated) throws NullPointerException {
		CurvePoint curvePointRegistered = new CurvePoint();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			if (curvePointCreated != null) {
				if (curvePointCreated.getCurveId() == null) {
					throw new IllegalArgumentException(
							"Empty data getCurveId of CurvePoint" + curvePointCreated + " provided and updated");
				}
				curvePointRegistered.setCurveId(curvePointCreated.getCurveId());
				curvePointRegistered.setTerm(curvePointCreated.getTerm());
				curvePointRegistered.setValue(curvePointCreated.getValue());
				curvePointRegistered.setCreationDate(timestamp);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
		
		curvePointRegistered = curvePointRepository.save(curvePointRegistered);
		return curvePointRegistered;
	}

	public CurvePointDTO getCurvePointById(Integer id) throws NullPointerException {
		CurvePoint curvePointFoundById = curvePointRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("CurvePoint" + id + " not found"));
		CurvePointDTO curvePointDTO = mapper.curvePointToCurvePointDTO(curvePointFoundById);
		return curvePointDTO;
	}

	public List<CurvePointDTO> getAllCurvePoints() throws NullPointerException {
		List<CurvePoint> AllcurvePoints = curvePointRepository.findAll();
		List<CurvePointDTO> allcurvePointDto = new ArrayList<CurvePointDTO>();
		if (AllcurvePoints != null) {
			AllcurvePoints.forEach(curvePoint -> {
				allcurvePointDto.add(mapper.curvePointToCurvePointDTO(curvePoint));
			});
		}

		return allcurvePointDto;
	}

	public CurvePoint updateCurvePointById(Integer id, CurvePoint curvePointUpdated)
			throws NullPointerException, IllegalArgumentException {
		CurvePoint curvePointToUpdate = new CurvePoint();
		curvePointToUpdate = curvePointRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("CurvePoint" + id + " not found for updating"));

		curvePointToUpdate.setCurveId(curvePointUpdated.getCurveId());
		curvePointToUpdate.setTerm(curvePointUpdated.getTerm());
		curvePointToUpdate.setValue(curvePointUpdated.getValue());
		curvePointToUpdate = curvePointRepository.save(curvePointToUpdate);
		return curvePointToUpdate;
	}

	public void deleteCurvePointById(Integer id) throws NullPointerException {
		curvePointRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("CurvePoint" + id + " not found for deleting"));
		curvePointRepository.deleteById(id);
	}

}
