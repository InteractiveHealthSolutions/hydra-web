package org.openmrs.module.hydra.web.controller.util;

import java.util.ArrayList;
import java.util.List;

public class FilterItem {

	private List<String> filterValues;

	private String alias;

	public FilterItem(List<String> filterValues, String alias) {
		super();

		List<String> itemNamesLower = new ArrayList<String>();
		for (String s : filterValues) {
			itemNamesLower.add(s.toLowerCase());
		}

		this.filterValues = itemNamesLower;
		this.alias = alias;
	}

	public List<String> getFilterValues() {

		return filterValues;
	}

	public void setFilterValues(List<String> filterValues) {
		this.filterValues = filterValues;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
