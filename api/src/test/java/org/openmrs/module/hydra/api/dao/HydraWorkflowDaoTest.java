package org.openmrs.module.hydra.api.dao;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraBaseTest;
import org.openmrs.module.hydra.model.workflow.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflowPhases;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HydraWorkflowDaoTest extends HydraBaseTest {

	@Autowired
	HydraDaoImpl dao;

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
	}

	/* Workflows */
	@Test
	public void shouldGetAllHydramoduleWorkflows() {
		Context.clearSession();
		List<HydramoduleWorkflow> expected = Arrays.asList(hogwartzWorkflow);
		List<HydramoduleWorkflow> list = dao.getAllWorkflows();
		assertTrue(list.size() == expected.size());
		assertThat(list, Matchers.hasItem(hogwartzWorkflow));
	}

	@Test
	public void shouldGetHydramoduleWorkflowById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleWorkflowByUuid() {
		Context.clearSession();
		assertEquals(hogwartzWorkflow, dao.getWorkflow("aaaaaaaa-bbbb-cccc-dddd-202006190005"));
	}

	@Test
	public void shouldSaveHydramoduleWorkflow() {
		Context.clearSession();
		Concept hogwartzWorkflowConcept = (Concept) sessionFactory.getCurrentSession().get(Concept.class, 1000);
		HydramoduleWorkflow ilvermornyWorkflow = new HydramoduleWorkflow();
		ilvermornyWorkflow.setWorkflowId(100);
		ilvermornyWorkflow.setConcept(hogwartzWorkflowConcept);
		ilvermornyWorkflow.setName("Ilvermorny Workflow");
		ilvermornyWorkflow.setDescription("Workflow for Ilvermorny school");
		ilvermornyWorkflow.setRetired(Boolean.FALSE);
		ilvermornyWorkflow.setUuid(UUID.randomUUID().toString());
		ilvermornyWorkflow = dao.saveWorkflow(ilvermornyWorkflow);
		assertThat(ilvermornyWorkflow, Matchers.hasProperty("dateCreated", Matchers.notNullValue()));
	}

	@Test
	public void shouldPurgeHydramoduleHydramoduleWorkflowCompletely() {
		Context.clearSession();
		dao.deleteWorkflow(hogwartzWorkflow);
		HydramodulePatientWorkflow shouldNotExist = (HydramodulePatientWorkflow) sessionFactory.getCurrentSession()
		        .get(HydramodulePatientWorkflow.class, 1);
		assertNull(shouldNotExist);
	}

	@Test
	public void shouldPurgeHydramoduleWorkflowSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireHydramoduleWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireHydramoduleWorkflow() {
		fail("Not yet implemented");
	}

	/* Workflow Phases */
	@Test
	public void shouldGetAllHydramoduleWorkflowPhases() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleWorkflowPhaseById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleWorkflowsPhasesByWorkflow() {
		Context.clearSession();
		List<HydramoduleWorkflowPhases> list = dao.getWorkflowPhase(hogwartzWorkflow);
		assertThat(list, hasItems(searchPhase, treatPhase));
	}

	@Test
	public void shouldGetHydramoduleWorkflowPhaseByUuid() {
		Context.clearSession();
		assertEquals(hogwartzWorkflow, dao.getWorkflow("aaaaaaaa-bbbb-cccc-dddd-202006190005"));
	}

	@Test
	public void shouldSaveHydramoduleWorkflowPhase() {
		Context.clearSession();
		HydramoduleWorkflowPhases preventPhase = new HydramoduleWorkflowPhases();
		preventPhase.setId(100);
		preventPhase.setHydramodulePhase(prevent);
		preventPhase.setHydramoduleWorkflow(hogwartzWorkflow);
		preventPhase.setDisplayOrder(2);
		preventPhase.setUuid(UUID.randomUUID().toString());
		preventPhase = dao.saveWorkflowPhase(preventPhase);
		assertThat(preventPhase, Matchers.hasProperty("dateCreated", Matchers.notNullValue()));
	}

	@Test
	public void shouldPurgeHydramoduleWorkflowPhaseCompletely() {
		Context.clearSession();
		dao.deleteWorkflow(hogwartzWorkflow);
		HydramodulePatientWorkflow shouldNotExist = (HydramodulePatientWorkflow) sessionFactory.getCurrentSession()
		        .get(HydramodulePatientWorkflow.class, 1);
		assertNull(shouldNotExist);
	}

	@Test
	public void shouldPurgeHydramoduleWorkflowPhaseSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireHydramoduleWorkflowPhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireHydramoduleWorkflowPhase() {
		fail("Not yet implemented");
	}

	/* Patient Workflow Phases */
	@Test
	public void shouldGetHydramodulePatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramodulePatientWorkflowById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramodulePatientWorkflowsByWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramodulePatientWorkflowByUuid() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramodulePatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeHydramodulePatientWorkflowCompletely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeHydramodulePatientWorkflowSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldVoidHydramodulePatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnvoidHydramodulePatientWorkflow() {
		fail("Not yet implemented");
	}
}
