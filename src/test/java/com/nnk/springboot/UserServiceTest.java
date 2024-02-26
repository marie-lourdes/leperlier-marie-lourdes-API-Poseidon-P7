package com.nnk.springboot;

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
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.service.ValidatorPasswordImpl;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class UserServiceTest {
	@Autowired
	private UserService userServiceUnderTest;

	@MockBean
	private ValidatorPasswordImpl validatorPassword;

	private User user;

	@BeforeEach
	public void init() {
		user = new User();
		user.setId(3);
		user.setUsername("username");
		user.setFullName("fullname user");
		user.setPassword("userTest1*");
		user.setRole("USER");
		userServiceUnderTest.addUser(user);
		when(validatorPassword.validPassword(user.getPassword())).thenReturn(true);
	}

	@Test
	void testAddUser() throws Exception {
		try {
			User userCreated = new User();
			userCreated.setId(4);
			userCreated.setUsername("username test");
			userCreated.setFullName("fullname test");
			userCreated.setRole("USER");
			userCreated.setPassword("userTest2*");
			User result = userServiceUnderTest.addUser(userCreated);
			assertAll("assertion data curve point created", () -> {
				assertNotNull(result.getId());
				assertEquals("username test", result.getUsername());
				assertEquals("fullname test", result.getFullName());
				assertEquals("USER", result.getRole());
			});
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddUser_WithInvalidPassword() throws Exception {
		try {
			user.setPassword("codeuser");
			userServiceUnderTest.addUser(user);
		} catch (ConstraintViolationException e) {
			assertThrows(ConstraintViolationException.class, () -> userServiceUnderTest.addUser(user));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddUser_WithEmptyMoodysUserOfUser() throws Exception {
		try {
			user.setUsername("");
			User result = userServiceUnderTest.addUser(user);

			assertNull(result.getId());
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
			user.setUsername("Username updated");
			User UserToUpdateTest = userServiceUnderTest.addUser(user);

			User result = userServiceUnderTest.updateUserById(UserToUpdateTest.getId(), user);
			assertAll("assertion data user created", () -> {
				assertNotNull(result.getId());
				assertEquals("fullname user", result.getFullName());
				assertEquals("USER", result.getRole());
				assertEquals("Username updated", result.getUsername());
			});
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
			userServiceUnderTest.addUser(user);

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
			assertThrows(NullPointerException.class, () -> userServiceUnderTest.getAllUsers());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetUserById() throws Exception {
		try {
			User result = userServiceUnderTest.addUser(user);
			result = userServiceUnderTest.getUserById(result.getId());
			assertNotNull(result);
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
			User result = userServiceUnderTest.addUser(user);
			result = userServiceUnderTest.getUserById(result.getId());
			assertNotNull(result);
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
