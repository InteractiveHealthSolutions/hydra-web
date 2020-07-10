package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hydra.api.dao.IHydramoduleAssetDao;
import org.openmrs.module.hydra.model.HydramoduleAsset;
import org.openmrs.module.hydra.model.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.HydramoduleAssetType;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraAssetService extends OpenmrsService {

	void setAssetDao(IHydramoduleAssetDao assetDao);

	HydramoduleAssetType saveAssetType(HydramoduleAssetType service) throws APIException;

	List<HydramoduleAssetType> getAllAssetTypes(boolean retired) throws APIException;

	HydramoduleAssetType getAssetType(String uuid) throws APIException;

	HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory service) throws APIException;

	List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired) throws APIException;

	HydramoduleAssetCategory getAssetCategory(String uuid) throws APIException;

	HydramoduleAsset saveAsset(HydramoduleAsset service) throws APIException;

	List<HydramoduleAsset> getAllAssets(boolean retired) throws APIException;

	HydramoduleAsset getAsset(String uuid) throws APIException;

}
