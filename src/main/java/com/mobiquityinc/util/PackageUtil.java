package com.mobiquityinc.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.pojos.Item;

public class PackageUtil {
	private PackageUtil() {
	}

	/**
	 * Returns a comma separated values (CSV) of items indexes. If there are no
	 * items in the package, it returns "-".
	 * 
	 * @param items The items with indexes.
	 * 
	 * @return CSV of items indexes or "-" if there are no items.
	 */
	public static String getCSVItemsIndexes(List<Item> items) {
		String csvIndexes = items.stream().map(item -> String.valueOf(item.getIndex()))
				.collect(Collectors.joining(","));

		return items.isEmpty() ? "-" : csvIndexes;
	}

	/**
	 * Sort package items by index.
	 * 
	 * @param items List of items to sort.
	 */
	public static void sortByIndex(List<Item> items) {
		Collections.sort(items, new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				return i1.getIndex().compareTo(i2.getIndex());
			}
		});
	}

}
