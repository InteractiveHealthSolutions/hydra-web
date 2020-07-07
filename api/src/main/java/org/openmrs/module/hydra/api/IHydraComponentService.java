package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.dao.IHydramoduleComponentDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFieldDao;
import org.openmrs.module.hydra.model.HydramoduleComponent;
import org.openmrs.module.hydra.model.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;

public interface IHydraComponentService {

	void setFieldDao(IHydramoduleFieldDao fieldDao);

	void setComponentDao(IHydramoduleComponentDao componentDao);

	HydramoduleComponent saveComponent(HydramoduleComponent component) throws APIException;

	HydramoduleComponent getComponentByUUID(String uuid) throws APIException;

	List<HydramoduleComponent> getAllComponents() throws APIException;

	void purgeComponent(HydramoduleComponent component) throws APIException;

	HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm item) throws APIException;

	HydramoduleComponentForm getComponentFormByUUID(String uuid) throws APIException;

	List<HydramoduleComponentForm> getAllComponentFormsRelations() throws APIException, CloneNotSupportedException;

	void retireComponentForm(HydramoduleComponentForm phaseComponent) throws APIException;

	HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,
			HydramoduleWorkflow hydramoduleWorkflow) throws APIException;

	List<HydramoduleComponentForm> getComponentFormsByComponent(String componentUUID) throws APIException;

}