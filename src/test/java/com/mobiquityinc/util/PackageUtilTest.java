package com.mobiquityinc.util;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Thing;

class PackageUtilTest {

	@Test
	void whenPackageIsEmptyShouldReturnMinusCharacter() {
		Assert.assertEquals("-", PackageUtil.getCSVThingsIndexes(new Package(BigDecimal.TEN)));
	}

	@Test
	void whenPackageIsNotEmptyShouldReturnCSVIndexes() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(BigDecimal.TEN)
				.withThings(Arrays.asList(new Thing[] { new Thing(1, new BigDecimal(3.5), new BigDecimal(10)),
						new Thing(2, new BigDecimal(4.2), new BigDecimal(8)),
						new Thing(3, new BigDecimal(7.0), new BigDecimal(10)) }))
				.now();
		Assert.assertEquals("1,2,3", PackageUtil.getCSVThingsIndexes(aPackage));
	}

}
