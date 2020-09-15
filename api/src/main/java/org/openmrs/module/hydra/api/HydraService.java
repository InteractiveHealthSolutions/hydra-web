package org.openmrs.module.hydra.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.IHydraAssetService;
import org.openmrs.module.hydra.api.IHydraComponentService;
import org.openmrs.module.hydra.api.IHydraEventService;
import org.openmrs.module.hydra.api.IHydraFieldService;
import org.openmrs.module.hydra.api.IHydraFormService;
import org.openmrs.module.hydra.api.IHydraParticipantService;
import org.openmrs.module.hydra.api.IHydraPhaseService;
import org.openmrs.module.hydra.api.IHydraServiceService;
import org.openmrs.module.hydra.api.IHydraWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface HydraService extends OpenmrsService {

	void setHydraEventService(IHydraEventService hydraEventService);

	void setHydraFieldService(IHydraFieldService hydraFieldService);

	void setHydraFormService(IHydraFormService hydraFormService);

	void setHydraParticipantService(IHydraParticipantService hydraParticipantService);

	void setHydraPhaseService(IHydraPhaseService hydraPhaseService);

	void setHydraServiceService(IHydraServiceService hydraServiceService);

	void setHydraWorkflowService(IHydraWorkflowService hydraWorkflowService);

	IHydraAssetService getHydraAssetService();

	IHydraComponentService getHydraComponentService();

	IHydraEventService getHydraEventService();

	IHydraFieldService getHydraFieldService();

	IHydraFormService getHydraFormService();

	IHydraParticipantService getHydraParticipantService();

	IHydraPhaseService getHydraPhaseService();

	IHydraServiceService getHydraServiceService();

	IHydraWorkflowService getHydraWorkflowService();

	void setHydraAssetService(IHydraAssetService hydraAssetService);

	void setHydraComponentService(IHydraComponentService hydraComponentService);

	void setHydraLocationTypeService(IHydraLocationTypeService hydraLocationTypeService);

	<<<<<<<

	HEAD HydramoduleWorkflow

	getWorkflowByName(String name) throws APIException;

	HydramoduleForm getHydraModuleFormByName(String name) throws APIException;

	HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,
			HydramoduleWorkflow hydramoduleWorkflow) throws APIException;

	HydramodulePatientWorkflow getHydramodulePatientWorkflowByPatient(Integer patientId) throws APIException;

	List<HydramodulePhaseComponents> getHydramodulePhaseComponentsByWorkflow(String uuid) throws APIException;

	List<HydramoduleWorkflowPhases> getWorkflowPhaseByWorkflow(String workflowUUID) throws APIException;

	List<HydramoduleComponentForm> getComponentFormsByComponent(String componentUUID) throws APIException;

	List<HydramoduleFormField> getFormFieldsByForm(String uuid) throws APIException;
	// HydramoduleEncounterMapper
	// saveHydramoduleEncounterMapper(HydramoduleEncounterMapper
	// hydramoduleEncounterMapper)
	// throws APIException;
	//
	// HydramoduleEncounterMapper getHydramoduleEncounterMapper(String uuid) throws
	// APIException;
	//
	// List<HydramoduleEncounterMapper> getAllHydramoduleEncounterMapper() throws
	// APIException;
	//
	// List<HydramoduleEncounterMapper> getEncounterMapperByPatient(String
	// patientIdentifier) throws APIException;
	=======

	IHydraLocationTypeService getHydraLocationTypeService();>>>>>>>qa

}
