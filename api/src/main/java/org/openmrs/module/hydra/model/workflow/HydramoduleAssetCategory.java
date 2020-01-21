package org.openmrs.module.hydra.model.workflow;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_asset_category", catalog = "hydra")
public class HydramoduleAssetCategory extends BaseOpenmrsMetadata {
	
	/**
	 * To keep track of object versions, may help in object storage
	 */
	private static final long serialVersionUID = -5072805330615138555L;
	
	@Id
	@GeneratedValue
	@Column(name = "asset_category_id", unique = true, nullable = false)
	private Integer assetCategoryId;
	
	@OneToMany(mappedBy = "assetCategory")
	private List<HydramoduleAssetType> assetTypes;
	
	public int getAssetCategoryId() {
		return assetCategoryId;
	}
	
	public void setAssetCategoryId(int assetCategoryId) {
		this.assetCategoryId = assetCategoryId;
	}
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return assetCategoryId;
	}
	
	@Override
	public void setId(Integer id) {
		this.assetCategoryId = id;
	}
	
	public List<HydramoduleAssetType> getAssetTypes() {
		return assetTypes;
	}
	
	public void setAssetTypes(List<HydramoduleAssetType> assetTypes) {
		this.assetTypes = assetTypes;
	}
	
	public void setAssetCategoryId(Integer assetCategoryId) {
		this.assetCategoryId = assetCategoryId;
	}
}
