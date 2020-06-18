package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.module.hydra.model.HydramoduleParticipant;
import org.openmrs.module.hydra.model.HydramoduleParticipantSalaryType;
import org.springframework.stereotype.Repository;

public interface IHydramoduleParticipantDao {

	HydramoduleParticipant getParticipantByUser(org.openmrs.User user);

	HydramoduleParticipant saveParticipant(HydramoduleParticipant serviceType);

	HydramoduleParticipant getParticipant(String uuid);

	List<HydramoduleParticipant> getAllParticipants(boolean retired);

	// ParticipantSalaryType
	HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType serviceType);

	HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid);

	List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired);

}
