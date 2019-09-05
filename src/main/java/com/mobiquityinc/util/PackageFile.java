package com.mobiquityinc.util;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Item;

public class PackageFile {
	List<PackageFileRow> rows = new ArrayList<>();

	public List<PackageFileRow> getRows() {
		return rows;
	}

	public void addRow(Package aPackage, List<Item> items) {
		rows.add(new PackageFileRow(aPackage, items));
	}
}
