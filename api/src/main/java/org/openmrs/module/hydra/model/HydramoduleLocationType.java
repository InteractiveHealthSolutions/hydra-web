package org.openmrs.module.hydra.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_location_type", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class HydramoduleLocationType extends BaseOpenmrsMetadata implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -2158916537478185182L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "location_type_id", unique = true, nullable = false)
	private Integer locationTypeId;

	@Column(name = "location_type_level")
	private Integer locationTypeLevel;

	public void setLocationTypeId(Integer loctionTypeId) {
		this.locationTypeId = loctionTypeId;
	}

	public void setLocationTypeLevel(Integer locationTypeLevel) {
		this.locationTypeLevel = locationTypeLevel;
	}

	public Integer getLocationTypeLevel() {
		return locationTypeLevel;
	}

	public Integer getLocationTypeId() {
		return locationTypeId;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

}
