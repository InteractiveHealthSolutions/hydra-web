package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.dao.IHydramoduleServiceDao;
import org.openmrs.module.hydra.model.HydramoduleService;
import org.openmrs.module.hydra.model.HydramoduleServiceType;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraServiceService {

	void setServiceDao(IHydramoduleServiceDao serviceDao);

	HydramoduleServiceType saveServiceType(HydramoduleServiceType form) throws APIException;

	List<HydramoduleServiceType> getAllServiceTypes(boolean retired) throws APIException;

	HydramoduleServiceType getServiceType(String uuid) throws APIException;

	HydramoduleService saveService(HydramoduleService service) throws APIException;

	List<HydramoduleService> getAllServices(boolean retired) throws APIException;

	HydramoduleService getService(String uuid) throws APIException;

}