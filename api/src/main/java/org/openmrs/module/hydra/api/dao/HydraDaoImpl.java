/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
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
import org.springframework.stereotype.Repository;

@Repository("hydra.HydraDao")
@Transactional
public class HydraDaoImpl {

	@Autowired
	DbSessionFactory sessionFactory;

	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}

	public HydramoduleWorkflow getWorkflow(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflow.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleWorkflow) criteria.uniqueResult();
	}

	public HydramoduleWorkflowPhases getWorkflowPhaseRelation(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflowPhases.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleWorkflowPhases) criteria.uniqueResult();
	}

	public HydramodulePhaseComponents getPhaseComponentRelation(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhaseComponents.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramodulePhaseComponents) criteria.uniqueResult();
	}

	public HydramoduleComponentForms getComponentFormRelation(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForms.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleComponentForms) criteria.uniqueResult();
	}

	public List<HydramoduleWorkflow> getAllWorkflows() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflow.class);
		criteria.addOrder(Order.asc("workflowId"));
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	public HydramodulePhase getPhase(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhase.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramodulePhase) criteria.uniqueResult();
	}

	public List<HydramodulePhase> getAllPhases() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhase.class);
		criteria.addOrder(Order.asc("phaseId"));
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	public List<HydramoduleWorkflowPhases> getAllPhasesWorkFlowPhaseRelations() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflowPhases.class);
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	public List<HydramodulePhaseComponents> getAllPhaseComponentRelations() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhaseComponents.class);
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	public List<HydramoduleComponentForms> getAllComponentFormRelations() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForms.class);
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	public HydramoduleComponent getComponent(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponent.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleComponent) criteria.uniqueResult();
	}

	public List<HydramoduleComponent> getAllComponents() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponent.class);
		criteria.addOrder(Order.asc("componentId"));
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	public HydraForm getHydraFormByUuid(String uuid) {
		return (HydraForm) getSession().createCriteria(HydraForm.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	public HydraForm getHydraModuleFormByTag(String tag) {
		DbSession session = getSession();
		HydraForm form = (HydraForm) session.createCriteria(HydraForm.class).add(Restrictions.eq("uuid", tag))
		        .uniqueResult();
		return form;
	}

	public java.util.Set<HydraForm> getHydraFormsByTag(String tag) throws APIException {
		return null;
	}

	public HydraForm saveForm(HydraForm form) {
		getSession().saveOrUpdate(form);
		return form;
	}

	public HydramoduleForm saveModuleForm(HydramoduleForm form) {
		getSession().saveOrUpdate(form);
		return form;
	}

	public List<HydramoduleForm> getAllModuleForm() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleForm.class);
		criteria.addOrder(Order.asc("hydramoduleFormId"));
		return criteria.list();
	}

	public HydramoduleWorkflowPhases saveWorkflowPhase(HydramoduleWorkflowPhases phases) {
		getSession().saveOrUpdate(phases);
		return phases;
	}

	public HydramoduleWorkflow saveWorkflow(HydramoduleWorkflow workflow) {
		getSession().saveOrUpdate(workflow);
		return workflow;
	}

	public HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases workflow) {
		getSession().saveOrUpdate(workflow);
		return workflow;
	}

	public HydramoduleComponentForms saveComponentFormRelation(HydramoduleComponentForms componentForm) {
		getSession().saveOrUpdate(componentForm);
		return componentForm;
	}

	public HydramodulePhaseComponents savePhaseComponentsRelation(HydramodulePhaseComponents phaseComponent) {
		getSession().saveOrUpdate(phaseComponent);
		return phaseComponent;
	}

	public HydramodulePhase savePhase(HydramodulePhase phase) {
		getSession().saveOrUpdate(phase);
		getSession().flush();
		return phase;
	}

	public HydramoduleComponent saveComponent(HydramoduleComponent component) {
		getSession().saveOrUpdate(component);
		getSession().flush();
		return component;
	}

	public HydramoduleComponent updateComponent(HydramoduleComponent component) {
		getSession().update(component);
		getSession().flush();
		return component;
	}

	public HydramoduleForm getModuleForm(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleForm.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleForm) criteria.uniqueResult();
	}

	// delete map classes

	public void deleteComponentForm(HydramoduleComponentForms componentForm) {
		sessionFactory.getCurrentSession().delete(componentForm);
	}

	public void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) {
		sessionFactory.getCurrentSession().delete(phaseComponent);
	}

	public void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases) {
		sessionFactory.getCurrentSession().delete(workflowphases);
	}

	public void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	public void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	public List<HydramoduleWorkflowPhases> getWorkflowPhase(HydramoduleWorkflow workflow) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflowPhases.class);
		criteria.add(Restrictions.eq("workflowId", workflow.getWorkflowId()));

		return criteria.list();
	}

	public List<HydramodulePhaseComponents> getPhaseComponent(HydramoduleWorkflow workflow) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhaseComponents.class);
		criteria.add(Restrictions.eq("hydramoduleWorkflow", workflow.getWorkflowId()));

		return criteria.list();
	}

	// Service Type
	public HydramoduleServiceType saveServiceType(HydramoduleServiceType serviceType) {
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleServiceType getServiceType(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleServiceType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleServiceType) criteria.uniqueResult();
	}

	public List<HydramoduleServiceType> getAllServiceTypes(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleServiceType.class);
		criteria.addOrder(Order.asc("serviceTypeId"));
		criteria.add(Restrictions.eq("retired", retired));

		return criteria.list();
	}

	// Service
	public HydramoduleService saveService(HydramoduleService service) {
		getSession().saveOrUpdate(service);
		getSession().flush();
		return service;
	}

	public HydramoduleService getService(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleService.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleService) criteria.uniqueResult();
	}

	public List<HydramoduleService> getAllServices(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleService.class);
		criteria.addOrder(Order.asc("serviceId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// AssetType
	public HydramoduleAssetType saveAssetType(HydramoduleAssetType serviceType) {
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleAssetType getAssetType(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleAssetType) criteria.uniqueResult();
	}

	public List<HydramoduleAssetType> getAllAssetTypes(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetType.class);
		criteria.addOrder(Order.asc("assetTypeId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// AssetCategory
	public HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory serviceType) {
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleAssetCategory getAssetCategory(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetCategory.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleAssetCategory) criteria.uniqueResult();
	}

	public List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetCategory.class);
		criteria.addOrder(Order.asc("assetCategoryId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// Asset
	public HydramoduleAsset saveAsset(HydramoduleAsset serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleAsset getAsset(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAsset.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleAsset) criteria.uniqueResult();
	}

	public List<HydramoduleAsset> getAllAssets(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAsset.class);
		criteria.addOrder(Order.asc("assetId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// Participant
	public HydramoduleParticipant saveParticipant(HydramoduleParticipant serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleParticipant getParticipant(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipant.class);
		criteria.add(Restrictions.eq("participantId", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleParticipant) criteria.uniqueResult();
	}

	public List<HydramoduleParticipant> getAllParticipants(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipant.class);
		criteria.addOrder(Order.asc("participantId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// ParticipantSalaryType
	public HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType serviceType) {
		System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipantSalaryType.class);
		criteria.add(Restrictions.eq("salaryTypeId", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleParticipantSalaryType) criteria.uniqueResult();
	}

	public List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipantSalaryType.class);
		criteria.addOrder(Order.asc("salaryTypeId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}
}
