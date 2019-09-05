package com.mobiquityinc.packer;

import java.math.BigDecimal;
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

		int weightLimit = aPackage.getWeightLimit().intValue();
		BigDecimal[][] costs = new BigDecimal[weightLimit + 1][nItems + 1];
		for (int i = 1; i <= nItems; i++) {

			Item item = availableItems.get(i - 1);
			for (int w = 1; w <= weightLimit; w++) {

				costs[w][i] = costs[w][i - 1] == null ? BigDecimal.ZERO : costs[w][i - 1];
				if (item.getWeight().compareTo(new BigDecimal(w)) <= 0) {
					
					BigDecimal maximumCost = getMaximumCost(costs, item, i, w);
					if (maximumCost.compareTo(costs[w][i]) > 0) {
						costs[w][i] = maximumCost;
					}
				}
			}
		}

		aPackage.setItems(getPackageItems(availableItems, weightLimit, costs));
	}

	private static List<Item> getPackageItems(List<Item> availableItems, int weightLimit, BigDecimal[][] costs) {
		List<Item> items = new ArrayList<>();

		int w = weightLimit;
		int i = availableItems.size();
		BigDecimal cost = costs[w][i];

		while (cost != null && cost.compareTo(BigDecimal.ZERO) > 0) {

			if (costs[w][i] != costs[w][i - 1]) {
				Item item = availableItems.get(i - 1);
				w -= item.getWeight().intValue();

				items.add(item);
			}
			i--;

			cost = costs[w][i];
		}

		PackageUtil.sortByIndex(items);
		
		return items;
	}

	private static BigDecimal getMaximumCost(BigDecimal[][] costs, Item item, int i, int w) {
		return costs[w - item.getWeight().intValue()][i - 1] == null ? item.getCost()
				: costs[w - item.getWeight().intValue()][i - 1].add(item.getCost());
	}
}
