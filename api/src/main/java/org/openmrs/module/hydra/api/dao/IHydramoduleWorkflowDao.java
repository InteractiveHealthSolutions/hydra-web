package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.module.hydra.model.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.HydramoduleUserWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflowPhases;
import org.springframework.stereotype.Repository;

public interface IHydramoduleWorkflowDao {

	HydramoduleWorkflow getWorkflow(String uuid);

	HydramoduleWorkflow getWorkflowByName(String name);

	List<HydramoduleWorkflow> getAllWorkflows();

	HydramoduleWorkflow saveWorkflow(HydramoduleWorkflow workflow);

	void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException;

	void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException;

	HydramoduleWorkflowPhases getWorkflowPhaseRelation(String uuid);

	List<HydramoduleWorkflowPhases> getAllPhasesWorkFlowPhaseRelations();

	HydramoduleWorkflowPhases saveWorkflowPhase(HydramoduleWorkflowPhases phases);

	HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases workflow);

	void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases);

	List<HydramoduleWorkflowPhases> getWorkflowPhase(HydramoduleWorkflow workflow);

	HydramodulePatientWorkflow saveHydramodulePatientWorkflow(HydramodulePatientWorkflow patientWorkflow);

	HydramodulePatientWorkflow getHydramodulePatientWorkflow(String uuid);

	HydramodulePatientWorkflow getPatientWorkflowByPatient(Patient patient);

	List<HydramodulePatientWorkflow> getAllHydramodulePatientWorkflows();

	HydramoduleUserWorkflow saveHydramoduleUserWorkflow(HydramoduleUserWorkflow hydramoduleUserWorkflow);

	HydramoduleUserWorkflow getHydramoduleUserWorkflow(String uuid);

	List<HydramoduleUserWorkflow> getAllHydramoduleUserWorkflow();

	List<HydramoduleUserWorkflow> getUserWorkflowByUser(User user);


}
