package org.openmrs.module.hydra.web.controller.util;

public class ReportConfigHolder {

	private String distributionColumn; // Age

	private DistributionItem[] distributionOn; // Male, Female; 18,19,20 as 18-20

	private String filterColumn; // pre condition

	private FilterItem[] filterIn; // diabetes and Hepatitis as CONDITION

	public String getDistributionColumn() {
		return distributionColumn;
	}

	public void setDistributionColumn(String distributionColumn) {
		this.distributionColumn = distributionColumn;
	}

	public DistributionItem[] getDistributionOn() {
		return distributionOn;
	}

	public void setDistributionOn(DistributionItem[] distributionOn) {
		this.distributionOn = distributionOn;
	}

	public String getFilterColumn() {
		return filterColumn;
	}

	public void setFilterColumn(String filterColumn) {
		this.filterColumn = filterColumn;
	}

	public FilterItem[] getFilterIn() {
		return filterIn;
	}

	public void setFilterIn(FilterItem[] filterIn) {
		this.filterIn = filterIn;
	}

}
