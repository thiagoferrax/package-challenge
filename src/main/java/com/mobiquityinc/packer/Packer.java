package com.mobiquityinc.packer;

import java.util.List;

import com.mobiquityinc.decorator.StringBuilderDecorator;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.factory.PackageSolver;
import com.mobiquityinc.factory.PackageSolverFactory;
import com.mobiquityinc.factory.PackageSolverFactory.Approach;
import com.mobiquityinc.parser.PackageFile;
import com.mobiquityinc.parser.PackageFileParser;
import com.mobiquityinc.parser.PackageFileRow;
import com.mobiquityinc.tos.Item;
import com.mobiquityinc.tos.Package;

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

		PackageSolver packageSolver = PackageSolverFactory.getInstance()
				.newPackageSolver(Approach.DYNAMIC_PROGRAMMING);
		
		StringBuilderDecorator builder = new StringBuilderDecorator(new StringBuilder());
		for (PackageFileRow row : file.getRows()) {
			Package aPackage = row.getPackage();
			List<Item> availableItems = row.getItems();

			packageSolver.pack(aPackage, availableItems);

			builder.append(aPackage.getItems());
		}

		return builder.toString();
	}

}
