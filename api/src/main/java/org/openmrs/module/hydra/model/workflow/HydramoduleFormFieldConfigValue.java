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

import org.openmrs.BaseOpenmrsMetadata;

@Deprecated
// @Entity
@Table(name = "hydramodule_form_field_config_value")
public class HydramoduleFormFieldConfigValue extends BaseOpenmrsMetadata implements Serializable {

	private static final long serialVersionUID = 2825439626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "config_value_id", unique = true, nullable = false)
	private Integer configValueId;

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

	@Column(name = "mandatory")
	private Boolean mandatory;

	@Column(name = "allow_decimal")
	private Boolean allowDecimal;

	@Column(name = "error_message")
	private String errorMessage;

	@Column(name = "regix")
	private String regix;

	@Column(name = "characters")
	private String characters;

	@ManyToOne
	@JoinColumn(name = "config_item_id")
	private HydramoduleFieldTypeConfigItem configItem;

	@ManyToOne
	@JoinColumn(name = "form_field_id")
	private HydramoduleFormField formField;

	@Override
	public Integer getId() {
		return configValueId;
	}

	@Override
	public void setId(Integer id) {
		this.configValueId = id;
	}

	public Integer getConfigValueId() {
		return configValueId;
	}

	public void setConfigValueId(Integer configValueId) {
		this.configValueId = configValueId;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getAllowDecimal() {
		return allowDecimal;
	}

	public void setAllowDecimal(Boolean allowDecimal) {
		this.allowDecimal = allowDecimal;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
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

	public HydramoduleFieldTypeConfigItem getConfigItem() {
		return configItem;
	}

	public void setConfigItem(HydramoduleFieldTypeConfigItem configItem) {
		this.configItem = configItem;
	}

	public HydramoduleFormField getFormField() {
		return formField;
	}

	public void setFormField(HydramoduleFormField formField) {
		this.formField = formField;
	}
}
