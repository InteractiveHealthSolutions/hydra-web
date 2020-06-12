package org.openmrs.module.hydra.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_participant_salary_type")
public class HydramoduleParticipantSalaryType extends BaseOpenmrsMetadata {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825439626685522357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "salary_type_id", unique = true, nullable = false)
	private Integer salaryTypeId;

	@Override
	public Integer getId() {
		return salaryTypeId;
	}

	@Override
	public void setId(Integer id) {
		this.salaryTypeId = id;
	}

	public Integer getSalaryTypeId() {
		return salaryTypeId;
	}

	public void setSalaryTypeId(Integer salaryTypeId) {
		this.salaryTypeId = salaryTypeId;
	}
}
