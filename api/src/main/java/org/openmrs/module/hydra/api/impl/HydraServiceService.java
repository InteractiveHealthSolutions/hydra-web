package org.openmrs.module.hydra.api.impl;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.IHydraServiceService;
import org.openmrs.module.hydra.api.dao.IHydramoduleServiceDao;
import org.openmrs.module.hydra.model.HydramoduleService;
import org.openmrs.module.hydra.model.HydramoduleServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraServiceService extends BaseOpenmrsService implements IHydraServiceService {

	private IHydramoduleServiceDao serviceDao;

	@Override
	public void setServiceDao(IHydramoduleServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	@Override
	@Transactional
	public HydramoduleServiceType saveServiceType(HydramoduleServiceType form) throws APIException {
		return serviceDao.saveServiceType(form);
	}

	@Override
	public List<HydramoduleServiceType> getAllServiceTypes(boolean retired) throws APIException {
		return serviceDao.getAllServiceTypes(retired);
	}

	@Override
	public HydramoduleServiceType getServiceType(String uuid) throws APIException {
		return serviceDao.getServiceType(uuid);
	}

	@Override
	@Transactional
	public HydramoduleService saveService(HydramoduleService service) throws APIException {
		return serviceDao.saveService(service);
	}

	@Override
	public List<HydramoduleService> getAllServices(boolean retired) throws APIException {
		return serviceDao.getAllServices(retired);
	}

	@Override
	public HydramoduleService getService(String uuid) throws APIException {
		return serviceDao.getService(uuid);
	}

}
