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

	public ItemBuilder withIndex(String index) {
		return withIndex(Integer.valueOf(index));
	}

	public ItemBuilder withWeight(String wight) {
		return withWeight(BigDecimal.valueOf(Double.valueOf(wight)));
	}

	public ItemBuilder withCost(String cost) {
		return withCost(BigDecimal.valueOf(Double.valueOf(cost)));
	}

	public Item now() {
		return item;
	}
}
