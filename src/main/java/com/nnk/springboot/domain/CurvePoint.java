package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "curve_point")
public class CurvePoint {
	// TODO: Map columns in data table CURVEPOINT with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull(message="must be not null")
	@Column(name = "curve_id")
	private Integer curveId;

	@Column(name = "as_of_date ")
	private Timestamp asOfDate;

	@Column(name = "term")
	private Double term;
	
	//@DecimalMin("0.0")
	@Positive
	@Column(name = "value")
	private Double value;

	
	@Column(name = "creation_date")
	private Timestamp creationDate;

	public CurvePoint() {

	}

	public CurvePoint(Integer curveId, Double term, Double value) {
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	};

}
