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
import org.openmrs.module.hydra.model.event_planner.HydraForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponentForms;
import org.openmrs.module.hydra.model.workflow.HydramoduleForm;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
import org.openmrs.module.hydra.model.workflow.HydramodulePhaseComponents;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflowPhases;
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
	 * Returns a {@link org.openmrs.module.hydra.model.event_planner.HydraForm} by encounterName. It can
	 * be called by any authenticated user. It is fetched in read only transaction.
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
	HydramoduleForm saveModuleForm(HydramoduleForm item) throws APIException;

	@Authorized()
	@Transactional(readOnly = true)
	HydramoduleForm getHydraModuleFormByUuid(String uuid) throws APIException;

	/**
	 * Returns a set of {@link org.openmrs.module.hydra.model.event_planner.HydraForm} by tag. It can be
	 * called by any authenticated user. It is fetched in read only transaction.
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

	public HydramoduleComponentForms saveComponentFromRelation(HydramoduleComponentForms item) throws APIException;

	HydramoduleWorkflow getWorkflowByUUID(String uuid) throws APIException;

	HydramoduleWorkflowPhases getWorkflowPhasesRelationByUUID(String uuid) throws APIException;

	HydramodulePhaseComponents getPhasesComponentRelationByUUID(String uuid) throws APIException;

	HydramoduleComponentForms getComponentFormRelationByUUID(String uuid) throws APIException;

	List<HydramoduleWorkflow> getAllWorkflows() throws APIException;

	List<HydramoduleWorkflowPhases> getAllWorkflowPhaseRelations() throws APIException;

	List<HydramodulePhaseComponents> getAllPhaseComponentsRelations() throws APIException;

	List<HydramoduleComponentForms> getAllComponentFormRelations() throws APIException;

	void purgeComponent(HydramoduleComponent component) throws APIException;

	List<HydramoduleForm> getAllModuleForm() throws APIException;

	void deleteComponentForm(HydramoduleComponentForms componentForm) throws APIException;

	void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) throws APIException;

	void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases) throws APIException;

	void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException;

	void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException;
}
