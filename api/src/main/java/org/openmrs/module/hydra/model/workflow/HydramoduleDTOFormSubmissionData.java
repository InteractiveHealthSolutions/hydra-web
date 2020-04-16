package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.openmrs.BaseOpenmrsObject;

// @Entity
// @Table(name = "hydramodule_asset", catalog = "hydra")
public class HydramoduleDTOFormSubmissionData extends BaseOpenmrsObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "asset_id", unique = true, nullable = false)
	private Integer assetId;
	
	@Column(name = "data")
	private String data;
	
	@Column(name = "metadata")
	private String metadata;
	
	public Integer getAssetId() {
		return assetId;
	}
	
	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return assetId;
	}
	
	@Override
	public void setId(Integer id) {
		assetId = id;
		
	}
	
	public String getMetadata() {
		return metadata;
	}
	
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
}
