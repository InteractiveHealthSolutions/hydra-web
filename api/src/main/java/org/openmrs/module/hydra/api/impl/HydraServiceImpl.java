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

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.model.event_planner.HydraForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleAsset;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetType;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponentForms;
import org.openmrs.module.hydra.model.workflow.HydramoduleForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleParticipant;
import org.openmrs.module.hydra.model.workflow.HydramoduleParticipantSalaryType;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
import org.openmrs.module.hydra.model.workflow.HydramodulePhaseComponents;
import org.openmrs.module.hydra.model.workflow.HydramoduleService;
import org.openmrs.module.hydra.model.workflow.HydramoduleServiceType;
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

		return dao.saveWorkflow(item);
	}

	public HydramoduleComponent saveComponent(HydramoduleComponent component) throws APIException {
		return dao.saveComponent(component);
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

	@Override
	public void purgeComponent(HydramoduleComponent component) throws APIException {
		component.setRetired(true);
		component.setRetiredBy(Context.getAuthenticatedUser());
		component.setDateRetired(new Date());
		dao.updateComponent(component);
	}

	@Override
	public HydramoduleForm saveModuleForm(HydramoduleForm form) throws APIException {
		return dao.saveModuleForm(form);
	}

	@Override
	public List<HydramoduleForm> getAllModuleForm() throws APIException {
		return dao.getAllModuleForm();
	}

	@Override
	public HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException {
		return dao.getModuleForm(uuid);
	}

	@Override
	public HydramodulePhaseComponents savePhaseComponentRelation(HydramodulePhaseComponents item) throws APIException {
		return dao.savePhaseComponentsRelation(item);
	}

	@Override
	public HydramodulePhaseComponents getPhasesComponentRelationByUUID(String uuid) throws APIException {
		return dao.getPhaseComponentRelation(uuid);
	}

	@Override
	public List<HydramodulePhaseComponents> getAllPhaseComponentsRelations() throws APIException {
		return dao.getAllPhaseComponentRelations();
	}

	@Override
	public HydramoduleComponentForms saveComponentFromRelation(HydramoduleComponentForms item) throws APIException {

		return dao.saveComponentFormRelation(item);
	}

	@Override
	public HydramoduleComponentForms getComponentFormRelationByUUID(String uuid) throws APIException {
		return dao.getComponentFormRelation(uuid);
	}

	@Override
	public List<HydramoduleComponentForms> getAllComponentFormRelations() throws APIException {
		return dao.getAllComponentFormRelations();
	}

	@Transactional
	@Override
	public void deleteComponentForm(HydramoduleComponentForms componentForm) throws APIException {
		dao.deleteComponentForm(componentForm);

	}

	@Transactional
	@Override
	public void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) throws APIException {
		dao.deletePhaseComponent(phaseComponent);

	}

	@Transactional
	@Override
	public void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases) throws APIException {
		dao.deleteWorkflowPhase(workflowphases);

	}

	@Override
	public void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	// ServiceType
	@Override
	@Transactional
	public HydramoduleServiceType saveServiceType(HydramoduleServiceType form) throws APIException {
		return dao.saveServiceType(form);
	}

	@Override
	public List<HydramoduleServiceType> getAllServiceTypes(boolean retired) throws APIException {
		return dao.getAllServiceTypes(retired);
	}

	@Override
	public HydramoduleServiceType getServiceType(String uuid) throws APIException {
		return dao.getServiceType(uuid);
	}

	// Service
	@Override
	@Transactional
	public HydramoduleService saveService(HydramoduleService service) throws APIException {
		return dao.saveService(service);
	}

	@Override
	public List<HydramoduleService> getAllServices(boolean retired) throws APIException {
		return dao.getAllServices(retired);
	}

	@Override
	public HydramoduleService getService(String uuid) throws APIException {
		return dao.getService(uuid);
	}

	// AssetType
	@Override
	@Transactional
	public HydramoduleAssetType saveAssetType(HydramoduleAssetType service) throws APIException {
		return dao.saveAssetType(service);
	}

	@Override
	public List<HydramoduleAssetType> getAllAssetTypes(boolean retired) throws APIException {
		return dao.getAllAssetTypes(retired);
	}

	@Override
	public HydramoduleAssetType getAssetType(String uuid) throws APIException {
		return dao.getAssetType(uuid);
	}

	// AssetCategory
	@Override
	@Transactional
	public HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory service) throws APIException {
		return dao.saveAssetCategory(service);
	}

	@Override
	public List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired) throws APIException {
		return dao.getAllAssetCategories(retired);
	}

	@Override
	public HydramoduleAssetCategory getAssetCategory(String uuid) throws APIException {
		return dao.getAssetCategory(uuid);
	}

	// Asset
	@Override
	@Transactional
	public HydramoduleAsset saveAsset(HydramoduleAsset service) throws APIException {
		return dao.saveAsset(service);
	}

	@Override
	public List<HydramoduleAsset> getAllAssets(boolean retired) throws APIException {
		return dao.getAllAssets(retired);
	}

	@Override
	public HydramoduleAsset getAsset(String uuid) throws APIException {
		return dao.getAsset(uuid);
	}

	// Participant
	@Override
	@Transactional
	public HydramoduleParticipant saveParticipant(HydramoduleParticipant service) throws APIException {
		return dao.saveParticipant(service);
	}

	@Override
	public List<HydramoduleParticipant> getAllParticipants(boolean retired) throws APIException {
		return dao.getAllParticipants(retired);
	}

	@Override
	public HydramoduleParticipant getParticipant(String uuid) throws APIException {
		return dao.getParticipant(uuid);
	}

	// ParticipantSalaryType
	@Override
	@Transactional
	public HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType service)
	        throws APIException {
		return dao.saveParticipantSalaryType(service);
	}

	@Override
	public List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired) throws APIException {
		return dao.getAllParticipantSalaryTypes(retired);
	}

	@Override
	public HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid) throws APIException {
		return dao.getParticipantSalaryType(uuid);
	}
}
