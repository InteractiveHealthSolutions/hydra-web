package org.openmrs.module.hydra.api;

import java.util.List;
import java.util.Set;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hydra.api.dao.IHydramoduleFormDao;
import org.openmrs.module.hydra.model.HydraForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraFormService extends OpenmrsService {

	void setFormDao(IHydramoduleFormDao formDao);

	HydraForm getHydraFormByUuid(String uuid) throws APIException;

	HydraForm saveForm(HydraForm item) throws APIException;

	Set<HydraForm> getHydraFormsByTag(String tag) throws APIException;

	HydraForm getHydraFormByEncounterName(String encounterName) throws APIException;

	HydramoduleForm saveHydramoduleForm(HydramoduleForm form) throws APIException;

	List<HydramoduleForm> getAllModuleForm() throws APIException;

	List<HydramoduleForm> getAllModuleFormsByComponent(String componentUUID) throws APIException;

	HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException;

	HydramoduleForm getHydraModuleFormByName(String name) throws APIException;

	HydramoduleFormField getFormFieldByUUID(String uuid) throws APIException;

	void saveFormEncounter(HydramoduleFormEncounter formEncounter);

	List<HydramoduleFormField> getFormFieldsByForm(String uuid) throws APIException;

}
