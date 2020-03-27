package org.openmrs.module.hydra.model.workflow;

// Generated Jul 19, 2019 12:33:37 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;
import org.openmrs.FieldType;

@Entity
@Table(name = "hydramodule_field_rule", catalog = "hydra", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class HydramoduleFieldRule extends BaseOpenmrsMetadata implements java.io.Serializable {

	private static final long serialVersionUID = -2668916537478185182L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rule_id", unique = true, nullable = false)
	private Integer ruleId;

	@Column(name = "action_name")
	private String actionName;

	@ManyToOne
	@JoinColumn(name = "form_id")
	private HydramoduleForm form;

	@ManyToOne
	@JoinColumn(name = "target_form_id")
	private HydramoduleForm targetForm;

	@ManyToOne
	@JoinColumn(name = "target_form_field_id")
	private HydramoduleFormField targetFormField;

	@ManyToOne
	@JoinColumn(name = "target_field_id")
	private HydramoduleField targetQuestion;

	@ManyToOne
	@JoinColumn(name = "target_field_answer_id")
	private HydramoduleFieldAnswer fieldAnswer;

	@OneToMany(mappedBy = "rule")
	private List<HydramoduleRuleToken> tokens;

	@Override
	public Integer getId() {
		return ruleId;
	}

	@Override
	public void setId(Integer id) {
		this.ruleId = id;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public HydramoduleForm getForm() {
		return form;
	}

	public void setForm(HydramoduleForm form) {
		this.form = form;
	}

	public HydramoduleForm getTargetForm() {
		return targetForm;
	}

	public void setTargetForm(HydramoduleForm targetForm) {
		this.targetForm = targetForm;
	}

	public HydramoduleField getTargetQuestion() {
		return targetQuestion;
	}

	public void setTargetQuestion(HydramoduleField targetQuestion) {
		this.targetQuestion = targetQuestion;
	}

	public List<HydramoduleRuleToken> getTokens() {
		return tokens;
	}

	public void setTokens(List<HydramoduleRuleToken> tokens) {
		this.tokens = tokens;
	}

	public HydramoduleFieldAnswer getFieldAnswer() {
		return fieldAnswer;
	}

	public void setFieldAnswer(HydramoduleFieldAnswer fieldAnswer) {
		this.fieldAnswer = fieldAnswer;
	}

}
