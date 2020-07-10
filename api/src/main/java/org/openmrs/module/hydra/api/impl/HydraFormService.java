package org.openmrs.module.hydra.api.impl;

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

	private IHydramoduleFormDao formDao;

	@Override
	public void setFormDao(IHydramoduleFormDao formDao) {
		this.formDao = formDao;
	}

	@Override
	public HydraForm getHydraFormByUuid(String uuid) throws APIException {
		return formDao.getHydraFormByUuid(uuid);
	}

	@Override
	@Transactional
	public HydraForm saveForm(HydraForm item) throws APIException {
		return formDao.saveForm(item);
	}

	@Override
	public Set<HydraForm> getHydraFormsByTag(String tag) throws APIException {
		return formDao.getHydraFormsByTag(tag);
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

		return formDao.saveModuleForm(form);
	}

	@Override
	public List<HydramoduleForm> getAllModuleForm() throws APIException {
		return formDao.getAllModuleForm();
	}

	@Override
	public List<HydramoduleForm> getAllModuleFormsByComponent(String componentUUID) throws APIException {
		return formDao.getAllModuleFormByComponentUUID(componentUUID);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException {
		return formDao.getModuleForm(uuid);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByName(String name) throws APIException {
		return formDao.getModuleFormByName(name);
	}

	@Override
	public HydramoduleFormField getFormFieldByUUID(String uuid) throws APIException {
		return formDao.getFormField(uuid);
	}

	@Override
	public void saveFormEncounter(HydramoduleFormEncounter formEncounter) {
		formDao.saveFormEncounter(formEncounter);
	}

	@Override
	public List<HydramoduleFormField> getFormFieldsByForm(String uuid) throws APIException {
		HydramoduleForm form = formDao.getModuleForm(uuid);
		List<HydramoduleFormField> fields = formDao.getFormFields(form);
		return fields;
	}

}
