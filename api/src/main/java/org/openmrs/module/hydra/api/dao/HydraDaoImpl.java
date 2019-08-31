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
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
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

	public List<HydramoduleWorkflow> getAllWorkflows() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflow.class);
		criteria.addOrder(Order.asc("workflowId"));
		return criteria.list();
	}

	public HydramodulePhase getPhase(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhase.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramodulePhase) criteria.uniqueResult();
	}

	public List<HydramodulePhase> getAllPhases() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhase.class);
		criteria.addOrder(Order.asc("phaseId"));
		return criteria.list();
	}

	public List<HydramoduleWorkflowPhases> getAllPhasesWorkFlowPhaseRelations() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflowPhases.class);
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	public HydramoduleComponent getComponent(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponent.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleComponent) criteria.uniqueResult();
	}

	public List<HydramoduleComponent> getAllComponents() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponent.class);
		criteria.addOrder(Order.asc("componentId"));
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

	public HydramodulePhase savePhase(HydramodulePhase phase) {
		getSession().saveOrUpdate(phase);
		getSession().flush();
		return phase;
	}
}
