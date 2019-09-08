package com.mobiquityinc.parser;

import java.math.BigDecimal;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;

public class PackageFileValidator {
	private PackageFileValidator() {
	}

	public static Package validate(Package aPackage) throws APIException {
		if (aPackage.getWeightLimit().compareTo(new BigDecimal(ParserConstants.MAX_WEIGHT)) > 0) {
			String message = ParserConstants.PACKAGE_WEIGHT_EXCEEDED_LIMIT;
			throw new APIException(message.replace(ParserConstants.LIMIT, String.valueOf(ParserConstants.MAX_WEIGHT)));
		}
		return aPackage;
	}

	public static Item validate(Item item) throws APIException {
		String message = ParserConstants.ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT;

		if (item.getWeight().compareTo(new BigDecimal(ParserConstants.MAX_WEIGHT)) > 0) {
			throw new APIException(message.replace(ParserConstants.LIMIT, String.valueOf(ParserConstants.MAX_WEIGHT)));
		}

		if (item.getCost().compareTo(new BigDecimal(ParserConstants.MAX_COST)) > 0) {
			throw new APIException(message.replace(ParserConstants.LIMIT, String.valueOf(ParserConstants.MAX_COST)));
		}

		return item;
	}
}
