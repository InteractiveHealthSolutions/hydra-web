package org.openmrs.module.hydra.api.dao;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraBaseTest;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HydraDaoTest extends HydraBaseTest {

	@Autowired
	HydraDaoImpl dao;

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
	}

	@Test
	public final void shouldGetActiveComponents() {
		Context.clearSession();
		List<HydramoduleComponent> active = Arrays.asList(preAdmission, admission);
		List<HydramoduleComponent> list = dao.getAllComponents();
		assertTrue(list.size() == active.size());
		assertThat(list, Matchers.not(Matchers.hasItem(orientation)));
	}
	
	@Test
	public void shouldGetAllComponents() {
		fail("No method available to get all components.");
	}
}
