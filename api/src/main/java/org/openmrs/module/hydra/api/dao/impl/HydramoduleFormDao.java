package org.openmrs.module.hydra.api.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleComponentDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFieldDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFormDao;
import org.openmrs.module.hydra.model.HydraForm;
import org.openmrs.module.hydra.model.HydramoduleComponent;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.model.HydramoduleFormFieldGroup;
import org.springframework.stereotype.Component;

@Component("formDao")
@Transactional
public class HydramoduleFormDao extends HydraDao implements IHydramoduleFormDao {

	private IHydramoduleFieldDao fieldDao;

	private IHydramoduleComponentDao componentDao;

	@Override
	public HydramoduleForm getModuleFormByName(String name) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleForm.class);
		criteria.add(Restrictions.eq("name", name));

		return (HydramoduleForm) criteria.uniqueResult();
	}

	@Override
	public HydraForm getHydraFormByUuid(String uuid) {
		return (HydraForm) getSession().createCriteria(HydraForm.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public HydraForm getHydraModuleFormByTag(String tag) {
		DbSession session = getSession();
		HydraForm form = (HydraForm) session.createCriteria(HydraForm.class).add(Restrictions.eq("uuid", tag))
		        .uniqueResult();
		return form;
	}

	@Override
	public java.util.Set<HydraForm> getHydraFormsByTag(String tag) throws APIException {
		return null;
	}

	@Override
	public HydraForm saveForm(HydraForm form) {
		getSession().saveOrUpdate(form);
		return form;
	}

	// TODO could be done better way, right now deleting all the existing then
	// adding new in case of edit
	@Override
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

	@Override
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

	@Override
	public List<HydramoduleFormField> getFormFields(HydramoduleForm form) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormField.class);
		criteria.add(Restrictions.eq("form", form));

		return criteria.list();
	}

	@Override
	public List<HydramoduleFormField> getFormFields(Integer formId) {
		DbSession session = sessionFactory.getCurrentSession();
		return session.createQuery("from HydramoduleFormField f where f.form.hydramoduleFormId=" + formId).list();
	}

	@Override
	public HydramoduleFormField getFormField(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormField.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleFormField) criteria.uniqueResult();
	}

	@Override
	public void deleteFormFields(HydramoduleForm form) {
		DbSession session = sessionFactory.getCurrentSession();
		List<HydramoduleFormField> formFields = getFormFields(form);
		deleteFormFields(formFields);
		session.flush();
	}

	// TODO use criteria
	@Override
	public void deleteFormFields(List<HydramoduleFormField> formFields) {
		DbSession session = sessionFactory.getCurrentSession();
		for (HydramoduleFormField ff : formFields) {
			System.out.println("Deleted!!!");
			fieldDao.deleteFieldRules(ff.getRules());
			session.delete(ff);
		}
		session.flush();
	}

	@Override
	public List<HydramoduleForm> getAllModuleForm() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleForm.class);
		criteria.addOrder(Order.asc("hydramoduleFormId"));
		return criteria.list();
	}

	@Override
	public List<HydramoduleForm> getAllModuleFormByComponentUUID(String componentUUID) {
		HydramoduleComponent component = componentDao.getComponent(componentUUID);
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

	@Override
	public HydramoduleForm getModuleForm(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleForm.class);
		criteria.add(Restrictions.eq("uuid", uuid));

		return (HydramoduleForm) criteria.uniqueResult();
	}

	// componentForm
	@Override
	public HydramoduleFormEncounter saveFormEncounter(HydramoduleFormEncounter formEncounter) {
		getSession().saveOrUpdate(formEncounter);
		getSession().flush();
		return formEncounter;
	}

	@Override
	public HydramoduleFormField getHydramoduleFormField(String formUUID, String fieldUUID) {
		HydramoduleForm form = getModuleForm(formUUID);
		HydramoduleField field = fieldDao.getHydramoduleField(fieldUUID);
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormField.class);
		criteria.add(Restrictions.eq("form", form));
		criteria.add(Restrictions.eq("field", field));

		System.out.println("Dataatatatatatatatatatat " + formUUID + " " + fieldUUID);
		return (HydramoduleFormField) criteria.uniqueResult();
	}

	@Override
	public HydramoduleFormFieldGroup saveHydramoduleFormFieldGroup(HydramoduleFormFieldGroup fieldGroup) {
		getSession().saveOrUpdate(fieldGroup);
		getSession().flush();
		return fieldGroup;
	}

	@Override
	public HydramoduleFormFieldGroup getHydramoduleFormFieldGroup(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormFieldGroup.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (HydramoduleFormFieldGroup) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleFormFieldGroup> getAllHydramoduleFormFieldGroups() {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleFormFieldGroup.class);
		criteria.addOrder(Order.asc("groupId"));
		return criteria.list();
	}

}
