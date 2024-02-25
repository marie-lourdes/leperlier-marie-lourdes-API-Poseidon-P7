package com.nnk.springboot.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nnk.springboot.domain.User;

/*public class UserRepositoryTest {
	private User user;

	@Autowired
	private IUserRepository userRepository;

	@BeforeEach
	public void setUpPerTest() {
		String pw = null;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		pw = encoder.encode("userTest1$");
		user = new User();
		user.setFullName("FullName test");
		user.setUsername("Username test");
		user.setPassword(pw );
		user.setRole("USER");
	}

	@DisplayName("test for save user operation")
	@Test
	public void givenUserObject_whenSave_thenReturnSavedUser() throws Exception {
		try {
			user = userRepository.save(user);
			assertNotNull(user.getId());
			assertEquals(user.getFullName(), "FullName test");
			assertEquals(user.getUsername(), "Username test");
			assertEquals(user.getRole(), "USER");
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for update user operation")
	@Test
	public void givenUserObject_whenUpdate_thenReturnUpdatedUser() throws Exception {
		try {
			user.setFullName("FullName test updated");
			user = userRepository.save(user);
			assertEquals(user.getFullName(), "FullName test updated");
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get all users operation")
	@Test
	public void givenUser_whenFindAll_thenReturnAllUsers() throws Exception {
		try {
			List<User> listResult = userRepository.findAll();
			assertTrue(listResult.size() > 0);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for get user by id  operation")
	@Test
	public void givenUserObject_whenFindById_thenReturnUser() throws Exception {
		try {
			user = userRepository.save(user);
			Integer id = user.getId();
			User result = userRepository.findById(id).get();
			assertNotNull(result);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@DisplayName("test for delete user operation")
	@Test
	public void givenUserObject_whenDeleteById_thenRemoveUser() throws Exception {
		try {
			user = userRepository.save(user);
			Integer id = user.getId();
			userRepository.deleteById(id);
			Optional<User> userList = userRepository.findById(id);
			assertFalse(userList.isPresent());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}*/
