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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.EncounterType;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.SExprHelper;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.model.event_planner.HydraForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleAsset;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetType;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponentForm;
//import org.openmrs.module.hydra.model.workflow.HydramoduleEncounterMapper;
import org.openmrs.module.hydra.model.workflow.HydramoduleEvent;
import org.openmrs.module.hydra.model.workflow.HydramoduleEventAsset;
import org.openmrs.module.hydra.model.workflow.HydramoduleEventParticipants;
import org.openmrs.module.hydra.model.workflow.HydramoduleEventSchedule;
import org.openmrs.module.hydra.model.workflow.HydramoduleEventService;
import org.openmrs.module.hydra.model.workflow.HydramoduleEventType;
import org.openmrs.module.hydra.model.workflow.HydramoduleField;
import org.openmrs.module.hydra.model.workflow.HydramoduleFieldAnswer;
import org.openmrs.module.hydra.model.workflow.HydramoduleFieldDTO;
import org.openmrs.module.hydra.model.workflow.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.workflow.HydramoduleForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.workflow.HydramoduleFormField;
import org.openmrs.module.hydra.model.workflow.HydramoduleParticipant;
import org.openmrs.module.hydra.model.workflow.HydramoduleParticipantSalaryType;
import org.openmrs.module.hydra.model.workflow.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
import org.openmrs.module.hydra.model.workflow.HydramodulePhaseComponents;
import org.openmrs.module.hydra.model.workflow.HydramoduleRuleToken;
import org.openmrs.module.hydra.model.workflow.HydramoduleService;
import org.openmrs.module.hydra.model.workflow.HydramoduleServiceType;
import org.openmrs.module.hydra.model.workflow.HydramoduleUserWorkflow;
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
	public HydramoduleWorkflow getWorkflowByName(String name) throws APIException {

		return dao.getWorkflowByName(name);
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
	public HydramoduleForm saveHydramoduleForm(HydramoduleForm form) throws APIException {
		if (form.getHydramoduleFormId() == null) {
			EncounterType encounterType = new EncounterType();
			encounterType.setName(form.getName());
			encounterType.setDescription(form.getDescription());
			encounterType = Context.getEncounterService().saveEncounterType(encounterType);
			form.setEncounterType(encounterType);
		} else {
			EncounterType encounterType = Context.getEncounterService().getEncounterType(form.getName());
			form.setEncounterType(encounterType);
		}

		return dao.saveModuleForm(form);
	}

	@Override
	public List<HydramoduleForm> getAllModuleForm() throws APIException {
		return dao.getAllModuleForm();
	}

	@Override
	public List<HydramoduleForm> getAllModuleFormsByComponent(String componentUUID) throws APIException {
		return dao.getAllModuleFormByComponentUUID(componentUUID);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException {
		return dao.getModuleForm(uuid);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByName(String name) throws APIException {
		return dao.getModuleFormByName(name);
	}

	@Override
	public HydramodulePhaseComponents savePhaseComponentRelation(HydramodulePhaseComponents item) throws APIException {
		return dao.savePhaseComponentsRelation(item);
	}

	@Override
	public HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm item) throws APIException {
		return dao.saveComponentFormRelation(item);
	}

	@Override
	public HydramodulePhaseComponents getPhasesComponentRelationByUUID(String uuid) throws APIException {
		return dao.getPhaseComponentRelation(uuid);
	}

	@Override
	public HydramoduleComponentForm getComponentFormByUUID(String uuid) throws APIException {
		return dao.getComponentFormRelation(uuid);
	}

	@Override
	public List<HydramodulePhaseComponents> getAllPhaseComponentsRelations() throws APIException {
		return dao.getAllPhaseComponentRelations();
	}

	@Override
	public List<HydramoduleComponentForm> getAllComponentFormsRelations() throws APIException, CloneNotSupportedException {
		List<HydramoduleComponentForm> componentForms = dao.getAllComponentFormRelations();

		SExprHelper exprHelper = SExprHelper.getInstance();

		for (HydramoduleComponentForm cf : componentForms) {
			HydramoduleForm form = cf.getForm();
			List<HydramoduleFormField> formFields = form.getFormFields();
			for (HydramoduleFormField ff : formFields) {
				// HydramoduleField field = dao.getHydramoduleField(ff.getField().getUuid());
				HydramoduleField field = ff.getField().clone(); // This is the targetField of a rule
				String parsedRule = exprHelper.compileComplex(dao, ff);
				// if (parsedRule != null) {
				field.setParsedRule(parsedRule);
				ff.setField(field);
				// }
			}
		}

		return componentForms;
	}

	@Override
	public HydramoduleFormField getFormFieldByUUID(String uuid) throws APIException {
		return dao.getFormField(uuid);
	}

	@Override
	public void saveFormEncounter(HydramoduleFormEncounter formEncounter) {
		dao.saveFormEncounter(formEncounter);
	}

	@Transactional
	@Override
	public void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) throws APIException {
		dao.deletePhaseComponent(phaseComponent);

	}

	@Transactional
	@Override
	public void retireComponentForm(HydramoduleComponentForm phaseComponent) throws APIException {
		phaseComponent.setRetired(true);
		phaseComponent.setRetiredBy(Context.getAuthenticatedUser());
		phaseComponent.setDateRetired(new Date());
		// dao.updateComponent(component);
		dao.updateComponentForm(phaseComponent);
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

	@Override
	public List<HydramoduleParticipant> getParticipantByUserUUID(String userUUID) throws APIException {
		User user = Context.getUserService().getUserByUuid(userUUID);
		List<HydramoduleParticipant> participantsList = new ArrayList();
		participantsList.add(dao.getParticipantByUser(user));
		return participantsList;
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

	// Event
	@Override
	@Transactional
	public HydramoduleEvent saveEvent(HydramoduleEvent service) throws APIException {
		return dao.saveHydramoduleEvent(service);
	}

	@Override
	public List<HydramoduleEvent> getAllEvents(boolean voided) throws APIException {
		return dao.getAllHydramoduleEvents(voided);
	}

	@Override
	public HydramoduleEvent getEvent(String uuid) throws APIException {
		return dao.getHydramoduleEvent(uuid);
	}

	// EventSchedule
	@Override
	@Transactional
	public HydramoduleEventSchedule saveEventSchedule(HydramoduleEventSchedule service) throws APIException {
		return dao.saveHydramoduleEventScedule(service);
	}

	@Override
	public List<HydramoduleEventSchedule> getAllEventSchedules(boolean voided) throws APIException {
		return dao.getAllHydramoduleEventScedules(voided);
	}

	@Override
	public HydramoduleEventSchedule getEventSchedule(String uuid) throws APIException {
		return dao.getHydramoduleEventScedule(uuid);
	}

	// EventType
	@Override
	@Transactional
	public HydramoduleEventType saveEventType(HydramoduleEventType service) throws APIException {
		return dao.saveHydramoduleEventType(service);
	}

	@Override
	public List<HydramoduleEventType> getAllEventTypes(boolean voided) throws APIException {
		return dao.getAllHydramoduleEventTypes(voided);
	}

	@Override
	public HydramoduleEventType getEventType(String uuid) throws APIException {
		return dao.getHydramoduleEventType(uuid);
	}

	// EventService
	@Override
	@Transactional
	public HydramoduleEventService saveEventService(HydramoduleEventService service) throws APIException {
		return dao.saveHydramoduleEventService(service);
	}

	@Override
	public List<HydramoduleEventService> getAllEventServices(boolean voided) throws APIException {
		return dao.getAllHydramoduleEventServices(voided);
	}

	@Override
	public HydramoduleEventService getEventService(String uuid) throws APIException {
		return dao.getHydramoduleEventService(uuid);
	}

	// EventAsset
	@Override
	@Transactional
	public HydramoduleEventAsset saveEventAsset(HydramoduleEventAsset service) throws APIException {
		return dao.saveHydramoduleEventAsset(service);
	}

	@Override
	public List<HydramoduleEventAsset> getAllEventAssets(boolean voided) throws APIException {
		return dao.getAllHydramoduleEventAssets(voided);
	}

	@Override
	public HydramoduleEventAsset getEventAsset(String uuid) throws APIException {
		return dao.getHydramoduleEventAsset(uuid);
	}

	// EventParticipant
	@Override
	@Transactional
	public HydramoduleEventParticipants saveEventParticipant(HydramoduleEventParticipants service) throws APIException {
		return dao.saveHydramoduleEventParticipant(service);
	}

	@Override
	public List<HydramoduleEventParticipants> getAllEventParticipants(boolean voided) throws APIException {
		return dao.getAllHydramoduleEventParticipants(voided);
	}

	@Override
	public HydramoduleEventParticipants getEventParticipant(String uuid) throws APIException {
		return dao.getHydramoduleEventParticipant(uuid);
	}

	// Field
	@Override
	@Transactional
	public HydramoduleField saveField(HydramoduleFieldDTO dto) throws APIException {

		return dao.saveField(dto);
	}

	public List<HydramoduleFieldDTO> getFieldsByName(String name) throws APIException {
		List<HydramoduleField> fields = dao.getAllFieldsByName(name);
		List<HydramoduleFieldDTO> fieldDTOs = new ArrayList();
		for (HydramoduleField f : fields) {
			HydramoduleFieldDTO dto = new HydramoduleFieldDTO();
			List<HydramoduleFieldAnswer> answers = dao.getAllFieldAnswersByID(f);
			dto.setField(f);
			dto.setAnswers(answers);
			fieldDTOs.add(dto);
		}
		return fieldDTOs;
	}

	// HydramoduleField
	@Override
	@Transactional
	public HydramoduleField saveHydramoduleField(HydramoduleField service) throws APIException {
		return dao.saveHydramoduleField(service);
	}

	@Override
	public List<HydramoduleField> getAllHydramoduleFields() throws APIException {
		return dao.getAllHydramoduleFields();
	}

	@Override
	public HydramoduleField getHydramoduleField(String uuid) throws APIException {
		return dao.getHydramoduleField(uuid);
	}

	@Override
	public List<HydramoduleField> getHydramoduleFieldsByName(String queryParam) {
		// TODO Auto-generated method stub
		return dao.getAllFieldsByName(queryParam);
	}

	// HydramoduleFieldAnswer
	@Override
	@Transactional
	public HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer service) throws APIException {
		return dao.saveHydramoduleFieldAnswer(service);
	}

	@Override
	public List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean voided) throws APIException {
		return dao.getAllHydramoduleFieldAnswers(voided);
	}

	@Override
	public HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid) throws APIException {
		return dao.getHydramoduleFieldAnswer(uuid);
	}

	// HydramoduleFieldRule
	@Override
	@Transactional
	public HydramoduleFieldRule saveHydramoduleFieldRule(HydramoduleFieldRule service) throws APIException {
		return dao.saveHydramoduleFieldRule(service);
	}

	@Override
	public List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean voided) throws APIException {
		return dao.getAllHydramoduleFieldRules(voided);
	}

	@Override
	public HydramoduleFieldRule getHydramoduleFieldRule(String uuid) throws APIException {
		return dao.getHydramoduleFieldRule(uuid);
	}

	// HydramoduleRuleToken
	@Override
	@Transactional
	public HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken service) throws APIException {
		return dao.saveHydramoduleRuleToken(service);
	}

	@Override
	public List<HydramoduleRuleToken> getAllHydramoduleRuleTokens() throws APIException {
		return dao.getAllHydramoduleRuleTokens();
	}

	@Override
	public HydramoduleRuleToken getHydramoduleRuleToken(String uuid) throws APIException {
		return dao.getHydramoduleRuleToken(uuid);
	}

	// HydramodulePatientWorkflow
	@Override
	@Transactional
	public HydramodulePatientWorkflow saveHydramodulePatientWorkflow(HydramodulePatientWorkflow service)
	        throws APIException {
		return dao.saveHydramodulePatientWorkflow(service);
	}

	@Override
	public List<HydramodulePatientWorkflow> getAllHydramodulePatientWorkflows() throws APIException {
		return dao.getAllHydramodulePatientWorkflows();
	}

	@Override
	public HydramodulePatientWorkflow getHydramodulePatientWorkflow(String uuid) throws APIException {
		return dao.getHydramodulePatientWorkflow(uuid);
	}

	// HydramoduleUserWorkflow
	@Override
	public HydramoduleUserWorkflow saveHydramoduleUserWorkflow(HydramoduleUserWorkflow hydramoduleUserWorkflow)
	        throws APIException {
		return dao.saveHydramoduleUserWorkflow(hydramoduleUserWorkflow);
	}

	@Override
	public List<HydramoduleUserWorkflow> getAllHydramoduleUserWorkflow() throws APIException {
		return dao.getAllHydramoduleUserWorkflow();
	}

	@Override
	public HydramoduleUserWorkflow getHydramoduleUserWorkflow(String uuid) throws APIException {
		return dao.getHydramoduleUserWorkflow(uuid);
	}

	@Override
	public List<HydramoduleUserWorkflow> getUserWorkflowByUser(String uuid) throws APIException {
		User user = Context.getUserService().getUserByUuid(uuid);
		List<HydramoduleUserWorkflow> users = dao.getUserWorkflowByUser(user);
		return users;
	}

	@Override
	public HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,
			HydramoduleWorkflow hydramoduleWorkflow) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HydramodulePatientWorkflow getHydramodulePatientWorkflowByPatient(Integer patientId) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HydramodulePhaseComponents> getHydramodulePhaseComponentsByWorkflow(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HydramoduleWorkflowPhases> getWorkflowPhaseByWorkflow(String workflowUUID) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HydramoduleComponentForm> getComponentFormsByComponent(String componentUUID) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HydramoduleFormField> getFormFieldsByForm(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	// // HydramoduleEncounterMapper
	// @Override
	// public HydramoduleEncounterMapper
	// saveHydramoduleEncounterMapper(HydramoduleEncounterMapper
	// hydramoduleEncounterMapper)
	// throws APIException {
	// return dao.saveHydramoduleEncounterMapper(hydramoduleEncounterMapper);
	// }
	//
	// @Override
	// public List<HydramoduleEncounterMapper> getAllHydramoduleEncounterMapper()
	// throws APIException {
	// return dao.getAllHydramoduleEncounterMapper();
	// }
	//
	// @Override
	// public HydramoduleEncounterMapper getHydramoduleEncounterMapper(String uuid)
	// throws APIException {
	// return dao.getHydramoduleEncounterMapper(uuid);
	// }
	//
	// @Override
	// public List<HydramoduleEncounterMapper> getEncounterMapperByPatient(String
	// patientIdentifier) throws APIException {
	// return dao.getEncounterMapperByPatient(patientIdentifier);
	// }

}
