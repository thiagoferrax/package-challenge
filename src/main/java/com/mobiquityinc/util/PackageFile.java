package com.mobiquityinc.util;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Thing;

public class PackageFile {
	List<PackageFileRow> rows = new ArrayList<>();

	public List<PackageFileRow> getRows() {
		return rows;
	}

	public void addRow(Package aPackage, List<Thing> things) {
		rows.add(new PackageFileRow(aPackage, things));
	}
}
