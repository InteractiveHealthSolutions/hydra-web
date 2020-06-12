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

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_service")
public class HydramoduleService extends BaseOpenmrsMetadata implements Serializable {

	/**
	 * To keep track of object versions, may help in object storage
	 */
	private static final long serialVersionUID = 2588114502971056722L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "service_id", unique = true, nullable = false)
	private Integer serviceId;

	@Column(name = "unit_cost")
	private String unitCost;

	@Column(name = "reference_id")
	private String referenceId;

	@ManyToOne
	@JoinColumn(name = "service_type_id")
	private HydramoduleServiceType serviceType;

	@ManyToOne
	@JoinColumn(name = "service_category_id")
	private HydraModuleServiceCategory serviceCategory;

	public String getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public HydramoduleServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(HydramoduleServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public HydraModuleServiceCategory getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(HydraModuleServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public Integer getId() {
		return serviceId;
	}

	@Override
	public void setId(Integer id) {
		serviceId = id;
	}
}
