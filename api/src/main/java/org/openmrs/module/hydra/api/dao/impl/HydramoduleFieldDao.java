package org.openmrs.module.hydra.api.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.FieldAnswer;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFieldDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFormDao;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleFieldAnswer;
import org.openmrs.module.hydra.model.HydramoduleFieldDTO;
import org.openmrs.module.hydra.model.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.model.HydramoduleRuleToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Transactional
public class HydramoduleFieldDao implements IHydramoduleFieldDao {

	private IHydramoduleFormDao hydraFormDao;

	@Autowired
	protected DbSessionFactory sessionFactory;

	protected DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}

	// Fields
	@Override
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

	@Override
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

	@Override
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
	@Override
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

	@Override
	public HydramoduleField getHydramoduleField(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleField.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleField) criteria.uniqueResult();
	}

	@Override
	public HydramoduleFormField getHydramoduleFormField(String formUUID, String fieldUUID) {
		HydramoduleForm form = hydraFormDao.getModuleForm(formUUID);
		HydramoduleField field = getHydramoduleField(fieldUUID);
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormField.class);
		criteria.add(Restrictions.eq("form", form));
		criteria.add(Restrictions.eq("field", field));

		System.out.println("Dataatatatatatatatatatat " + formUUID + " " + fieldUUID);
		return (HydramoduleFormField) criteria.uniqueResult();
	}

	@Override
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
	@Override
	public HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldAnswer.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleFieldAnswer) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldAnswer.class);
		criteria.addOrder(Order.asc("fieldAnswerId"));
		return criteria.list();
	}

	// HydramoduleFieldRule

	@Override
	public void deleteFieldRules(List<HydramoduleFieldRule> rules) {
		DbSession session = sessionFactory.getCurrentSession();
		for (HydramoduleFieldRule ff : rules) {
			deleteRuleTokensByFieldRuleId(ff.getRuleId());
			session.delete(ff);
		}
		session.flush();
	}

	@Override
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

	@Override
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
	@Override
	@Deprecated
	public List<HydramoduleFieldRule> getHydramoduleFieldRuleByTargetField(HydramoduleField field) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldRule.class);
		criteria.add(Restrictions.eq("targetQuestion", field));
		return (List<HydramoduleFieldRule>) criteria.list();
	}

	@Override
	public List<HydramoduleFieldRule> getHydramoduleFieldRuleByTargetFormField(HydramoduleFormField field) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldRule.class);
		criteria.add(Restrictions.eq("targetFormField", field));
		return (List<HydramoduleFieldRule>) criteria.list();
	}

	@Override
	public List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFieldRule.class);
		criteria.add(Restrictions.eq("retired", retired));

		return criteria.list();
	}

	@Override
	public void deleteRuleTokensByFieldRuleId(Integer fieldRuleId) {
		DbSession session = sessionFactory.getCurrentSession();
		session.createQuery("delete from HydramoduleRuleToken f where f.rule.ruleId =" + fieldRuleId).executeUpdate();
	}

	@Override
	public HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleRuleToken getHydramoduleRuleToken(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleRuleToken.class);
		return (HydramoduleRuleToken) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleRuleToken> getAllHydramoduleRuleTokens() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleRuleToken.class);
		criteria.addOrder(Order.asc("tokenId"));
		return criteria.list();
	}

	@Override
	public HydramoduleFieldAnswer saveHydramoduleDTOFieldAnswer(HydramoduleFieldAnswer fieldAnswer) {
		getSession().saveOrUpdate(fieldAnswer);
		getSession().flush();
		return fieldAnswer;
	}

	@Override
	public void deleteFieldAnswers(int fieldId) {
		DbSession session = sessionFactory.getCurrentSession();
		session.createQuery("delete from HydramoduleFieldAnswer f where f.field.fieldId=" + fieldId).executeUpdate();
	}

}
