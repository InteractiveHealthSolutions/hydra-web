package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hydra.api.dao.IHydramoduleParticipantDao;
import org.openmrs.module.hydra.model.HydramoduleParticipant;
import org.openmrs.module.hydra.model.HydramoduleParticipantSalaryType;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class HydramoduleParticipantDao implements IHydramoduleParticipantDao {

	@Autowired
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public HydramoduleParticipant getParticipantByUser(org.openmrs.User user) {
		if (user != null) {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(HydramoduleParticipant.class);
			criteria.add(Restrictions.eq("user", user));
			// criteria.
			List<HydramoduleParticipant> fields = criteria.list();
			return fields.get(0);
		}

		return null;
	}

	@Override
	public HydramoduleParticipant saveParticipant(HydramoduleParticipant serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleParticipant getParticipant(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipant.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleParticipant) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleParticipant> getAllParticipants(boolean retired) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipant.class);
		criteria.addOrder(Order.asc("participantId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// ParticipantSalaryType
	@Override
	public HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType serviceType) {
		System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipantSalaryType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleParticipantSalaryType) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleParticipantSalaryType.class);
		criteria.addOrder(Order.asc("salaryTypeId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

}
