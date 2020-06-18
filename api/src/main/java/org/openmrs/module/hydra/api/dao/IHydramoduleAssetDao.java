package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.module.hydra.model.HydramoduleAsset;
import org.openmrs.module.hydra.model.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.HydramoduleAssetType;
import org.springframework.stereotype.Repository;

public interface IHydramoduleAssetDao {

	HydramoduleAssetType saveAssetType(HydramoduleAssetType serviceType);

	HydramoduleAssetType getAssetType(String uuid);

	List<HydramoduleAssetType> getAllAssetTypes(boolean retired);

	// AssetCategory
	HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory serviceType);

	HydramoduleAssetCategory getAssetCategory(String uuid);

	List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired);

	// Asset
	HydramoduleAsset saveAsset(HydramoduleAsset serviceType);

	HydramoduleAsset getAsset(String uuid);

	List<HydramoduleAsset> getAllAssets(boolean retired);

}
