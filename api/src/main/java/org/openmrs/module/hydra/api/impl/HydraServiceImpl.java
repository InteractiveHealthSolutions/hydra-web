/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api.impl;

import java.util.List;
import java.util.Set;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.model.event_planner.HydraForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflowPhases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraServiceImpl extends BaseOpenmrsService implements HydraService {

	@Autowired
	private HydraDaoImpl dao;

	// @Autowired
	// private UserService userService;

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(HydraDaoImpl dao) {
		this.dao = dao;
	}

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		// this.userService = userService;
	}

	@Override
	public HydraForm getHydraFormByUuid(String uuid) throws APIException {
		return dao.getHydraFormByUuid(uuid);
	}

	@Override
	@Transactional
	public HydraForm saveForm(HydraForm item) throws APIException {
		return dao.saveForm(item);
	}

	@Override
	@Transactional
	public HydramoduleWorkflow saveWorkflow(HydramoduleWorkflow item) throws APIException {
		return dao.saveWorkflow(item);
	}

	@Override
	@Transactional
	public HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases item) throws APIException {
		return dao.saveWorkflowPhaseRelation(item);
	}

	@Override
	@Transactional
	public HydramodulePhase savePhase(HydramodulePhase item) throws APIException {
		HydramodulePhase phase = dao.savePhase(item);
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
	public Set<HydraForm> getHydraFormsByTag(String tag) throws APIException {
		return dao.getHydraFormsByTag(tag);
	}

	@Override
	public HydraForm getHydraFormByEncounterName(String encunterName) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HydramoduleWorkflow getWorkflowByUUID(String uuid) throws APIException {

		return dao.getWorkflow(uuid);
	}

	@Override
	public List<HydramoduleWorkflow> getAllWorkflows() throws APIException {

		return dao.getAllWorkflows();
	}

	@Override
	public HydramodulePhase getPhaseByUUID(String uuid) throws APIException {

		return dao.getPhase(uuid);
	}

	@Override
	public List<HydramodulePhase> getAllPhases() throws APIException {

		return dao.getAllPhases();
	}

	@Override
	public HydramoduleComponent getComponentByUUID(String uuid) throws APIException {

		return dao.getComponent(uuid);
	}

	@Override
	public List<HydramoduleComponent> getAllComponents() throws APIException {

		return dao.getAllComponents();
	}

	@Override
	public HydramoduleWorkflowPhases getWorkflowPhasesRelationByUUID(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return dao.getWorkflowPhaseRelation(uuid);
	}

	@Override
	public List<HydramoduleWorkflowPhases> getAllWorkflowPhaseRelations() throws APIException {
		return dao.getAllPhasesWorkFlowPhaseRelations();
	}
}
