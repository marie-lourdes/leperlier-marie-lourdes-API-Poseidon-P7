package com.nnk.springboot.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.IUserRepository;
import com.nnk.springboot.utils.Constants;

@Service
public class UserService {
	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private IUserRepository userRepository;
	

	@Autowired
	private ValidatorPasswordImpl validatorPassword;
	
	public User addUser(User userCreated)
			throws NullPointerException {
		User userRegistered = new User();
		
		try {
			if (userCreated == null) {
				throw new IllegalArgumentException("Empty data of User " + userCreated + " provided and created");	
			}
			else {
				boolean isPasswordUserValid= validatorPassword.validPassword(userCreated.getPassword());
					if(isPasswordUserValid) {
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

	/*public UserDTO getBidById(Integer id) throws NullPointerException {
		User bidlistFoundById = bidListRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Bid " + id + " not found"));
		UserDTO bidListDTO = mapper.bidListToUserDTO(bidlistFoundById);
		return bidListDTO;
	}

	public List<UserDTO> getAllBids() throws NullPointerException {
		List<User> allUsers = bidListRepository.findAll();
		List<UserDTO> allUserDto = new ArrayList<UserDTO>();
		if (allUsers.isEmpty()) {
			return new ArrayList<>();
		}
		allUsers.forEach(bid -> {
			allUserDto.add(mapper.bidListToUserDTO(bid));
		});
		return allUserDto;
	}

	public User updateBidById(Integer id, User bidListUpdated)
			throws NullPointerException, IllegalArgumentException {
		User bidListToUpdate = new User();
		bidListToUpdate = bidListRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Bid " + id + " not found for updating"));

		bidListToUpdate.setAccount(bidListUpdated.getAccount());
		bidListToUpdate.setType(bidListUpdated.getType());
		bidListToUpdate.setBidQuantity(bidListUpdated.getBidQuantity());
		bidListToUpdate = bidListRepository.save(bidListToUpdate);
		return bidListToUpdate;
	}

	public void deleteBidById(Integer id) throws NullPointerException {
		bidListRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Bid " + id + " not found for deleting"));
		bidListRepository.deleteById(id);
	}*/
}
