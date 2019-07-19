package org.openmrs.module.hydra.model.event_planner;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class AssetType {

	@Id
	@GeneratedValue
	private int assetTypeId;

	@Column(unique = true)
	private String name;

}
