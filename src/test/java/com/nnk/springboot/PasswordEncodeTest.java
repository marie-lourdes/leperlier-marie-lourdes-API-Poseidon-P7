package com.nnk.springboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nnk.springboot.service.UserService;

@SpringBootTest
public class PasswordEncodeTest {
	private static final Logger log = LogManager.getLogger(UserService.class);

	@Test
	public void testPassword() throws Exception {
		String pw = null;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		pw = encoder.encode("userPoseidon1$");
		log.debug(" test PasswordEncoder: {} ", pw);
	}
}
