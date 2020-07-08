package org.openmrs.module.hydra.model;

// Generated Jul 19, 2019 12:33:37 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;
import org.openmrs.FieldType;

@Entity
@Table(name = "hydramodule_field", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class HydramoduleField extends BaseOpenmrsMetadata implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -2668916537478185182L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "field_id", unique = true, nullable = false)
	private Integer fieldId;

	/* widgetType */
	@ManyToOne
	@JoinColumn(name = "field_type_id")
	private FieldType fieldType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "concept_id")
	private Concept concept;

	/* isAttribute */
	@Column(name = "table_name")
	private String tableName;

	/* conceptDataType */
	@Column(name = "attribute_name")
	private String attributeName;

	@Column(name = "default_value")
	private String defaultValue;

	@Transient
	private String parsedRule;

	@Column(name = "select_multiple")
	private Boolean selectMultiple = false;

	@OneToMany(mappedBy = "field",fetch = FetchType.EAGER)
	private Set<HydramoduleFieldAnswer> answers;

	@OneToMany(mappedBy = "field")
	private Set<HydramoduleFormField> formFields;

	@Column(name = "is_default")
	private Boolean isDefault = false;

	@Override
	public Integer getId() {
		return fieldId;
	}

	@Override
	public void setId(Integer id) {
		this.fieldId = id;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public Set<HydramoduleFormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(Set<HydramoduleFormField> formFields) {
		this.formFields = formFields;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getSelectMultiple() {
		return selectMultiple;
	}

	public void setSelectMultiple(Boolean selectMultiple) {
		this.selectMultiple = selectMultiple;
	}

	public Set<HydramoduleFieldAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<HydramoduleFieldAnswer> answers) {
		this.answers = answers;
	}

	public String getParsedRule() {
		return parsedRule;
	}

	public void setParsedRule(String parsedRule) {
		this.parsedRule = parsedRule;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result + ((fieldId == null) ? 0 : fieldId.hashCode());
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
		HydramoduleField other = (HydramoduleField) obj;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		if (fieldId == null) {
			if (other.fieldId != null)
				return false;
		} else if (!fieldId.equals(other.fieldId))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "HydramoduleField [fieldId=" + fieldId + ", fieldType=" + fieldType + ", concept=" + concept + ", tableName="
		        + tableName + ", attributeName=" + attributeName + ", defaultValue=" + defaultValue + ", selectMultiple="
		        + selectMultiple + "]";
	}

	@Override
	public HydramoduleField clone() throws CloneNotSupportedException {
		// A shallow copy because I do not need to change the inner objects
		return (HydramoduleField) super.clone();
	}

}
