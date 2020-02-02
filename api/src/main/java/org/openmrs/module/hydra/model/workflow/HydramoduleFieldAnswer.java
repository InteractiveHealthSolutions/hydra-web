package org.openmrs.module.hydra.model.workflow;

// Generated Jul 19, 2019 12:33:37 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;
import org.openmrs.Field;
import org.openmrs.FieldAnswer;

@Entity
@Table(name = "hydramodule_field_answer", catalog = "hydra", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class HydramoduleFieldAnswer extends BaseOpenmrsObject implements java.io.Serializable {

	private static final long serialVersionUID = -2668916537478185182L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "field_answer_id", unique = true, nullable = false)
	private Integer fieldAnswerId;

	@ManyToOne
	@JoinColumn(name = "concept_id")
	private Concept concept;

	@ManyToOne
	@JoinColumn(name = "field_id")
	private HydramoduleField field;

	@Override
	public Integer getId() {
		return fieldAnswerId;
	}

	@Override
	public void setId(Integer id) {
		this.fieldAnswerId = id;
	}

	public Integer getFieldAnswerId() {
		return fieldAnswerId;
	}

	public void setFieldAnswerId(Integer fieldAnswerId) {
		this.fieldAnswerId = fieldAnswerId;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public HydramoduleField getField() {
		return field;
	}

	public void setField(HydramoduleField field) {
		this.field = field;
	}
}
