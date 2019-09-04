package com.mobiquityinc.pojos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Package {
	private BigDecimal weightLimit;
	private List<Thing> things;
	
	public Package(BigDecimal weightLimit) {
		this.weightLimit = weightLimit;
		this.things = new ArrayList<>();
	}
	
	public Package(BigDecimal weightLimit, List<Thing> things) {
		this.weightLimit = weightLimit;
		this.things = things;
	}

	public BigDecimal getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(BigDecimal weightLimit) {
		this.weightLimit = weightLimit;
	}

	public List<Thing> getThings() {
		return things;
	}

	public void setThings(List<Thing> things) {
		this.things = things;
	}
}
