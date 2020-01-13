package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.tools.DocumentationTool.Location;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.openmrs.BaseOpenmrsData;

@Entity
@Table(name = "hydramodule_event", catalog = "hydra")
public class HydramoduleEvent extends BaseOpenmrsData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 929698027018457514L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_id", unique = true, nullable = false)
	private Integer eventId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "reference_id")
	private String referenceId;

	@OneToOne
	@JoinColumn(name = "schedule_id")
	@JsonManagedReference
	private HydramoduleEventSchedule schedule;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne
	@JoinColumn(name = "event_type_id")
	EventType eventType;

	public HydramoduleEvent() {
		super();
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public HydramoduleEventSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(HydramoduleEventSchedule schedule) {
		this.schedule = schedule;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	@Override
	public Integer getId() {
		return eventId;
	}

	@Override
	public void setId(Integer id) {
		this.eventId = id;
	}

}
