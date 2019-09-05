package com.mobiquityinc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Item;

public class PackageFileParser {
	private static final int WEIGHT_LIMIT = 0;
	private static final int ITEMS = 1;
	private static final int INDEX = 1;
	private static final int WEIGHT = 2;
	private static final int COST = 3;

	private static final String FILE_NOT_CORRECT = "File format is not correct!";

	private PackageFileParser() {
	}

	public static PackageFile parse(String filePath) throws APIException {
		PackageFile packageFile = new PackageFile();

		try {
			File file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String row;
			Pattern pattern = Pattern.compile("(\\d+),(\\d+.*\\d+),€(\\d+)");

			while ((row = reader.readLine()) != null) {
				String[] rowData = row.split(" : ");
				packageFile.addRow(new Package(getWeightLimit(rowData)), getItems(pattern, rowData));
			}

			reader.close();
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}

		return packageFile;
	}

	private static List<Item> getItems(Pattern pattern, String[] rowData) throws APIException {
		List<Item> items = new ArrayList<>();
		String[] arrayOfItems = rowData[ITEMS].substring(1, rowData[ITEMS].length()).split("\\) \\(");
		for (String item : arrayOfItems) {
			Matcher matcher = pattern.matcher(item);
			if (matcher.find()) {
				Integer index = Integer.valueOf(matcher.group(INDEX));
				BigDecimal weight = BigDecimal.valueOf(Double.valueOf(matcher.group(WEIGHT)));
				BigDecimal cost = BigDecimal.valueOf(Double.valueOf(matcher.group(COST)));

				items.add(new Item(index, weight, cost));
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
