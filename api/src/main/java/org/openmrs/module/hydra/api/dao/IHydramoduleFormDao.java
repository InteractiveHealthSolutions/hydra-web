package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.model.HydraForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.model.HydramoduleFormFieldGroup;
import org.springframework.stereotype.Repository;

public interface IHydramoduleFormDao {

	HydramoduleForm getModuleFormByName(String name);

	HydraForm getHydraFormByUuid(String uuid);

	HydraForm getHydraModuleFormByTag(String tag);

	java.util.Set<HydraForm> getHydraFormsByTag(String tag) throws APIException;

	HydraForm saveForm(HydraForm form);

	// TODO could be done better way, right now deleting all the existing then
	// adding new in case of edit
	HydramoduleForm saveModuleForm(HydramoduleForm form);

	HydramoduleFormField saveFormField(HydramoduleFormField field);

	List<HydramoduleFormField> getFormFields(HydramoduleForm form);

	List<HydramoduleFormField> getFormFields(Integer formId);

	HydramoduleFormField getFormField(String uuid);

	void deleteFormFields(HydramoduleForm form);

	// TODO use criteria
	void deleteFormFields(List<HydramoduleFormField> formFields);

	List<HydramoduleForm> getAllModuleForm();

	List<HydramoduleForm> getAllModuleFormByComponentUUID(String componentUUID);

	HydramoduleForm getModuleForm(String uuid);

	// componentForm
	HydramoduleFormEncounter saveFormEncounter(HydramoduleFormEncounter formEncounter);

	HydramoduleFormField getHydramoduleFormField(String formUUID, String fieldUUID);

	HydramoduleFormFieldGroup saveHydramoduleFormFieldGroup(HydramoduleFormFieldGroup fieldGroup);

	HydramoduleFormFieldGroup getHydramoduleFormFieldGroup(String uuid);

	List<HydramoduleFormFieldGroup> getAllHydramoduleFormFieldGroups();

}
