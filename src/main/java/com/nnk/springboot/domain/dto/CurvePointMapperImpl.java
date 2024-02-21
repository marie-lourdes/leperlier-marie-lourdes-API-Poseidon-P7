package com.nnk.springboot.domain.dto;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.CurvePoint;

@Component
public class CurvePointMapperImpl implements IMapper< CurvePointDTO,  CurvePoint> {
	
	public CurvePointDTO curvePointToCurvePointDTO(CurvePoint curvePoint) {
		return this.entityToObjectDTO(curvePoint);
	}
	
	@Override
	public CurvePointDTO entityToObjectDTO( CurvePoint bidList) {
		Integer id = bidList.getId();
		Integer curveId= bidList.getCurveId();
		Double term = bidList.getTerm();
		Double value = bidList.getValue();

		return new CurvePointDTO(id,curveId, term, value);
	}
}
