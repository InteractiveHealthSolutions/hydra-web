package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hydra.api.dao.IHydramoduleWorkflowDao;
import org.openmrs.module.hydra.model.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.HydramoduleUserWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflowPhases;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraWorkflowService extends OpenmrsService {

	void setWorkflowDao(IHydramoduleWorkflowDao workflowDao);

	HydramoduleWorkflow saveWorkflow(HydramoduleWorkflow item) throws APIException;

	HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases item) throws APIException;

	HydramoduleWorkflow getWorkflowByUUID(String uuid) throws APIException;

	HydramoduleWorkflow getWorkflowByName(String name) throws APIException;

	List<HydramoduleWorkflow> getAllWorkflows() throws APIException;

	HydramoduleWorkflowPhases getWorkflowPhasesRelationByUUID(String uuid) throws APIException;

	List<HydramoduleWorkflowPhases> getAllWorkflowPhaseRelations() throws APIException;

	void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases) throws APIException;

	void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException;

	void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException;

	HydramodulePatientWorkflow saveHydramodulePatientWorkflow(HydramodulePatientWorkflow service) throws APIException;

	List<HydramodulePatientWorkflow> getAllHydramodulePatientWorkflows() throws APIException;

	HydramodulePatientWorkflow getHydramodulePatientWorkflow(String uuid) throws APIException;

	HydramoduleUserWorkflow saveHydramoduleUserWorkflow(HydramoduleUserWorkflow hydramoduleUserWorkflow) throws APIException;

	List<HydramoduleUserWorkflow> getAllHydramoduleUserWorkflow() throws APIException;

	HydramoduleUserWorkflow getHydramoduleUserWorkflow(String uuid) throws APIException;

	List<HydramoduleUserWorkflow> getUserWorkflowByUser(String uuid) throws APIException;

	HydramodulePatientWorkflow getHydramodulePatientWorkflowByPatient(Integer patientId) throws APIException;

	List<HydramoduleWorkflowPhases> getWorkflowPhaseByWorkflow(String workflowUUID) throws APIException;

}
