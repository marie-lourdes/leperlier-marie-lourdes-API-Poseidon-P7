package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "curve_point")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
	
	@NotNull
	@Column(name = "curve_id")
	private Integer curveId;
	
	@Column(name = "as_of_date ")
	private Timestamp asOfDate ;
	
	@NotNull
	@Column(name = "term")
	private Double  term;
	
	@NotNull
	@Column(name = "  creation_date")
	private Timestamp   creationDate;
	
}
