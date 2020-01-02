package org.openmrs.module.hydra.model.event_planner;

import javax.persistence.JoinColumn;

import org.openmrs.Role;
import org.openmrs.User;

public class HydramoduleEventParticipants {

	@JoinColumn(name = "event_id", referencedColumnName = "event_id")
	private HydramoduleEvent event;

	private boolean attended;

	private String absence_reason;
}
