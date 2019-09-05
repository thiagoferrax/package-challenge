package com.mobiquityinc.util;

import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Item;

public class PackageUtil {
	private PackageUtil() {
	}

	/**
	 * Returns a comma separated values (CSV) of items indexes. If there are no
	 * items in the package, it returns "-".
	 * 
	 * @param aPackage The package with items.
	 * @return CSV of items indexes or "-" if there are no items.
	 */
	public static String getCSVItemsIndexes(Package aPackage) {
		List<Item> items = aPackage.getItems();
		String csvIndexes = items.stream().map(item -> String.valueOf(item.getIndex()))
				.collect(Collectors.joining(","));

		return items.isEmpty() ? "-" : csvIndexes;
	}

}
