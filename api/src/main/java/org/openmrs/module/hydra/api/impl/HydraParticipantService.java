package org.openmrs.module.hydra.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.IHydraParticipantService;
import org.openmrs.module.hydra.api.dao.IHydramoduleParticipantDao;
import org.openmrs.module.hydra.model.HydramoduleParticipant;
import org.openmrs.module.hydra.model.HydramoduleParticipantSalaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraParticipantService extends BaseOpenmrsService implements IHydraParticipantService {

	@Autowired
	private IHydramoduleParticipantDao participantDao;

	@Override
	public void setParticipantDao(IHydramoduleParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	@Override
	@Transactional
	public HydramoduleParticipant saveParticipant(HydramoduleParticipant service) throws APIException {
		return participantDao.saveParticipant(service);
	}

	@Override
	public List<HydramoduleParticipant> getAllParticipants(boolean retired) throws APIException {
		return participantDao.getAllParticipants(retired);
	}

	@Override
	public HydramoduleParticipant getParticipant(String uuid) throws APIException {
		return participantDao.getParticipant(uuid);
	}

	@Override
	public List<HydramoduleParticipant> getParticipantByUserUUID(String userUUID) throws APIException {
		User user = Context.getUserService().getUserByUuid(userUUID);
		List<HydramoduleParticipant> participantsList = new ArrayList();
		participantsList.add(participantDao.getParticipantByUser(user));
		return participantsList;
	}

	// ParticipantSalaryType
	@Override
	@Transactional
	public HydramoduleParticipantSalaryType saveParticipantSalaryType(HydramoduleParticipantSalaryType service)
	        throws APIException {
		return participantDao.saveParticipantSalaryType(service);
	}

	@Override
	public List<HydramoduleParticipantSalaryType> getAllParticipantSalaryTypes(boolean retired) throws APIException {
		return participantDao.getAllParticipantSalaryTypes(retired);
	}

	@Override
	public HydramoduleParticipantSalaryType getParticipantSalaryType(String uuid) throws APIException {
		return participantDao.getParticipantSalaryType(uuid);
	}

}
