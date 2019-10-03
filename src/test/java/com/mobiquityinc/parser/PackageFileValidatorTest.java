package com.mobiquityinc.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.builders.ItemBuilder;
import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.tos.Item;

class PackageFileValidatorTest {
	@Test
	void whenPackageWeightLessThanLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(
				PackageBuilder.newPackage().withWeightLimit(new BigDecimal(PackageFileValidator.MAX_WEIGHT - 1)).now());
	}

	@Test
	void whenPackageWeightEqualsToLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(
				PackageBuilder.newPackage().withWeightLimit(new BigDecimal(PackageFileValidator.MAX_WEIGHT)).now());
	}

	@Test
	void whenPackageWeightGreaterThanLimitThrowsAPIExpection() {
		try {
			PackageFileValidator.validate(
					PackageBuilder.newPackage().withWeightLimit(new BigDecimal(PackageFileValidator.MAX_WEIGHT + 1)).now());
			Assertions.fail();
		} catch (APIException e) {
			Assertions.assertEquals(PackageFileValidator.PACKAGE_WEIGHT_EXCEEDED_LIMIT.replace(PackageFileValidator.LIMIT,
					String.valueOf(PackageFileValidator.MAX_WEIGHT)), e.getMessage());
		}
	}

	@Test
	void whenItemWeightLessThanLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(new BigDecimal(PackageFileValidator.MAX_WEIGHT - 1))
				.withCost(BigDecimal.TEN).now());
	}

	@Test
	void whenItemWeightEqualsToLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(new BigDecimal(PackageFileValidator.MAX_WEIGHT))
				.withCost(BigDecimal.TEN).now());
	}

	@Test
	void whenItemWeightGreaterThanLimitThrowsAPIExpection() {
		try {
			PackageFileValidator.validate(ItemBuilder.newItem()
					.withWeight(new BigDecimal(PackageFileValidator.MAX_WEIGHT + 1)).withCost(BigDecimal.TEN).now());
			Assertions.fail();
		} catch (APIException e) {
			Assertions.assertEquals(PackageFileValidator.ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT.replace(PackageFileValidator.LIMIT,
					String.valueOf(PackageFileValidator.MAX_WEIGHT)), e.getMessage());
		}
	}

	@Test
	void whenItemCostLessThanLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(BigDecimal.TEN)
				.withCost(new BigDecimal(PackageFileValidator.MAX_COST - 1)).now());
	}

	@Test
	void whenItemCostEqualsToLimitDoesNotThrowExpection() throws APIException {
		PackageFileValidator.validate(ItemBuilder.newItem().withWeight(BigDecimal.TEN)
				.withCost(new BigDecimal(PackageFileValidator.MAX_COST)).now());
	}

	@Test
	void whenItemCostGreaterThanLimitThrowsAPIExpection() {
		try {
			PackageFileValidator.validate(ItemBuilder.newItem().withWeight(BigDecimal.TEN)
					.withCost(new BigDecimal(PackageFileValidator.MAX_COST + 1)).now());
			Assertions.fail();
		} catch (APIException e) {
			Assertions.assertEquals(PackageFileValidator.ITEM_WEIGHT_OR_COST_EXCEEDED_LIMIT.replace(PackageFileValidator.LIMIT,
					String.valueOf(PackageFileValidator.MAX_COST)), e.getMessage());
		}
	}

	@Test
	void whenNumberOfItemsLessThanLimitDoesNotThrowsAPIExpection() throws APIException {
		List<Item> availableItems = Arrays.asList(new Item[] {
				ItemBuilder.newItem().withIndex(1).withWeight(new BigDecimal(40)).withCost(new BigDecimal(500)).now(),
				ItemBuilder.newItem().withIndex(2).withWeight(new BigDecimal(20)).withCost(new BigDecimal(400)).now(),
				ItemBuilder.newItem().withIndex(3).withWeight(new BigDecimal(10)).withCost(new BigDecimal(300)).now(),
				ItemBuilder.newItem().withIndex(4).withWeight(new BigDecimal(30)).withCost(new BigDecimal(450))
						.now() });

		PackageFileValidator.validate(availableItems);
	}

	@Test
	void whenNumberOfItemsEqualsToLimitDoesNotThrowsAPIExpection() throws APIException {
		List<Item> items = new ArrayList<Item>();

		for (int index = 0; index < PackageFileValidator.MAX_NUMBER_OF_ITEMS; index++) {
			items.add(ItemBuilder.newItem().withIndex(index).withWeight(new BigDecimal(40))
					.withCost(new BigDecimal(500)).now());
		}

		PackageFileValidator.validate(items);
	}

	@Test
	void whenNumberOfItemsGreaterThanLimitThrowsAPIExpection() {
		List<Item> items = new ArrayList<Item>();

		for (int index = 0; index < PackageFileValidator.MAX_NUMBER_OF_ITEMS; index++) {
			items.add(ItemBuilder.newItem().withIndex(index).withWeight(new BigDecimal(40))
					.withCost(new BigDecimal(500)).now());
		}
		items.add(
				ItemBuilder.newItem().withIndex(16).withWeight(new BigDecimal(40)).withCost(new BigDecimal(500)).now());

		try {
			PackageFileValidator.validate(items);
			Assertions.fail();
		} catch (APIException e) {
			Assertions.assertEquals(PackageFileValidator.LIMIT_OF_AVAILABLE_ITEMS_WAS_EXCEEDED.replace(PackageFileValidator.LIMIT,
					String.valueOf(PackageFileValidator.MAX_NUMBER_OF_ITEMS)), e.getMessage());
		}
	}

}
