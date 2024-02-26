package com.nnk.springboot.domain.dto;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.User;

@Component(value = "userMapperImpl")
public class UserMapperImpl implements IMapper< UserDTO,  User> {
	
	public UserDTO userToUserDTO(User user) {
		return this.entityToObjectDTO(user);
	}
	
	@Override
	public UserDTO entityToObjectDTO( User user) {
		Integer id = user.getId();
		String username= user.getUsername();
		String fullName =user.getFullName();
		String role= user.getRole();

		return new UserDTO(id,username,fullName, role);
	}
}
