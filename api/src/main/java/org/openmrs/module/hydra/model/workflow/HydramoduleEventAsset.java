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
@Table(name = "hydramodule_event_asset")
public class HydramoduleEventAsset extends BaseOpenmrsData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18035890351L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_asset_id", unique = true, nullable = false)
	private Integer eventAssetId;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private HydramoduleEvent event;

	@ManyToOne
	@JoinColumn(name = "asset_id")
	private HydramoduleAsset asset;

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

	public HydramoduleEventAsset() {
		super();
	}

	public Integer getEventAssetId() {
		return eventAssetId;
	}

	public void setEventAssetId(Integer eventAssetId) {
		this.eventAssetId = eventAssetId;
	}

	public HydramoduleEvent getEvent() {
		return event;
	}

	public void setEvent(HydramoduleEvent event) {
		this.event = event;
	}

	public HydramoduleAsset getAsset() {
		return asset;
	}

	public void setAsset(HydramoduleAsset asset) {
		this.asset = asset;
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
		return eventAssetId;
	}

	@Override
	public void setId(Integer id) {
		eventAssetId = id;
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
