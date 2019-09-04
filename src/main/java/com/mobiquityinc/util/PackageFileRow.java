package com.mobiquityinc.util;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.pojos.Package;
import com.mobiquityinc.pojos.Thing;

public class PackageFileRow {
	private Package aPackage;
	List<Thing> things = new ArrayList<>();
	
	public PackageFileRow(Package aPackage, List<Thing> things) {
		super();
		this.aPackage = aPackage;
		this.things = things;
	}

	public Package getPackage() {
		return aPackage;
	}

	public void setPackage(Package aPackage) {
		this.aPackage = aPackage;
	}

	public List<Thing> getThings() {
		return things;
	}

	public void setThings(List<Thing> things) {
		this.things = things;
	}
	
}
