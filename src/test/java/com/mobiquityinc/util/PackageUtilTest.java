package com.mobiquityinc.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Item;

class PackageUtilTest {

	@Test
	void whenPackageIsEmptyShouldReturnMinusCharacter() {
		// Given
		Package aPackage = new Package(BigDecimal.TEN);
		
		// When and Then
		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(aPackage));
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
		Assert.assertEquals("1,2,3", PackageUtil.getCSVItemsIndexes(aPackage));
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
		Assert.assertEquals(Arrays.asList(new Item[] { new Item(1, new BigDecimal(3.5), new BigDecimal(10)),
				new Item(2, new BigDecimal(4.2), new BigDecimal(8)),
				new Item(3, new BigDecimal(7.0), new BigDecimal(10)) }), items);
	}

}
