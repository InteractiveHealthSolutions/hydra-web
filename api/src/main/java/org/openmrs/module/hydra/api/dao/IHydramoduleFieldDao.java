package org.openmrs.module.hydra.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleFieldAnswer;
import org.openmrs.module.hydra.model.HydramoduleFieldDTO;
import org.openmrs.module.hydra.model.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.model.HydramoduleRuleToken;
import org.springframework.stereotype.Repository;

public interface IHydramoduleFieldDao {

	// Fields
	HydramoduleField saveField(HydramoduleFieldDTO fieldDTO);

	List<HydramoduleFieldAnswer> getAllFieldAnswersByID(HydramoduleField fieldId);

	List<HydramoduleField> getAllFieldsByName(String name);

	// HydramoduleField
	HydramoduleField saveHydramoduleField(HydramoduleField field);

	HydramoduleField getHydramoduleField(String uuid);

	HydramoduleFormField getHydramoduleFormField(String formUUID, String fieldUUID);

	List<HydramoduleField> getAllHydramoduleFields();

	// HydramoduleFieldAnswer
	HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer serviceType);

	HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid);

	List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean retired);

	void deleteFieldRules(List<HydramoduleFieldRule> rules);

	HydramoduleFieldRule saveHydramoduleFieldRule(HydramoduleFieldRule fieldRule);

	HydramoduleFieldRule getHydramoduleFieldRule(String uuid);

	/**
	 * Does same thing the wrong way.
	 * 
	 * @deprecated use {@link #getHydramoduleFieldRuleByTargetFormField()} instead.
	 */
	List<HydramoduleFieldRule> getHydramoduleFieldRuleByTargetField(HydramoduleField field);

	List<HydramoduleFieldRule> getHydramoduleFieldRuleByTargetFormField(HydramoduleFormField field);

	List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean retired);

	void deleteRuleTokensByFieldRuleId(Integer fieldRuleId);

	HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken serviceType);

	HydramoduleRuleToken getHydramoduleRuleToken(String uuid);

	List<HydramoduleRuleToken> getAllHydramoduleRuleTokens();

	HydramoduleFieldAnswer saveHydramoduleDTOFieldAnswer(HydramoduleFieldAnswer fieldAnswer);

	void deleteFieldAnswers(int fieldId);

}
