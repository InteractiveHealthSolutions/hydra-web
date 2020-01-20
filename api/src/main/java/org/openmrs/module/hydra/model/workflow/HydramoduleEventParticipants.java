package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsData;

@Entity
@Table(name = "hydramodule_event_participants", catalog = "hydra")
public class HydramoduleEventParticipants extends BaseOpenmrsData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18035890351L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_participant_id", unique = true, nullable = false)
	private Integer eventParticipantId;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private HydramoduleEvent event;

	@ManyToOne
	@JoinColumn(name = "participant_id")
	private HydramoduleParticipant participant;

	@Column(name = "attendance")
	private Boolean attendance;

	@Column(name = "absence_reason")
	private String absenceReason;

	@Column(name = "planned_for_event")
	private Boolean plannedForEvent;

	public HydramoduleEventParticipants() {
		super();
	}

	public Integer getEventParticipantId() {
		return eventParticipantId;
	}

	public void setEventParticipantId(Integer eventParticipantId) {
		this.eventParticipantId = eventParticipantId;
	}

	public HydramoduleEvent getEvent() {
		return event;
	}

	public void setEvent(HydramoduleEvent event) {
		this.event = event;
	}

	public HydramoduleParticipant getParticipant() {
		return participant;
	}

	public void setParticipant(HydramoduleParticipant participant) {
		this.participant = participant;
	}

	public void setAttendance(Boolean attendance) {
		this.attendance = attendance;
	}

	public String getAbsenceReason() {
		return absenceReason;
	}

	public void setAbsenceReason(String absenceReason) {
		this.absenceReason = absenceReason;
	}

	@Override
	public Integer getId() {
		return eventParticipantId;
	}

	@Override
	public void setId(Integer id) {
		eventParticipantId = id;
	}

	public Boolean getPlannedForEvent() {
		return plannedForEvent;
	}

	public void setPlannedForEvent(Boolean plannedForEvent) {
		this.plannedForEvent = plannedForEvent;
	}

	public Boolean getAttendance() {
		return attendance;
	}

}
