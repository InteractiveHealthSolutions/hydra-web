/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Concept;
import org.openmrs.User;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.ValidationException;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraBaseTest;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.api.dao.HydraWorkflowDaoTest;
import org.openmrs.module.hydra.api.impl.HydraServiceImpl;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflowPhases;

/**
 * This is a unit test, which verifies logic in CommonLabTestService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class HydraServiceTest extends HydraBaseTest {

	@InjectMocks
	HydraServiceImpl service;

	@Mock
	HydraDaoImpl workflowDao;

	@Before
	public void initMockito() throws Exception {
		super.initTestData();
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void resetUser() throws Exception {
		User admin = (User) sessionFactory.getCurrentSession().get(User.class, 1);
		Context.becomeUser(admin.getSystemId());
	}

	@Test
	public void setupService() {
		assertNotNull(Context.getService(HydraService.class));
	}

	/* Test Privileges */
	public void shouldGetWithPrivilege() {
		Context.becomeUser(doby.getSystemId());
		service.getAllPhases();
	}

	@Test(expected = APIAuthenticationException.class)
	public void shouldNOTGetWithoutPrivilege() {
		Context.becomeUser(doby.getSystemId());
		service.getAllWorkflows();
	}

	@Test
	public void shouldCreateWithPrivilege() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldNOTCreateWithoutPrivilege() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUpdateWithPrivilege() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldNOTUpdateWithoutPrivilege() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldDeleteWithPrivilege() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldNOTDeleteWithoutPrivilege() {
		fail("Not yet implemented");
	}

	/* Workflow */
	@Test
	public void shouldGetHydramoduleWorkflows() {
		List<HydramoduleWorkflow> expected = Arrays.asList(hogwartzWorkflow);
		when(workflowDao.getAllWorkflows()).thenReturn(expected);
		List<HydramoduleWorkflow> list = service.getAllWorkflows();
		assertEquals(list.size(), expected.size());
		for (HydramoduleWorkflow item : list) {
			assertFalse(item.getRetired());
		}
	}

	@Test
	public void shouldGetActiveHydramoduleWorkflows() {
		fail("Not yet implemented");
	}

	/**
	 * see {@link HydraWorkflowDaoTest}
	 */
	@Test
	public void shouldGetHydramoduleWorkflowById() {
		fail("Not yet implemented");
	}

	/**
	 * see {@link HydraWorkflowDaoTest}
	 */
	@Test
	public void shouldGetHydramoduleWorkflowByName() {
		fail("Not yet implemented");
	}

	/**
	 * see {@link HydraWorkflowDaoTest}
	 */
	@Test
	public void shouldGetHydramoduleWorkflowByUuid() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldCreateHydramoduleWorkflow() {
		when(workflowDao.saveWorkflow(any(HydramoduleWorkflow.class))).thenReturn(hogwartzWorkflow);
		HydramoduleWorkflow saved = service.saveWorkflow(hogwartzWorkflow);
		assertNotNull(saved.getDateCreated());
		verify(workflowDao, times(1)).saveWorkflow(any(HydramoduleWorkflow.class));
		verifyNoMoreInteractions(workflowDao);
	}

	@Test(expected = ValidationException.class)
	public void shouldNOTCreateHydramoduleWorkflowWithInvalidConcept() {
		Concept yesConcept = Context.getConceptService().getConcept(7);
		hogwartzWorkflow.setConcept(yesConcept);
		service.saveWorkflow(hogwartzWorkflow);
	}

	@Test(expected = ValidationException.class)
	public void shouldNOTCreateHydramoduleWorkflowWithInvalidDisplayOrder() {
		Set<HydramoduleWorkflowPhases> phases = new HashSet<HydramoduleWorkflowPhases>();
		searchPhase.setDisplayOrder(1);
		phases.add(searchPhase);
		treatPhase.setDisplayOrder(1);
		phases.add(treatPhase);
		hogwartzWorkflow.setHydramoduleWorkflowPhaseses(phases);
		service.saveWorkflow(hogwartzWorkflow);
	}

	@Test
	public void shouldRetireHydramoduleWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireHydramoduleWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldDeleteHydramoduleWorkflow() {
		doNothing().when(workflowDao).deleteWorkflow(hogwartzWorkflow);
		when(workflowDao.getWorkflowPhase(any(HydramoduleWorkflow.class)))
		        .thenReturn(Collections.<HydramoduleWorkflowPhases> emptyList());
		service.deleteWorkflow(hogwartzWorkflow);
		verify(workflowDao, times(1)).getWorkflowPhase(any(HydramoduleWorkflow.class));
		verify(workflowDao, times(1)).deleteWorkflow(any(HydramoduleWorkflow.class));
		verifyNoMoreInteractions(workflowDao);
	}

}
