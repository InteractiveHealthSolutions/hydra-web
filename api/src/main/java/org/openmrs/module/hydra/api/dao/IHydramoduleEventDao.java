package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.module.hydra.model.HydramoduleEvent;
import org.openmrs.module.hydra.model.HydramoduleEventAsset;
import org.openmrs.module.hydra.model.HydramoduleEventParticipants;
import org.openmrs.module.hydra.model.HydramoduleEventSchedule;
import org.openmrs.module.hydra.model.HydramoduleEventService;
import org.openmrs.module.hydra.model.HydramoduleEventType;
import org.springframework.stereotype.Repository;

public interface IHydramoduleEventDao {

	HydramoduleEvent saveHydramoduleEvent(HydramoduleEvent event);

	HydramoduleEvent getHydramoduleEvent(String uuid);

	HydramoduleEvent getHydramoduleEvent(Integer id);

	List<HydramoduleEvent> getAllHydramoduleEvents(boolean retired);

	// EventScedule
	HydramoduleEventSchedule saveHydramoduleEventScedule(HydramoduleEventSchedule serviceType);

	HydramoduleEventSchedule getHydramoduleEventScedule(String uuid);

	List<HydramoduleEventSchedule> getAllHydramoduleEventScedules(boolean retired);

	// EventType
	HydramoduleEventType saveHydramoduleEventType(HydramoduleEventType serviceType);

	HydramoduleEventType getHydramoduleEventType(String uuid);

	List<HydramoduleEventType> getAllHydramoduleEventTypes(boolean retired);

	// EventAsset
	HydramoduleEventAsset saveHydramoduleEventAsset(HydramoduleEventAsset eventAsset);

	HydramoduleEventAsset getHydramoduleEventAsset(String uuid);

	List<HydramoduleEventAsset> getAllHydramoduleEventAssets(boolean retired);

	// EventService
	HydramoduleEventService saveHydramoduleEventService(HydramoduleEventService serviceType);

	HydramoduleEventService getHydramoduleEventService(String uuid);

	List<HydramoduleEventService> getAllHydramoduleEventServices(boolean retired);

	// EventParticipant
	HydramoduleEventParticipants saveHydramoduleEventParticipant(HydramoduleEventParticipants eventParticipants);

	HydramoduleEventParticipants getHydramoduleEventParticipant(String uuid);

	List<HydramoduleEventParticipants> getAllHydramoduleEventParticipants(boolean retired);

}
