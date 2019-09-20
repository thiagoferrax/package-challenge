package com.mobiquityinc.util;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.parser.PackageFile;
import com.mobiquityinc.parser.PackageFileParser;
import com.mobiquityinc.parser.PackageFileRow;
import com.mobiquityinc.pojos.Item;

class PackageFileParserTest {

	@Test
	void whenFileIsNotFoundThrowsAPIException() {
		try {
			// Given
			String filePath = "non_existent_file.txt";

			// When
			PackageFileParser.parse(filePath);
			Assertions.fail();
		} catch (APIException e) {
			// Then
			Assertions.assertTrue(true);
		}
	}

	@Test
	void whenTryingToParseCorrectFileShouldReturnPackageFile() throws APIException {
		// Given
		String filePath = "package_file.txt";

		// When
		PackageFile file = PackageFileParser.parse(filePath);

		// Then
		Assertions.assertEquals(4, file.getRows().size());

		PackageFileRow firstRow = file.getRows().get(0);
		Assertions.assertTrue(BigDecimal.valueOf(81).compareTo(firstRow.getPackage().getWeightLimit()) == 0);
		Assertions.assertEquals(6, firstRow.getItems().size());

		PackageFileRow secondRow = file.getRows().get(1);
		Assertions.assertTrue(BigDecimal.valueOf(8).compareTo(secondRow.getPackage().getWeightLimit()) == 0);
		Assertions.assertEquals(1, secondRow.getItems().size());

		PackageFileRow thirdRow = file.getRows().get(2);
		Assertions.assertTrue(BigDecimal.valueOf(75).compareTo(thirdRow.getPackage().getWeightLimit()) == 0);
		Assertions.assertEquals(9, thirdRow.getItems().size());

		PackageFileRow fourthRow = file.getRows().get(3);
		Assertions.assertTrue(BigDecimal.valueOf(56).compareTo(fourthRow.getPackage().getWeightLimit()) == 0);
		Assertions.assertEquals(9, fourthRow.getItems().size());
	}

	@Test
	void whenTryingToParseCorrectFileShouldReturnPackageFileWithCorrectWeightLimit() throws APIException {
		// Given
		String filePath = "package_file.txt";
		
		// When
		PackageFile file = PackageFileParser.parse(filePath);

		// Then
		PackageFileRow firstRow = file.getRows().get(0);
		Assertions.assertTrue(BigDecimal.valueOf(81).compareTo(firstRow.getPackage().getWeightLimit()) == 0);
		Assertions.assertEquals(6, firstRow.getItems().size());
	}

	@Test
	void whenTryingToParseCorrectFileShouldReturnPackageFileWithCorrectItems() throws APIException {
		// Given
		String filePath = "package_file.txt";
		
		// When
		PackageFile file = PackageFileParser.parse(filePath);

		// Then
		PackageFileRow secondRow = file.getRows().get(1);
		Assertions.assertEquals(1, secondRow.getItems().size());

		Item item = secondRow.getItems().get(0);
		Assertions.assertEquals(Integer.valueOf(1), item.getIndex());
		Assertions.assertEquals(Double.valueOf(15.3), Double.valueOf(item.getWeight().doubleValue()));
		Assertions.assertEquals(Double.valueOf(34.0), Double.valueOf(item.getCost().doubleValue()));
	}

}
