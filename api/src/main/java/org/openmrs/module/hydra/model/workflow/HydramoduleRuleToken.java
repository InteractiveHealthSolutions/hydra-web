package org.openmrs.module.hydra.model.workflow;

// Generated Jul 19, 2019 12:33:37 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openmrs.BaseOpenmrsObject;

@Entity
@Table(name = "hydramodule_rule_token", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class HydramoduleRuleToken extends BaseOpenmrsObject implements java.io.Serializable {

	private static final long serialVersionUID = -2668916537478185182L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "token_id", unique = true, nullable = false)
	private Integer tokenId;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "value")
	private String value;

	@ManyToOne
	@JoinColumn(name = "rule_id")
	private HydramoduleFieldRule rule;

	@Override
	public Integer getId() {
		return tokenId;
	}

	@Override
	public void setId(Integer id) {
		this.tokenId = id;
	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public HydramoduleFieldRule getRule() {
		return rule;
	}

	public void setRule(HydramoduleFieldRule rule) {
		this.rule = rule;
	}
}
