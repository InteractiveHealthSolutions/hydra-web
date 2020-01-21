package org.openmrs.module.hydra.model.workflow;

// Generated Jul 19, 2019 12:33:37 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Form;

@Entity
@Table(name = "hydramodule_form", catalog = "hydra", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class HydramoduleForm extends BaseOpenmrsMetadata implements java.io.Serializable {
	
	private static final long serialVersionUID = -2668916537478185182L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "hydramodule_form_id", unique = true, nullable = false)
	private Integer hydramoduleFormId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "form_id")
	private Form form;
	
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
	
	public Form getForm() {
		return this.form;
	}
	
	public void setForm(Form form) {
		this.form = form;
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
}
