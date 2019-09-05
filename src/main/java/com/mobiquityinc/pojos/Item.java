package com.mobiquityinc.pojos;

import java.math.BigDecimal;

public class Item {
	private int index;
	private BigDecimal weight;
	private BigDecimal cost;
	
	public Item(int index, BigDecimal weigth, BigDecimal cost) {
		super();
		this.index = index;
		this.weight = weigth;
		this.cost = cost;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return index + ", " + weight + ", " + cost;
	}
	
	
}
