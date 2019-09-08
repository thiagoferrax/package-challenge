package com.mobiquityinc.parser;

public class ParserConstants {

	private ParserConstants() {
	}

	public static final int WEIGHT_LIMIT_INDEX = 0;
	public static final int ITEMS_INDEX = 1;
	public static final int INDEX = 1;
	public static final int WEIGHT_INDEX = 2;
	public static final int COST_INDEX = 3;

	public static final String WEIGHT_AND_ITEMS_REGEXP = "\\s*:\\s*";
	public static final String ITEMS_REGEXP = "\\)\\s*\\(";
	public static final String ITEM_INFO_REGEXP = "(\\d+),(\\d+.*\\d+),€(\\d+)";
	public static final String FILE_NOT_CORRECT = "File format is not correct!";
	public static final String PATH_NOT_NULL_OR_EMPTY = "The file path may not be null or empty!";
	
	public static final int MAX_WEIGHT = 100;
	public static final int MAX_COST = 100;
	public static final String PACKAGE_WEIGHT_EXCEEDED_LIMIT = "Max weight that a package can take is less than or equal to #LIMIT#.";
	public static final String ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT = "Max weight and cost of an item should be less than or equal to #LIMIT#.";
	public static final String LIMIT = "#LIMIT#";
}
