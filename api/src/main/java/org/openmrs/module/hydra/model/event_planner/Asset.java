package org.openmrs.module.hydra.model.event_planner;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class Asset {

	@Id
	@GeneratedValue
	private int assetId;

	private String name;

	private String capitalValue;

	private String referenceId;

	private Date dateProcured;

	@OneToOne
	@JoinColumn(name = "asset_type_id", referencedColumnName = "asset_type_id")
	private AssetType assetType;

}
