package org.openmrs.module.hydra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
public class HydraFormTag extends BaseOpenmrsMetadata {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "locale")
	private String locale;

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;

	}

}
