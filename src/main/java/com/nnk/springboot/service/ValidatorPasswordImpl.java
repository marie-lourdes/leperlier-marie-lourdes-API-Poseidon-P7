package com.nnk.springboot.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.nnk.springboot.utils.Constants;

@Component
public class ValidatorPasswordImpl implements IValidatorPassword {
	private boolean matches;

	@Override
	public boolean validPassword(String password) {
		Pattern pattern = Pattern.compile(Constants.REGEX_PWD);
		Matcher matcher = pattern.matcher(password);
		matches = matcher.matches();

		if (!matches) {
			return true;
		}

		return false;
	}

}
