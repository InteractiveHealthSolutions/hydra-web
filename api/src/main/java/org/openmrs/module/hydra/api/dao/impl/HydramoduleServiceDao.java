package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleServiceDao;
import org.openmrs.module.hydra.model.HydramoduleService;
import org.openmrs.module.hydra.model.HydramoduleServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Transactional
public class HydramoduleServiceDao implements IHydramoduleServiceDao {

	@Autowired
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public HydramoduleServiceType saveServiceType(HydramoduleServiceType serviceType) {
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleServiceType getServiceType(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleServiceType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleServiceType) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleServiceType> getAllServiceTypes(boolean retired) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleServiceType.class);
		criteria.addOrder(Order.asc("serviceTypeId"));
		criteria.add(Restrictions.eq("retired", retired));

		return criteria.list();
	}

	// Service
	@Override
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

	@Override
	public HydramoduleService getService(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleService.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleService) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleService> getAllServices(boolean retired) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleService.class);
		criteria.addOrder(Order.asc("serviceId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

}
