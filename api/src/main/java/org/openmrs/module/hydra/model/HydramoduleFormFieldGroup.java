package org.openmrs.module.hydra.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsObject;

@Entity
@Table(name = "hydramodule_form_field_group")
public class HydramoduleFormFieldGroup extends BaseOpenmrsObject implements Serializable {

	private static final long serialVersionUID = 2829659626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "group_id", unique = true, nullable = false)
	private Integer groupId;

	@Column(name = "group_name")
	private String groupName;

	@Override
	public Integer getId() {
		return groupId;
	}

	public void setId(Integer id) {
		this.groupId = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
