package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hydra.api.dao.IHydramoduleComponentDao;
import org.openmrs.module.hydra.model.HydramoduleComponent;
import org.openmrs.module.hydra.model.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class HydramoduleComponentDao implements IHydramoduleComponentDao {

	@Autowired
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public HydramoduleComponentForm getComponentFormRelation(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleComponentForm) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleComponentForm> getAllComponentFormRelations() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.add(Restrictions.eq("retired", false));
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	@Override
	public List<HydramoduleComponentForm> getAllComponentFormRelations(HydramoduleForm form) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.addOrder(Order.asc("displayOrder"));
		criteria.add(Restrictions.eq("form", form));
		return criteria.list();
	}

	@Override
	public HydramoduleComponent getComponent(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponent.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleComponent) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleComponent> getAllComponents() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponent.class);
		criteria.addOrder(Order.asc("componentId"));
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@Override
	public HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm componentForm) {
		getSession().saveOrUpdate(componentForm);
		return componentForm;
	}

	@Override
	public HydramoduleComponent saveComponent(HydramoduleComponent component) {
		getSession().saveOrUpdate(component);
		getSession().flush();
		return component;
	}

	@Override
	public HydramoduleComponent updateComponent(HydramoduleComponent component) {
		getSession().update(component);
		getSession().flush();
		return component;
	}

	@Override
	public HydramoduleComponentForm updateComponentForm(HydramoduleComponentForm componentForm) {
		getSession().update(componentForm);
		getSession().flush();
		return componentForm;
	}

	@Override
	public HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,
	        HydramoduleWorkflow hydramoduleWorkflow) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.add(Restrictions.eq("form", hydramoduleForm));
		criteria.add(Restrictions.eq("workflow", hydramoduleWorkflow));
		return (HydramoduleComponentForm) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleComponentForm> getComponentFormByWorkflow(HydramoduleWorkflow hydramoduleWorkflow) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);

		criteria.add(Restrictions.eq("workflow", hydramoduleWorkflow));
		return criteria.list();
	}

	@Override
	public List<HydramoduleComponentForm> getComponentFormByComponent(HydramoduleComponent component) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleComponentForm.class);
		criteria.add(Restrictions.eq("component", component));
		return criteria.list();
	}

}
