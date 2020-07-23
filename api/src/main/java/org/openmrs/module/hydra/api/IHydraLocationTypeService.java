package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.module.hydra.api.dao.IHydramoduleLocationTypeDao;
import org.openmrs.module.hydra.api.dao.impl.HydramoduleLocationTypeDao;
import org.openmrs.module.hydra.model.HydramoduleLocationType;

public interface IHydraLocationTypeService {

	void setLocationTypeDao(IHydramoduleLocationTypeDao locationTypeDao);

	HydramoduleLocationType saveHydramoduleLocationType(HydramoduleLocationType hydramoduleLocationType);

	List<HydramoduleLocationType> getAllLocationType(boolean retired);

	HydramoduleLocationType getLocationTypeByUuid(String uuid);

}
