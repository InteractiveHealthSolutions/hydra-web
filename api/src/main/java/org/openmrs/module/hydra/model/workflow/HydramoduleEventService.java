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
@Table(name = "hydramodule_event_service", catalog = "hydra")
public class HydramoduleEventService extends BaseOpenmrsData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18035890351L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_service_id", unique = true, nullable = false)
	private Integer eventServiceId;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private HydramoduleEvent event;

	@ManyToOne
	@JoinColumn(name = "service_id")
	private HydramoduleService service;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "actual_cost")
	private String actualCost;

	@Column(name = "description")
	private String description;

	@Column(name = "planned_for_event")
	private Boolean plannedForEvent;

	@Column(name = "available_in_event")
	private Boolean availableInEvent;

	public HydramoduleEventService() {
		super();
	}

	public Integer getEventServiceId() {
		return eventServiceId;
	}

	public void setEventServiceId(Integer eventServiceId) {
		this.eventServiceId = eventServiceId;
	}

	public HydramoduleEvent getEvent() {
		return event;
	}

	public void setEvent(HydramoduleEvent event) {
		this.event = event;
	}

	public HydramoduleService getService() {
		return service;
	}

	public void setService(HydramoduleService service) {
		this.service = service;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Integer getId() {
		return eventServiceId;
	}

	@Override
	public void setId(Integer id) {
		eventServiceId = id;
	}

	public String getActualCost() {
		return actualCost;
	}

	public void setActualCost(String actualCost) {
		this.actualCost = actualCost;
	}

	public Boolean getPlannedForEvent() {
		return plannedForEvent;
	}

	public void setPlannedForEvent(Boolean plannedForEvent) {
		this.plannedForEvent = plannedForEvent;
	}

	public Boolean getAvailableInEvent() {
		return availableInEvent;
	}

	public void setAvailableInEvent(Boolean availableInEvent) {
		this.availableInEvent = availableInEvent;
	}

}
