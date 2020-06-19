package org.openmrs.module.hydra.api.dao;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
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
import org.openmrs.module.hydra.model.workflow.HydramoduleUserWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflowPhases;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HydraWorkflowDaoTest extends HydraBaseTest {

	@Autowired
	HydraDaoImpl dao;

	HydramoduleWorkflow hogwartzWorkflow;

	HydramoduleWorkflowPhases searchPhase, treatPhase;

	HydramodulePatientWorkflow harryWorkflow, tomWorkflow;

	HydramoduleUserWorkflow dumbledoreWorkflow;

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
		initWorkflows();
	}

	private void initWorkflows() throws ParseException {
		hogwartzWorkflow = (HydramoduleWorkflow) sessionFactory.getCurrentSession().get(HydramoduleWorkflow.class, 1);
		searchPhase = (HydramoduleWorkflowPhases) sessionFactory.getCurrentSession()
				.get(HydramoduleWorkflowPhases.class, 1);
		treatPhase = (HydramoduleWorkflowPhases) sessionFactory.getCurrentSession().get(HydramoduleWorkflowPhases.class,
				2);
		harryWorkflow = (HydramodulePatientWorkflow) sessionFactory.getCurrentSession()
				.get(HydramodulePatientWorkflow.class, 1);
		tomWorkflow = (HydramodulePatientWorkflow) sessionFactory.getCurrentSession()
				.get(HydramodulePatientWorkflow.class, 2);
		dumbledoreWorkflow = (HydramoduleUserWorkflow) sessionFactory.getCurrentSession()
				.get(HydramoduleUserWorkflow.class, 1);
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
	public void shouldPurgeHydramoduleWorkflowCompletely() {
		Context.clearSession();
		dao.deleteWorkflow(hogwartzWorkflow);
		HydramodulePatientWorkflow shouldNotExist = (HydramodulePatientWorkflow) sessionFactory.getCurrentSession()
				.get(HydramodulePatientWorkflow.class, 1);
		assertNull(shouldNotExist);
	}

	@Test
	public void shouldPurgeWorkflowSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireWorkflow() {
		fail("Not yet implemented");
	}

	/* Workflow Phases */
	@Test
	public void shouldGetAllWorkflowPhases() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetWorkflowPhaseById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetWorkflowsPhasesByWorkflow() {
		Context.clearSession();
		List<HydramoduleWorkflowPhases> list = dao.getWorkflowPhase(hogwartzWorkflow);
		assertThat(list, hasItems(searchPhase, treatPhase));
	}

	@Test
	public void shouldGetWorkflowPhaseByUuid() {
		Context.clearSession();
		assertEquals(hogwartzWorkflow, dao.getWorkflow("aaaaaaaa-bbbb-cccc-dddd-202006190005"));
	}

	@Test
	public void shouldSaveWorkflowPhase() {
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
	public void shouldPurgeWorkflowPhaseCompletely() {
		Context.clearSession();
		dao.deleteWorkflow(hogwartzWorkflow);
		HydramodulePatientWorkflow shouldNotExist = (HydramodulePatientWorkflow) sessionFactory.getCurrentSession()
				.get(HydramodulePatientWorkflow.class, 1);
		assertNull(shouldNotExist);
	}

	@Test
	public void shouldPurgeWorkflowPhaseSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireWorkflowPhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireWorkflowPhase() {
		fail("Not yet implemented");
	}
	
	/* Patient Workflow Phases */
	@Test
	public void shouldGetPatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPatientWorkflowById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPatientWorkflowsByWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPatientWorkflowByUuid() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSavePatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgePatientWorkflowCompletely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgePatientWorkflowSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldVoidPatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnvoidPatientWorkflow() {
		fail("Not yet implemented");
	}
}
