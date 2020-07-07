package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.dao.IHydramoduleEventDao;
import org.openmrs.module.hydra.model.HydramoduleEvent;
import org.openmrs.module.hydra.model.HydramoduleEventAsset;
import org.openmrs.module.hydra.model.HydramoduleEventParticipants;
import org.openmrs.module.hydra.model.HydramoduleEventSchedule;
import org.openmrs.module.hydra.model.HydramoduleEventService;
import org.openmrs.module.hydra.model.HydramoduleEventType;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraEventService {

	void setEventDao(IHydramoduleEventDao eventDao);

	HydramoduleEvent saveEvent(HydramoduleEvent service) throws APIException;

	List<HydramoduleEvent> getAllEvents(boolean voided) throws APIException;

	HydramoduleEvent getEvent(String uuid) throws APIException;

	// EventSchedule
	HydramoduleEventSchedule saveEventSchedule(HydramoduleEventSchedule service) throws APIException;

	List<HydramoduleEventSchedule> getAllEventSchedules(boolean voided) throws APIException;

	HydramoduleEventSchedule getEventSchedule(String uuid) throws APIException;

	HydramoduleEventType saveEventType(HydramoduleEventType service) throws APIException;

	List<HydramoduleEventType> getAllEventTypes(boolean voided) throws APIException;

	HydramoduleEventType getEventType(String uuid) throws APIException;

	// EventService
	HydramoduleEventService saveEventService(HydramoduleEventService service) throws APIException;

	List<HydramoduleEventService> getAllEventServices(boolean voided) throws APIException;

	HydramoduleEventService getEventService(String uuid) throws APIException;

	// EventAsset
	HydramoduleEventAsset saveEventAsset(HydramoduleEventAsset service) throws APIException;

	List<HydramoduleEventAsset> getAllEventAssets(boolean voided) throws APIException;

	HydramoduleEventAsset getEventAsset(String uuid) throws APIException;

	// EventParticipant
	HydramoduleEventParticipants saveEventParticipant(HydramoduleEventParticipants service) throws APIException;

	List<HydramoduleEventParticipants> getAllEventParticipants(boolean voided) throws APIException;

	HydramoduleEventParticipants getEventParticipant(String uuid) throws APIException;

}