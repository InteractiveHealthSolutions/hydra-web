package org.openmrs.module.hydra.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_asset")
public class HydramoduleAsset extends BaseOpenmrsMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "asset_id", unique = true, nullable = false)
	private Integer assetId;

	@Column(name = "capital_value")
	private String capitalValue;

	@Column(name = "fixed_asset")
	private Boolean fixedAsset;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "date_procured")
	private Date dateProcured;

	@ManyToOne
	@JoinColumn(name = "asset_type_id")
	private HydramoduleAssetType assetType;

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
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

	@Override
	public Integer getId() {
		return assetId;
	}

	@Override
	public void setId(Integer id) {
		assetId = id;

	}

	public Boolean getFixedAsset() {
		return fixedAsset;
	}

	public void setFixedAsset(Boolean fixedAsset) {
		this.fixedAsset = fixedAsset;
	}
}
