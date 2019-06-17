package org.openmrs.module.hydra.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Schedule {

	@Id
	@GeneratedValue
	private int scheduleId;

	private Date plannedDate;

	private Date eventDate;

	private String reasonDeferred;
}
