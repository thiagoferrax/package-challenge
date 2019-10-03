package com.mobiquityinc.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquityinc.builders.ItemBuilder;
import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.tos.Item;
import com.mobiquityinc.tos.Package;

public class PackageFileParser {
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
				Package emptyPackage = getEmptyPackage(rowData);
				List<Item> items = PackageFileValidator.validate(getItems(pattern, rowData));

				packageFile.addRow(emptyPackage, items);
			}
		} catch (Exception e) {
			throw new APIException(e.getMessage(), e);
		}

		return packageFile;
	}

	private static Package getEmptyPackage(String[] rowData) throws APIException {
		return PackageFileValidator
				.validate(PackageBuilder.newPackage().withWeightLimit(rowData[WEIGHT_LIMIT_INDEX]).now());
	}

	private static List<Item> getItems(Pattern pattern, String[] rowData) throws APIException {
		List<Item> items = new ArrayList<>();
		String[] arrayOfItems = rowData[ITEMS_INDEX].substring(1, rowData[ITEMS_INDEX].length()).split(ITEMS_REGEXP);

		for (String itemData : arrayOfItems) {
			Matcher matcher = pattern.matcher(itemData);
			if (matcher.find()) {
				items.add(PackageFileValidator.validate(ItemBuilder.newItem().withIndex(matcher.group(INDEX))
						.withWeight(matcher.group(WEIGHT_INDEX)).withCost(matcher.group(COST_INDEX)).now()));
			} else {
				throw new APIException(FILE_NOT_CORRECT);
			}
		}

		return items;
	}

}
