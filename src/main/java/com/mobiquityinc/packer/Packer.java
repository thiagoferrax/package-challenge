package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.util.PackageFile;
import com.mobiquityinc.util.PackageFileParser;
import com.mobiquityinc.util.PackageFileRow;
import com.mobiquityinc.util.PackageUtil;

public class Packer {

	private Packer() {
	}

	public static String pack(String filePath) throws APIException {
		PackageFile file = PackageFileParser.parse(filePath);

		StringBuilder builder = new StringBuilder();

		for (PackageFileRow row : file.getRows()) {
			Package aPackage = row.getPackage();
			List<Item> availableItems = row.getItems();

			pack(aPackage, availableItems);

			builder.append(PackageUtil.getCSVItemsIndexes(aPackage));
		}

		return builder.toString();
	}

	public static void pack(Package aPackage, List<Item> availableItems) {

		int weightLimit = aPackage.getWeightLimit().intValue();
		int nItems = availableItems.size();

		if (nItems == 0) {
			return;
		}

		BigDecimal[][] costs = new BigDecimal[weightLimit + 1][nItems + 1];

		for (int i = 1; i <= nItems; i++) {
			Item item = availableItems.get(i - 1);
			BigDecimal weight = item.getWeight();
			BigDecimal cost = item.getCost();

			for (int p = 1; p <= weightLimit; p++) {

				int previusItem = i - 1;
				costs[p][i] = costs[p][previusItem] == null ? BigDecimal.ZERO : costs[p][previusItem];

				BigDecimal packageWeight = new BigDecimal(p);
				if (weight.compareTo(packageWeight) <= 0) {

					BigDecimal previusCost = costs[p][previusItem] == null ? BigDecimal.ZERO : costs[p][previusItem];
					if (previusItem - 1 >= 0 && weight.add(availableItems.get(previusItem - 1).getWeight()).compareTo(packageWeight) <= 0) {
						costs[p][i] = costs[p][i].add(cost);
					} else if (costs[p][i].compareTo(previusCost) > 0) {
						costs[p][i] = cost;
					}
				}
			}
		}

		print(costs);

	}

	private static void print(BigDecimal[][] costs) {
		for (int i = 0; i < costs[0].length; i++) {
			List<BigDecimal> row = new ArrayList<BigDecimal>();
			for (int j = 0; j < costs.length; j++) {
				row.add(costs[j][i]);
			}
			String rowString = row.toString();
			System.out.println(rowString.substring(1, rowString.length()));
		}
	}
}
