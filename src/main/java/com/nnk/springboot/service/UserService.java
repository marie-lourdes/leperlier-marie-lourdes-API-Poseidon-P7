package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.domain.dto.UserMapperImpl;
import com.nnk.springboot.repositories.IUserRepository;

@Service
public class UserService {
	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapperImpl mapper;

	@Autowired
	private ValidatorPasswordImpl validatorPassword;

	public User addUser(User userCreated) throws NullPointerException {
		User userRegistered = new User();

		try {
			if (userCreated == null) {
				throw new IllegalArgumentException("Empty data of User " + userCreated + " provided and created");
			} else {
				boolean isPasswordUserValid = validatorPassword.validPassword(userCreated.getPassword());
				if (isPasswordUserValid) {
					throw new IllegalArgumentException("Password of User " + userCreated + " provided is incorrect");
				}
				userRegistered.setUsername(userCreated.getUsername());
				userRegistered.setPassword(userCreated.getPassword());
				userRegistered.setFullName(userCreated.getFullName());
				userRegistered.setRole(userCreated.getRole());

			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}

		userRegistered = userRepository.save(userRegistered);
		return userRegistered;
	}

	public UserDTO getUserById(Integer id) throws NullPointerException {
		User userFoundById = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("User" + id + " not found"));
		UserDTO userDTO = mapper.userToUserDTO(userFoundById);
		return userDTO;
	}
	
	public UserDTO getUserByUserName(String username) throws NullPointerException {
		User userFoundById = userRepository.findByUsername(username)
				.orElseThrow(() -> new NullPointerException("User" + username+ " not found"));
		UserDTO userDTO = mapper.userToUserDTO(userFoundById);
		return userDTO;
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
}
