package com.mobiquityinc.util;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.pojos.Item;
import com.mobiquityinc.pojos.Package;

public class PackageFileRow {
	private Package aPackage;
	List<Item> items = new ArrayList<>();

	public PackageFileRow(Package aPackage, List<Item> items) {
		super();
		this.aPackage = aPackage;
		this.items = items;
	}

	public Package getPackage() {
		return aPackage;
	}

	public List<Item> getItems() {
		return items;
	}

}
