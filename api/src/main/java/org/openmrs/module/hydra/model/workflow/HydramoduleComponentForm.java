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

import org.hibernate.search.annotations.Field;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Retireable;
import org.openmrs.User;

import freemarker.core.ReturnInstruction.Return;

@Entity
@Table(name = "hydramodule_component_form", catalog = "hydra")
public class HydramoduleComponentForm extends BaseOpenmrsObject implements Retireable, Serializable {
	
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
	
	@Column(name = "retired", nullable = false)
	@Field
	private Boolean retired = Boolean.FALSE;
	
	@Column(name = "date_retired")
	private Date dateRetired;
	
	@ManyToOne
	@JoinColumn(name = "retired_by")
	private User retiredBy;
	
	@Column(name = "retire_reason", length = 255)
	private String retireReason;
	
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
	
	@Override
	public Boolean isRetired() {
		return retired;
	}
	
	@Override
	public Boolean getRetired() {
		return retired;
	}
	
	@Override
	public void setRetired(Boolean retired) {
		this.retired = retired;
	}
	
	@Override
	public User getRetiredBy() {
		return retiredBy;
	}
	
	@Override
	public void setRetiredBy(User retiredBy) {
		this.retiredBy = retiredBy;
	}
	
	@Override
	public Date getDateRetired() {
		return dateRetired;
	}
	
	@Override
	public void setDateRetired(Date dateRetired) {
		this.dateRetired = dateRetired;
	}
	
	@Override
	public String getRetireReason() {
		return retireReason;
	}
	
	@Override
	public void setRetireReason(String retireReason) {
		this.retireReason = retireReason;
	}
	
}
