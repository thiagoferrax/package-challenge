package com.mobiquityinc.parser;

public class Constants {
	public static final int WEIGHT_LIMIT = 0;
	public static final int ITEMS = 1;
	public static final int INDEX = 1;
	public static final int WEIGHT = 2;
	public static final int COST = 3;

	public static final String WEIGHT_AND_ITEMS_REGEXP = "\\s*:\\s*";
	public static final String ITEMS_REGEXP = "\\)\\s*\\(";
	public static final String ITEM_INFO_REGEXP = "(\\d+),(\\d+.*\\d+),€(\\d+)";
	public static final String FILE_NOT_CORRECT = "File format is not correct!";
	public static final String PATH_NOT_NULL_OR_EMPTY = "The file path may not be null or empty!";
}
