package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;

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

	@Column(name = "closure_notes")
	private String closureNotes;

	@Column(name = "description")
	private String description;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "closed")
	private Boolean closed;

	@OneToOne
	@JoinColumn(name = "schedule_id")
	@JsonManagedReference
	private HydramoduleEventSchedule schedule;

	@ManyToOne
	@JoinColumn(name = "location_id")
	Location location;

	@ManyToOne
	@JoinColumn(name = "event_type_id")
	HydramoduleEventType eventType;

	@OneToMany(mappedBy = "event")
	List<HydramoduleEventAsset> eventAssets;

	@OneToMany(mappedBy = "event")
	List<HydramoduleEventService> eventServices;

	@OneToMany(mappedBy = "event")
	List<HydramoduleEventParticipants> eventParticipants;

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

	public HydramoduleEventType getEventType() {
		return eventType;
	}

	public void setEventType(HydramoduleEventType eventType) {
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

	public List<HydramoduleEventAsset> getEventAssets() {
		return eventAssets;
	}

	public void setEventAssets(List<HydramoduleEventAsset> eventAssets) {
		this.eventAssets = eventAssets;
	}

	public List<HydramoduleEventService> getEventServices() {
		return eventServices;
	}

	public void setEventServices(List<HydramoduleEventService> eventServices) {
		this.eventServices = eventServices;
	}

	public List<HydramoduleEventParticipants> getEventParticipants() {
		return eventParticipants;
	}

	public void setEventParticipants(List<HydramoduleEventParticipants> eventParticipants) {
		this.eventParticipants = eventParticipants;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getClosureNotes() {
		return closureNotes;
	}

	public void setClosureNotes(String closureNotes) {
		this.closureNotes = closureNotes;
	}
}
