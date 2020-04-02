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
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

@Entity
@Table(name = "hydramodule_user_workflow", catalog = "hydra")
public class HydramoduleUserWorkflow extends BaseOpenmrsData implements Serializable {

	private static final long serialVersionUID = 8825939626685522359L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_workflow_id", unique = true, nullable = false)
	private Integer userWorkflowId;

	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private HydramoduleWorkflow workflow;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Integer getUserWorkflowId() {
		return userWorkflowId;
	}

	public void setUserWorkflowId(Integer userWorkflowId) {
		this.userWorkflowId = userWorkflowId;
	}

	public HydramoduleWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(HydramoduleWorkflow workflow) {
		this.workflow = workflow;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Integer getId() {
		return userWorkflowId;
	}

	@Override
	public void setId(Integer id) {
		this.userWorkflowId = id;
	}

}
