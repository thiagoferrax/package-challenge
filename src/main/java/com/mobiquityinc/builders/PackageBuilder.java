package com.mobiquityinc.builders;

import java.math.BigDecimal;
import java.util.List;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Item;

public class PackageBuilder {
	private Package aPackage;

	private PackageBuilder() {
	}
	
	public static PackageBuilder newPackage() {
		PackageBuilder builder = new PackageBuilder();
		builder.aPackage = new Package(BigDecimal.ZERO);
		return builder;
	}

	public PackageBuilder withWeightLimit(BigDecimal weightLimit) {
		aPackage.setWeightLimit(weightLimit);
		return this;
	}

	public PackageBuilder withItems(List<Item> items) {
		aPackage.setItems(items);
		return this;
	}

	public Package now() {
		return aPackage;
	}
}
