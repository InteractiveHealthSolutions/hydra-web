package org.openmrs.module.hydra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Program;

@Entity
public class HydraForm extends BaseOpenmrsData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3960210059878434425L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer hydraModuleFormId;

	@ManyToOne
	@JoinColumn(name = "form_id")
	private Form form;

	@OneToOne
	@JoinColumn(name = "program_id")
	private Program program;

	@ManyToOne
	@JoinColumn(name = "workflow_concept_id")
	private Concept workflow;

	@ManyToOne
	@JoinColumn(name = "component_concept_id")
	private Concept component;

	@ManyToOne
	@JoinColumn(name = "phase_concept_id")
	private Concept phase;

	@Column(name = "actions_xml", nullable = false)
	private String actionsXML;

	@Override
	public Integer getId() {
		return hydraModuleFormId;
	}

	@Override
	public void setId(Integer id) {
		this.hydraModuleFormId = id;
	}

	/**
	 * @return the hydraModuleFormId
	 */
	public Integer getHydraModuleFormId() {
		return hydraModuleFormId;
	}

	/**
	 * @param hydraModuleFormId the hydraModuleFormId to set
	 */
	public void setHydraModuleFormId(Integer hydraModuleFormId) {
		this.hydraModuleFormId = hydraModuleFormId;
	}

	/**
	 * @return the form
	 */
	public Form getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(Form form) {
		this.form = form;
	}

	/**
	 * @return the program
	 */
	public Program getProgram() {
		return program;
	}

	/**
	 * @param program the program to set
	 */
	public void setProgram(Program program) {
		this.program = program;
	}

	/**
	 * @return the workflow
	 */
	public Concept getWorkflow() {
		return workflow;
	}

	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(Concept workflow) {
		this.workflow = workflow;
	}

	/**
	 * @return the component
	 */
	public Concept getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(Concept component) {
		this.component = component;
	}

	/**
	 * @return the phase
	 */
	public Concept getPhase() {
		return phase;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(Concept phase) {
		this.phase = phase;
	}

	/**
	 * @return the actionsXML
	 */
	public String getActionsXML() {
		return actionsXML;
	}

	/**
	 * @param actionsXML the actionsXML to set
	 */
	public void setActionsXML(String actionsXML) {
		this.actionsXML = actionsXML;
	}

}
