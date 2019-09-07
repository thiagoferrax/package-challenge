package com.mobiquityinc.factory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.util.PackageUtil;

public class DynamicProgrammingPackageSolver implements PackageSolver {
	
	protected DynamicProgrammingPackageSolver() {
	}
	
	/**
	 * Add package items according to the package weight limit and maximizing the
	 * total cost.
	 * 
	 * @param aPackage An empty package with weight limit.
	 * @param items    The list of available items with their indexes, weights and
	 *                 costs.
	 */
	@Override
	public void pack(Package aPackage, List<Item> items) {

		int nItems = items.size();
		if (nItems == 0) {
			return;
		}

		int weightLimit = round(aPackage.getWeightLimit());
		BigDecimal[][] costs = new BigDecimal[nItems + 1][weightLimit + 1];
		for (int i = 1; i <= nItems; i++) {

			Item item = items.get(i - 1);
			for (int w = 1; w <= weightLimit; w++) {

				costs[i][w] = getPreviousItemMaximumCost(costs, i, w);

				if (doesItemFitInPackage(item, w)) {
					BigDecimal itemMaximumCost = getItemMaximumCost(costs, item, i, w);

					// Does item have maximum cost greater than or equal to previous one?
					if (itemMaximumCost.compareTo(costs[i][w]) >= 0) {
						costs[i][w] = itemMaximumCost;
					}
				}
			}
		}

		aPackage.setItems(getPackageItems(items, weightLimit, costs));
	}

	private static BigDecimal getPreviousItemMaximumCost(BigDecimal[][] costs, int i, int w) {
		return costs[i - 1][w] == null ? BigDecimal.ZERO : costs[i - 1][w];
	}

	private static boolean doesItemFitInPackage(Item item, int w) {
		return item.getWeight().compareTo(new BigDecimal(w)) <= 0;
	}

	/**
	 * Find all items selected to be added in the package.
	 * 
	 * @param items       The list of available items.
	 * @param weightLimit The package weight limit.
	 * @param costs       The costs matrix builded by the core of package algorithm.
	 * @return Lists that were selected to be added in the package.
	 */
	private static List<Item> getPackageItems(List<Item> items, int weightLimit, BigDecimal[][] costs) {
		List<Item> packageItems = new ArrayList<>();

		int w = weightLimit;
		int i = items.size();
		BigDecimal cost = costs[i][w];
		while (cost != null && cost.compareTo(BigDecimal.ZERO) > 0) {

			// If current maximum cost changed than that item was added in the package.
			if (costs[i][w] != costs[i - 1][w]) {
				Item item = items.get(i - 1);

				// Update the weight removing the current item weight.
				w -= round(item.getWeight());

				packageItems.add(item);
			}
			// Now, checking the previous items.
			i--;
			cost = costs[i][w];
		}

		PackageUtil.sortByIndex(packageItems);

		return packageItems;
	}

	/**
	 * Uses the costs matrix to get maximum cost without item weight and then add
	 * the current cost to find the current item maximum cost.
	 * 
	 * @param costs The costs matrix builded by the core of package algorithm.
	 * @param item  The list of available items.
	 * @param i     Current item index.
	 * @param w     Current weight index.
	 * @return Current item maximum cost.
	 */
	private static BigDecimal getItemMaximumCost(BigDecimal[][] costs, Item item, int i, int w) {
		BigDecimal maximumCostWithoutItemWeight = costs[i - 1][w - round(item.getWeight())];
		return maximumCostWithoutItemWeight == null ? item.getCost() : maximumCostWithoutItemWeight.add(item.getCost());
	}

	private static int round(BigDecimal value) {
		return value.setScale(0, RoundingMode.HALF_UP).intValue();
	}

}
