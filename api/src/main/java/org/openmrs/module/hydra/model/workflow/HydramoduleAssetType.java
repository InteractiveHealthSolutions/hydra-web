package org.openmrs.module.hydra.model.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_asset_type", catalog = "hydra")
public class HydramoduleAssetType extends BaseOpenmrsMetadata {

	/**
	 * To keep track of object versions, may help in object storage
	 */
	private static final long serialVersionUID = -3785441749369003756L;

	@Id
	@GeneratedValue
	@Column(name = "asset_type_id", unique = true, nullable = false)
	private Integer assetTypeId;

	public Integer getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(Integer assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return assetTypeId;
	}

	@Override
	public void setId(Integer id) {
		assetTypeId = id;

	}

}
