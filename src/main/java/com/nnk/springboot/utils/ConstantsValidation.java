package com.nnk.springboot.utils;

public class ConstantsValidation {
	public static final String REGEX_PWD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
	public static final String REGEX_CHARACTER ="^(\\D*)";
	public static final String REGEX_DIGIT ="[0-9]";
	public static final int MIN_SIZE_ACCOUNTANDTYPE_DATA = 1;
	public static final int MAX_SIZE_ACCOUNTANDTYPE_DATA = 30;
	public static final int MIN_SIZE_TRADE_DATA = 0;
	public static final int MAX_SIZE_TRADE_DATA = 10;
	public static final String ERROR_NULL_MSG = "must be not null";
	public static final String ERROR_BLANK_MSG = "is mandatory";
	public static final String ERROR_NOT_POSITIVE_MSG = "must be > 0";
	public static final String ERROR_NOT_CHARACTER_MSG = "must be only characters";
	public static final String ERROR_SIZE_CHARACTER_MSG = "size must be  maximum 10";
	public static final String ERROR_SIZE_CHARACTER_ACCOUNTANDTYPE_MSG = "size must be between 1 -30";
	public static final String ERROR_NOT_DIGIT_MSG = "must be only numbers";

	private ConstantsValidation() {}
}
