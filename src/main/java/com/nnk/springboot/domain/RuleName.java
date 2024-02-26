package com.nnk.springboot.domain;

import com.nnk.springboot.utils.ConstantsValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
	@Pattern(regexp=ConstantsValidation.REGEX_CHARACTER, message=ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@NotBlank(message = "Name "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "name")
	private String name;
	
	@Pattern(regexp=ConstantsValidation.REGEX_CHARACTER, message=ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@NotBlank(message = "Description "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "description")
	private String description;
	
	@NotBlank(message = "Json "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "json")
	private String json;
	
	@Pattern(regexp=ConstantsValidation.REGEX_CHARACTER, message=ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@NotBlank(message = "Template "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "template")
	private String template;

	@NotBlank(message = "SQL Str "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "sql_str ")
	private String sqlStr;

	@NotBlank(message = "SQL Part "+ConstantsValidation.ERROR_BLANK_MSG )
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
