package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsObject;

@Entity
@Table(name = "hydramodule_component_form", catalog = "hydra")
public class HydramoduleComponentForm extends BaseOpenmrsObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2824539626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "component_form_id", unique = true, nullable = false)
	private Integer componentFormId;

	@Column(name = "display_order")
	private Integer displayOrder;

	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private HydramoduleWorkflow workflow;

	@ManyToOne
	@JoinColumn(name = "phase_id")
	private HydramodulePhase phase;

	@ManyToOne
	@JoinColumn(name = "component_id")
	private HydramoduleComponent component;

	@ManyToOne
	@JoinColumn(name = "form_id")
	private HydramoduleForm form;

	@Override
	public Integer getId() {
		return componentFormId;
	}

	@Override
	public void setId(Integer id) {
		componentFormId = id;
	}

	public Integer getComponentFormId() {
		return componentFormId;
	}

	public void setComponentFormId(Integer componentFormId) {
		this.componentFormId = componentFormId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public HydramoduleComponent getComponent() {
		return component;
	}

	public void setComponent(HydramoduleComponent component) {
		this.component = component;
	}

	public HydramoduleForm getForm() {
		return form;
	}

	public void setForm(HydramoduleForm form) {
		this.form = form;
	}

	public HydramoduleWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(HydramoduleWorkflow workflow) {
		this.workflow = workflow;
	}

	public HydramodulePhase getPhase() {
		return phase;
	}

	public void setPhase(HydramodulePhase phase) {
		this.phase = phase;
	}

}
