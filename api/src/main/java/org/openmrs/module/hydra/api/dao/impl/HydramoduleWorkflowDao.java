package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleWorkflowDao;
import org.openmrs.module.hydra.model.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.HydramoduleUserWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.HydramoduleWorkflowPhases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Transactional
public class HydramoduleWorkflowDao implements IHydramoduleWorkflowDao {

	@Autowired
	protected DbSessionFactory sessionFactory;

	protected DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public HydramoduleWorkflow getWorkflow(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflow.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleWorkflow) criteria.uniqueResult();
	}

	@Override
	public HydramoduleWorkflow getWorkflowByName(String name) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflow.class);
		criteria.add(Restrictions.eq("name", name));

		return (HydramoduleWorkflow) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleWorkflow> getAllWorkflows() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflow.class);
		criteria.addOrder(Order.asc("workflowId"));
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@Override
	public HydramoduleWorkflow saveWorkflow(HydramoduleWorkflow workflow) {
		getSession().saveOrUpdate(workflow);
		return workflow;
	}

	@Override
	public void deleteWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purgeWorkflow(HydramoduleWorkflow workflow) throws APIException {
		// TODO Auto-generated method stub

	}

	@Override
	public HydramoduleWorkflowPhases getWorkflowPhaseRelation(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflowPhases.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleWorkflowPhases) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleWorkflowPhases> getAllPhasesWorkFlowPhaseRelations() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflowPhases.class);
		criteria.addOrder(Order.asc("displayOrder"));
		return criteria.list();
	}

	@Override
	public HydramoduleWorkflowPhases saveWorkflowPhase(HydramoduleWorkflowPhases phases) {
		getSession().saveOrUpdate(phases);
		return phases;
	}

	@Override
	public HydramoduleWorkflowPhases saveWorkflowPhaseRelation(HydramoduleWorkflowPhases workflow) {
		getSession().saveOrUpdate(workflow);
		return workflow;
	}

	@Override
	public void deleteWorkflowPhase(HydramoduleWorkflowPhases workflowphases) {
		sessionFactory.getCurrentSession().delete(workflowphases);
	}

	@Override
	public List<HydramoduleWorkflowPhases> getWorkflowPhase(HydramoduleWorkflow workflow) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleWorkflowPhases.class);
		criteria.add(Restrictions.eq("hydramoduleWorkflow", workflow));

		return criteria.list();
	}

	@Override
	public HydramodulePatientWorkflow saveHydramodulePatientWorkflow(HydramodulePatientWorkflow patientWorkflow) {
		getSession().saveOrUpdate(patientWorkflow);
		getSession().flush();
		return patientWorkflow;
	}

	@Override
	public HydramodulePatientWorkflow getHydramodulePatientWorkflow(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePatientWorkflow.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramodulePatientWorkflow) criteria.uniqueResult();
	}

	@Override
	public List<HydramodulePatientWorkflow> getAllHydramodulePatientWorkflows() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePatientWorkflow.class);
		criteria.addOrder(Order.asc("patientWorkflowId"));
		return criteria.list();
	}

	@Override
	public HydramoduleUserWorkflow saveHydramoduleUserWorkflow(HydramoduleUserWorkflow hydramoduleUserWorkflow) {
		getSession().saveOrUpdate(hydramoduleUserWorkflow);
		getSession().flush();
		return hydramoduleUserWorkflow;
	}

	@Override
	public HydramoduleUserWorkflow getHydramoduleUserWorkflow(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleUserWorkflow.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleUserWorkflow) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleUserWorkflow> getAllHydramoduleUserWorkflow() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleUserWorkflow.class);
		criteria.addOrder(Order.asc("userWorkflowId"));
		return criteria.list();
	}

	@Override
	public List<HydramoduleUserWorkflow> getUserWorkflowByUser(User user) {

		if (user != null) {
			DbSession session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(HydramoduleUserWorkflow.class);
			criteria.add(Restrictions.eq("user", user));
			List<HydramoduleUserWorkflow> users = criteria.list();
			return users;

		}
		return null;

	}

	@Override
	public HydramodulePatientWorkflow getPatientWorkflowByPatient(Patient patient) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramodulePatientWorkflow.class);
		criteria.add(Restrictions.eq("patient", patient));
		return (HydramodulePatientWorkflow) criteria.uniqueResult();
	}

}
