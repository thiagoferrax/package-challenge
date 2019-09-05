package com.mobiquityinc.util;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Item;

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

	public void setPackage(Package aPackage) {
		this.aPackage = aPackage;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
