package org.openmrs.module.hydra.model.event_planner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commonlabtest_test")
public class EventType {

	@Id
	@GeneratedValue
	private int eventTypeId;

	@Column(unique = true)
	private String name;

}
