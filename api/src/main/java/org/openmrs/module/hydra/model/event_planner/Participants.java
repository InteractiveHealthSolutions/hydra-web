package org.openmrs.module.hydra.model.event_planner;

import javax.persistence.JoinColumn;

import org.openmrs.Role;
import org.openmrs.User;

public class Participants {

	@JoinColumn(name = "event_id", referencedColumnName = "event_id")
	private Event event;

	private User user;

	private Role role;

	private boolean attended;

	private String absence_reason;
}
