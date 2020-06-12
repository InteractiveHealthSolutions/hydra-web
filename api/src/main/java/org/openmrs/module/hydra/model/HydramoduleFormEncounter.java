package org.openmrs.module.hydra.model;

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
import org.openmrs.Encounter;

@Entity
@Table(name = "hydramodule_form_encounter")
public class HydramoduleFormEncounter extends BaseOpenmrsObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "form_encounter_id", unique = true, nullable = false)
	private Integer formEncounterId;

	@ManyToOne
	@JoinColumn(name = "component_form_id")
	private HydramoduleComponentForm componentForm;

	@ManyToOne
	@JoinColumn(name = "encounter_id")
	private Encounter encounter;

	@Override
	public Integer getId() {
		return formEncounterId;
	}

	@Override
	public void setId(Integer id) {
		formEncounterId = id;
	}

	public Integer getFormEncounterId() {
		return formEncounterId;
	}

	public void setFormEncounterId(Integer forEncounterId) {
		this.formEncounterId = forEncounterId;
	}

	public HydramoduleComponentForm getComponentForm() {
		return componentForm;
	}

	public void setComponentForm(HydramoduleComponentForm componentForm) {
		this.componentForm = componentForm;
	}

	public Encounter getEncounter() {
		return encounter;
	}

	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
}
