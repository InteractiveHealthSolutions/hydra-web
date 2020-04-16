package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

@Entity
@Table(name = "hydramodule_event_summary", catalog = "hydra")
public class HydramoduleEventSumary extends BaseOpenmrsObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_summary_id", unique = true, nullable = false)
	private Integer eventSummaryId;
	
	@Column(name = "total_screen")
	private Integer totalScreen;
	
	@Column(name = "total_presumptive")
	private Integer totalPresumptive;
	
	@Column(name = "sample_collected")
	private Integer sampleCollected;
	
	@Column(name = "event_id")
	private Integer eventId;
	
	public Integer getEventSummaryId() {
		return eventSummaryId;
	}
	
	public void setEventSummaryId(Integer eventSummaryId) {
		this.eventSummaryId = eventSummaryId;
	}
	
	public Integer getTotalScreen() {
		return totalScreen;
	}
	
	public void setTotalScreen(Integer totalScreen) {
		this.totalScreen = totalScreen;
	}
	
	public Integer getTotalPresumptive() {
		return totalPresumptive;
	}
	
	public void setTotalPresumptive(Integer totalPresumptive) {
		this.totalPresumptive = totalPresumptive;
	}
	
	public Integer getSampleCollected() {
		return sampleCollected;
	}
	
	public void setSampleCollected(Integer sampleCollected) {
		this.sampleCollected = sampleCollected;
	}
	
	public Integer getEventId() {
		return eventId;
	}
	
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	@Override
	public Integer getId() {
		return eventSummaryId;
	}
	
	@Override
	public void setId(Integer id) {
		eventSummaryId = id;
	}
	
}
