package org.openmrs.module.hydra.api.impl;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.IHydraWorkflowService;
import org.openmrs.module.hydra.api.dao.IHydramoduleWorkflowDao;
import org.openmrs.module.hydra.model.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.HydramoduleUserWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflowPhases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraWorkflowService extends BaseOpenmrsService implements IHydraWorkflowService {

	private IHydramoduleWorkflowDao workflowDao;

	@Override
	public void setWorkflowDao(IHydramoduleWorkflowDao workflowDao) {
		this.workflowDao = workflowDao;
	}

	@Override
	@Transactional
	public HydramoduleWorkflow saveWorkflow(HydramoduleWorkflow item) throws APIException {

		// if (item.getRetired()) {
		// List<HydramoduleWorkflowPhases> workflowPhase =
		// dao.getWorkflowPhase(item);
		// List<HydramodulePhaseComponents> phaseComp =
		// dao.getPhaseComponent(item);
		//
		// if (workflowPhase != null) {
		// for (HydramoduleWorkflowPhases hydramoduleWorkflowPhases :
		// workflowPhase)
		// {
		// deleteWorkflowPhase(hydramoduleWorkflowPhases);
		// }

		// if (phaseComp != null) {
		// for (HydramodulePhaseComponents hydramodulePhaseComponents :
		// phaseComp) {
		// deletePhaseComponent(hydramodulePhaseComponents);
		// }
		// }
		// }

		// return dao.saveWorkflow(item);
		//
		// }
		// else {
		// return dao.saveWorkflow(item);
		// }

		return workflowDao.saveWorkflow(item);
	}

	@Override
	@Transactional
	public HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases item) throws APIException {
		return workflowDao.saveWorkflowPhaseRelation(item);
	}

	@Override
	public HydramoduleWorkflow getWorkflowByUUID(String uuid) throws APIException {

		return workflowDao.getWorkflow(uuid);
	}

	@Override
	public HydramoduleWorkflow getWorkflowByName(String name) throws APIException {

		return workflowDao.getWorkflowByName(name);
	}

	@Override
	public List<HydramoduleWorkflow> getAllWorkflows() throws APIException {

		return workflowDao.getAllWorkflows();
	}

	@Override
	public HydramoduleWorkflowPhases getWorkflowPhasesRelationByUUID(String uuid) throws APIException {
		return workflowDao.getWorkflowPhaseRelation(uuid);
	}

	@Override
	public List<HydramoduleWorkflowPhases> getAllWorkflowPhaseRelations() throws APIException {
		return workflowDao.getAllPhasesWorkFlowPhaseRelations();
	}

	@Override
	public void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases) throws APIException {
		workflowDao.deleteWorkflowPhase(workflowphases);

	}

	@Override
	public void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public HydramodulePatientWorkflow saveHydramodulePatientWorkflow(HydramodulePatientWorkflow service)
	        throws APIException {
		return workflowDao.saveHydramodulePatientWorkflow(service);
	}

	@Override
	public List<HydramodulePatientWorkflow> getAllHydramodulePatientWorkflows() throws APIException {
		return workflowDao.getAllHydramodulePatientWorkflows();
	}

	@Override
	public HydramodulePatientWorkflow getHydramodulePatientWorkflow(String uuid) throws APIException {
		return workflowDao.getHydramodulePatientWorkflow(uuid);
	}

	@Override
	public HydramoduleUserWorkflow saveHydramoduleUserWorkflow(HydramoduleUserWorkflow hydramoduleUserWorkflow)
	        throws APIException {
		return workflowDao.saveHydramoduleUserWorkflow(hydramoduleUserWorkflow);
	}

	@Override
	public List<HydramoduleUserWorkflow> getAllHydramoduleUserWorkflow() throws APIException {
		return workflowDao.getAllHydramoduleUserWorkflow();
	}

	@Override
	public HydramoduleUserWorkflow getHydramoduleUserWorkflow(String uuid) throws APIException {
		return workflowDao.getHydramoduleUserWorkflow(uuid);
	}

	@Override
	public List<HydramoduleUserWorkflow> getUserWorkflowByUser(String uuid) throws APIException {
		User user = Context.getUserService().getUserByUuid(uuid);
		List<HydramoduleUserWorkflow> users = workflowDao.getUserWorkflowByUser(user);
		return users;
	}

	@Override
	public HydramodulePatientWorkflow getHydramodulePatientWorkflowByPatient(Integer patientId) throws APIException {
		Patient patient = Context.getPatientService().getPatient(patientId);
		HydramodulePatientWorkflow hydramodulePatientWorkflow = workflowDao.getPatientWorkflowByPatient(patient);
		return hydramodulePatientWorkflow;
	}

	@Override
	public List<HydramoduleWorkflowPhases> getWorkflowPhaseByWorkflow(String workflowUUID) throws APIException {
		HydramoduleWorkflow hydramoduleWorkflow = workflowDao.getWorkflow(workflowUUID);
		if (hydramoduleWorkflow != null) {
			List<HydramoduleWorkflowPhases> hydramoduleWorkflowPhases = workflowDao.getWorkflowPhase(hydramoduleWorkflow);
			return hydramoduleWorkflowPhases;

		}
		return null;
	}

}
