package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsData;

// @Entity
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
	private boolean attendance;

	@Column(name = "absence_reason")
	private String absence_reason;

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

	public boolean isAttendance() {
		return attendance;
	}

	public void setAttendance(boolean attendance) {
		this.attendance = attendance;
	}

	public String getAbsence_reason() {
		return absence_reason;
	}

	public void setAbsence_reason(String absence_reason) {
		this.absence_reason = absence_reason;
	}

	@Override
	public Integer getId() {
		return eventParticipantId;
	}

	@Override
	public void setId(Integer id) {
		eventParticipantId = id;
	}
}
