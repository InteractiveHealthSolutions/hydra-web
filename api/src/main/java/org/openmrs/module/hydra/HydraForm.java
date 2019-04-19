/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jdom.Element;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Program;

/**
 * Please note that a corresponding table schema must be created in liquibase.xml.
 */
// Uncomment 2 lines below if you want to make the Item class persistable, see
// also HydraDaoTest and liquibase.xml
@Entity(name = "hydra.HydraForm")
@Table(name = "hydramodule_form")
public class HydraForm extends BaseOpenmrsData {

	private static final long serialVersionUID = -7693869863402476051L;

	@Id
	@GeneratedValue
	@Column(name = "hydramodule_form_id")
	private Integer hydraFormId;

	@ManyToOne
	@JoinColumn(name = "form_id")
	private Form form;

	@ManyToOne
	@JoinColumn(name = "program_id")
	private Program program;

	@ManyToOne
	@JoinColumn(name = "workflow_concept_id")
	private Concept workflowConcept;

	@ManyToOne
	@JoinColumn(name = "phase_concept_id")
	private Concept phaseConcept;

	@ManyToOne
	@JoinColumn(name = "component_concept_id")
	private Concept componentConcept;

	@Basic
	@Column(name = "actions_xml", length = 255)
	private String actionsXml;

	@Override
	public Integer getId() {
		return hydraFormId;
	}

	@Override
	public void setId(Integer id) {
		this.hydraFormId = id;
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
	 * @return the workflowConcept
	 */
	public Concept getWorkflowConcept() {
		return workflowConcept;
	}

	/**
	 * @param workflowConcept the workflowConcept to set
	 */
	public void setWorkflowConcept(Concept workflowConcept) {
		this.workflowConcept = workflowConcept;
	}

	/**
	 * @return the phaseConcept
	 */
	public Concept getPhaseConcept() {
		return phaseConcept;
	}

	/**
	 * @param phaseConcept the phaseConcept to set
	 */
	public void setPhaseConcept(Concept phaseConcept) {
		this.phaseConcept = phaseConcept;
	}

	/**
	 * @return the componentConcept
	 */
	public Concept getComponentConcept() {
		return componentConcept;
	}

	/**
	 * @param componentConcept the componentConcept to set
	 */
	public void setComponentConcept(Concept componentConcept) {
		this.componentConcept = componentConcept;
	}

	/**
	 * @return the actionsXml
	 */
	public String getActionsXml() {
		return actionsXml;
	}

	/**
	 * @param actionsXml the actionsXml to set
	 */
	public void setActionsXml(String actionsXml) {
		this.actionsXml = actionsXml;
	}

	/**
	 * Converts XML object into string
	 * 
	 * @param xml
	 */
	public void setActionsXml(Element xml) {
		setActionsXml(xml.toString());
	}
}
