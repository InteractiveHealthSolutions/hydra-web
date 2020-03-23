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
import org.openmrs.Encounter;

@Entity
@Table(name = "hydramodule_event_encounter", catalog = "hydra")
public class HydramoduleEventEncounter extends BaseOpenmrsObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_encounter_id", unique = true, nullable = false)
	private Integer eventEncounterId;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private HydramoduleEvent event;

	@ManyToOne
	@JoinColumn(name = "encounter_id")
	private Encounter encounter;

	@Override
	public Integer getId() {
		return eventEncounterId;
	}

	@Override
	public void setId(Integer id) {
		eventEncounterId = id;
	}

	public Integer getEventEncounterId() {
		return eventEncounterId;
	}

	public void setEventEncounterId(Integer eventEncounterId) {
		this.eventEncounterId = eventEncounterId;
	}

	public HydramoduleEvent getEvent() {
		return event;
	}

	public void setEvent(HydramoduleEvent event) {
		this.event = event;
	}

	public Encounter getEncounter() {
		return encounter;
	}

	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}

}
