package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsMetadata;

@Entity
@Table(name = "hydramodule_service_category", catalog = "hydra")
public class HydraModuleServiceCategory extends BaseOpenmrsMetadata implements java.io.Serializable {
	
	/**
	 * To keep track of object versions, may help in object storage
	 */
	private static final long serialVersionUID = 8415965401053481566L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "service_category_id", unique = true, nullable = false)
	private Integer serviceCategoryId;
	
	public Integer getServiceCategoryId() {
		return serviceCategoryId;
	}
	
	public void setServiceCategoryId(Integer serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}
	
	@Override
	public Integer getId() {
		return serviceCategoryId;
	}
	
	@Override
	public void setId(Integer id) {
		this.serviceCategoryId = id;
		
	}
}
