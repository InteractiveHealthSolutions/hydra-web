package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.api.dao.IHydramodulePhaseDao;
import org.openmrs.module.hydra.model.HydramodulePhase;
import org.openmrs.module.hydra.model.HydramodulePhaseComponents;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Transactional
public class HydramodulePhaseDao implements IHydramodulePhaseDao {

	@Autowired
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public HydramodulePhaseComponents getPhaseComponentRelation(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhaseComponents.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramodulePhaseComponents) criteria.uniqueResult();
	}

	@Override
	public HydramodulePhase getPhase(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhase.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramodulePhase) criteria.uniqueResult();
	}

	@Override
	public List<HydramodulePhase> getAllPhases() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhase.class);
		criteria.addOrder(Order.asc("phaseId"));
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@Override
	public List<HydramodulePhaseComponents> getAllPhaseComponentRelations() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhaseComponents.class);
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	@Override
	public HydramodulePhaseComponents savePhaseComponentsRelation(HydramodulePhaseComponents phaseComponent) {
		getSession().saveOrUpdate(phaseComponent);
		return phaseComponent;
	}

	@Override
	public HydramodulePhase savePhase(HydramodulePhase phase) {
		getSession().saveOrUpdate(phase);
		getSession().flush();
		return phase;
	}

	@Override
	public void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) {
		sessionFactory.getCurrentSession().delete(phaseComponent);
	}

	@Override
	public List<HydramodulePhaseComponents> getPhaseComponentByWorkflow(HydramoduleWorkflow workflow) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePhaseComponents.class);
		criteria.add(Restrictions.eq("hydramoduleWorkflow", workflow));

		return criteria.list();
	}

}
