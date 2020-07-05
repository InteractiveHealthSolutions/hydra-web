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
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.SExprHelper;
import org.openmrs.module.hydra.api.dao.IHydramoduleAssetDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleComponentDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleEventDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFieldDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFormDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleParticipantDao;
import org.openmrs.module.hydra.api.dao.IHydramodulePhaseDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleServiceDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleWorkflowDao;
import org.openmrs.module.hydra.model.HydraForm;
import org.openmrs.module.hydra.model.HydramoduleAsset;
import org.openmrs.module.hydra.model.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.HydramoduleAssetType;
import org.openmrs.module.hydra.model.HydramoduleComponent;
import org.openmrs.module.hydra.model.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.HydramoduleEvent;
import org.openmrs.module.hydra.model.HydramoduleEventAsset;
import org.openmrs.module.hydra.model.HydramoduleEventParticipants;
import org.openmrs.module.hydra.model.HydramoduleEventSchedule;
import org.openmrs.module.hydra.model.HydramoduleEventService;
import org.openmrs.module.hydra.model.HydramoduleEventType;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleFieldAnswer;
import org.openmrs.module.hydra.model.HydramoduleFieldDTO;
import org.openmrs.module.hydra.model.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.model.HydramoduleParticipant;
import org.openmrs.module.hydra.model.HydramoduleParticipantSalaryType;
import org.openmrs.module.hydra.model.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.HydramodulePhase;
import org.openmrs.module.hydra.model.HydramodulePhaseComponents;
import org.openmrs.module.hydra.model.HydramoduleRuleToken;
import org.openmrs.module.hydra.model.HydramoduleService;
import org.openmrs.module.hydra.model.HydramoduleServiceType;
import org.openmrs.module.hydra.model.HydramoduleUserWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflowPhases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraServiceImpl extends BaseOpenmrsService implements HydraService {

	@Autowired
	private IHydramoduleAssetDao assetDao;

	@Autowired
	private IHydramoduleComponentDao componentDao;

	@Autowired
	private IHydramoduleEventDao eventDao;

	@Autowired
	private IHydramoduleFieldDao fieldDao;

	@Autowired
	private IHydramoduleFormDao formDao;

	@Autowired
	private IHydramoduleParticipantDao participantDao;

	@Autowired
	private IHydramoduleServiceDao serviceDao;

	@Autowired
	private IHydramodulePhaseDao phaseDao;

	@Autowired
	private IHydramoduleWorkflowDao workflowDao;

	@Autowired
	private UserService userService;

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setAssetDao(IHydramoduleAssetDao assetDao) {
		this.assetDao = assetDao;
	}

	public void setComponentDao(IHydramoduleComponentDao componentDao) {
		this.componentDao = componentDao;
	}

	public void setEventDao(IHydramoduleEventDao eventDao) {
		this.eventDao = eventDao;
	}

	public void setFieldDao(IHydramoduleFieldDao fieldDao) {
		this.fieldDao = fieldDao;
	}

	public void setFormDao(IHydramoduleFormDao formDao) {
		this.formDao = formDao;
	}

	public void setParticipantDao(IHydramoduleParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public void setPhaseDao(IHydramodulePhaseDao phaseDao) {
		this.phaseDao = phaseDao;
	}

	public void setServiceDao(IHydramoduleServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	public void setWorkflowDao(IHydramoduleWorkflowDao workflowDao) {
		this.workflowDao = workflowDao;
	}

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		// this.userService = userService;
	}

	@Override
	public HydraForm getHydraFormByUuid(String uuid) throws APIException {
		return formDao.getHydraFormByUuid(uuid);
	}

	@Override
	@Transactional
	public HydraForm saveForm(HydraForm item) throws APIException {
		return formDao.saveForm(item);
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

	public HydramoduleComponent saveComponent(HydramoduleComponent component) throws APIException {
		return componentDao.saveComponent(component);
	}

	@Override
	@Transactional
	public HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases item) throws APIException {
		return workflowDao.saveWorkflowPhaseRelation(item);
	}

	@Override
	@Transactional
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
	public Set<HydraForm> getHydraFormsByTag(String tag) throws APIException {
		return formDao.getHydraFormsByTag(tag);
	}

	@Override
	public HydraForm getHydraFormByEncounterName(String encounterName) throws APIException {
		// TODO Auto-generated method stub
		return null;
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
	public HydramodulePhase getPhaseByUUID(String uuid) throws APIException {

		return phaseDao.getPhase(uuid);
	}

	@Override
	public List<HydramodulePhase> getAllPhases() throws APIException {

		return phaseDao.getAllPhases();
	}

	@Override
	public HydramoduleComponent getComponentByUUID(String uuid) throws APIException {

		return componentDao.getComponent(uuid);
	}

	@Override
	public List<HydramoduleComponent> getAllComponents() throws APIException {

		return componentDao.getAllComponents();
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
	public void purgeComponent(HydramoduleComponent component) throws APIException {
		component.setRetired(true);
		component.setRetiredBy(Context.getAuthenticatedUser());
		component.setDateRetired(new Date());
		componentDao.updateComponent(component);
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

		return formDao.saveModuleForm(form);
	}

	@Override
	public List<HydramoduleForm> getAllModuleForm() throws APIException {
		return formDao.getAllModuleForm();
	}

	@Override
	public List<HydramoduleForm> getAllModuleFormsByComponent(String componentUUID) throws APIException {
		return formDao.getAllModuleFormByComponentUUID(componentUUID);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException {
		return formDao.getModuleForm(uuid);
	}

	@Override
	public HydramoduleForm getHydraModuleFormByName(String name) throws APIException {
		return formDao.getModuleFormByName(name);
	}

	@Override
	public HydramodulePhaseComponents savePhaseComponentRelation(HydramodulePhaseComponents item) throws APIException {
		return phaseDao.savePhaseComponentsRelation(item);
	}

	@Override
	public HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm item) throws APIException {
		return componentDao.saveComponentFormRelation(item);
	}

	@Override
	public HydramodulePhaseComponents getPhasesComponentRelationByUUID(String uuid) throws APIException {
		return phaseDao.getPhaseComponentRelation(uuid);
	}

	@Override
	public HydramoduleComponentForm getComponentFormByUUID(String uuid) throws APIException {
		return componentDao.getComponentFormRelation(uuid);
	}

	@Override
	public List<HydramodulePhaseComponents> getAllPhaseComponentsRelations() throws APIException {
		return phaseDao.getAllPhaseComponentRelations();
	}

	@Override
	public List<HydramoduleComponentForm> getAllComponentFormsRelations() throws APIException, CloneNotSupportedException {
		List<HydramoduleComponentForm> componentForms = componentDao.getAllComponentFormRelations();

		SExprHelper exprHelper = SExprHelper.getInstance();

		for (HydramoduleComponentForm cf : componentForms) {
			HydramoduleForm form = cf.getForm();
			List<HydramoduleFormField> formFields = form.getFormFields();
			for (HydramoduleFormField ff : formFields) {
				// HydramoduleField field = dao.getHydramoduleField(ff.getField().getUuid());
				HydramoduleField field = ff.getField().clone(); // This is the targetField of a rule
				String parsedRule = exprHelper.compileComplex(fieldDao, ff);
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
		return formDao.getFormField(uuid);
	}

	@Override
	public void saveFormEncounter(HydramoduleFormEncounter formEncounter) {
		formDao.saveFormEncounter(formEncounter);
	}

	@Transactional
	@Override
	public void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) throws APIException {
		phaseDao.deletePhaseComponent(phaseComponent);

	}

	@Transactional
	@Override
	public void retireComponentForm(HydramoduleComponentForm phaseComponent) throws APIException {
		phaseComponent.setRetired(true);
		phaseComponent.setRetiredBy(Context.getAuthenticatedUser());
		phaseComponent.setDateRetired(new Date());
		// dao.updateComponent(component);
		componentDao.updateComponentForm(phaseComponent);
	}

	@Transactional
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

	// ServiceType
	@Override
	@Transactional
	public HydramoduleServiceType saveServiceType(HydramoduleServiceType form) throws APIException {
		return serviceDao.saveServiceType(form);
	}

	@Override
	public List<HydramoduleServiceType> getAllServiceTypes(boolean retired) throws APIException {
		return serviceDao.getAllServiceTypes(retired);
	}

	@Override
	public HydramoduleServiceType getServiceType(String uuid) throws APIException {
		return serviceDao.getServiceType(uuid);
	}

	// Service
	@Override
	@Transactional
	public HydramoduleService saveService(HydramoduleService service) throws APIException {
		return serviceDao.saveService(service);
	}

	@Override
	public List<HydramoduleService> getAllServices(boolean retired) throws APIException {
		return serviceDao.getAllServices(retired);
	}

	@Override
	public HydramoduleService getService(String uuid) throws APIException {
		return serviceDao.getService(uuid);
	}

	// AssetType
	@Override
	@Transactional
	public HydramoduleAssetType saveAssetType(HydramoduleAssetType service) throws APIException {
		return assetDao.saveAssetType(service);
	}

	@Override
	public List<HydramoduleAssetType> getAllAssetTypes(boolean retired) throws APIException {
		return assetDao.getAllAssetTypes(retired);
	}

	@Override
	public HydramoduleAssetType getAssetType(String uuid) throws APIException {
		return assetDao.getAssetType(uuid);
	}

	// AssetCategory
	@Override
	@Transactional
	public HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory service) throws APIException {
		return assetDao.saveAssetCategory(service);
	}

	@Override
	public List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired) throws APIException {
		return assetDao.getAllAssetCategories(retired);
	}

	@Override
	public HydramoduleAssetCategory getAssetCategory(String uuid) throws APIException {
		return assetDao.getAssetCategory(uuid);
	}

	// Asset
	@Override
	@Transactional
	public HydramoduleAsset saveAsset(HydramoduleAsset service) throws APIException {
		return assetDao.saveAsset(service);
	}

	@Override
	public List<HydramoduleAsset> getAllAssets(boolean retired) throws APIException {
		return assetDao.getAllAssets(retired);
	}

	@Override
	public HydramoduleAsset getAsset(String uuid) throws APIException {
		return assetDao.getAsset(uuid);
	}

	// Participant
	@Override
	@Transactional
	public HydramoduleParticipant saveParticipant(HydramoduleParticipant service) throws APIException {
		return participantDao.saveParticipant(service);
	}

	@Override
	public List<HydramoduleParticipant> getAllParticipants(boolean retired) throws APIException {
		return participantDao.getAllParticipants(retired);
	}

	@Override
	public HydramoduleParticipant getParticipant(String uuid) throws APIException {
		return participantDao.getParticipant(uuid);
	}

	@Override
	public List<HydramoduleParticipant> getParticipantByUserUUID(String userUUID) throws APIException {
		User user = Context.getUserService().getUserByUuid(userUUID);
		List<HydramoduleParticipant> participantsList = new ArrayList();
		participantsList.add(participantDao.getParticipantByUser(user));
		return participantsList;
	}

	// ParticipantSalaryType
	@Override
	@Transactional
	public HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType service)
	        throws APIException {
		return participantDao.saveParticipantSalaryType(service);
	}

	@Override
	public List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired) throws APIException {
		return participantDao.getAllParticipantSalaryTypes(retired);
	}

	@Override
	public HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid) throws APIException {
		return participantDao.getParticipantSalaryType(uuid);
	}

	// Event
	@Override
	@Transactional
	public HydramoduleEvent saveEvent(HydramoduleEvent service) throws APIException {
		return eventDao.saveHydramoduleEvent(service);
	}

	@Override
	public List<HydramoduleEvent> getAllEvents(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEvents(voided);
	}

	@Override
	public HydramoduleEvent getEvent(String uuid) throws APIException {
		return eventDao.getHydramoduleEvent(uuid);
	}

	// EventSchedule
	@Override
	@Transactional
	public HydramoduleEventSchedule saveEventSchedule(HydramoduleEventSchedule service) throws APIException {
		return eventDao.saveHydramoduleEventScedule(service);
	}

	@Override
	public List<HydramoduleEventSchedule> getAllEventSchedules(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventScedules(voided);
	}

	@Override
	public HydramoduleEventSchedule getEventSchedule(String uuid) throws APIException {
		return eventDao.getHydramoduleEventScedule(uuid);
	}

	// EventType
	@Override
	@Transactional
	public HydramoduleEventType saveEventType(HydramoduleEventType service) throws APIException {
		return eventDao.saveHydramoduleEventType(service);
	}

	@Override
	public List<HydramoduleEventType> getAllEventTypes(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventTypes(voided);
	}

	@Override
	public HydramoduleEventType getEventType(String uuid) throws APIException {
		return eventDao.getHydramoduleEventType(uuid);
	}

	// EventService
	@Override
	@Transactional
	public HydramoduleEventService saveEventService(HydramoduleEventService service) throws APIException {
		return eventDao.saveHydramoduleEventService(service);
	}

	@Override
	public List<HydramoduleEventService> getAllEventServices(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventServices(voided);
	}

	@Override
	public HydramoduleEventService getEventService(String uuid) throws APIException {
		return eventDao.getHydramoduleEventService(uuid);
	}

	// EventAsset
	@Override
	@Transactional
	public HydramoduleEventAsset saveEventAsset(HydramoduleEventAsset service) throws APIException {
		return eventDao.saveHydramoduleEventAsset(service);
	}

	@Override
	public List<HydramoduleEventAsset> getAllEventAssets(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventAssets(voided);
	}

	@Override
	public HydramoduleEventAsset getEventAsset(String uuid) throws APIException {
		return eventDao.getHydramoduleEventAsset(uuid);
	}

	// EventParticipant
	@Override
	@Transactional
	public HydramoduleEventParticipants saveEventParticipant(HydramoduleEventParticipants service) throws APIException {
		return eventDao.saveHydramoduleEventParticipant(service);
	}

	@Override
	public List<HydramoduleEventParticipants> getAllEventParticipants(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventParticipants(voided);
	}

	@Override
	public HydramoduleEventParticipants getEventParticipant(String uuid) throws APIException {
		return eventDao.getHydramoduleEventParticipant(uuid);
	}

	// Field
	@Override
	@Transactional
	public HydramoduleField saveField(HydramoduleFieldDTO dto) throws APIException {

		return fieldDao.saveField(dto);
	}

	public List<HydramoduleFieldDTO> getFieldsByName(String name) throws APIException {
		List<HydramoduleField> fields = fieldDao.getAllFieldsByName(name);
		List<HydramoduleFieldDTO> fieldDTOs = new ArrayList();
		for (HydramoduleField f : fields) {
			HydramoduleFieldDTO dto = new HydramoduleFieldDTO();
			List<HydramoduleFieldAnswer> answers = fieldDao.getAllFieldAnswersByID(f);
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
		return fieldDao.saveHydramoduleField(service);
	}

	@Override
	public List<HydramoduleField> getAllHydramoduleFields() throws APIException {
		return fieldDao.getAllHydramoduleFields();
	}

	@Override
	public HydramoduleField getHydramoduleField(String uuid) throws APIException {
		return fieldDao.getHydramoduleField(uuid);
	}

	@Override
	public List<HydramoduleField> getHydramoduleFieldsByName(String queryParam) {
		// TODO Auto-generated method stub
		return fieldDao.getAllFieldsByName(queryParam);
	}

	// HydramoduleFieldAnswer
	@Override
	@Transactional
	public HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer service) throws APIException {
		return fieldDao.saveHydramoduleFieldAnswer(service);
	}

	@Override
	public List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean voided) throws APIException {
		return fieldDao.getAllHydramoduleFieldAnswers(voided);
	}

	@Override
	public HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid) throws APIException {
		return fieldDao.getHydramoduleFieldAnswer(uuid);
	}

	// HydramoduleFieldRule
	@Override
	@Transactional
	public HydramoduleFieldRule saveHydramoduleFieldRule(HydramoduleFieldRule service) throws APIException {
		return fieldDao.saveHydramoduleFieldRule(service);
	}

	@Override
	public List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean voided) throws APIException {
		return fieldDao.getAllHydramoduleFieldRules(voided);
	}

	@Override
	public HydramoduleFieldRule getHydramoduleFieldRule(String uuid) throws APIException {
		return fieldDao.getHydramoduleFieldRule(uuid);
	}

	// HydramoduleRuleToken
	@Override
	@Transactional
	public HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken service) throws APIException {
		return fieldDao.saveHydramoduleRuleToken(service);
	}

	@Override
	public List<HydramoduleRuleToken> getAllHydramoduleRuleTokens() throws APIException {
		return fieldDao.getAllHydramoduleRuleTokens();
	}

	@Override
	public HydramoduleRuleToken getHydramoduleRuleToken(String uuid) throws APIException {
		return fieldDao.getHydramoduleRuleToken(uuid);
	}

	// HydramodulePatientWorkflow
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

	// HydramoduleUserWorkflow
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
	public HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,
	        HydramoduleWorkflow hydramoduleWorkflow) throws APIException {
		HydramoduleComponentForm componentForm = componentDao.getComponentFormByFormAndWorkflow(hydramoduleForm,
		    hydramoduleWorkflow);
		return componentForm;
	}

	@Override
	public HydramodulePatientWorkflow getHydramodulePatientWorkflowByPatient(Integer patientId) throws APIException {
		Patient patient = Context.getPatientService().getPatient(patientId);
		HydramodulePatientWorkflow hydramodulePatientWorkflow = workflowDao.getPatientWorkflowByPatient(patient);
		return hydramodulePatientWorkflow;
	}

	@Override
	public List<HydramodulePhaseComponents> getHydramodulePhaseComponentsByWorkflow(String uuid) throws APIException {

		HydramoduleWorkflow hydramoduleWorkflow = workflowDao.getWorkflow(uuid);
		List<HydramodulePhaseComponents> hydramodulePhaseComponents = phaseDao
		        .getPhaseComponentByWorkflow(hydramoduleWorkflow);

		return hydramodulePhaseComponents;
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

	@Override
	public List<HydramoduleComponentForm> getComponentFormsByPhase(String phaseUUID) throws APIException {
		HydramodulePhase phase = phaseDao.getPhase(phaseUUID);
		List<HydramoduleComponentForm> hydramoduleComponents = componentDao.getComponentFormByPhase(phase);
		return hydramoduleComponents;
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
