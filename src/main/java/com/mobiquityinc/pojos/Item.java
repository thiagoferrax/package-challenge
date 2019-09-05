package com.mobiquityinc.pojos;

import java.math.BigDecimal;

public class Item {
	private int index;
	private BigDecimal weigth;
	private BigDecimal cost;
	
	public Item(int index, BigDecimal weigth, BigDecimal cost) {
		super();
		this.index = index;
		this.weigth = weigth;
		this.cost = cost;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public BigDecimal getWeigth() {
		return weigth;
	}
	public void setWeigth(BigDecimal weigth) {
		this.weigth = weigth;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return index + ", " + weigth + ", " + cost;
	}
	
	
}
