package org.openmrs.module.hydra.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class EventType {

	@Id
	@GeneratedValue
	private int eventTypeId;

	@Column(unique = true)
	private String name;

}
