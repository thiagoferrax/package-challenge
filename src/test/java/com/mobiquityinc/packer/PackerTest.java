package com.mobiquityinc.packer;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.util.PackageUtil;

class PackerTest {

	@Test
	void whenThePackageIsEmptyShouldReturnMinusCharacter() {
		Package emptyPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(8)).now();

		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(emptyPackage));
	}

}
