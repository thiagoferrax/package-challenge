package com.mobiquityinc.parser;

import java.math.BigDecimal;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.tos.Item;
import com.mobiquityinc.tos.Package;

public class PackageFileValidator {
	public static final int MAX_WEIGHT = 100;
	public static final int MAX_COST = 100;
	public static final int MAX_NUMBER_OF_ITEMS = 15;

	public static final String PACKAGE_WEIGHT_EXCEEDED_LIMIT = "Max weight that a package can take is less than or equal to #LIMIT#.";
	public static final String ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT = "Max weight and cost of an item should be less than or equal to #LIMIT#.";
	public static final String LIMIT_OF_AVAILABLE_ITEMS_WAS_EXCEEDED = "There might be up to #LIMIT# available items to choose from.";
	public static final String LIMIT = "#LIMIT#";

	private PackageFileValidator() {
	}

	public static Package validate(final Package aPackage) throws APIException {
		if (aPackage.getWeightLimit().compareTo(new BigDecimal(MAX_WEIGHT)) > 0) {
			String message = PACKAGE_WEIGHT_EXCEEDED_LIMIT;
			throw new APIException(message.replace(LIMIT, String.valueOf(MAX_WEIGHT)));
		}
		return aPackage;
	}

	public static Item validate(final Item item) throws APIException {
		String message = ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT;

		if (item.getWeight().compareTo(new BigDecimal(MAX_WEIGHT)) > 0) {
			throw new APIException(message.replace(LIMIT, String.valueOf(MAX_WEIGHT)));
		}

		if (item.getCost().compareTo(new BigDecimal(MAX_COST)) > 0) {
			throw new APIException(message.replace(LIMIT, String.valueOf(MAX_COST)));
		}

		return item;
	}

	public static List<Item> validate(final List<Item> items) throws APIException {
		if (items.size() > MAX_NUMBER_OF_ITEMS) {
			String message = LIMIT_OF_AVAILABLE_ITEMS_WAS_EXCEEDED;
			throw new APIException(message.replace(LIMIT, String.valueOf(MAX_NUMBER_OF_ITEMS)));
		}
		return items;
	}
}
