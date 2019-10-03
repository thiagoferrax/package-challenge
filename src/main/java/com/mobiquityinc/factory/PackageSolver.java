package com.mobiquityinc.factory;

import java.util.List;

import com.mobiquityinc.tos.Item;
import com.mobiquityinc.tos.Package;

public interface PackageSolver {
	void pack(Package aPackage, List<Item> items);
}

