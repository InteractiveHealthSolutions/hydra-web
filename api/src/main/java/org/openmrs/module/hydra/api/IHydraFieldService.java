package org.openmrs.module.hydra.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.dao.IHydramoduleFieldDao;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleFieldAnswer;
import org.openmrs.module.hydra.model.HydramoduleFieldDTO;
import org.openmrs.module.hydra.model.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.HydramoduleRuleToken;
import org.springframework.transaction.annotation.Transactional;

public interface IHydraFieldService {

	void setFieldDao(IHydramoduleFieldDao fieldDao);

	// Field
	HydramoduleField saveField(HydramoduleFieldDTO dto) throws APIException;

	List<HydramoduleFieldDTO> getFieldsByName(String name) throws APIException;

	// HydramoduleField
	HydramoduleField saveHydramoduleField(HydramoduleField service) throws APIException;

	List<HydramoduleField> getAllHydramoduleFields() throws APIException;

	HydramoduleField getHydramoduleField(String uuid) throws APIException;

	List<HydramoduleField> getHydramoduleFieldsByName(String queryParam);

	// HydramoduleFieldAnswer
	HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer service) throws APIException;

	List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean voided) throws APIException;

	HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid) throws APIException;

	HydramoduleFieldRule saveHydramoduleFieldRule(HydramoduleFieldRule service) throws APIException;

	List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean voided) throws APIException;

	HydramoduleFieldRule getHydramoduleFieldRule(String uuid) throws APIException;

	HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken service) throws APIException;

	List<HydramoduleRuleToken> getAllHydramoduleRuleTokens() throws APIException;

	HydramoduleRuleToken getHydramoduleRuleToken(String uuid) throws APIException;

}