package org.openmrs.module.hydra.model.workflow;

// Generated Jul 19, 2019 12:33:37 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

/**
 * HydramodulePhaseComponents generated by hbm2java
 */
@Entity
@Table(name = "hydramodule_phase_components", catalog = "hydra")
public class HydramodulePhaseComponents extends BaseOpenmrsObject implements java.io.Serializable {

	private static final long serialVersionUID = -1558943664233602349L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_id", nullable = false)
	@JsonBackReference
	private HydramoduleComponent hydramoduleComponent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "phase_id", nullable = false)
	@JsonBackReference
	private HydramodulePhase hydramodulePhase;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id", nullable = false)
	@JsonBackReference
	private HydramoduleWorkflow hydramoduleWorkflow;

	@Column(name = "display_order")
	private int displayOrder;

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public HydramodulePhaseComponents() {
	}

	public HydramodulePhaseComponents(HydramoduleComponent hydramoduleComponent, HydramodulePhase hydramodulePhase) {
		this.hydramoduleComponent = hydramoduleComponent;
		this.hydramodulePhase = hydramodulePhase;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HydramoduleComponent getHydramoduleComponent() {
		return this.hydramoduleComponent;
	}

	public void setHydramoduleComponent(HydramoduleComponent hydramoduleComponent) {
		this.hydramoduleComponent = hydramoduleComponent;
	}

	public HydramodulePhase getHydramodulePhase() {
		return this.hydramodulePhase;
	}

	public void setHydramodulePhase(HydramodulePhase hydramodulePhase) {
		this.hydramodulePhase = hydramodulePhase;
	}

	public HydramoduleWorkflow getHydramoduleWorkflow() {
		return this.hydramoduleWorkflow;
	}

	public void setHydramoduleWorkflow(HydramoduleWorkflow hydramoduleWorkflow) {
		this.hydramoduleWorkflow = hydramoduleWorkflow;
	}

	public String getPhaseUUID() {
		return hydramodulePhase.getUuid();
	}

	public String getComponentUUID() {
		return hydramoduleComponent.getUuid();
	}

}
