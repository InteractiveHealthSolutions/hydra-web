package org.openmrs.module.hydra.api.dao;

import static org.junit.Assert.assertEquals;
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
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraBaseTest;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HydraDaoComponentTest extends HydraBaseTest {

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
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldGetComponentById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetComponentByUuid() {
		Context.clearSession();
		assertEquals(admission, dao.getAssetCategory("aaaaaaaa-bbbb-cccc-dddd-202006050009"));
	}

	@Test
	public void shouldSaveComponent() {
		HydramoduleComponent component = new HydramoduleComponent();
		component.setName("Test Component");
		component.setDescription(component.getName());
		component.setRetired(Boolean.FALSE);
		component.setUuid(UUID.randomUUID().toString());
		component = dao.saveComponent(component);
		assertThat(component, Matchers.hasProperty("dateCreated", Matchers.notNullValue()));
	}

	@Test
	public void shouldPurgeComponentCompletely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeComponentSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireComponent() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireComponent() {
		fail("Not yet implemented");
	}
}
