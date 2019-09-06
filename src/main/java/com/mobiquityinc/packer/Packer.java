package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;
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

		int nItems = availableItems.size();
		if (nItems == 0) {
			return;
		}

		int weightLimit = roundUp(aPackage.getWeightLimit());
		BigDecimal[][] costs = new BigDecimal[nItems + 1][weightLimit + 1];
		for (int i = 1; i <= nItems; i++) {

			Item item = availableItems.get(i - 1);
			for (int w = 1; w <= weightLimit; w++) {

				costs[i][w] = costs[i - 1][w] == null ? BigDecimal.ZERO : costs[i - 1][w];
				if (item.getWeight().compareTo(new BigDecimal(w)) <= 0) {

					BigDecimal maximumCost = getMaximumCost(costs, item, i, w);
					if (maximumCost.compareTo(costs[i][w]) >= 0) {
						costs[i][w] = maximumCost;
					}
				}
			}
		}

		aPackage.setItems(getPackageItems(availableItems, weightLimit, costs));
	}

	private static int roundUp(BigDecimal value) {
		return value.setScale(0, RoundingMode.UP).intValue();
	}

	private static List<Item> getPackageItems(List<Item> availableItems, int weightLimit, BigDecimal[][] costs) {
		List<Item> items = new ArrayList<>();

		int w = weightLimit;
		int i = availableItems.size();
		BigDecimal cost = costs[i][w];

		while (cost != null && cost.compareTo(BigDecimal.ZERO) > 0) {

			if (costs[i][w] != costs[i - 1][w]) {
				Item item = availableItems.get(i - 1);
				w -= roundUp(item.getWeight());
				items.add(item);
			}
			i--;

			cost = costs[i][w];
		}

		PackageUtil.sortByIndex(items);

		return items;
	}

	private static BigDecimal getMaximumCost(BigDecimal[][] costs, Item item, int i, int w) {
		return costs[i - 1][w - roundUp(item.getWeight())] == null ? item.getCost()
				: costs[i - 1][w - roundUp(item.getWeight())].add(item.getCost());
	}
}
