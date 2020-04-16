package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;
import org.openmrs.Field;
import org.openmrs.FieldAnswer;

// @Entity
// @Table(name = "hydramodule_asset", catalog = "hydra")
public class HydramoduleFieldDTO extends BaseOpenmrsObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "field_id", unique = true, nullable = false)
	private Integer fieldId;
	
	@Column(name = "field")
	HydramoduleField field;
	
	@Column(name = "concept")
	Concept concept;
	
	@Transient
	private List<HydramoduleFieldAnswer> answers;
	
	public Integer getFieldId() {
		return fieldId;
	}
	
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	
	public HydramoduleField getField() {
		return field;
	}
	
	public void setField(HydramoduleField field) {
		this.field = field;
	}
	
	@Override
	public Integer getId() {
		return fieldId;
	}
	
	@Override
	public void setId(Integer id) {
		fieldId = id;
		
	}
	
	public List<HydramoduleFieldAnswer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(List<HydramoduleFieldAnswer> answers) {
		this.answers = answers;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
}
