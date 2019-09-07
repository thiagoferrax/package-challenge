package com.mobiquityinc.packer;

import java.util.List;

import com.mobiquityinc.decorator.PackageDecorator;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.factory.DynamicProgrammingPackageSolver;
import com.mobiquityinc.factory.PackageSolver;
import com.mobiquityinc.factory.PackageSolverFactory;
import com.mobiquityinc.parser.PackageFile;
import com.mobiquityinc.parser.PackageFileParser;
import com.mobiquityinc.parser.PackageFileRow;
import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.util.PackageUtil;

public class Packer {

	private Packer() {
	}

	/**
	 * Parses a file and returns, per line, the indexes of selected items to add in
	 * each package, according to the package weight limit and maximizing the total
	 * cost.
	 * 
	 * @param filePath The path of a file with packages weights limits and available
	 *                 items per line.
	 * @return Comma separated items indexes per line.
	 * @throws APIException When file is not found or bad formated.
	 */
	public static String pack(String filePath) throws APIException {
		PackageFile file = PackageFileParser.parse(filePath);

		StringBuilder builder = new StringBuilder();

		for (PackageFileRow row : file.getRows()) {
			Package aPackage = row.getPackage();
			List<Item> availableItems = row.getItems();

			PackageSolver packageSolver = PackageSolverFactory.getInstance().newDynamicProgrammingPackageSolver();
			packageSolver.pack(aPackage, availableItems);

			if (builder.length() > 0) {
				builder.append(System.lineSeparator());
			}
			builder.append(new PackageDecorator(aPackage));
		}

		return builder.toString();
	}

}
