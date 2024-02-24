package com.nnk.springboot.domain;

import com.nnk.springboot.utils.Constants;
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
@Table(name = "users")
public class User {
	private final String REGEX_PWD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "Username " + ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = " username")
	private String username;

	@Pattern(regexp = ConstantsValidation.REGEX_PWD, message = "must contain at least 8 characters, an uppercase letter, a symbol, a number")
	@NotBlank(message = "Password " + ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = " password")
	private String password;

	@NotBlank(message = "FullName " + ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = " fullname")
	private String fullName;

	@NotBlank(message = "Role " + ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = " role")
	private String role;
	
}
