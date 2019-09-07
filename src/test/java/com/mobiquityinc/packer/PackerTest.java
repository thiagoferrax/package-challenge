package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.builders.ItemBuilder;
import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.util.PackageUtil;

class PackerTest {

	@Test
	void whenNoAvailableItemsShouldReturnMinusCharacter() {
		Package emptyPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(8)).now();
		ArrayList<Item> noAvailableItems = new ArrayList<Item>();

		Packer.pack(emptyPackage, noAvailableItems);

		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(emptyPackage));
	}

	@Test
	public void whenAllItemsWeightsGreaterThanLimitShouldReturnMinusCharacter() throws APIException {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(5)).now();
		List<Item> availableItems = Arrays.asList(new Item[] {
				ItemBuilder.newItem().withIndex(1).withWeight(new BigDecimal(40)).withCost(new BigDecimal(500)).now(),
				ItemBuilder.newItem().withIndex(2).withWeight(new BigDecimal(20)).withCost(new BigDecimal(400)).now(),
				ItemBuilder.newItem().withIndex(3).withWeight(new BigDecimal(10)).withCost(new BigDecimal(300)).now(),
				ItemBuilder.newItem().withIndex(4).withWeight(new BigDecimal(30)).withCost(new BigDecimal(450))
						.now() });

		Packer.pack(aPackage, availableItems);

		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	void whenAllItemsWeightsLessThanLimitShouldReturnCSVIndexes() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(5)).now();
		List<Item> availableItems = Arrays.asList(new Item[] {
				ItemBuilder.newItem().withIndex(1).withWeight(new BigDecimal(4)).withCost(new BigDecimal(500)).now(),
				ItemBuilder.newItem().withIndex(2).withWeight(new BigDecimal(2)).withCost(new BigDecimal(400)).now(),
				ItemBuilder.newItem().withIndex(3).withWeight(new BigDecimal(1)).withCost(new BigDecimal(300)).now(),
				ItemBuilder.newItem().withIndex(4).withWeight(new BigDecimal(3)).withCost(new BigDecimal(450)).now() });

		Packer.pack(aPackage, availableItems);

		Assert.assertEquals("2,4", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	void whenSomeItemsWeightsGreaterThanLimitShouldReturnCSVIndexes() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(81)).now();
		List<Item> availableItems = Arrays.asList(new Item[] {
				ItemBuilder.newItem().withIndex(1).withWeight(new BigDecimal(53.38)).withCost(new BigDecimal(45)).now(),
				ItemBuilder.newItem().withIndex(2).withWeight(new BigDecimal(88.62)).withCost(new BigDecimal(98)).now(),
				ItemBuilder.newItem().withIndex(3).withWeight(new BigDecimal(78.48)).withCost(new BigDecimal(3)).now(),
				ItemBuilder.newItem().withIndex(4).withWeight(new BigDecimal(72.32)).withCost(new BigDecimal(76)).now(),
				ItemBuilder.newItem().withIndex(5).withWeight(new BigDecimal(30.18)).withCost(new BigDecimal(9)).now(),
				ItemBuilder.newItem().withIndex(6).withWeight(new BigDecimal(46.34)).withCost(new BigDecimal(48))
						.now() });

		Packer.pack(aPackage, availableItems);

		Assert.assertEquals("4", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	void whenOnlyOneItemsWeightGreaterThanLimitShouldReturnMinusCharacter() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(8)).now();
		List<Item> availableItems = Arrays.asList(new Item[] { ItemBuilder.newItem().withIndex(1)
				.withWeight(new BigDecimal(15.3)).withCost(new BigDecimal(34)).now() });

		Packer.pack(aPackage, availableItems);

		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	void whenSomeOthersItemsWeightsGreaterThanLimitShouldReturnCSVIndexes() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(75)).now();
		List<Item> availableItems = Arrays.asList(new Item[] {
				ItemBuilder.newItem().withIndex(1).withWeight(new BigDecimal(85.31)).withCost(new BigDecimal(29)).now(),
				ItemBuilder.newItem().withIndex(2).withWeight(new BigDecimal(14.55)).withCost(new BigDecimal(74)).now(),
				ItemBuilder.newItem().withIndex(3).withWeight(new BigDecimal(3.98)).withCost(new BigDecimal(16)).now(),
				ItemBuilder.newItem().withIndex(4).withWeight(new BigDecimal(26.24)).withCost(new BigDecimal(55)).now(),
				ItemBuilder.newItem().withIndex(5).withWeight(new BigDecimal(63.69)).withCost(new BigDecimal(52)).now(),
				ItemBuilder.newItem().withIndex(6).withWeight(new BigDecimal(76.25)).withCost(new BigDecimal(75)).now(),
				ItemBuilder.newItem().withIndex(7).withWeight(new BigDecimal(60.02)).withCost(new BigDecimal(74)).now(),
				ItemBuilder.newItem().withIndex(8).withWeight(new BigDecimal(93.18)).withCost(new BigDecimal(55)).now(),
				ItemBuilder.newItem().withIndex(9).withWeight(new BigDecimal(89.95)).withCost(new BigDecimal(78))
						.now() });

		Packer.pack(aPackage, availableItems);

		Assert.assertEquals("2,7", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	void whenOthersItemsWeightsGreaterThanLimitShouldReturnCSVIndexes() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(56)).now();
		List<Item> availableItems = Arrays.asList(new Item[] {
				ItemBuilder.newItem().withIndex(1).withWeight(new BigDecimal(90.72)).withCost(new BigDecimal(13)).now(),
				ItemBuilder.newItem().withIndex(2).withWeight(new BigDecimal(33.80)).withCost(new BigDecimal(40)).now(),
				ItemBuilder.newItem().withIndex(3).withWeight(new BigDecimal(43.15)).withCost(new BigDecimal(10)).now(),
				ItemBuilder.newItem().withIndex(4).withWeight(new BigDecimal(37.97)).withCost(new BigDecimal(16)).now(),
				ItemBuilder.newItem().withIndex(5).withWeight(new BigDecimal(46.81)).withCost(new BigDecimal(36)).now(),
				ItemBuilder.newItem().withIndex(6).withWeight(new BigDecimal(48.77)).withCost(new BigDecimal(79)).now(),
				ItemBuilder.newItem().withIndex(7).withWeight(new BigDecimal(81.80)).withCost(new BigDecimal(45)).now(),
				ItemBuilder.newItem().withIndex(8).withWeight(new BigDecimal(19.36)).withCost(new BigDecimal(79)).now(),
				ItemBuilder.newItem().withIndex(9).withWeight(new BigDecimal(6.76)).withCost(new BigDecimal(64))
						.now() });

		Packer.pack(aPackage, availableItems);

		Assert.assertEquals("8,9", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	public void whenHappyDay() throws APIException {

		List<Item> items = new ArrayList<>();
		items.add(new Item(1, BigDecimal.valueOf((float) 8.77), BigDecimal.valueOf((float) 79)));
		items.add(new Item(2, BigDecimal.valueOf((float) 81.80), BigDecimal.valueOf((float) 45)));
		items.add(new Item(3, BigDecimal.valueOf((float) 0.96), BigDecimal.valueOf((float) 79)));
		items.add(new Item(4, BigDecimal.valueOf((float) 1.76), BigDecimal.valueOf((float) 64)));

		Package aPackage = new Package(new BigDecimal(10));

		Packer.pack(aPackage, items);

		Assert.assertEquals("1,3", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	public void whenItemsWeightsEqualsToPackageLimit() throws APIException {

		List<Item> items = new ArrayList<>();
		items.add(new Item(1, BigDecimal.valueOf((float) 10.00), BigDecimal.valueOf((float) 79)));
		items.add(new Item(2, BigDecimal.valueOf((float) 81.80), BigDecimal.valueOf((float) 45)));
		items.add(new Item(3, BigDecimal.valueOf((float) 0.96), BigDecimal.valueOf((float) 79)));
		items.add(new Item(4, BigDecimal.valueOf((float) 1.76), BigDecimal.valueOf((float) 64)));

		Package aPackage = new Package(new BigDecimal(10), items);

		Packer.pack(aPackage, items);

		Assert.assertEquals("3,4", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	public void whenItemsWeightAreIntegerValues() throws APIException {

		List<Item> items = new ArrayList<>();
		items.add(new Item(1, BigDecimal.valueOf((float) 5.00), BigDecimal.valueOf((float) 40)));
		items.add(new Item(2, BigDecimal.valueOf((float) 4.00), BigDecimal.valueOf((float) 10)));
		items.add(new Item(3, BigDecimal.valueOf((float) 6.00), BigDecimal.valueOf((float) 30)));
		items.add(new Item(4, BigDecimal.valueOf((float) 3.00), BigDecimal.valueOf((float) 50)));

		Package aPackage = new Package(new BigDecimal(10));

		Packer.pack(aPackage, items);

		Assert.assertEquals("1,4", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	public void whenSomeItemsHaveSameCost() throws APIException {

		List<Item> items = new ArrayList<>();
		items.add(new Item(1, BigDecimal.valueOf((float) 48.77), BigDecimal.valueOf((float) 79)));
		items.add(new Item(2, BigDecimal.valueOf((float) 81.80), BigDecimal.valueOf((float) 45)));
		items.add(new Item(3, BigDecimal.valueOf((float) 19.36), BigDecimal.valueOf((float) 79)));
		items.add(new Item(4, BigDecimal.valueOf((float) 6.76), BigDecimal.valueOf((float) 64)));

		Package aPackage = new Package(new BigDecimal(56));

		Packer.pack(aPackage, items);

		Assert.assertEquals("3,4", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	public void whenItemsHaveSameWeight() throws APIException {
		Package aPackage = new Package(new BigDecimal(56));

		List<Item> items = new ArrayList<>();
		items.add(new Item(1, BigDecimal.valueOf((float) 16.36), BigDecimal.valueOf((float) 79)));
		items.add(new Item(2, BigDecimal.valueOf((float) 16.36), BigDecimal.valueOf((float) 45)));
		items.add(new Item(3, BigDecimal.valueOf((float) 16.36), BigDecimal.valueOf((float) 79)));
		items.add(new Item(4, BigDecimal.valueOf((float) 16.36), BigDecimal.valueOf((float) 64)));

		Packer.pack(aPackage, items);

		Assert.assertEquals("1,3,4", PackageUtil.getCSVItemsIndexes(aPackage));
	}

}
