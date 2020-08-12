package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hydra.api.dao.IHydramoduleLocationTypeDao;
import org.openmrs.module.hydra.model.HydramoduleLocationType;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class HydramoduleLocationTypeDao implements IHydramoduleLocationTypeDao {

	@Autowired
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public HydramoduleLocationType saveLocationType(HydramoduleLocationType hydramoduleLocationType) {
		getSession().saveOrUpdate(hydramoduleLocationType);
		// getSession().flush();
		return hydramoduleLocationType;
	}

	@Override
	public HydramoduleLocationType getLocationTypeByUuid(String uuid) {
		Criteria criteria = getSession().createCriteria(HydramoduleLocationType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleLocationType) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleLocationType> getAllLocationType(boolean retired) {
		Criteria criteria = getSession().createCriteria(HydramoduleLocationType.class);
		criteria.addOrder(Order.asc("locationTypeId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

}
