package org.openmrs.module.hydra.model.event_planner;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_asset", catalog = "hydra")
public class HydramoduleAsset extends BaseOpenmrsMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;

	@Id
	@GeneratedValue
	@Column(name = "asset_id", unique = true, nullable = false)
	private Integer assetId;

	@Column(name = "capital_value")
	private String capitalValue;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "date_procured")
	private Date dateProcured;

	@ManyToOne
	@JoinColumn(name = "asset_type_id")
	private HydramoduleAssetType assetType;

	@ManyToOne
	@JoinColumn(name = "asset_category_id")
	private HydramoduleAssetCategory assetCategory;

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getCapitalValue() {
		return capitalValue;
	}

	public void setCapitalValue(String capitalValue) {
		this.capitalValue = capitalValue;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Date getDateProcured() {
		return dateProcured;
	}

	public void setDateProcured(Date dateProcured) {
		this.dateProcured = dateProcured;
	}

	public HydramoduleAssetType getAssetType() {
		return assetType;
	}

	public void setAssetType(HydramoduleAssetType assetType) {
		this.assetType = assetType;
	}

	//
	// public HydramoduleAssetCategory getAssetCategory() {
	// return assetCategory;
	// }
	//
	// public void setAssetCategory(HydramoduleAssetCategory assetCategory) {
	// this.assetCategory = assetCategory;
	// }

	@Override
	public Integer getId() {
		return assetId;
	}

	@Override
	public void setId(Integer id) {
		assetId = id;

	}
}
