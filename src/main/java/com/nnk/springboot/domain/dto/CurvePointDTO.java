package com.nnk.springboot.domain.dto;

import lombok.Data;

@Data
public class CurvePointDTO {
	private Integer id;
	private Integer curveId;
	private Double term;
	private Double value;

	public CurvePointDTO() {
	}

	public CurvePointDTO(Integer id, Integer curveId, Double term, Double value) {
		this.id = id;
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	@Override
	public String toString() {
		return "CurvePointDTO {" + "id:" + id + ", curveId :" + curveId + ", term:" + term + ", value:" + value + "}";
	}
}
