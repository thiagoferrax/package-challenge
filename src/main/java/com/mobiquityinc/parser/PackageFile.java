package com.mobiquityinc.parser;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.tos.Item;
import com.mobiquityinc.tos.Package;

public class PackageFile {
	List<PackageFileRow> rows = new ArrayList<>();

	public List<PackageFileRow> getRows() {
		return rows;
	}

	public void addRow(Package aPackage, List<Item> items) {
		rows.add(new PackageFileRow(aPackage, items));
	}
}
