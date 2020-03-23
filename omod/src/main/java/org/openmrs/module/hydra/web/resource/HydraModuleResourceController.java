package org.openmrs.module.hydra.web.resource;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/hydra")
public class HydraModuleResourceController extends MainResourceController {

	@Override
	public String getNamespace() {
		return RestConstants.VERSION_1 + "/hydra";
	}
}
