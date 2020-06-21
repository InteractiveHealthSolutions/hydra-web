/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api;

import java.util.List;
import java.util.Set;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hydra.HydraConfig;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
@Transactional
public interface HydraService extends OpenmrsService {

	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read only
	 * transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	HydraForm getHydraFormByUuid(String uuid) throws APIException;

	/**
	 * Returns a {@link org.openmrs.module.hydra.model.HydraForm} by encounterName. It can be called by
	 * any authenticated user. It is fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	HydraForm getHydraFormByEncounterName(String encunterName) throws APIException;

	/**
	 * Saves a form.
	 * 
	 * @param item
	 * @return
	 * @throws APIException
	 */
	@Authorized(HydraConfig.MODULE_PRIVILEGE)
	@Transactional
	HydraForm saveForm(HydraForm item) throws APIException;

	@Authorized(HydraConfig.MODULE_PRIVILEGE)
	@Transactional
	HydramoduleForm saveHydramoduleForm(HydramoduleForm item) throws APIException;

	@Authorized()
	@Transactional(readOnly = true)
	HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException;

	/**
	 * Returns a set of {@link org.openmrs.module.hydra.model.HydraForm} by tag. It can be called by any
	 * authenticated user. It is fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	Set<HydraForm> getHydraFormsByTag(String tag) throws APIException;

	HydramodulePhase getPhaseByUUID(String uuid) throws APIException;

	List<HydramodulePhase> getAllPhases() throws APIException;

	HydramoduleComponent getComponentByUUID(String uuid) throws APIException;

	List<HydramoduleComponent> getAllComponents() throws APIException;

	HydramodulePhase savePhase(HydramodulePhase item) throws APIException;

	HydramoduleComponent saveComponent(HydramoduleComponent component) throws APIException;

	HydramoduleWorkflow saveWorkflow(HydramoduleWorkflow item) throws APIException;

	public HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases item) throws APIException;

	public HydramodulePhaseComponents savePhaseComponentRelation(HydramodulePhaseComponents item) throws APIException;

	HydramoduleWorkflow getWorkflowByUUID(String uuid) throws APIException;

	HydramoduleWorkflowPhases getWorkflowPhasesRelationByUUID(String uuid) throws APIException;

	HydramodulePhaseComponents getPhasesComponentRelationByUUID(String uuid) throws APIException;

	List<HydramoduleWorkflow> getAllWorkflows() throws APIException;

	List<HydramoduleWorkflowPhases> getAllWorkflowPhaseRelations() throws APIException;

	List<HydramodulePhaseComponents> getAllPhaseComponentsRelations() throws APIException;

	void purgeComponent(HydramoduleComponent component) throws APIException;

	List<HydramoduleForm> getAllModuleForm() throws APIException;

	void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) throws APIException;

	void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases) throws APIException;

	void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException;

	void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException;

	HydramoduleService saveService(HydramoduleService service) throws APIException;

	List<HydramoduleService> getAllServices(boolean retired) throws APIException;

	HydramoduleService getService(String uuid) throws APIException;

	HydramoduleServiceType saveServiceType(HydramoduleServiceType form) throws APIException;

	List<HydramoduleServiceType> getAllServiceTypes(boolean retired) throws APIException;

	HydramoduleServiceType getServiceType(String uuid) throws APIException;

	HydramoduleAssetType saveAssetType(HydramoduleAssetType service) throws APIException;

	List<HydramoduleAssetType> getAllAssetTypes(boolean retired) throws APIException;

	HydramoduleAssetType getAssetType(String uuid) throws APIException;

	HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory service) throws APIException;

	List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired) throws APIException;

	HydramoduleAssetCategory getAssetCategory(String uuid) throws APIException;

	HydramoduleAsset saveAsset(HydramoduleAsset service) throws APIException;

	List<HydramoduleAsset> getAllAssets(boolean retired) throws APIException;

	HydramoduleAsset getAsset(String uuid) throws APIException;

	HydramoduleParticipant saveParticipant(HydramoduleParticipant service) throws APIException;

	List<HydramoduleParticipant> getAllParticipants(boolean retired) throws APIException;

	HydramoduleParticipant getParticipant(String uuid) throws APIException;

	HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType service) throws APIException;

	List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired) throws APIException;

	HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid) throws APIException;

	HydramoduleEvent saveEvent(HydramoduleEvent service) throws APIException;

	List<HydramoduleEvent> getAllEvents(boolean voided) throws APIException;

	HydramoduleEvent getEvent(String uuid) throws APIException;

	HydramoduleEventSchedule saveEventSchedule(HydramoduleEventSchedule service) throws APIException;

	List<HydramoduleEventSchedule> getAllEventSchedules(boolean voided) throws APIException;

	HydramoduleEventSchedule getEventSchedule(String uuid) throws APIException;

	HydramoduleEventType saveEventType(HydramoduleEventType service) throws APIException;

	List<HydramoduleEventType> getAllEventTypes(boolean voided) throws APIException;

	HydramoduleEventType getEventType(String uuid) throws APIException;

	HydramoduleEventService saveEventService(HydramoduleEventService service) throws APIException;

	List<HydramoduleEventService> getAllEventServices(boolean voided) throws APIException;

	HydramoduleEventService getEventService(String uuid) throws APIException;

	HydramoduleEventAsset saveEventAsset(HydramoduleEventAsset service) throws APIException;

	List<HydramoduleEventAsset> getAllEventAssets(boolean voided) throws APIException;

	HydramoduleEventAsset getEventAsset(String uuid) throws APIException;

	HydramoduleEventParticipants saveEventParticipant(HydramoduleEventParticipants service) throws APIException;

	List<HydramoduleEventParticipants> getAllEventParticipants(boolean voided) throws APIException;

	HydramoduleEventParticipants getEventParticipant(String uuid) throws APIException;

	List<HydramoduleForm> getAllModuleFormsByComponent(String componentUUID) throws APIException;

	HydramoduleField saveField(HydramoduleFieldDTO dto) throws APIException;

	List<HydramoduleFieldDTO> getFieldsByName(String name) throws APIException;

	HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm item) throws APIException;

	HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,HydramoduleWorkflow hydramoduleWorkflow) throws APIException;

	HydramoduleComponentForm getComponentFormByUUID(String uuid) throws APIException;

	List<HydramoduleComponentForm> getAllComponentFormsRelations() throws APIException, CloneNotSupportedException;

	void retireComponentForm(HydramoduleComponentForm phaseComponent) throws APIException;

	HydramoduleField saveHydramoduleField(HydramoduleField service) throws APIException;

	List<HydramoduleField> getAllHydramoduleFields() throws APIException;

	HydramoduleField getHydramoduleField(String uuid) throws APIException;

	HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid) throws APIException;

	List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean voided) throws APIException;

	HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer service) throws APIException;

	List<HydramoduleField> getHydramoduleFieldsByName(String queryParam);

	HydramoduleFieldRule saveHydramoduleFieldRule(HydramoduleFieldRule service) throws APIException;

	List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean voided) throws APIException;

	HydramoduleFieldRule getHydramoduleFieldRule(String uuid) throws APIException;

	HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken service) throws APIException;

	List<HydramoduleRuleToken> getAllHydramoduleRuleTokens() throws APIException;

	HydramoduleRuleToken getHydramoduleRuleToken(String uuid) throws APIException;

	void saveFormEncounter(HydramoduleFormEncounter formEncounter);

	List<HydramoduleParticipant> getParticipantByUserUUID(String userUUID) throws APIException;

	HydramodulePatientWorkflow saveHydramodulePatientWorkflow(HydramodulePatientWorkflow service) throws APIException;
	
	HydramodulePatientWorkflow getHydramodulePatientWorkflowByPatient(Integer patientId) throws APIException;

	List<HydramodulePatientWorkflow> getAllHydramodulePatientWorkflows() throws APIException;

	HydramodulePatientWorkflow getHydramodulePatientWorkflow(String uuid) throws APIException;

	HydramoduleFormField getFormFieldByUUID(String uuid) throws APIException;

	HydramoduleUserWorkflow saveHydramoduleUserWorkflow(HydramoduleUserWorkflow hydramoduleUserWorkflow) throws APIException;

	List<HydramoduleUserWorkflow> getAllHydramoduleUserWorkflow() throws APIException;

	List<HydramoduleUserWorkflow> getUserWorkflowByUser(String uuid) throws APIException;

	HydramoduleUserWorkflow getHydramoduleUserWorkflow(String uuid) throws APIException;

	HydramoduleWorkflow getWorkflowByName(String name) throws APIException;

	HydramoduleForm getHydraModuleFormByName(String name) throws APIException;

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

}
