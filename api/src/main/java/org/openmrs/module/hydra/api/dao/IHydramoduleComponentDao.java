package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.module.hydra.model.HydramoduleComponent;
import org.openmrs.module.hydra.model.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramodulePhase;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.springframework.stereotype.Repository;

public interface IHydramoduleComponentDao {

	HydramoduleComponentForm getComponentFormRelation(String uuid);

	List<HydramoduleComponentForm> getAllComponentFormRelations();

	List<HydramoduleComponentForm> getAllComponentFormRelations(HydramoduleForm form);

	HydramoduleComponent getComponent(String uuid);

	List<HydramoduleComponent> getAllComponents();

	HydramoduleComponentForm saveComponentFormRelation(HydramoduleComponentForm componentForm);

	HydramoduleComponent saveComponent(HydramoduleComponent component);

	HydramoduleComponent updateComponent(HydramoduleComponent component);

	HydramoduleComponentForm updateComponentForm(HydramoduleComponentForm componentForm);

	HydramoduleComponentForm getComponentFormByFormAndWorkflow(HydramoduleForm hydramoduleForm,
	        HydramoduleWorkflow hydramoduleWorkflow);

	List<HydramoduleComponentForm> getComponentFormByWorkflow(HydramoduleWorkflow hydramoduleWorkflow);


	List<HydramoduleComponentForm> getComponentFormByPhase(HydramodulePhase phase);

}
