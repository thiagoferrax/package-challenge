package com.mobiquityinc.decorator;

import java.util.List;

import com.mobiquityinc.tos.Item;
import com.mobiquityinc.util.PackageUtil;

public class StringBuilderDecorator {
	private StringBuilder stringBuilder;

	public StringBuilderDecorator(StringBuilder stringBuilder) {
		this.stringBuilder = stringBuilder;
	}

	public StringBuilderDecorator append(List<Item> items) {
		if (stringBuilder.length() > 0) {
			stringBuilder.append(System.lineSeparator());
		}
		this.stringBuilder.append(PackageUtil.getCSVItemsIndexes(items));
		return this;
	}

	@Override
	public String toString() {
		return stringBuilder.toString();
	}

}
