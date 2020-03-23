package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_event_type", catalog = "hydra")
public class HydramoduleEventType extends BaseOpenmrsMetadata {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2697044324045780795L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_type_id", unique = true, nullable = false)
	private Integer eventTypeId;

	public HydramoduleEventType() {
		super();
	}

	public Integer getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	@Override
	public Integer getId() {
		return eventTypeId;
	}

	@Override
	public void setId(Integer id) {
		eventTypeId = id;
	}
}
