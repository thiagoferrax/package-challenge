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
		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(new Package(BigDecimal.TEN)));
	}

	@Test
	void whenPackageIsNotEmptyShouldReturnCSVIndexes() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(BigDecimal.TEN)
				.withItems(Arrays.asList(new Item[] { new Item(1, new BigDecimal(3.5), new BigDecimal(10)),
						new Item(2, new BigDecimal(4.2), new BigDecimal(8)),
						new Item(3, new BigDecimal(7.0), new BigDecimal(10)) }))
				.now();
		Assert.assertEquals("1,2,3", PackageUtil.getCSVItemsIndexes(aPackage));
	}

	@Test
	void whenSortingItemsShouldReturnIndexesInAscendingOrder() {
		List<Item> items = Arrays.asList(new Item[] { new Item(2, new BigDecimal(4.2), new BigDecimal(8)),
				new Item(3, new BigDecimal(7.0), new BigDecimal(10)),
				new Item(1, new BigDecimal(3.5), new BigDecimal(10)) });
		
		PackageUtil.sortByIndex(items);
		
		Assert.assertEquals(Arrays.asList(new Item[] { new Item(1, new BigDecimal(3.5), new BigDecimal(10)),
				new Item(2, new BigDecimal(4.2), new BigDecimal(8)),
				new Item(3, new BigDecimal(7.0), new BigDecimal(10)) }), items);
	}

}
