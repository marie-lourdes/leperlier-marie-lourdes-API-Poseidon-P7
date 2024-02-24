package com.nnk.springboot.domain;

import com.nnk.springboot.utils.Constants;
import com.nnk.springboot.utils.ConstantsErrorMessage;

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
@Table(name ="users")
public class User {
	private final String REGEX_PWD="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @NotBlank(message = "Username"+ConstantsErrorMessage.ERROR_BLANK_DATA)
    @Column(name=" username")
    private String username;
    
    @Pattern(regexp=Constants.REGEX_PWD,message = "must contain at least 8 characters, an uppercase letter, a symbol, a number")
    @NotBlank(message = "Password"+ConstantsErrorMessage.ERROR_BLANK_DATA)
    @Column(name=" password")
    private String password;
    
    @NotBlank(message = "FullName"+ConstantsErrorMessage.ERROR_BLANK_DATA)
    @Column(name=" fullname")
    private String fullname;
    
    @NotBlank(message = "Role"+ConstantsErrorMessage.ERROR_BLANK_DATA)
    @Column(name=" role")
    private String role;
}
