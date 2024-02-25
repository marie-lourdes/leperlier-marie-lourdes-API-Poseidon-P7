package com.nnk.springboot.domain.dto;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.User;

@Component
public class UserLoginMapperImpl implements IMapper<UserLoginDTO, User> {
	public UserLoginDTO userToUserLoginDTO(User user) {
		return this.entityToObjectDTO(user);
	}

	@Override
	public UserLoginDTO entityToObjectDTO(User user) {
		Integer id = user.getId();
		String username = user.getUsername();
		String password = user.getPassword();
		String role = user.getRole();

		return new UserLoginDTO(id, username, password, role);
	}
}
