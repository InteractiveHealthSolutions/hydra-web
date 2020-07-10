package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hydra.api.dao.IHydramoduleParticipantDao;
import org.openmrs.module.hydra.model.HydramoduleParticipant;
import org.openmrs.module.hydra.model.HydramoduleParticipantSalaryType;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraParticipantService extends OpenmrsService {

	void setParticipantDao(IHydramoduleParticipantDao participantDao);

	HydramoduleParticipant saveParticipant(HydramoduleParticipant service) throws APIException;

	List<HydramoduleParticipant> getAllParticipants(boolean retired) throws APIException;

	HydramoduleParticipant getParticipant(String uuid) throws APIException;

	List<HydramoduleParticipant> getParticipantByUserUUID(String userUUID) throws APIException;

	// ParticipantSalaryType
	HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType service) throws APIException;

	List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired) throws APIException;

	HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid) throws APIException;

}
