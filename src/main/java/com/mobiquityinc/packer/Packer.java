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

	/**
	 * Parses a file specified by the filePath and returns, per line, the selected
	 * items indexes to add in each package, according to its weight limit and
	 * maximizing the total cost.
	 * 
	 * @param filePath The file path with the informations about weight limits and
	 *                 available items.
	 * @return Comma separated items indexes.
	 * @throws APIException When file is not found or bad formated.
	 */
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

	/**
	 * Add items to the package according to the package weight limit and maximizing
	 * the total cost.
	 * 
	 * @param aPackage An empty package with weight limit.
	 * @param items    The list of available items with their weights and costs.
	 */
	public static void pack(Package aPackage, List<Item> items) {

		int nItems = items.size();
		if (nItems == 0) {
			return;
		}

		int weightLimit = round(aPackage.getWeightLimit());
		BigDecimal[][] costs = new BigDecimal[nItems + 1][weightLimit + 1];
		for (int i = 1; i <= nItems; i++) {

			Item item = items.get(i - 1);
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

		aPackage.setItems(getPackageItems(items, weightLimit, costs));
	}

	private static List<Item> getPackageItems(List<Item> items, int weightLimit, BigDecimal[][] costs) {
		List<Item> packageItems = new ArrayList<>();

		int w = weightLimit;
		int i = items.size();
		BigDecimal cost = costs[i][w];
		while (cost != null && cost.compareTo(BigDecimal.ZERO) > 0) {
			if (costs[i][w] != costs[i - 1][w]) {
				Item item = items.get(i - 1);
				w -= round(item.getWeight());

				packageItems.add(item);
			}
			i--;
			cost = costs[i][w];
		}

		PackageUtil.sortByIndex(packageItems);

		return packageItems;
	}

	private static BigDecimal getMaximumCost(BigDecimal[][] costs, Item item, int i, int w) {
		return costs[i - 1][w - round(item.getWeight())] == null ? item.getCost()
				: costs[i - 1][w - round(item.getWeight())].add(item.getCost());
	}

	private static int round(BigDecimal value) {
		return value.setScale(0, RoundingMode.HALF_UP).intValue();
	}
}
