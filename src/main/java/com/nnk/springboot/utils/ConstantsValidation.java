package com.nnk.springboot.utils;

public class ConstantsValidation {
	public static final String REGEX_PWD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
	public static final int MIN_SIZE_ACCOUNTANDTYPE_DATA = 1;
	public static final int MAX_SIZE_ACCOUNTANDTYPE_DATA = 30;
	public static final String ERROR_NULL_MSG = "must be not null";
	public static final String ERROR_BLANK_MSG = "is mandatory";
	public static final String ERROR_NOT_POSITIVE_MSG = "must be > 0";

	private ConstantsValidation() {}
}
