package org.openmrs.module.hydra.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleUserWorkflow;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
		+ "/hydra/userworkflow", supportedClass = HydramoduleUserWorkflow.class, supportedOpenmrsVersions = {
				"2.0.*,2.1.*,2.2.*"})
public class UserWorkflowController extends DataDelegatingCrudResource<HydramoduleUserWorkflow> {

	protected final Log log = LogFactory.getLog(getClass());

	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleUserWorkflow newDelegate() {
		return new HydramoduleUserWorkflow();
	}

	@Override
	public HydramoduleUserWorkflow save(HydramoduleUserWorkflow hydramoduleUserWokflow) {

		return service.saveHydramoduleUserWorkflow(hydramoduleUserWokflow);
	}

	@Override
	protected void delete(HydramoduleUserWorkflow arg0, String arg1, RequestContext arg2) throws ResponseException {

	}

	@Override
	public HydramoduleUserWorkflow getByUniqueId(String uuid) {
		return service.getHydramoduleUserWorkflow(uuid);
	}

	@Override
	public void purge(HydramoduleUserWorkflow arg0, RequestContext arg1) throws ResponseException {

	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
