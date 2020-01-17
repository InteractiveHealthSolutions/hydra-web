package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

@Entity
@Table(name = "hydramodule_event_schedule", catalog = "hydra")
public class HydramoduleEventSchedule extends BaseOpenmrsData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -425331194421303733L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "schedule_id", unique = true, nullable = false)
	private int scheduleId;

	@Column(name = "planned_date")
	private Date plannedDate;

	@Column(name = "event_date")
	private Date eventDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "reason_deferred")
	private String reasonDeferred;

	public HydramoduleEventSchedule() {
		super();
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Date getPlannedDate() {
		return plannedDate;
	}

	public void setPlannedDate(Date plannedDate) {
		this.plannedDate = plannedDate;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getReasonDeferred() {
		return reasonDeferred;
	}

	public void setReasonDeferred(String reasonDeferred) {
		this.reasonDeferred = reasonDeferred;
	}

	@Override
	public Integer getId() {
		return scheduleId;
	}

	@Override
	public void setId(Integer id) {
		this.scheduleId = id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
