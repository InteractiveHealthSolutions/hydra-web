package org.openmrs.module.hydra.api.impl;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.IHydraEventService;
import org.openmrs.module.hydra.api.dao.IHydramoduleEventDao;
import org.openmrs.module.hydra.model.HydramoduleEvent;
import org.openmrs.module.hydra.model.HydramoduleEventAsset;
import org.openmrs.module.hydra.model.HydramoduleEventParticipants;
import org.openmrs.module.hydra.model.HydramoduleEventSchedule;
import org.openmrs.module.hydra.model.HydramoduleEventService;
import org.openmrs.module.hydra.model.HydramoduleEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraEventService implements IHydraEventService {
	
	@Autowired
	private IHydramoduleEventDao eventDao;
	
	@Override
	public void setEventDao(IHydramoduleEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Override
	@Transactional
	public HydramoduleEvent saveEvent(HydramoduleEvent service) throws APIException {
		return eventDao.saveHydramoduleEvent(service);
	}

	@Override
	public List<HydramoduleEvent> getAllEvents(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEvents(voided);
	}

	@Override
	public HydramoduleEvent getEvent(String uuid) throws APIException {
		return eventDao.getHydramoduleEvent(uuid);
	}

	// EventSchedule
	@Override
	@Transactional
	public HydramoduleEventSchedule saveEventSchedule(HydramoduleEventSchedule service) throws APIException {
		return eventDao.saveHydramoduleEventScedule(service);
	}

	@Override
	public List<HydramoduleEventSchedule> getAllEventSchedules(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventScedules(voided);
	}

	@Override
	public HydramoduleEventSchedule getEventSchedule(String uuid) throws APIException {
		return eventDao.getHydramoduleEventScedule(uuid);
	}

	// EventType

    @Override
	@Transactional
	public HydramoduleEventType saveEventType(HydramoduleEventType service) throws APIException {
		return eventDao.saveHydramoduleEventType(service);
	}

	@Override
	public List<HydramoduleEventType> getAllEventTypes(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventTypes(voided);
	}

	@Override
	public HydramoduleEventType getEventType(String uuid) throws APIException {
		return eventDao.getHydramoduleEventType(uuid);
	}

	// EventService
	@Override
	@Transactional
	public HydramoduleEventService saveEventService(HydramoduleEventService service) throws APIException {
		return eventDao.saveHydramoduleEventService(service);
	}

	@Override
	public List<HydramoduleEventService> getAllEventServices(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventServices(voided);
	}

	
	@Override
	public HydramoduleEventService getEventService(String uuid) throws APIException {
		return eventDao.getHydramoduleEventService(uuid);
	}

	// EventAsset
	@Override
	@Transactional
	public HydramoduleEventAsset saveEventAsset(HydramoduleEventAsset service) throws APIException {
		return eventDao.saveHydramoduleEventAsset(service);
	}

	@Override
	public List<HydramoduleEventAsset> getAllEventAssets(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventAssets(voided);
	}

	@Override
	public HydramoduleEventAsset getEventAsset(String uuid) throws APIException {
		return eventDao.getHydramoduleEventAsset(uuid);
	}

	// EventParticipant
	@Override
	@Transactional
	public HydramoduleEventParticipants saveEventParticipant(HydramoduleEventParticipants service) throws APIException {
		return eventDao.saveHydramoduleEventParticipant(service);
	}

	@Override
	public List<HydramoduleEventParticipants> getAllEventParticipants(boolean voided) throws APIException {
		return eventDao.getAllHydramoduleEventParticipants(voided);
	}

	@Override
	public HydramoduleEventParticipants getEventParticipant(String uuid) throws APIException {
		return eventDao.getHydramoduleEventParticipant(uuid);
	}

}
