package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.dto.UserLoginDTO;

@Service
public class AuthenticationUserService implements UserDetailsService{

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLoginDTO userDTO =userService.getUserByUserName(username);
	    User	userdetails= new User(userDTO.getUsername(), userDTO.getPassword(), getGrantedAuthorities(userDTO.getRole()));
		return userdetails;
	}

	protected List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));		
		return authorities;
	}
}
