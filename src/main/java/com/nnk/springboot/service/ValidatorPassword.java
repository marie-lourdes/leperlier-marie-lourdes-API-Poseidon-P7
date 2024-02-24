package com.nnk.springboot.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nnk.springboot.utils.Constants;

public interface ValidatorPassword {
	default boolean validPassword(String password) {
		Pattern pattern = Pattern.compile(Constants.REGEX_PWD);
		Matcher matcher = pattern.matcher(password);
		boolean matches = matcher.matches();
		
		if(!matches) {
			return true;
		}
		
	    return false;
	}
}
