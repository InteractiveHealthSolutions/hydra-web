package org.openmrs.module.hydra.model.workflow;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.User;

@Entity
@Table(name = "hydramodule_participant", catalog = "hydra")
public class HydramoduleParticipant extends BaseOpenmrsMetadata {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1360228080219284596L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "participant_id", unique = true, nullable = false)
	private Integer participantId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "salary_type_id")
	private HydramoduleParticipantSalaryType salaryType;

	@Column(name = "salary_value")
	private String salaryValue;

	@Override
	public Integer getId() {
		return participantId;
	}

	@Override
	public void setId(Integer id) {
		this.participantId = id;

	}

	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HydramoduleParticipantSalaryType getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(HydramoduleParticipantSalaryType salaryType) {
		this.salaryType = salaryType;
	}

	public String getSalaryValue() {
		return salaryValue;
	}

	public void setSalaryValue(String salaryValue) {
		this.salaryValue = salaryValue;
	}
}
