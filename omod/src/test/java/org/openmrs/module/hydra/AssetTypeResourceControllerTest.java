package org.openmrs.module.hydra;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.impl.HydraServiceImpl;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AssetTypeResourceControllerTest extends MainResourceControllerTest {

	@Autowired
	HydraService service;

	@Before
	public void setUp() throws Exception {
		executeDataSet("HydraService-initialData.xml");
	}

	@Override
	public long getAllCount() {
		return 4;
	}

	@Override
	public String getURI() {
		return "hydra/assetType";
	}

	@Override
	public String getUuid() {
		return "aaaaaaaa-bbbb-cccc-dddd-202006120004";
	}

	@Test
	public void shouldSave() throws Exception {
		String uri = getURI();
		SimpleObject assetType = new SimpleObject();
		assetType.add("name", "Test Asset Type");

		MockHttpServletRequest newPostRequest = newPostRequest(uri, assetType);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}
}
