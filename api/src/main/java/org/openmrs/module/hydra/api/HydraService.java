/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hydra.HydraConfig;
import org.openmrs.module.hydra.HydraForm;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface HydraService extends OpenmrsService {

	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read only
	 * transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	HydraForm getItemByUuid(String uuid) throws APIException;

	/**
	 * Saves a form.
	 * 
	 * @param item
	 * @return
	 * @throws APIException
	 */
	@Authorized(HydraConfig.MODULE_PRIVILEGE)
	@Transactional
	HydraForm saveForm(HydraForm item) throws APIException;
}
