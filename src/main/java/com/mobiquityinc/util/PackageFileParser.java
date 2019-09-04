package com.mobiquityinc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Thing;

public class PackageFileParser {
	private static final String FILE_IS_NOT_CORRECT = "File is not in the correct format!";
	public static final String FILE_NOT_FOUND = "No file or directory!";
	public static final String IO_ERROR = "Error trying to read the file!";

	private static final int WEIGHT_LIMIT = 0;
	//private static final int THINGS = 1;

	private PackageFileParser() {
	}

	public static PackageFile parse(String filePath) throws APIException {
		PackageFile packageFile = new PackageFile();

		try {
			File file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String row;
			while ((row = reader.readLine()) != null) {
				String[] data = row.split(":");

				BigDecimal weightLimit = BigDecimal.valueOf(Double.valueOf(data[WEIGHT_LIMIT]));

				List<Thing> things = new ArrayList<>();
				//String thingsInfo = data[THINGS];

				Package aPackage = new Package(weightLimit, things);
				packageFile.addRow(aPackage, things);
			}

			reader.close();
		} catch (NumberFormatException e) {
			throw new APIException(FILE_IS_NOT_CORRECT);
		} catch (FileNotFoundException e) {
			throw new APIException(FILE_NOT_FOUND);
		} catch (IOException e) {
			throw new APIException(IO_ERROR);
		}

		return packageFile;
	}
}
