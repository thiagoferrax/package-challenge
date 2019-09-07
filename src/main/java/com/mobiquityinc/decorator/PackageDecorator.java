package com.mobiquityinc.decorator;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.util.PackageUtil;

public class PackageDecorator {
	private Package aPackage;

	public PackageDecorator(Package aPackage) {
		this.aPackage = aPackage;
	}
	
	@Override
	public String toString() {
		return PackageUtil.getCSVItemsIndexes(aPackage);
	}
}
