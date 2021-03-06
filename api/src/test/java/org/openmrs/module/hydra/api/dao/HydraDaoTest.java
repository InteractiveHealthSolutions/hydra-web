/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api.dao;

import org.junit.Test;
import org.junit.Ignore;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.model.event_planner.HydraForm;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class HydraDaoTest extends BaseModuleContextSensitiveTest {

	@Autowired
	HydraDaoImpl dao;

	@Autowired
	UserService userService;

	@Test
	public void saveItem_shouldSaveAllPropertiesInDb() {
		// Given
		HydraForm item = new HydraForm();
		item.setActionsXML("<root><node>NO TEXT</node></root>");
		dao.saveForm(item);
		Context.flushSession();
		Context.clearSession();
		// Then
		HydraForm savedItem = dao.getHydraFormByUuid(item.getUuid());
		assertThat(savedItem, hasProperty("uuid", is(item.getUuid())));
		assertThat(savedItem, hasProperty("form", is(item.getForm())));
		assertThat(savedItem, hasProperty("actionsXml", is(item.getActionsXML())));
	}
}
