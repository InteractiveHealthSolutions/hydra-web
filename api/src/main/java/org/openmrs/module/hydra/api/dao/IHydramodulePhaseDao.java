package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.module.hydra.model.HydramodulePhase;
import org.openmrs.module.hydra.model.HydramodulePhaseComponents;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.springframework.stereotype.Repository;

public interface IHydramodulePhaseDao {

	HydramodulePhaseComponents getPhaseComponentRelation(String uuid);

	HydramodulePhase getPhase(String uuid);

	List<HydramodulePhase> getAllPhases();

	List<HydramodulePhaseComponents> getAllPhaseComponentRelations();

	HydramodulePhaseComponents savePhaseComponentsRelation(HydramodulePhaseComponents phaseComponent);

	HydramodulePhase savePhase(HydramodulePhase phase);

	void deletePhaseComponent(HydramodulePhaseComponents phaseComponent);

	List<HydramodulePhaseComponents> getPhaseComponentByWorkflow(HydramoduleWorkflow workflow);

}
