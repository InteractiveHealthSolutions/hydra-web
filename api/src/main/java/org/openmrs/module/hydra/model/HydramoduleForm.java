package org.openmrs.module.hydra.model;

// Generated Jul 19, 2019 12:33:37 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.EncounterType;

@Entity
@Table(name = "hydramodule_form", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class HydramoduleForm extends BaseOpenmrsMetadata implements java.io.Serializable {

	private static final long serialVersionUID = -2668916537478185182L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "hydramodule_form_id", unique = true, nullable = false)
	private Integer hydramoduleFormId;

	@ManyToOne
	@JoinColumn(name = "encounter_type_id")
	private EncounterType encounterType;

	@OneToMany(mappedBy = "form")
	private List<HydramoduleFormField> formFields;

	@Column(name = "core")
	private Boolean core = false;

	@Column(name = "form_actions", length = 10000)
	private String formActions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hydramoduleForm")
	private Set<HydramoduleFormTagMap> hydramoduleFormTagMaps = new HashSet<HydramoduleFormTagMap>(0);

	@ManyToOne
	@JoinColumn(name = "component_id")
	HydramoduleComponent component;

	public HydramoduleForm() {
	}

	public Integer getHydramoduleFormId() {
		return this.hydramoduleFormId;
	}

	public void setHydramoduleFormId(Integer hydramoduleFormId) {
		this.hydramoduleFormId = hydramoduleFormId;
	}

	public EncounterType getEncounterType() {
		return encounterType;
	}

	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}

	public List<HydramoduleFormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<HydramoduleFormField> formFields) {
		this.formFields = formFields;
	}

	public Boolean getCore() {
		return core;
	}

	public void setCore(Boolean core) {
		this.core = core;
	}

	public String getFormActions() {
		return formActions;
	}

	public void setFormActions(String formActions) {
		this.formActions = formActions;
	}

	public Set<HydramoduleFormTagMap> getHydramoduleFormTagMaps() {
		return this.hydramoduleFormTagMaps;
	}

	public void setHydramoduleFormTagMaps(Set<HydramoduleFormTagMap> hydramoduleFormTagMaps) {
		this.hydramoduleFormTagMaps = hydramoduleFormTagMaps;
	}

	public HydramoduleComponent getComponent() {
		return component;
	}

	public void setComponent(HydramoduleComponent component) {
		this.component = component;
	}

	@Override
	public Integer getId() {
		return hydramoduleFormId;
	}

	@Override
	public void setId(Integer id) {
		this.hydramoduleFormId = id;
	}

	@Override
	public String toString() {
		return "HydramoduleForm [hydramoduleFormId=" + hydramoduleFormId + ", encounterType=" + encounterType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((core == null) ? 0 : core.hashCode());
		result = prime * result + ((encounterType == null) ? 0 : encounterType.hashCode());
		result = prime * result + ((hydramoduleFormId == null) ? 0 : hydramoduleFormId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HydramoduleForm other = (HydramoduleForm) obj;
		if (core == null) {
			if (other.core != null)
				return false;
		} else if (!core.equals(other.core))
			return false;
		if (encounterType == null) {
			if (other.encounterType != null)
				return false;
		} else if (!encounterType.equals(other.encounterType))
			return false;
		if (hydramoduleFormId == null) {
			if (other.hydramoduleFormId != null)
				return false;
		} else if (!hydramoduleFormId.equals(other.hydramoduleFormId))
			return false;
		return true;
	}

}
