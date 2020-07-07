package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.dao.IHydramodulePhaseDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleWorkflowDao;
import org.openmrs.module.hydra.model.HydramodulePhase;
import org.openmrs.module.hydra.model.HydramodulePhaseComponents;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraPhaseService {

	void setPhaseDao(IHydramodulePhaseDao phaseDao);

	void setWorkflowDao(IHydramoduleWorkflowDao workflowDao);

	HydramodulePhase savePhase(HydramodulePhase item) throws APIException;

	HydramodulePhase getPhaseByUUID(String uuid) throws APIException;

	List<HydramodulePhase> getAllPhases() throws APIException;

	HydramodulePhaseComponents savePhaseComponentRelation(HydramodulePhaseComponents item) throws APIException;

	HydramodulePhaseComponents getPhasesComponentRelationByUUID(String uuid) throws APIException;

	List<HydramodulePhaseComponents> getAllPhaseComponentsRelations() throws APIException;

	void deletePhaseComponent(HydramodulePhaseComponents phaseComponent) throws APIException;

	List<HydramodulePhaseComponents> getHydramodulePhaseComponentsByWorkflow(String uuid) throws APIException;

}