package org.openmrs.module.hydra.api.impl;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.IHydraAssetService;
import org.openmrs.module.hydra.api.dao.IHydramoduleAssetDao;
import org.openmrs.module.hydra.model.HydramoduleAsset;
import org.openmrs.module.hydra.model.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.HydramoduleAssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraAssetService implements IHydraAssetService {
	
	@Autowired
	private IHydramoduleAssetDao assetDao;

	@Override
	public void setAssetDao(IHydramoduleAssetDao assetDao) {
		this.assetDao = assetDao;
	}

	@Override
	@Transactional
	public HydramoduleAssetType saveAssetType(HydramoduleAssetType service) throws APIException {
		return assetDao.saveAssetType(service);
	}

	@Override
	public List<HydramoduleAssetType> getAllAssetTypes(boolean retired) throws APIException {
		return assetDao.getAllAssetTypes(retired);
	}

	@Override
	public HydramoduleAssetType getAssetType(String uuid) throws APIException {
		return assetDao.getAssetType(uuid);
	}

	@Override
	@Transactional
	public HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory service) throws APIException {
		return assetDao.saveAssetCategory(service);
	}

	@Override
	public List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired) throws APIException {
		return assetDao.getAllAssetCategories(retired);
	}

	@Override
	public HydramoduleAssetCategory getAssetCategory(String uuid) throws APIException {
		return assetDao.getAssetCategory(uuid);
	}

	@Override
	@Transactional
	public HydramoduleAsset saveAsset(HydramoduleAsset service) throws APIException {
		return assetDao.saveAsset(service);
	}

	@Override
	public List<HydramoduleAsset> getAllAssets(boolean retired) throws APIException {
		return assetDao.getAllAssets(retired);
	}

	@Override
	public HydramoduleAsset getAsset(String uuid) throws APIException {
		return assetDao.getAsset(uuid);
	}

}
