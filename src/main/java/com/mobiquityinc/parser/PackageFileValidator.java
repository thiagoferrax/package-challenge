package com.mobiquityinc.parser;

import java.math.BigDecimal;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;

public class PackageFileValidator {
	private PackageFileValidator() {
	}

	public static Package validate(final Package aPackage) throws APIException {
		if (aPackage.getWeightLimit().compareTo(new BigDecimal(ParserConstants.MAX_WEIGHT)) > 0) {
			String message = ParserConstants.PACKAGE_WEIGHT_EXCEEDED_LIMIT;
			throw new APIException(message.replace(ParserConstants.LIMIT, String.valueOf(ParserConstants.MAX_WEIGHT)));
		}
		return aPackage;
	}

	public static Item validate(final Item item) throws APIException {
		String message = ParserConstants.ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT;

		if (item.getWeight().compareTo(new BigDecimal(ParserConstants.MAX_WEIGHT)) > 0) {
			throw new APIException(message.replace(ParserConstants.LIMIT, String.valueOf(ParserConstants.MAX_WEIGHT)));
		}

		if (item.getCost().compareTo(new BigDecimal(ParserConstants.MAX_COST)) > 0) {
			throw new APIException(message.replace(ParserConstants.LIMIT, String.valueOf(ParserConstants.MAX_COST)));
		}

		return item;
	}
	
	public static List<Item> validate(final List<Item> items) throws APIException {
		if(items.size() > ParserConstants.MAX_NUMBER_OF_ITEMS) {
			String message = ParserConstants.LIMIT_OF_AVAILABLE_ITEMS_WAS_EXCEEDED;
			throw new APIException(message.replace(ParserConstants.LIMIT, String.valueOf(ParserConstants.MAX_NUMBER_OF_ITEMS)));	
		}
		return items;
	}
}
