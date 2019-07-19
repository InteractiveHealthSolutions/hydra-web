package org.openmrs.module.hydra.web.resource;

import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/hydra/phase", supportedClass = HydramodulePhase.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class PhaseController extends MetadataDelegatingCrudResource<HydramodulePhase> {

	@Override
	public HydramodulePhase newDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HydramodulePhase save(HydramodulePhase delegate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HydramodulePhase getByUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void purge(HydramodulePhase delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
		
	}

}
