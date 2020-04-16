package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.openmrs.BaseOpenmrsObject;

// @Entity
// @Table(name = "hydramodule_asset", catalog = "hydra")
public class HydramoduleDTOFieldAnswer extends BaseOpenmrsObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "asset_id", unique = true, nullable = false)
	private Integer assetId;
	
	@Column(name = "field_uuid")
	private String fieldUUID;
	
	@Column(name = "concept_uuid")
	private String conceptUUID;
	
	public Integer getAssetId() {
		return assetId;
	}
	
	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}
	
	public String getFieldUUID() {
		return fieldUUID;
	}
	
	public void setFieldUUID(String fieldUUID) {
		this.fieldUUID = fieldUUID;
	}
	
	public String getConceptUUID() {
		return conceptUUID;
	}
	
	public void setConceptUUID(String conceptUUID) {
		this.conceptUUID = conceptUUID;
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
	
}
