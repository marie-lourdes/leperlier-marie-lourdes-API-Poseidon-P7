package com.nnk.springboot.domain.dto;

import lombok.Data;

@Data
public class UserDTO {
	private Integer id;
	private String username;
	private String fullName;
	private String role;
	
	public UserDTO(){} 
	
	public UserDTO(Integer id,String username,String fullName,String role){
		this.id=id;
		this.username=username;
		this.fullName=fullName;
		this.role=role;
	} 

	@Override
	public String toString() {
		return "UserDTO{" + "id:" + id+ ", username:" + username  + ", fullName:" + fullName+", role:" + role+"}";
	}
}