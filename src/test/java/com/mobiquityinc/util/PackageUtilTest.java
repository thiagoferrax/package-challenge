package com.mobiquityinc.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.tos.Item;
import com.mobiquityinc.tos.Package;

class PackageUtilTest {

	@Test
	void whenPackageIsEmptyShouldReturnMinusCharacter() {
		// Given
		Package emptyPackage = new Package(BigDecimal.TEN);

		// When and Then
		Assertions.assertEquals("-", PackageUtil.getCSVItemsIndexes(emptyPackage.getItems()));
	}

	@Test
	void whenPackageIsNotEmptyShouldReturnCSVIndexes() {
		// Given
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(BigDecimal.TEN)
				.withItems(Arrays.asList(new Item[] { new Item(1, new BigDecimal(3.5), new BigDecimal(10)),
						new Item(2, new BigDecimal(4.2), new BigDecimal(8)),
						new Item(3, new BigDecimal(7.0), new BigDecimal(10)) }))
				.now();

		// When and Then
		Assertions.assertEquals("1,2,3", PackageUtil.getCSVItemsIndexes(aPackage.getItems()));
	}

	@Test
	void whenSortingItemsShouldReturnIndexesInAscendingOrder() {
		// Given
		List<Item> items = Arrays.asList(new Item[] { new Item(2, new BigDecimal(4.2), new BigDecimal(8)),
				new Item(3, new BigDecimal(7.0), new BigDecimal(10)),
				new Item(1, new BigDecimal(3.5), new BigDecimal(10)) });

		// When
		PackageUtil.sortByIndex(items);

		// Then
		Assertions.assertEquals(Arrays.asList(new Item[] { new Item(1, new BigDecimal(3.5), new BigDecimal(10)),
				new Item(2, new BigDecimal(4.2), new BigDecimal(8)),
				new Item(3, new BigDecimal(7.0), new BigDecimal(10)) }), items);
	}

}
