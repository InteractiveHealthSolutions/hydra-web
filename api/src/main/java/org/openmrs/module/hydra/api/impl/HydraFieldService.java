package org.openmrs.module.hydra.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hydra.api.IHydraFieldService;
import org.openmrs.module.hydra.api.dao.IHydramoduleFieldDao;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleFieldAnswer;
import org.openmrs.module.hydra.model.HydramoduleFieldDTO;
import org.openmrs.module.hydra.model.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.HydramoduleRuleToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HydraFieldService implements IHydraFieldService {
	
	
	@Autowired
	private IHydramoduleFieldDao fieldDao;

	@Override
	public void setFieldDao(IHydramoduleFieldDao fieldDao) {
		this.fieldDao = fieldDao;
	}

	// Field
	@Override
	@Transactional
	public HydramoduleField saveField(HydramoduleFieldDTO dto) throws APIException {

		return fieldDao.saveField(dto);
	}

	@Override
	public List<HydramoduleFieldDTO> getFieldsByName(String name) throws APIException {
		List<HydramoduleField> fields = fieldDao.getAllFieldsByName(name);
		List<HydramoduleFieldDTO> fieldDTOs = new ArrayList();
		for (HydramoduleField f : fields) {
			HydramoduleFieldDTO dto = new HydramoduleFieldDTO();
			List<HydramoduleFieldAnswer> answers = fieldDao.getAllFieldAnswersByID(f);
			dto.setField(f);
			dto.setAnswers(answers);
			fieldDTOs.add(dto);
		}
		return fieldDTOs;
	}

	// HydramoduleField
	@Override
	@Transactional
	public HydramoduleField saveHydramoduleField(HydramoduleField service) throws APIException {
		return fieldDao.saveHydramoduleField(service);
	}

	@Override
	public List<HydramoduleField> getAllHydramoduleFields() throws APIException {
		return fieldDao.getAllHydramoduleFields();
	}

	@Override
	public HydramoduleField getHydramoduleField(String uuid) throws APIException {
		return fieldDao.getHydramoduleField(uuid);
	}

	@Override
	public List<HydramoduleField> getHydramoduleFieldsByName(String queryParam) {
		// TODO Auto-generated method stub
		return fieldDao.getAllFieldsByName(queryParam);
	}

	// HydramoduleFieldAnswer
    @Override
	@Transactional
	public HydramoduleFieldAnswer saveHydramoduleFieldAnswer(HydramoduleFieldAnswer service) throws APIException {
		return fieldDao.saveHydramoduleFieldAnswer(service);
	}

	@Override
	public List<HydramoduleFieldAnswer> getAllHydramoduleFieldAnswers(boolean voided) throws APIException {
		return fieldDao.getAllHydramoduleFieldAnswers(voided);
	}

	@Override
	public HydramoduleFieldAnswer getHydramoduleFieldAnswer(String uuid) throws APIException {
		return fieldDao.getHydramoduleFieldAnswer(uuid);
	}

	@Override
	@Transactional
	public HydramoduleFieldRule saveHydramoduleFieldRule(HydramoduleFieldRule service) throws APIException {
		return fieldDao.saveHydramoduleFieldRule(service);
	}

	@Override
	public List<HydramoduleFieldRule> getAllHydramoduleFieldRules(boolean voided) throws APIException {
		return fieldDao.getAllHydramoduleFieldRules(voided);
	}

	@Override
	public HydramoduleFieldRule getHydramoduleFieldRule(String uuid) throws APIException {
		return fieldDao.getHydramoduleFieldRule(uuid);
	}

	@Override
	@Transactional
	public HydramoduleRuleToken saveHydramoduleRuleToken(HydramoduleRuleToken service) throws APIException {
		return fieldDao.saveHydramoduleRuleToken(service);
	}

	@Override
	public List<HydramoduleRuleToken> getAllHydramoduleRuleTokens() throws APIException {
		return fieldDao.getAllHydramoduleRuleTokens();
	}

	@Override
	public HydramoduleRuleToken getHydramoduleRuleToken(String uuid) throws APIException {
		return fieldDao.getHydramoduleRuleToken(uuid);
	}


}
