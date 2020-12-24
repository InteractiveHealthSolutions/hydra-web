package org.openmrs.module.hydra.web.controller.util;

import java.util.ArrayList;
import java.util.List;

public class DistributionItem {

	public DistributionItem(List<String> itemNames, String alias) {
		super();

		List<String> itemNamesLower = new ArrayList<String>();
		for (String s : itemNames) {
			itemNamesLower.add(s.toLowerCase());
		}

		this.itemNames = itemNamesLower;
		this.alias = alias;
	}

	private List<String> itemNames;

	private String alias;

	public List<String> getItemNames() {

		return itemNames;
	}

	public void setItemNames(List<String> itemNames) {
		this.itemNames = itemNames;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
