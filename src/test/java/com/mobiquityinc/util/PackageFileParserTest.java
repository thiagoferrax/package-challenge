package com.mobiquityinc.util;

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
			Assert.assertEquals(PackageFileParser.FILE_NOT_FOUND, e.getMessage());
		}
	}
	
	void whenAnIOErrorOccursThrowsAPIException() {
		try {
			PackageFileParser.parse("package_file.txt");
			Assert.fail();
		} catch (APIException e) {
			Assert.assertEquals(PackageFileParser.IO_ERROR, e.getMessage());
		}
	}

}
