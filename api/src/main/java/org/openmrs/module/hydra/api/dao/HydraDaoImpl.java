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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("hydra.HydraDao")
public class HydraDaoImpl {

	@Autowired
	DbSessionFactory sessionFactory;

	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
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
}
