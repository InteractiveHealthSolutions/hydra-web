package org.openmrs.module.hydra.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

public class Event {

	@Id
	@GeneratedValue
	private int eventId;

	private String name;

	private String description;

	private String referenceId;

	@JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
	private Schedule schedule;

}
