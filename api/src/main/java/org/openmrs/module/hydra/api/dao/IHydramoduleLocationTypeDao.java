package org.openmrs.module.hydra.api.dao;

import java.util.List;

import org.openmrs.module.hydra.model.HydramoduleLocationType;

public interface IHydramoduleLocationTypeDao {

	HydramoduleLocationType saveLocationType(HydramoduleLocationType hydramoduleLocationType);

	HydramoduleLocationType getLocationTypeByUuid(String uuid);

	List<HydramoduleLocationType> getAllLocationType(boolean retired);

}
