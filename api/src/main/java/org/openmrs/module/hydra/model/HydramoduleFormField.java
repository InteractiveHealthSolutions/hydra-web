package org.openmrs.module.hydra.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_form_field")
public class HydramoduleFormField extends BaseOpenmrsMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2824539626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "form_field_id", unique = true, nullable = false)
	private Integer formFieldId;

	@Column(name = "display_order")
	private Integer displayOrder;

	@Column(name = "min_occurrence")
	private Integer minOccurrence;

	@Column(name = "max_occurrence")
	private Integer maxOccurrence;

	@Column(name = "min_value")
	private Integer minValue;

	@Column(name = "max_value")
	private Integer maxValue;

	@Column(name = "min_length")
	private Integer minLength;

	@Column(name = "max_length")
	private Integer maxLength;

	@Column(name = "min_selections")
	private Integer minSelections;

	@Column(name = "allow_future_date")
	private Boolean allowFutureDate;

	@Column(name = "allow_past_date")
	private Boolean allowPastDate;

	@Column(name = "display_text")
	private String displayText;

	@Column(name = "error_message")
	private String errorMessage;

	@Column(name = "scoreable")
	private Boolean scoreable;

	@Column(name = "allow_decimal")
	private Boolean allowDecimal;

	@Column(name = "mandatory")
	private Boolean mandatory;

	@Column(name = "disabled")
	private Boolean disabled;

	@Column(name = "default_value")
	private String defaultValue;

	/*
	 * @Column(name = "disabled") private String disabled;
	 */

	@Column(name = "regix")
	private String regix;

	@Column(name = "characters")
	private String characters;

	@Column(name = "score_field")
	private Boolean scoreField;

	// TODO use attribute while refactoring code, saving time right now - as always
	// this needs to be done as soon as possible
	@Column(name = "create_patient")
	private Boolean createPatient;

	@Column(name = "is_core")
	private Boolean isCore;

	@ManyToOne
	@JoinColumn(name = "field_id")
	private HydramoduleField field;

	@ManyToOne
	@JoinColumn(name = "form_id")
	private HydramoduleForm form;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private HydramoduleFormField parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private HydramoduleFormFieldGroup group;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY/* , cascade = CascadeType.ALL */)
	// TODO need to explore why
	// its not working the
	// expected way
	private List<HydramoduleFormField> children;

	@OneToMany(mappedBy = "targetFormField", fetch = FetchType.LAZY)
	private List<HydramoduleFieldRule> rules;

	@Transient
	private List<HydramoduleFieldDTO> fieldData;

	@Override
	public Integer getId() {
		return formFieldId;
	}

	@Override
	public void setId(Integer id) {
		formFieldId = id;
	}

	public Integer getFormFieldId() {
		return formFieldId;
	}

	public void setFormFieldId(Integer formFieldId) {
		this.formFieldId = formFieldId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getMinOccurrence() {
		return minOccurrence;
	}

	public void setMinOccurrence(Integer minOccurrence) {
		this.minOccurrence = minOccurrence;
	}

	public Integer getMaxOccurrence() {
		return maxOccurrence;
	}

	public void setMaxOccurrence(Integer maxOccurrence) {
		this.maxOccurrence = maxOccurrence;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinSelections() {
		return minSelections;
	}

	public void setMinSelections(Integer minSelections) {
		this.minSelections = minSelections;
	}

	public Boolean getAllowFutureDate() {
		return allowFutureDate;
	}

	public void setAllowFutureDate(Boolean allowFutureDate) {
		this.allowFutureDate = allowFutureDate;
	}

	public Boolean getAllowPastDate() {
		return allowPastDate;
	}

	public void setAllowPastDate(Boolean allowPastDate) {
		this.allowPastDate = allowPastDate;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRegix() {
		return regix;
	}

	public void setRegix(String regix) {
		this.regix = regix;
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public HydramoduleField getField() {
		return field;
	}

	public void setField(HydramoduleField field) {
		this.field = field;
	}

	public HydramoduleForm getForm() {
		return form;
	}

	public void setForm(HydramoduleForm form) {
		this.form = form;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getScoreable() {
		return scoreable;
	}

	public void setScoreable(Boolean scoreable) {
		this.scoreable = scoreable;
	}

	public Boolean getAllowDecimal() {
		return allowDecimal;
	}

	public void setAllowDecimal(Boolean allowDecimal) {
		this.allowDecimal = allowDecimal;
	}

	public List<HydramoduleFieldDTO> getFieldData() {
		return fieldData;
	}

	public void setFieldData(List<HydramoduleFieldDTO> fieldData) {
		this.fieldData = fieldData;
	}

	public Boolean getScoreField() {
		return scoreField;
	}

	public void setScoreField(Boolean scoreField) {
		this.scoreField = scoreField;
	}

	public Boolean getIsCore() {
		return isCore;
	}

	public void setIsCore(Boolean isCore) {
		this.isCore = isCore;
	}

	public HydramoduleFormField getParent() {
		return parent;
	}

	public void setParent(HydramoduleFormField parent) {
		this.parent = parent;
	}

	public HydramoduleFormFieldGroup getGroup() {
		return group;
	}

	public void setGroup(HydramoduleFormFieldGroup group) {
		this.group = group;
	}

	public List<HydramoduleFormField> getChildren() {
		return children;
	}

	public void setChildren(List<HydramoduleFormField> children) {
		this.children = children;
	}

	public Boolean getCreatePatient() {
		return createPatient;
	}

	public void setCreatePatient(Boolean createPatient) {
		this.createPatient = createPatient;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public List<HydramoduleFieldRule> getRules() {
		return rules;
	}

	public void setRules(List<HydramoduleFieldRule> rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		return "HydramoduleFormField [formFieldId=" + formFieldId + ", displayOrder=" + displayOrder + ", field=" + field
		        + ", form=" + form + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((form == null) ? 0 : form.hashCode());
		result = prime * result + ((formFieldId == null) ? 0 : formFieldId.hashCode());
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
		HydramoduleFormField other = (HydramoduleFormField) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (form == null) {
			if (other.form != null)
				return false;
		} else if (!form.equals(other.form))
			return false;
		if (formFieldId == null) {
			if (other.formFieldId != null)
				return false;
		} else if (!formFieldId.equals(other.formFieldId))
			return false;
		return true;
	}

}
