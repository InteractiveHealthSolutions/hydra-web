package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;

@Entity
@Table(name = "hydramodule_service_type")
public class HydramoduleServiceType extends BaseOpenmrsMetadata implements java.io.Serializable {

	/**
	 * To keep track of object versions, may help in object storage
	 */
	private static final long serialVersionUID = 8415965501353491566L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "service_type_id", unique = true, nullable = false)
	private Integer serviceTypeId;

	@ManyToOne
	@JoinColumn(name = "encounter_type_id")
	private EncounterType encounterType;

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return serviceTypeId;
	}

	@Override
	public void setId(Integer id) {
		serviceTypeId = id;
	}

	public EncounterType getEncounterType() {
		return encounterType;
	}

	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}

}
