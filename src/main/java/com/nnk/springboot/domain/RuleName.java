package com.nnk.springboot.domain;

import com.nnk.springboot.utils.ConstantsErrorMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "rule_name")
public class RuleName {
	// TODO: Map columns in data table RULENAME with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "Name "+ConstantsErrorMessage.ERROR_BLANK_DATA)
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Description "+ConstantsErrorMessage.ERROR_BLANK_DATA)
	@Column(name = "description")
	private String description;

	@NotBlank(message = "Json "+ConstantsErrorMessage.ERROR_BLANK_DATA)
	@Column(name = "json")
	private String json;

	@NotBlank(message = "Template "+ConstantsErrorMessage.ERROR_BLANK_DATA)
	@Column(name = "template")
	private String template;

	@NotBlank(message = "SQL Str "+ConstantsErrorMessage.ERROR_BLANK_DATA)
	@Column(name = "sql_str ")
	private String sqlStr;

	@NotBlank(message = "SQL Part "+ConstantsErrorMessage.ERROR_BLANK_DATA)
	@Column(name = "sql_part ")
	private String sqlPart;

	public RuleName() {
	}

	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

	@Override
	public String toString() {
		return "Rating{" + "id:" + id + ", name :" + name + ", description:" + description + ",json" + json
				+ ", template:" + template + ",sqlStr" + sqlStr + ",sqlPart" + sqlPart + "}";
	}
}
