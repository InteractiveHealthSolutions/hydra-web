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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;

@Entity
@Table(name = "hydramodule_patient_workflow", catalog = "hydra")
public class HydramodulePatientWorkflow extends BaseOpenmrsData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "patient_workflow_id", unique = true, nullable = false)
	private Integer patientWorkflowId;
	
	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private HydramoduleWorkflow workflow;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@Override
	public Integer getId() {
		return patientWorkflowId;
	}
	
	@Override
	public void setId(Integer id) {
		patientWorkflowId = id;
	}
	
	public Integer getPatientWorkflowId() {
		return patientWorkflowId;
	}
	
	public void setPatientWorkflowId(Integer patientWorkflowId) {
		this.patientWorkflowId = patientWorkflowId;
	}
	
	public HydramoduleWorkflow getWorkflow() {
		return workflow;
	}
	
	public void setWorkflow(HydramoduleWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
