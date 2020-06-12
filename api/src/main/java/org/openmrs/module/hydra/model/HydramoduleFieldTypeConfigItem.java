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
import org.openmrs.FieldType;

@Deprecated
// @Entity
@Table(name = "hydramodule_field_type_config_item")
public class HydramoduleFieldTypeConfigItem extends BaseOpenmrsObject implements Serializable {

	private static final long serialVersionUID = 2829659626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "config_item_id", unique = true, nullable = false)
	private Integer configItemId;

	@Column(name = "item_name")
	private String itemName;

	@ManyToOne
	@JoinColumn(name = "field_type_id")
	private FieldType fieldType;

	@Override
	public Integer getId() {
		return configItemId;
	}

	@Override
	public void setId(Integer id) {
		this.configItemId = id;
	}

	public Integer getConfigItemId() {
		return configItemId;
	}

	public void setConfigItemId(Integer configItemId) {
		this.configItemId = configItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

}
