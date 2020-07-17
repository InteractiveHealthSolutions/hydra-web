package org.openmrs.module.hydra.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openmrs.EncounterType;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.IHydraFormService;
import org.openmrs.module.hydra.api.dao.IHydramoduleFormDao;
import org.openmrs.module.hydra.model.HydraForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraFormService extends BaseOpenmrsService implements IHydraFormService {

	@Autowired
	private IHydramoduleFormDao hydraFormDao;

	@Override
	public void setHydraFormDao(IHydramoduleFormDao formDao) {
		this.hydraFormDao = formDao;
	}

	@Override
	public HydraForm getHydraFormByUuid(String uuid) throws APIException {
		return hydraFormDao.getHydraFormByUuid(uuid);
	}

	@Override
	@Transactional
	public HydraForm saveForm(HydraForm item) throws APIException {
		return hydraFormDao.saveForm(item);
	}

	@Override
	public Set<HydraForm> getHydraFormsByTag(String tag) throws APIException {
		return hydraFormDao.getHydraFormsByTag(tag);
	}

	@Override
	public HydraForm getHydraFormByEncounterName(String encounterName) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HydramoduleForm saveHydramoduleForm(HydramoduleForm form) throws APIException {
		if (form.getHydramoduleFormId() == null) {
			EncounterType encounterType = new EncounterType();
			encounterType.setName(form.getName());
			encounterType.setDescription(form.getDescription());
			encounterType = Context.getEncounterService().saveEncounterType(encounterType);
			form.setEncounterType(encounterType);
		} else {
			EncounterType encounterType = Context.getEncounterService().getEncounterType(form.getName());
			form.setEncounterType(encounterType);
		}

		return hydraFormDao.saveModuleForm(form);
	}

	@Override
	public List<HydramoduleForm> getAllModuleForm() throws APIException {
		return hydraFormDao.getAllModuleForm();
	}

	@Override
	public List<HydramoduleForm> getAllModuleFormsByComponent(String componentUUID) throws APIException {
		return hydraFormDao.getAllModuleFormByComponentUUID(componentUUID);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException {
		return hydraFormDao.getModuleForm(uuid);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByName(String name) throws APIException {
		return hydraFormDao.getModuleFormByName(name);
	}

	@Override
	public HydramoduleFormField getFormFieldByUUID(String uuid) throws APIException {
		return hydraFormDao.getFormField(uuid);
	}
	
	@Override
	public List<HydramoduleFormField> getFormFieldsByForm(String uuid) throws APIException {
		HydramoduleForm form = hydraFormDao.getModuleForm(uuid);
		List<HydramoduleFormField> fields = hydraFormDao.getFormFields(form);
		return fields;
	}
	
	@Override
	public HydramoduleFormEncounter getFormEncounterByUUID(String uuid) throws APIException {
		return hydraFormDao.getFormEncounter(uuid);
	}
	
	@Override
	public List<HydramoduleFormEncounter> getAllFormEncounters() throws APIException {
		return hydraFormDao.getAllFormEncounters();
	}
	
	@Override
	public List<HydramoduleFormEncounter> searchFormEncounters(String searchString) throws APIException {
		return new ArrayList<HydramoduleFormEncounter>();
	}

	@Override
	public HydramoduleFormEncounter saveFormEncounter(HydramoduleFormEncounter formEncounter) {
		return hydraFormDao.saveFormEncounter(formEncounter);
	}

	@Override
	public List<HydramoduleFormEncounter> getAllFormEncounters(Integer componentFormId, Integer patientId) {
		
		return hydraFormDao.getAllFormEncounters(componentFormId, patientId);
	}

	

}
