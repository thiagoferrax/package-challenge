package com.mobiquityinc.util;

import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Thing;

public class PackageUtil {
	private PackageUtil() {
	}

	/**
	 * Returns a comma separated values (CSV) of things indexes. If there are no
	 * things in the package, it returns "-".
	 * 
	 * @param aPackage The package with things.
	 * @return CSV of things indexes or "-" if there are no things.
	 */
	public static String getCSVThingsIndexes(Package aPackage) {
		List<Thing> things = aPackage.getThings();
		String csvIndexes = things.stream().map(thing -> String.valueOf(thing.getIndex()))
				.collect(Collectors.joining(","));

		return things.isEmpty() ? "-" : csvIndexes;
	}

}
