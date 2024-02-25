package com.nnk.springboot.domain.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
	private Integer id;
	private String username;
	private String password;
	private String role;

	public UserLoginDTO() {
	}

	public UserLoginDTO(Integer id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "id:" + id +  ", username:" + username + ", password:" + password + ", role:" + role + "}";
	}
}