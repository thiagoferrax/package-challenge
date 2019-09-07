package com.mobiquityinc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquityinc.builders.ItemBuilder;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;

public class PackageFileParser {
	private static final int WEIGHT_LIMIT = 0;
	private static final int ITEMS = 1;
	private static final int INDEX = 1;
	private static final int WEIGHT = 2;
	private static final int COST = 3;

	private static final String WEIGHT_AND_ITEMS_REGEXP = "\\s*:\\s*";
	private static final String ITEMS_REGEXP = "\\)\\s*\\(";
	private static final String ITEM_INFO_REGEXP = "(\\d+),(\\d+.*\\d+),€(\\d+)";
	private static final String FILE_NOT_CORRECT = "File format is not correct!";
	public static final String PATH_NOT_NULL_OR_EMPTY = "The file path may not be null or empty!";

	private PackageFileParser() {
	}

	public static PackageFile parse(String filePath) throws APIException {
		if (filePath == null || filePath.isEmpty()) {
			throw new APIException(PATH_NOT_NULL_OR_EMPTY);
		}

		PackageFile packageFile = new PackageFile();

		try (FileReader file = new FileReader(filePath); BufferedReader reader = new BufferedReader(file)) {
			String row;
			Pattern pattern = Pattern.compile(ITEM_INFO_REGEXP);
			while ((row = reader.readLine()) != null) {
				String[] rowData = row.split(WEIGHT_AND_ITEMS_REGEXP);
				packageFile.addRow(new Package(getWeightLimit(rowData)), getItems(pattern, rowData));
			}
		} catch (Exception e) {
			throw new APIException(e.getMessage(), e);
		}

		return packageFile;
	}

	private static List<Item> getItems(Pattern pattern, String[] rowData) throws APIException {
		String itemsData = rowData[ITEMS];
		String[] arrayOfItems = itemsData.substring(1, itemsData.length()).split(ITEMS_REGEXP);

		List<Item> items = new ArrayList<>();
		for (String item : arrayOfItems) {
			Matcher matcher = pattern.matcher(item);
			if (matcher.find()) {
				items.add(ItemBuilder.newItem().withIndex(Integer.valueOf(matcher.group(INDEX)))
						.withWeight(BigDecimal.valueOf(Double.valueOf(matcher.group(WEIGHT))))
						.withCost(BigDecimal.valueOf(Double.valueOf(matcher.group(COST)))).now());
			} else {
				throw new APIException(FILE_NOT_CORRECT);
			}
		}

		return items;
	}

	private static BigDecimal getWeightLimit(String[] rowData) {
		return BigDecimal.valueOf(Double.valueOf(rowData[WEIGHT_LIMIT]));
	}
}
