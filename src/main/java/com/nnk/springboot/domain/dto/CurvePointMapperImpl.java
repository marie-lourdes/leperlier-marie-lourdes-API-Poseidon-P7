package com.nnk.springboot.domain.dto;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.CurvePoint;

@Component
public class CurvePointMapperImpl implements IMapper< CurvePointDTO,  CurvePoint> {
	
	public CurvePointDTO curvePointToCurvePointDTO(CurvePoint curvePoint) {
		return this.entityToObjectDTO(curvePoint);
	}
	
	@Override
	public CurvePointDTO entityToObjectDTO( CurvePoint curvePoint) {
		Integer id = curvePoint.getId();
		Integer curveId= curvePoint.getCurveId();
		Double term =curvePoint.getTerm();
		Double value = curvePoint.getValue();

		return new CurvePointDTO(id,curveId, term, value);
	}
}
