/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api.impl;

import java.util.Set;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.model.event_planner.HydraForm;

public class HydraServiceImpl extends BaseOpenmrsService implements HydraService {

	HydraDao dao;

	UserService userService;

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(HydraDao dao) {
		this.dao = dao;
	}

	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public HydraForm getHydraFormByUuid(String uuid) throws APIException {
		return dao.getHydraFormByUuid(uuid);
	}

	@Override
	public HydraForm saveForm(HydraForm item) throws APIException {
		return dao.saveForm(item);
	}

	@Override
	public Set<HydraForm> getHydraFormsByTag(String tag) throws APIException {
		return dao.getHydraFormsByTag(tag);
	}

	@Override
	public HydraForm getHydraFormByEncounterName(String encunterName) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
}
