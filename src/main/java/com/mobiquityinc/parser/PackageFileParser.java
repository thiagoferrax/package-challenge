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
			throw new APIException(ParserConstants.PATH_NOT_NULL_OR_EMPTY);
		}

		PackageFile packageFile = new PackageFile();

		try (FileReader file = new FileReader(filePath); BufferedReader reader = new BufferedReader(file)) {
			String row;
			Pattern pattern = Pattern.compile(ParserConstants.ITEM_INFO_REGEXP);
			while ((row = reader.readLine()) != null) {
				String[] rowData = row.split(ParserConstants.WEIGHT_AND_ITEMS_REGEXP);
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
				.validate(PackageBuilder.newPackage().withWeightLimit(rowData[ParserConstants.WEIGHT_LIMIT_INDEX]).now());
	}

	private static List<Item> getItems(Pattern pattern, String[] rowData) throws APIException {
		List<Item> items = new ArrayList<>();
		String[] arrayOfItems = rowData[ParserConstants.ITEMS_INDEX].substring(1, rowData[ParserConstants.ITEMS_INDEX].length())
				.split(ParserConstants.ITEMS_REGEXP);

		for (String itemData : arrayOfItems) {
			Matcher matcher = pattern.matcher(itemData);
			if (matcher.find()) {
				items.add(PackageFileValidator
						.validate(ItemBuilder.newItem().withIndex(matcher.group(ParserConstants.INDEX))
								.withWeight(matcher.group(ParserConstants.WEIGHT_INDEX))
								.withCost(matcher.group(ParserConstants.COST_INDEX)).now()));
			} else {
				throw new APIException(ParserConstants.FILE_NOT_CORRECT);
			}
		}

		return items;
	}

}
