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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.FieldAnswer;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hydra.model.event_planner.HydraForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleAsset;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetType;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleEncounterMapper;
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
import org.openmrs.module.hydra.model.workflow.HydramoduleFormFieldGroup;
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

	public HydramoduleComponentForm getComponentFormRelation(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleComponentForm) criteria.uniqueResult();
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

	public List<HydramoduleComponentForm> getAllComponentFormRelations() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.add(Restrictions.eq("retired", false));
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	public List<HydramoduleComponentForm> getAllComponentFormRelations(HydramoduleForm form) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.addOrder(Order.asc("displayOrder"));
		criteria.add(Restrictions.eq("form", form));
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

	// TODO could be done better way, right now deleting all the existing then
	// adding new in case of edit
	public HydramoduleForm saveModuleForm(HydramoduleForm form) {
		List<HydramoduleFormField> fields = form.getFormFields();

		if (form.getHydramoduleFormId() != null) {
			List<HydramoduleFormField> existingFormFields = getFormFields(form.getHydramoduleFormId());
			List<Integer> toKeepIds = new ArrayList<Integer>();
			for (HydramoduleFormField existingField : existingFormFields) {
				for (HydramoduleFormField newField : fields) {
					if (existingField.getField().getFieldId() == newField.getField().getFieldId()) {
						newField.setFormFieldId(existingField.getFormFieldId());
						newField.setUuid(existingField.getUuid());
						toKeepIds.add(existingField.getFormFieldId());
						break;
					}
				}
			}

			if (form.getHydramoduleFormId() != null) {
				List<HydramoduleFormField> existingToDelete = new ArrayList<HydramoduleFormField>();
				for (HydramoduleFormField existingField : existingFormFields) {
					if (!toKeepIds.contains(existingField.getFormFieldId())) {
						existingToDelete.add(existingField);
					}
				}
				deleteFormFields(existingToDelete);
			}
			// List<HydramoduleFormField> fields = form.getFormFields();

		}

		getSession().clear();
		getSession().saveOrUpdate(form);
		if (fields != null) {
			for (HydramoduleFormField field : fields) {
				field.setForm(form);
				saveFormField(field);
			}
		}

		getSession().saveOrUpdate(form);
		getSession().flush();
		return form;
	}

	public HydramoduleFormField saveFormField(HydramoduleFormField field) {
		getSession().saveOrUpdate(field);

		List<HydramoduleFormField> children = field.getChildren();
		if (children != null) {
			for (HydramoduleFormField child : children) {
				child.setParent(field);
				saveFormField(child);
			}
		}

		getSession().flush();
		return field;
	}

	public List<HydramoduleFormField> getFormFields(HydramoduleForm form) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormField.class);
		criteria.add(Restrictions.eq("form", form));

		return criteria.list();
	}

	public List<HydramoduleFormField> getFormFields(Integer formId) {
		DbSession session = sessionFactory.getCurrentSession();
		return session.createQuery("from HydramoduleFormField f where f.form.hydramoduleFormId=" + formId).list();
	}

	public HydramoduleFormField getFormField(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormField.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleFormField) criteria.uniqueResult();
	}

	public List<HydramoduleFieldAnswer> getFieldAnswers(HydramoduleField field) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldAnswer.class);
		criteria.add(Restrictions.eq("field", field));
		List<HydramoduleFieldAnswer> results = criteria.list();
		// session.clear();
		return results;
	}

	// TODO use criteria
	public void deleteFormFields(HydramoduleForm form) {
		DbSession session = sessionFactory.getCurrentSession();
		List<HydramoduleFormField> formFields = getFormFields(form);
		deleteFormFields(formFields);
		session.flush();
	}

	// TODO use criteria
	public void deleteFormFields(List<HydramoduleFormField> formFields) {
		DbSession session = sessionFactory.getCurrentSession();
		for (HydramoduleFormField ff : formFields) {
			System.out.println("Deleted!!!");
			deleteFieldRules(ff.getRules());
			session.delete(ff);
		}
		session.flush();
	}

	public void deleteFieldAnswers(int fieldId) {
		DbSession session = sessionFactory.getCurrentSession();
		session.createQuery("delete from HydramoduleFieldAnswer f where f.field.fieldId=" + fieldId).executeUpdate();
	}

	// TODO use criteria
	public void deleteFieldAnswers(HydramoduleField field) {
		DbSession session = sessionFactory.getCurrentSession();
		List<HydramoduleFieldAnswer> fieldAnswers = getFieldAnswers(field);
		for (HydramoduleFieldAnswer ff : fieldAnswers) {
			System.out.println("Deleted!!!");
			session.delete(ff);
		}
		session.flush();
	}

	public List<HydramoduleForm> getAllModuleForm() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleForm.class);
		criteria.addOrder(Order.asc("hydramoduleFormId"));
		return criteria.list();
	}

	public List<HydramoduleForm> getAllModuleFormByComponentUUID(String componentUUID) {
		HydramoduleComponent component = getComponent(componentUUID);
		if (component != null) {
			DbSession session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(HydramoduleForm.class);
			criteria.add(Restrictions.eq("component", component));
			criteria.addOrder(Order.asc("hydramoduleFormId"));
			return criteria.list();
		} else {
			return new ArrayList<HydramoduleForm>();
		}
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

	public HydramodulePhaseComponents savePhaseComponentsRelation(HydramodulePhaseComponents phaseComponent) {
		getSession().saveOrUpdate(phaseComponent);
		return phaseComponent;
	}

	public HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm componentForm) {
		getSession().saveOrUpdate(componentForm);
		return componentForm;
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

	// componentForm
	public HydramoduleFormEncounter saveFormEncounter(HydramoduleFormEncounter formEncounter) {
		getSession().saveOrUpdate(formEncounter);
		getSession().flush();
		return formEncounter;
	}

	// delete map classes
	public void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) {
		sessionFactory.getCurrentSession().delete(phaseComponent);
	}

	public HydramoduleComponentForm updateComponentForm(HydramoduleComponentForm componentForm) {
		getSession().update(componentForm);
		getSession().flush();
		return componentForm;
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
		HydramoduleServiceType serviceType = service.getServiceType();
		if (serviceType != null) {
			serviceType = getServiceType(serviceType.getUuid());
			service.setServiceType(serviceType);
		}

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
		HydramoduleAssetCategory assetCategory = serviceType.getAssetCategory();
		if (assetCategory != null) {
			assetCategory = getAssetCategory(assetCategory.getUuid());
			serviceType.setAssetCategory(assetCategory);
		}
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
		HydramoduleAssetType assetType = serviceType.getAssetType();
		if (assetType != null) {
			assetType = getAssetType(assetType.getUuid());
			serviceType.setAssetType(assetType);
		}

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
	public HydramoduleParticipant getParticipantByUser(org.openmrs.User user) {
		if (user != null) {
			DbSession session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(HydramoduleParticipant.class);
			criteria.add(Restrictions.eq("user", user));
			// criteria.
			List<HydramoduleParticipant> fields = criteria.list();
			return fields.get(0);
		}

		return null;
	}

	public HydramoduleParticipant saveParticipant(HydramoduleParticipant serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleParticipant getParticipant(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipant.class);
		criteria.add(Restrictions.eq("uuid", uuid));
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
		criteria.add(Restrictions.eq("uuid", uuid));
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

	// Event
	public HydramoduleEvent saveHydramoduleEvent(HydramoduleEvent event) {
		Integer eventId = event.getEventId();

		HydramoduleEventSchedule schedule = event.getSchedule();
		schedule = saveHydramoduleEventScedule(schedule);
		event.setSchedule(schedule);
		getSession().saveOrUpdate(event);
		getSession().flush();

		// TODO #BadPractice, code below block should be in service layer not
		// there in
		// data
		// access layer!
		/* if (eventId == null) */ {
			// saving EventAssets
			List<HydramoduleEventAsset> assets = event.getEventAssets();
			if (assets != null) {
				if (assets.size() > 0) {
					for (HydramoduleEventAsset asset : assets) {
						asset.setEvent(event);
						/* persistantAssets.add */saveHydramoduleEventAsset(asset);
					}
				}
			}

			// saving EventServices
			List<HydramoduleEventService> services = event.getEventServices();
			if (services != null) {
				if (services.size() > 0) {
					for (HydramoduleEventService service : services) {
						service.setEvent(event);
						saveHydramoduleEventService(service);
					}
				}
			}

			// saving EventParticipants
			List<HydramoduleEventParticipants> participants = event.getEventParticipants();
			if (participants != null) {
				if (participants.size() > 0) {
					for (HydramoduleEventParticipants eventParticipants : participants) {
						eventParticipants.setEvent(event);
						/* persistantParticipants.add */saveHydramoduleEventParticipant(eventParticipants);
					}
				}
			}
		}
		return event;
	}

	public HydramoduleEvent getHydramoduleEvent(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEvent.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEvent) criteria.uniqueResult();
	}

	public HydramoduleEvent getHydramoduleEvent(Integer id) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEvent.class);
		criteria.add(Restrictions.eq("eventId", id));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEvent) criteria.uniqueResult();
	}

	public List<HydramoduleEvent> getAllHydramoduleEvents(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEvent.class);
		criteria.addOrder(Order.asc("eventId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventScedule
	public HydramoduleEventSchedule saveHydramoduleEventScedule(HydramoduleEventSchedule serviceType) {
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleEventSchedule getHydramoduleEventScedule(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventSchedule.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventSchedule) criteria.uniqueResult();
	}

	public List<HydramoduleEventSchedule> getAllHydramoduleEventScedules(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventSchedule.class);
		criteria.addOrder(Order.asc("scheduleId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventType
	public HydramoduleEventType saveHydramoduleEventType(HydramoduleEventType serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleEventType getHydramoduleEventType(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleEventType) criteria.uniqueResult();
	}

	public List<HydramoduleEventType> getAllHydramoduleEventTypes(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventType.class);
		criteria.addOrder(Order.asc("eventTypeId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// EventAsset
	public HydramoduleEventAsset saveHydramoduleEventAsset(HydramoduleEventAsset eventAsset) {
		/*
		 * HydramoduleAsset asset = eventAsset.getAsset(); asset =
		 * getAsset(asset.getUuid()); eventAsset.setAsset(asset);
		 * 
		 * HydramoduleEvent event = eventAsset.getEvent(); event =
		 * getHydramoduleEvent(event.getUuid()); eventAsset.setEvent(event);
		 */

		getSession().saveOrUpdate(eventAsset);
		getSession().flush();
		return eventAsset;
	}

	public HydramoduleEventAsset getHydramoduleEventAsset(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventAsset.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventAsset) criteria.uniqueResult();
	}

	public List<HydramoduleEventAsset> getAllHydramoduleEventAssets(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventAsset.class);
		criteria.addOrder(Order.asc("eventAssetId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventService
	public HydramoduleEventService saveHydramoduleEventService(HydramoduleEventService serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleEventService getHydramoduleEventService(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventService.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventService) criteria.uniqueResult();
	}

	public List<HydramoduleEventService> getAllHydramoduleEventServices(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventService.class);
		criteria.addOrder(Order.asc("eventServiceId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventParticipant
	public HydramoduleEventParticipants saveHydramoduleEventParticipant(HydramoduleEventParticipants eventParticipants) {
		// System.out.println(serviceType.getUuid());
		if (eventParticipants.getParticipant() == null)
			return null;
		getSession().saveOrUpdate(eventParticipants);

		getSession().flush();
		return eventParticipants;
	}

	public HydramoduleEventParticipants getHydramoduleEventParticipant(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventParticipants.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventParticipants) criteria.uniqueResult();
	}

	public List<HydramoduleEventParticipants> getAllHydramoduleEventParticipants(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventParticipants.class);
		criteria.addOrder(Order.asc("eventParticipantId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventParticipant
	public HydramoduleFieldAnswer saveHydramoduleDTOFieldAnswer(HydramoduleFieldAnswer fieldAnswer) {
		getSession().saveOrUpdate(fieldAnswer);
		getSession().flush();
		return fieldAnswer;
	}

	// Fields
	public HydramoduleField saveField(HydramoduleFieldDTO fieldDTO) {
		// System.out.println(serviceType.getUuid());
		// Field fieldReceived =
		// Context.getFormService().saveField(serviceType.getField());
		HydramoduleField fieldReceived = fieldDTO.getField();
		System.out.println("Saving field ");
		getSession().saveOrUpdate(fieldReceived);
		System.out.println("Saved field " + fieldReceived.toString());
		getSession().flush();
		for (HydramoduleFieldAnswer a : fieldDTO.getAnswers()) {
			a.setField(fieldReceived);
			saveHydramoduleDTOFieldAnswer(a);
		}
		return fieldReceived;
	}

	public List<HydramoduleFieldAnswer> getAllFieldAnswersByID(HydramoduleField fieldId) {
		// HydramoduleComponent component = getComponent(componentUUID);
		if (fieldId != null) {
			DbSession session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(FieldAnswer.class);
			criteria.add(Restrictions.eq("field", fieldId)).setMaxResults(25);
			// criteria.
			List<HydramoduleFieldAnswer> fields = criteria.list();
			// System.out.println("Naaaaaaaaaaaaaaaaaaame " + name +
			// fields.size());
			// criteria.addOrder(Order.asc("hydramoduleFormId"));
			return fields;

		} else {
			return new ArrayList<HydramoduleFieldAnswer>();
		}
	}

	public List<HydramoduleField> getAllFieldsByName(String name) {
		// HydramoduleComponent component = getComponent(componentUUID);
		if (name != null) {
			DbSession session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(HydramoduleField.class);
			criteria.add(Restrictions.like("name", "%" + name + "%")).setMaxResults(25);
			// criteria.
			List<HydramoduleField> fields = criteria.list();
			// criteria.addOrder(Order.asc("hydramoduleFormId"));
			return fields;

		} else {
			return new ArrayList<HydramoduleField>();
		}
	}

	// HydramoduleField
	public HydramoduleField saveHydramoduleField(HydramoduleField field) {
		// System.out.println(serviceType.getUuid());
		ConceptService conceptService = Context.getConceptService();

		/*
		 * Concept fieldConcept = field.getConcept(); if (fieldConcept.getId() == null)
		 * { fieldConcept = conceptService.saveConcept(fieldConcept); }
		 * field.setConcept(fieldConcept);
		 */
		// fieldConcept.getNames(); // to avoid lazyload issue
		// getSession().clear();
		if (field.getFieldId() != null) {
			deleteFieldAnswers(field.getFieldId());
		}
		getSession().saveOrUpdate(field);
		getSession().flush();

		Set<HydramoduleFieldAnswer> answers = field.getAnswers();
		for (HydramoduleFieldAnswer answer : answers) {
			answer.setField(field);
			saveHydramoduleFieldAnswer(answer);

			// adding in openmrs ConceptAnswer

			try {
				Concept answerConcept = answer.getConcept();
				Concept questionConcept = field.getConcept();
				Concept persistedConcept = conceptService.getConcept(questionConcept.getId());
				Collection<ConceptAnswer> conceptAnswers = persistedConcept.getAnswers();
				boolean answerFound = false;
				for (ConceptAnswer conceptAnswer : conceptAnswers) {
					if (conceptAnswer.getConcept().equals(answerConcept)) {
						answerFound = true;
						continue;
					}
				}
				if (!answerFound) {
					if (!persistedConcept.getAnswers().contains(answer.getConcept())) {
						ConceptAnswer conceptAnswer = new ConceptAnswer();
						// conceptAnswer.setConcept(questionConcept);
						conceptAnswer.setAnswerConcept(answer.getConcept());
						persistedConcept.addAnswer(conceptAnswer);
						conceptService.saveConcept(persistedConcept);
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return field;
	}

	public HydramoduleField getHydramoduleField(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleField.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleField) criteria.uniqueResult();
	}

	public HydramoduleFormField getHydramoduleFormField(String formUUID, String fieldUUID) {
		HydramoduleForm form = getModuleForm(formUUID);
		HydramoduleField field = getHydramoduleField(fieldUUID);
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormField.class);
		criteria.add(Restrictions.eq("form", form));
		criteria.add(Restrictions.eq("field", field));

		System.out.println("Dataatatatatatatatatatat " + formUUID + " " + fieldUUID);
		return (HydramoduleFormField) criteria.uniqueResult();
	}

	public List<HydramoduleField> getAllHydramoduleFields() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleField.class);
		criteria.addOrder(Order.asc("fieldId"));
		return criteria.list();
	}

	/*
	 * public List<HydramoduleField> getHydramoduleFieldsByName(String name) { if
	 * (name != null) { DbSession session = sessionFactory.getCurrentSession();
	 * Criteria criteria = session.createCriteria(HydramoduleField.class);
	 * criteria.add(Restrictions.like("name", "%" + name + "%")); return
	 * criteria.list(); } else { return new ArrayList<HydramoduleField>(); } }
	 */

	// HydramoduleFieldAnswer
	public HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldAnswer.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleFieldAnswer) criteria.uniqueResult();
	}

	public List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldAnswer.class);
		criteria.addOrder(Order.asc("fieldAnswerId"));
		return criteria.list();
	}

	// HydramoduleFieldRule

	public void deleteFieldRules(List<HydramoduleFieldRule> rules) {
		DbSession session = sessionFactory.getCurrentSession();
		for (HydramoduleFieldRule ff : rules) {
			deleteRuleTokensByFieldRuleId(ff.getRuleId());
			session.delete(ff);
		}
		session.flush();
	}

	public HydramoduleFieldRule saveHydramoduleFieldRule(HydramoduleFieldRule fieldRule) {

		getSession().saveOrUpdate(fieldRule);
		getSession().flush();
		if (fieldRule.getTokens() != null) {
			for (HydramoduleRuleToken token : fieldRule.getTokens()) {
				token.setRule(fieldRule);
				saveHydramoduleRuleToken(token);
			}
		}

		return fieldRule;
	}

	public HydramoduleFieldRule getHydramoduleFieldRule(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldRule.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleFieldRule) criteria.uniqueResult();
	}

	/**
	 * Does same thing the wrong way.
	 * 
	 * @deprecated use {@link #getHydramoduleFieldRuleByTargetFormField()} instead.
	 */
	@Deprecated
	public List<HydramoduleFieldRule> getHydramoduleFieldRuleByTargetField(HydramoduleField field) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldRule.class);
		criteria.add(Restrictions.eq("targetQuestion", field));
		return (List<HydramoduleFieldRule>) criteria.list();
	}

	public List<HydramoduleFieldRule> getHydramoduleFieldRuleByTargetFormField(HydramoduleFormField field) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldRule.class);
		criteria.add(Restrictions.eq("targetFormField", field));
		return (List<HydramoduleFieldRule>) criteria.list();
	}

	public List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldRule.class);
		criteria.add(Restrictions.eq("retired", retired));

		return criteria.list();
	}

	// HydramoduleRuleToken
	public void deleteRuleTokensByFieldRuleId(Integer fieldRuleId) {
		DbSession session = sessionFactory.getCurrentSession();
		session.createQuery("delete from HydramoduleRuleToken f where f.rule.ruleId =" + fieldRuleId).executeUpdate();
	}

	public HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	public HydramoduleRuleToken getHydramoduleRuleToken(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleRuleToken.class);
		return (HydramoduleRuleToken) criteria.uniqueResult();
	}

	public List<HydramoduleRuleToken> getAllHydramoduleRuleTokens() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleRuleToken.class);
		criteria.addOrder(Order.asc("tokenId"));
		return criteria.list();
	}

	// HydramodulePatientWorkflow
	public HydramodulePatientWorkflow saveHydramodulePatientWorkflow(HydramodulePatientWorkflow patientWorkflow) {
		getSession().saveOrUpdate(patientWorkflow);
		getSession().flush();
		return patientWorkflow;
	}

	public HydramodulePatientWorkflow getHydramodulePatientWorkflow(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePatientWorkflow.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramodulePatientWorkflow) criteria.uniqueResult();
	}

	public List<HydramodulePatientWorkflow> getAllHydramodulePatientWorkflows() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePatientWorkflow.class);
		criteria.addOrder(Order.asc("patientWorkflowId"));
		return criteria.list();
	}

	// HydramodulePatientWorkflow
	public HydramoduleFormFieldGroup saveHydramoduleFormFieldGroup(HydramoduleFormFieldGroup fieldGroup) {
		getSession().saveOrUpdate(fieldGroup);
		getSession().flush();
		return fieldGroup;
	}

	public HydramoduleFormFieldGroup getHydramoduleFormFieldGroup(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormFieldGroup.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleFormFieldGroup) criteria.uniqueResult();
	}

	public List<HydramoduleFormFieldGroup> getAllHydramoduleFormFieldGroups() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormFieldGroup.class);
		criteria.addOrder(Order.asc("groupId"));
		return criteria.list();
	}

	// HydramoduleUserWorkflow
	public HydramoduleUserWorkflow saveHydramoduleUserWorkflow(HydramoduleUserWorkflow hydramoduleUserWorkflow) {
		getSession().saveOrUpdate(hydramoduleUserWorkflow);
		getSession().flush();
		return hydramoduleUserWorkflow;
	}

	public HydramoduleUserWorkflow getHydramoduleUserWorkflow(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleUserWorkflow.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleUserWorkflow) criteria.uniqueResult();
	}

	public List<HydramoduleUserWorkflow> getAllHydramoduleUserWorkflow() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleUserWorkflow.class);
		criteria.addOrder(Order.asc("userWorkflowId"));
		return criteria.list();
	}

	public HydramoduleUserWorkflow getUserWorkflowByUser(User user) {

		if (user != null) {
			DbSession session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(HydramoduleUserWorkflow.class);
			criteria.add(Restrictions.eq("user", user));
			List<HydramoduleUserWorkflow> users = criteria.list();
			return users.get(0);

		}
		return null;

	}

	// HydramoduleUserWorkflow

	// HydramoduleEncounterMapper
	public HydramoduleEncounterMapper saveHydramoduleEncounterMapper(HydramoduleEncounterMapper hydramoduleEncounterMapper) {
		getSession().saveOrUpdate(hydramoduleEncounterMapper);
		getSession().flush();
		return hydramoduleEncounterMapper;
	}

	public HydramoduleEncounterMapper getHydramoduleEncounterMapper(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEncounterMapper.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleEncounterMapper) criteria.uniqueResult();
	}

	public List<HydramoduleEncounterMapper> getAllHydramoduleEncounterMapper() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEncounterMapper.class);
		criteria.addOrder(Order.asc("encounterMapperId"));
		return criteria.list();
	}
}
