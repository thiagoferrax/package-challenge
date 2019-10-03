package com.mobiquityinc.tos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Package {
	private BigDecimal weightLimit;
	private List<Item> items;
	
	public Package(BigDecimal weightLimit) {
		this.weightLimit = weightLimit;
		this.items = new ArrayList<>();
	}
	
	public Package(BigDecimal weightLimit, List<Item> items) {
		this.weightLimit = weightLimit;
		this.items = items;
	}

	public BigDecimal getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(BigDecimal weightLimit) {
		this.weightLimit = weightLimit;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
