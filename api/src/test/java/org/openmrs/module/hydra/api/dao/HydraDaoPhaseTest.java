package org.openmrs.module.hydra.api.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraBaseTest;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HydraDaoPhaseTest extends HydraBaseTest {

	@Autowired
	HydraDaoImpl dao;

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
	}

	@Test
	public void shouldGetAllPhases() {
		Context.clearSession();
		List<HydramodulePhase> active = Arrays.asList(search, treat, prevent);
		List<HydramodulePhase> list = dao.getAllPhases();
		assertTrue(list.size() == active.size());
	}

	@Test
	public void shouldGetPhaseById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPhaseByName() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPhaseByUuid() {
		Context.clearSession();
		assertEquals(admission, dao.getAssetCategory("aaaaaaaa-bbbb-cccc-dddd-202006050009"));
	}

	@Test
	public void shouldSavePhase() {
		HydramodulePhase phase = new HydramodulePhase();
		phase.setName("Test Phase");
		phase.setDescription(phase.getName());
		phase.setRetired(Boolean.FALSE);
		phase.setUuid(UUID.randomUUID().toString());
		phase = dao.savePhase(phase);
		assertThat(phase, Matchers.hasProperty("dateCreated", Matchers.notNullValue()));
	}

	@Test
	public void shouldNOTPurgePhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireComponent() {
		fail("Not yet implemented");
	}
}
