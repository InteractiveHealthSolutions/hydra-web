package org.openmrs.module.hydra.api.impl;

import java.util.List;

import org.openmrs.module.hydra.api.IHydraLocationTypeService;
import org.openmrs.module.hydra.api.dao.IHydramoduleLocationTypeDao;
import org.openmrs.module.hydra.api.dao.impl.HydramoduleLocationTypeDao;
import org.openmrs.module.hydra.model.HydramoduleLocationType;
import org.springframework.beans.factory.annotation.Autowired;

public class HydraLocationTypeService implements IHydraLocationTypeService {

	@Autowired
	private IHydramoduleLocationTypeDao hydraLocationTypeDao;

	@Override
	public void setLocationTypeDao(IHydramoduleLocationTypeDao locationTypeDao) {
		this.hydraLocationTypeDao = locationTypeDao;
	}

	@Override
	public HydramoduleLocationType saveHydramoduleLocationType(HydramoduleLocationType hydramoduleLocationType) {
		return hydraLocationTypeDao.saveLocationType(hydramoduleLocationType);
	}

	@Override
	public List<HydramoduleLocationType> getAllLocationType(boolean retired) {
		return hydraLocationTypeDao.getAllLocationType(retired);
	}

	@Override
	public HydramoduleLocationType getLocationTypeByUuid(String uuid) {
		return hydraLocationTypeDao.getLocationTypeByUuid(uuid);
	}

}
