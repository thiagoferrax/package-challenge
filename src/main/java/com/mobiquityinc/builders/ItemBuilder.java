package com.mobiquityinc.builders;

import java.math.BigDecimal;

import com.mobiquityinc.pojos.Item;

public class ItemBuilder {
	private Item item;

	private ItemBuilder() {
	}
	
	public static ItemBuilder newItem() {
		ItemBuilder builder = new ItemBuilder();
		builder.item = new Item();
		return builder;
	}

	public ItemBuilder withIndex(Integer index) {
		item.setIndex(index);
		return this;
	}

	public ItemBuilder withWeight(BigDecimal weight) {
		item.setWeight(weight);
		return this;
	}
	
	public ItemBuilder withCost(BigDecimal cost) {
		item.setCost(cost);
		return this;
	}

	public Item now() {
		return item;
	}
}
