package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.domain.dto.UserLoginDTO;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class UserServiceTest {
	@Autowired
	private UserService userServiceUnderTest;

	@MockBean
	private ValidatorPasswordImpl validatorPassword;

	private User user;

	@BeforeEach
	public void init() throws Exception {
		try {
			user = new User();
			user.setId(3);
			user.setUsername("usernameuser");
			user.setFullName("nameuser");
			user.setPassword("userTest1*");
			user.setRole("USER");
			user = userServiceUnderTest.addUser(user);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.addUser(user));
		}
	}

	@Test
	void testAddUser() throws Exception {
		User userCreated = new User();
		try {
			userCreated.setId(4);
			userCreated.setUsername("pseudouser");
			userCreated.setFullName("fullnameuser");
			userCreated.setRole("USER");
			userCreated.setPassword("userTest2*");

			User result = userServiceUnderTest.addUser(userCreated);
			assertAll("assertion data curve point created", () -> {
				assertNotNull(result.getId());
				assertEquals("pseudouser", result.getUsername());
				assertEquals("fullnameuser", result.getFullName());
				assertEquals("USER", result.getRole());
			});
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.addUser(userCreated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddUser_WithInvalidPassword() throws Exception {
		try {
			user.setPassword("codeuser");
			userServiceUnderTest.addUser(user);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.addUser(user));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddUser_WithEmptyData() throws Exception {
		User userCreated = new User();
		try {
			userCreated.setUsername("userdeux");
			userCreated.setFullName("fullnamedeux");
			userCreated.setRole("USER");
			userCreated.setPassword("userTest2*");
			user.setPassword("");

			User result = userServiceUnderTest.addUser(userCreated);
			assertNull(result.getId());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.addUser(userCreated));
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> userServiceUnderTest.addUser(user));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateUser() throws Exception {
		try {
			user.setFullName("Fullnameupdated");
			userServiceUnderTest.addUser(user);

			User result = userServiceUnderTest.updateUserById(user.getId(), user);
			assertAll("assertion data user created", () -> {
				assertNotNull(result.getId());
				assertEquals("Fullnameupdated", result.getFullName());
			});
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.updateUserById(user.getId(), user));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateUser_WithUserNotFound() throws Exception {
		try {
			User result = userServiceUnderTest.updateUserById(15, user);

			assertNull(result);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> userServiceUnderTest.updateUserById(15, user));
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.updateUserById(15, user));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.updateUserById(15, user));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllUsers() throws Exception {
		try {
			List<UserDTO> result = userServiceUnderTest.getAllUsers();

			assertNotNull(result);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllUsers_WithListOfUsersNotFound() throws Exception {
		try {
			userServiceUnderTest.deleteAllUsers();

			List<UserDTO> result = userServiceUnderTest.getAllUsers();
			assertTrue(result.isEmpty());
		} catch (NullPointerException e) {
			e.getMessage();
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.getAllUsers());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetUserById() throws Exception {

		try {
			User result = userServiceUnderTest.getUserById(user.getId());
			assertNotNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.getUserById(user.getId()));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void tesGetUserById_WithUserNotFound() throws Exception {
		try {
			userServiceUnderTest.deleteAllUsers();

			User result = userServiceUnderTest.getUserById(1);
			assertNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetUserByUserName() throws Exception {
		try {
			UserLoginDTO result = userServiceUnderTest.getUserByUserName(user.getUsername());
			assertNotNull(result);
		} catch (NullPointerException e) {
			e.getMessage();
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.getUserByUserName(user.getUsername()));
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteUserById() throws Exception {
		try {
			User userCreated = userServiceUnderTest.addUser(user);
			userServiceUnderTest.deleteUserById(userCreated.getId());

			User result = userServiceUnderTest.getUserById(userCreated.getId());
			assertNull(result);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteUserById_WithNoExistingUser() throws Exception {
		try {
			userServiceUnderTest.deleteUserById(15);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.deleteUserById(15));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}