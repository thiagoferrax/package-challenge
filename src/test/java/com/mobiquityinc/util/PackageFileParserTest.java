package com.mobiquityinc.util;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.exception.APIException;

class PackageFileParserTest {

	@Test
	void whenFileIsNotFoundThrowsAPIException() {
		try {
			PackageFileParser.parse("non_existent_file.txt");
			Assert.fail();
		} catch (APIException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	void whenTryingToParseACorrectFileShouldReturnThePackageFile() throws APIException {
		PackageFile file = PackageFileParser.parse("package_file.txt");
		Assert.assertEquals(4, file.getRows().size());
		
		PackageFileRow firstRow = file.getRows().get(0);
		Assert.assertTrue(BigDecimal.valueOf(81).compareTo(firstRow.getPackage().getWeightLimit())==0);
		Assert.assertEquals(6, firstRow.getThings().size());
		
		PackageFileRow secondRow = file.getRows().get(1);
		Assert.assertTrue(BigDecimal.valueOf(8).compareTo(secondRow.getPackage().getWeightLimit())==0);
		Assert.assertEquals(1, secondRow.getThings().size());

		PackageFileRow thirdRow = file.getRows().get(2);
		Assert.assertTrue(BigDecimal.valueOf(75).compareTo(thirdRow.getPackage().getWeightLimit())==0);
		Assert.assertEquals(9, thirdRow.getThings().size());
		
		PackageFileRow fourthRow = file.getRows().get(3);
		Assert.assertTrue(BigDecimal.valueOf(56).compareTo(fourthRow.getPackage().getWeightLimit())==0);
		Assert.assertEquals(9, fourthRow.getThings().size());
	}

}
