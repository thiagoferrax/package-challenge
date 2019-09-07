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
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;

public class PackageFileParser {

	private PackageFileParser() {
	}

	public static PackageFile parse(String filePath) throws APIException {
		if (filePath == null || filePath.isEmpty()) {
			throw new APIException(Constants.PATH_NOT_NULL_OR_EMPTY);
		}

		PackageFile packageFile = new PackageFile();

		try (FileReader file = new FileReader(filePath); BufferedReader reader = new BufferedReader(file)) {
			String row;
			Pattern pattern = Pattern.compile(Constants.ITEM_INFO_REGEXP);
			while ((row = reader.readLine()) != null) {
				String[] rowData = row.split(Constants.WEIGHT_AND_ITEMS_REGEXP);
				packageFile.addRow(getEmptyPackage(rowData), getItems(pattern, rowData));
			}
		} catch (Exception e) {
			throw new APIException(e.getMessage(), e);
		}

		return packageFile;
	}

	private static Package getEmptyPackage(String[] rowData) {
		return PackageBuilder.newPackage().withWeightLimit(rowData[Constants.WEIGHT_LIMIT]).now();
	}

	private static List<Item> getItems(Pattern pattern, String[] rowData) throws APIException {
		List<Item> items = new ArrayList<>();
		String[] arrayOfItems = rowData[Constants.ITEMS].substring(1, rowData[Constants.ITEMS].length())
				.split(Constants.ITEMS_REGEXP);

		for (String item : arrayOfItems) {
			Matcher matcher = pattern.matcher(item);
			if (matcher.find()) {
				items.add(ItemBuilder.newItem().withIndex(matcher.group(Constants.INDEX))
						.withWeight(matcher.group(Constants.WEIGHT)).withCost(matcher.group(Constants.COST)).now());
			} else {
				throw new APIException(Constants.FILE_NOT_CORRECT);
			}
		}

		return items;
	}

}
