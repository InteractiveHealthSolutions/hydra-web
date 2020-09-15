package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.module.hydra.model.HydramoduleService;
import org.openmrs.module.hydra.model.HydramoduleServiceType;
import org.springframework.stereotype.Repository;

public interface IHydramoduleServiceDao {

	HydramoduleServiceType saveServiceType(HydramoduleServiceType serviceType);

	HydramoduleServiceType getServiceType(String uuid);

	List<HydramoduleServiceType> getAllServiceTypes(boolean retired);

	// Service
	HydramoduleService saveService(HydramoduleService service);

	HydramoduleService getService(String uuid);

	List<HydramoduleService> getAllServices(boolean retired);

}
