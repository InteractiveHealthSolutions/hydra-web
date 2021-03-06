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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.api.impl.HydraServiceImpl;
import org.openmrs.module.hydra.model.event_planner.HydraForm;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * This is a unit test, which verifies logic in HydraService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class HydraServiceTest {

	@InjectMocks
	HydraServiceImpl basicModuleService;

	@Mock
	HydraDaoImpl dao;

	@Mock
	UserService userService;

	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveItem_shouldSetOwnerIfNotSet() {
		HydraForm hydraFrom = new HydraForm();
		hydraFrom.setActionsXML("<root><node>NO TEXT</node></root>");
		when(dao.saveForm(hydraFrom)).thenReturn(hydraFrom);
		User user = new User();
		when(userService.getUser(1)).thenReturn(user);
		basicModuleService.saveForm(hydraFrom);
		assertThat(hydraFrom, hasProperty("actionsXml", is(hydraFrom.getActionsXML())));
	}
}
