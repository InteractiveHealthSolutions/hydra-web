package org.openmrs.module.hydra.api.impl;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.IHydraPhaseService;
import org.openmrs.module.hydra.api.dao.IHydramodulePhaseDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleWorkflowDao;
import org.openmrs.module.hydra.model.HydramodulePhase;
import org.openmrs.module.hydra.model.HydramodulePhaseComponents;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraPhaseService implements IHydraPhaseService {
	
	@Autowired
	private IHydramodulePhaseDao phaseDao;
	
	@Autowired
	private IHydramoduleWorkflowDao workflowDao;


	@Override
	public void setPhaseDao(IHydramodulePhaseDao phaseDao) {
		this.phaseDao = phaseDao;
	}

	@Override
	public void setWorkflowDao(IHydramoduleWorkflowDao workflowDao) {
		this.workflowDao = workflowDao;
	}

	@Override
	public HydramodulePhase savePhase(HydramodulePhase item) throws APIException {
		HydramodulePhase phase = phaseDao.savePhase(item);
		// This set
		/*
		 * List<HydramoduleWorkflowPhases> workflowPhases =
		 * phase.getHydramoduleWorkflowPhases();
		 * 
		 * List<HydramoduleWorkflowPhases> saveable = new
		 * ArrayList<HydramoduleWorkflowPhases>(); Iterator<HydramoduleWorkflowPhases>
		 * it = workflowPhases.iterator(); while (it.hasNext()) {
		 * HydramoduleWorkflowPhases workflowPhase = (HydramoduleWorkflowPhases)
		 * it.next(); String workflowUUID = workflowPhase.getWorkflowUUID();
		 * HydramoduleWorkflow workflow = dao.getWorkflow(workflowUUID);
		 * 
		 * workflowPhase.setHydramodulePhase(phase);
		 * workflowPhase.setHydramoduleWorkflow(workflow); saveable.add(workflowPhase);
		 * }
		 * 
		 * // Saving workflow phases Iterator<HydramoduleWorkflowPhases> saveableIt =
		 * saveable.iterator(); while (saveableIt.hasNext()) { HydramoduleWorkflowPhases
		 * type = (HydramoduleWorkflowPhases) saveableIt.next();
		 * dao.saveWorkflowPhase(type); }
		 */

		return phase;
	}
	
	@Override
	public HydramodulePhase getPhaseByUUID(String uuid) throws APIException {

		return phaseDao.getPhase(uuid);
	}

	@Override
	public List<HydramodulePhase> getAllPhases() throws APIException {

		return phaseDao.getAllPhases();
	}
	
	@Override
	public HydramodulePhaseComponents savePhaseComponentRelation(HydramodulePhaseComponents item) throws APIException {
		return phaseDao.savePhaseComponentsRelation(item);
	}
	
	@Override
	public HydramodulePhaseComponents getPhasesComponentRelationByUUID(String uuid) throws APIException {
		return phaseDao.getPhaseComponentRelation(uuid);
	}

	@Override
	public List<HydramodulePhaseComponents> getAllPhaseComponentsRelations() throws APIException {
		return phaseDao.getAllPhaseComponentRelations();
	}
	
	@Override
	@Transactional
	public void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) throws APIException {
		phaseDao.deletePhaseComponent(phaseComponent);

	}
	
	@Override
	public List<HydramodulePhaseComponents> getHydramodulePhaseComponentsByWorkflow(String uuid) throws APIException {

		HydramoduleWorkflow hydramoduleWorkflow = workflowDao.getWorkflow(uuid);
		List<HydramodulePhaseComponents> hydramodulePhaseComponents = phaseDao
		        .getPhaseComponentByWorkflow(hydramoduleWorkflow);

		return hydramodulePhaseComponents;
	}


}
