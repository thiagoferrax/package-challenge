package com.mobiquityinc.parser;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.builders.ItemBuilder;
import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.exception.APIException;

class PackageFileValidatorTest {
	@Test
	void whenPackageWeightLessThanLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(
				PackageBuilder.newPackage().withWeightLimit(new BigDecimal(ParserConstants.MAX_WEIGHT - 1)).now());
	}

	@Test
	void whenPackageWeightEqualsToLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(
				PackageBuilder.newPackage().withWeightLimit(new BigDecimal(ParserConstants.MAX_WEIGHT)).now());
	}

	@Test
	void whenPackageWeightGreaterThanLimitThrowsAPIExpection() {
		try {
			PackageFileValidator.validate(
					PackageBuilder.newPackage().withWeightLimit(new BigDecimal(ParserConstants.MAX_WEIGHT + 1)).now());
			Assert.fail();
		} catch (APIException e) {
			Assert.assertEquals(ParserConstants.PACKAGE_WEIGHT_EXCEEDED_LIMIT.replace(ParserConstants.LIMIT,
					String.valueOf(ParserConstants.MAX_WEIGHT)), e.getMessage());
		}
	}

	@Test
	void whenItemWeightLessThanLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(new BigDecimal(ParserConstants.MAX_WEIGHT - 1))
				.withCost(BigDecimal.TEN).now());
	}

	@Test
	void whenItemWeightEqualsToLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(new BigDecimal(ParserConstants.MAX_WEIGHT))
				.withCost(BigDecimal.TEN).now());
	}

	@Test
	void whenItemWeightGreaterThanLimitThrowsAPIExpection() {
		try {
			PackageFileValidator.validate(ItemBuilder.newItem()
					.withWeight(new BigDecimal(ParserConstants.MAX_WEIGHT + 1)).withCost(BigDecimal.TEN).now());
			Assert.fail();
		} catch (APIException e) {
			Assert.assertEquals(ParserConstants.ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT.replace(ParserConstants.LIMIT,
					String.valueOf(ParserConstants.MAX_WEIGHT)), e.getMessage());
		}
	}

	@Test
	void whenItemCostLessThanLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(BigDecimal.TEN)
				.withCost(new BigDecimal(ParserConstants.MAX_COST - 1)).now());
	}

	@Test
	void whenItemCostEqualsToLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(BigDecimal.TEN)
				.withCost(new BigDecimal(ParserConstants.MAX_COST)).now());
	}

	@Test
	void whenItemCostGreaterThanLimitThrowsAPIExpection() {
		try {
			PackageFileValidator.validate(ItemBuilder.newItem().withWeight(BigDecimal.TEN)
					.withCost(new BigDecimal(ParserConstants.MAX_COST + 1)).now());
			Assert.fail();
		} catch (APIException e) {
			Assert.assertEquals(ParserConstants.ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT.replace(ParserConstants.LIMIT,
					String.valueOf(ParserConstants.MAX_COST)), e.getMessage());
		}
	}

}
