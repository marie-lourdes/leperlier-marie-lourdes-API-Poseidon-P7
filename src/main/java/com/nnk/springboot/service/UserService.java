package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.domain.dto.UserLoginDTO;
import com.nnk.springboot.domain.dto.UserLoginMapperImpl;
import com.nnk.springboot.domain.dto.UserMapperImpl;
import com.nnk.springboot.repositories.IUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapperImpl mapper;

	@Autowired
	private UserLoginMapperImpl userLoginMapper;

	@Autowired
	private ValidatorPasswordImpl validatorPassword;

	public User addUser(User userCreated) throws NullPointerException, IllegalArgumentException {
		User userRegistered = new User();
		boolean hasExistingUser = getAllUsers().removeIf(user -> user.getUsername().equals(userCreated.getUsername()));

		boolean isPasswordUserValid = validatorPassword.validPassword(userCreated.getPassword());
		if (userCreated != null) {
			if (hasExistingUser) {
				throw new IllegalArgumentException("Username  " + userCreated + " already exist");
			} else if (isPasswordUserValid) {
				throw new IllegalArgumentException("Password of User " + userCreated + " provided is incorrect");
			} else
				;
			userRegistered.setUsername(userCreated.getUsername());
			userRegistered.setPassword(userCreated.getPassword());
			userRegistered.setFullName(userCreated.getFullName());
			userRegistered.setRole(userCreated.getRole());
		}
		userRegistered = userRepository.save(userRegistered);
		return userRegistered;
	}

	public User getUserById(Integer id) throws NullPointerException {
		User userFoundById = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("User" + id + " not found"));
		return userFoundById;
	}

	public UserLoginDTO getUserByUserName(String username) throws NullPointerException {
		User userFoundByUserName = userRepository.findByUsername(username)
				.orElseThrow(() -> new NullPointerException("User" + username + " not found"));
		UserLoginDTO userLoginDTO = userLoginMapper.userToUserLoginDTO(userFoundByUserName);
		return userLoginDTO;
	}

	public User getUserEntityById(Integer id) throws NullPointerException {
		User userFoundById = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("User" + id + " not found"));

		return userFoundById;
	}

	public List<UserDTO> getAllUsers() throws NullPointerException {
		List<User> allUsers = userRepository.findAll();
		List<UserDTO> allUserDto = new ArrayList<UserDTO>();
		if (allUsers.isEmpty()) {
			return new ArrayList<>();
		}
		allUsers.forEach(user -> {
			allUserDto.add(mapper.userToUserDTO(user));
		});
		return allUserDto;
	}

	public User updateUserById(Integer id, User userUpdated) throws NullPointerException, IllegalArgumentException {
		User userToUpdate = new User();
		userToUpdate = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("User" + id + " not found for updating"));

		userToUpdate.setUsername(userUpdated.getUsername());
		userToUpdate.setPassword(userUpdated.getPassword());
		userToUpdate.setFullName(userUpdated.getFullName());
		userToUpdate.setRole(userUpdated.getRole());
		userToUpdate = userRepository.save(userToUpdate);

		return userToUpdate;
	}

	public void deleteUserById(Integer id) throws NullPointerException {
		userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("User" + id + " not found for deleting"));
		userRepository.deleteById(id);
	}

	public void deleteAllUsers() throws Exception {
		userRepository.deleteAll();
	}
}
