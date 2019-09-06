package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.builders.PackageBuilder;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.util.PackageFile;
import com.mobiquityinc.util.PackageFileParser;
import com.mobiquityinc.util.PackageFileRow;
import com.mobiquityinc.util.PackageUtil;

class PackerTest {

	@Test
	void whenThePackageIsEmptyShouldReturnMinusCharacter() {
		Package emptyPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(8)).now();

		Packer.pack(emptyPackage, new ArrayList<Item>());
		
		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(emptyPackage));
	}
	
	@Test
	void whenThePackageIsNotEmptyShouldReturnCSVIndexes() {
		Package aPackage = PackageBuilder.newPackage().withWeightLimit(new BigDecimal(5)).now();
		List<Item> availableItems = Arrays.asList(new Item[] {
				new Item(1, new BigDecimal(4), new BigDecimal(500)),
				new Item(2, new BigDecimal(2), new BigDecimal(400)),
				new Item(3, new BigDecimal(1), new BigDecimal(300)),
				new Item(4, new BigDecimal(3), new BigDecimal(450)),
		});
		
		Packer.pack(aPackage, availableItems);
		
		Assert.assertEquals("2,4", PackageUtil.getCSVItemsIndexes(aPackage));
	}
	
	
	@Test
	void whenThePackageIsNotEmptyShouldReturnCSVIndexes_AssignmentFile() throws APIException {
		PackageFile file = PackageFileParser.parse("package_file.txt");
		
		PackageFileRow packageFileRow = file.getRows().get(0);
		Package aPackage = packageFileRow.getPackage();
		Packer.pack(aPackage, packageFileRow.getItems());
		
		Assert.assertEquals("4", PackageUtil.getCSVItemsIndexes(aPackage));
		
		packageFileRow = file.getRows().get(1);
		aPackage = packageFileRow.getPackage();
		Packer.pack(aPackage, packageFileRow.getItems());
		
		Assert.assertEquals("-", PackageUtil.getCSVItemsIndexes(aPackage));
		
		packageFileRow = file.getRows().get(2);
		aPackage = packageFileRow.getPackage();
		Packer.pack(aPackage, packageFileRow.getItems());
		
		Assert.assertEquals("2,7", PackageUtil.getCSVItemsIndexes(aPackage));
		
		packageFileRow = file.getRows().get(3);
		aPackage = packageFileRow.getPackage();
		Packer.pack(aPackage, packageFileRow.getItems());
		
		Assert.assertEquals("8,9", PackageUtil.getCSVItemsIndexes(aPackage));
		
	}

}
