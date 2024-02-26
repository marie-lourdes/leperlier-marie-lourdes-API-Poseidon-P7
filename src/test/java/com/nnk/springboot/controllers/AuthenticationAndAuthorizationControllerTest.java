package com.nnk.springboot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationAndAuthorizationControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void givenUserUnauthenticated_WhenGetLoginPage_ThenRedirectOnPageLogin() throws Exception {
		mvc.perform(get("/login")).andDo(print()).andExpect(status().is(302));
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void givenUserRole_WhenGetBidListPage_ThenReturnPage() throws Exception {
		mvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().is(200));
	}

	@Test
	public void givenAdminRole_GetBidListPage__ThenRedirectLoginPagewithCode302() throws Exception {
		mvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().is(302));
	}

	@Test
	public void givenAnonymousUser_WhenGetBidListPage_ThenRedirectLoginPagewithCode302() throws Exception {
		mvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().is(302));
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void givenUserRole_WhenUserGetFormCreationPageBid_ThenReturn403() throws Exception {
		mvc.perform(get("/bidList/add")).andDo(print()).andExpect(status().is(403));
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void givenAdminRole_WhenAdministratorGetFormCreationPageBid_ThenReturn200() throws Exception {
		mvc.perform(get("/bidList/add")).andDo(print()).andExpect(status().is(200));
	}

	@Test
	public void givenAnonymousUser_WhenGetHomePage_ThenReturn200() throws Exception {
		mvc.perform(get("/")).andDo(print()).andExpect(status().is(200));
	}
}
