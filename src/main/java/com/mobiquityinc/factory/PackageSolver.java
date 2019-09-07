package com.mobiquityinc.factory;

import java.util.List;

import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;

public interface PackageSolver {
	void pack(Package aPackage, List<Item> items);
}

