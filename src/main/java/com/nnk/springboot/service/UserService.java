package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapperImpl mapper;

	@Autowired
	private UserLoginMapperImpl userLoginMapper;

	@Autowired
	private ValidatorPasswordImpl validatorPassword;

	public User addUser(User userCreated) throws NullPointerException {
		User userRegistered = new User();
		UserLoginDTO existingUser= new UserLoginDTO();
		if (userCreated == null ) {	
			throw new IllegalArgumentException("Empty data of User " + userCreated + " provided and created");
		}else {
			 existingUser= this.getUserByUserName(userCreated.getUsername());
		}
		
		try {
			 if( existingUser != null) {
				throw new IllegalArgumentException(" Username " + existingUser.getUsername()+ " already exist");
			}
			else {
				boolean isPasswordUserValid = validatorPassword.validPassword(userCreated.getPassword());
				if (isPasswordUserValid  ) {
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

	public User getUserById(Integer id) throws NullPointerException {
		User userFoundById = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("User" + id + " not found"));
		return userFoundById ;
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
		log.info("user to update {}",userToUpdate);
		userToUpdate.setUsername(userUpdated.getUsername());
		userToUpdate.setPassword(userUpdated.getPassword());
		userToUpdate.setFullName(userUpdated.getFullName());
		userToUpdate.setRole(userUpdated.getRole());
		userToUpdate = userRepository.save(userToUpdate);
		log.info("user updated {}",userToUpdate);
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

	public UserService() {
		// TODO Auto-generated constructor stub
	}
}
