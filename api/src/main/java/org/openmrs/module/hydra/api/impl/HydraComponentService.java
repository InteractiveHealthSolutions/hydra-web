package org.openmrs.module.hydra.api.impl;

import java.util.Date;
import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.IHydraComponentService;
import org.openmrs.module.hydra.api.SExprHelper;
import org.openmrs.module.hydra.api.dao.IHydramoduleComponentDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleFieldDao;
import org.openmrs.module.hydra.model.HydramoduleComponent;
import org.openmrs.module.hydra.model.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.springframework.beans.factory.annotation.Autowired;

public class HydraComponentService extends BaseOpenmrsService implements IHydraComponentService {

	@Autowired
	private IHydramoduleComponentDao componentDao;

	@Autowired
	private IHydramoduleFieldDao fieldDao;

	@Override
	public void setFieldDao(IHydramoduleFieldDao fieldDao) {
		this.fieldDao = fieldDao;
	}

	@Override
	public void setComponentDao(IHydramoduleComponentDao componentDao) {
		this.componentDao = componentDao;
	}

	@Override
	public HydramoduleComponent saveComponent(HydramoduleComponent component) throws APIException {
		return componentDao.saveComponent(component);
	}

	@Override
	public HydramoduleComponent getComponentByUUID(String uuid) throws APIException {

		return componentDao.getComponent(uuid);
	}

	@Override
	public List<HydramoduleComponent> getAllComponents() throws APIException {

		return componentDao.getAllComponents();
	}

	@Override
	public void purgeComponent(HydramoduleComponent component) throws APIException {
		component.setRetired(true);
		component.setRetiredBy(Context.getAuthenticatedUser());
		component.setDateRetired(new Date());
		componentDao.updateComponent(component);
	}

	@Override
	public HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm item) throws APIException {
		return componentDao.saveComponentFormRelation(item);
	}

	@Override
	public HydramoduleComponentForm getComponentFormByUUID(String uuid) throws APIException {
		return componentDao.getComponentFormRelation(uuid);
	}

	@Override
	public List<HydramoduleComponentForm> getAllComponentFormsRelations() throws APIException, CloneNotSupportedException {
		List<HydramoduleComponentForm> componentForms = componentDao.getAllComponentFormRelations();

		SExprHelper exprHelper = SExprHelper.getInstance();

		for (HydramoduleComponentForm cf : componentForms) {
			HydramoduleForm form = cf.getForm();
			List<HydramoduleFormField> formFields = form.getFormFields();
			for (HydramoduleFormField ff : formFields) {
				// HydramoduleField field = dao.getHydramoduleField(ff.getField().getUuid());
				HydramoduleField field = ff.getField().clone(); // This is the targetField of a rule
				String parsedRule = exprHelper.compileComplex(fieldDao, ff);
				// if (parsedRule != null) {
				field.setParsedRule(parsedRule);
				ff.setField(field);
				// }
			}
		}

		return componentForms;
	}

	@Override
	public void retireComponentForm(HydramoduleComponentForm phaseComponent) throws APIException {
		phaseComponent.setRetired(true);
		phaseComponent.setRetiredBy(Context.getAuthenticatedUser());
		phaseComponent.setDateRetired(new Date());
		// dao.updateComponent(component);
		componentDao.updateComponentForm(phaseComponent);
	}

	@Override
	public HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,
	        HydramoduleWorkflow hydramoduleWorkflow) throws APIException {
		HydramoduleComponentForm componentForm = componentDao.getComponentFormByFormAndWorkflow(hydramoduleForm,
		    hydramoduleWorkflow);
		return componentForm;
	}

	@Override
	public List<HydramoduleComponentForm> getComponentFormsByComponent(String componentUUID) throws APIException {
		HydramoduleComponent component = componentDao.getComponent(componentUUID);
		List<HydramoduleComponentForm> hydramoduleComponents = componentDao.getComponentFormByComponent(component);
		return hydramoduleComponents;
	}

}
