package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_form_field", catalog = "hydra")
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

	@Column(name = "default_value")
	private String defaultValue;

	/*
	 * @Column(name = "disabled") private String disabled;
	 */

	@Column(name = "regix")
	private String regix;

	@Column(name = "characters")
	private String characters;

	@ManyToOne
	@JoinColumn(name = "field_id")
	private HydramoduleField field;

	@ManyToOne
	@JoinColumn(name = "form_id")
	private HydramoduleForm form;

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

}
