package com.mobiquityinc.packer;

import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Thing;
import com.mobiquityinc.util.PackageFile;
import com.mobiquityinc.util.PackageFileParser;
import com.mobiquityinc.util.PackageFileRow;
import com.mobiquityinc.util.PackageUtil;

public class Packer {

	private Packer() {
	}

	public static String pack(String filePath) throws APIException {
		PackageFile file = PackageFileParser.parse(filePath);

		StringBuilder builder = new StringBuilder();
		
		for (PackageFileRow row : file.getRows()) {
			Package aPackage = row.getPackage();
			List<Thing> availableThings = row.getThings();
			
			pack(aPackage, availableThings);
			
			builder.append(PackageUtil.getCSVThingsIndexes(aPackage));
		}

		return builder.toString();
	}

	public static void pack(Package aPackage, List<Thing> availableThings) {
		
	}
}
