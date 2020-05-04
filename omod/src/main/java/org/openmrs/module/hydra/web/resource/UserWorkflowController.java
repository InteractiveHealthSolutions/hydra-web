package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleFieldAnswer;
import org.openmrs.module.hydra.model.workflow.HydramoduleUserWorkflow;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/userworkflow", supportedClass = HydramoduleUserWorkflow.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class UserWorkflowController extends DelegatingCrudResource<HydramoduleUserWorkflow> {

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
	protected PageableResult doSearch(RequestContext context) {
		String queryParam = context.getParameter("q");
		List<HydramoduleUserWorkflow> users = service.getUserWorkflowByUser(queryParam);
		return new NeedsPaging<HydramoduleUserWorkflow>(users, context);
	}

	@Override
	public void purge(HydramoduleUserWorkflow arg0, RequestContext arg1) throws ResponseException {

	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addSelfLink();
		description.addProperty("uuid");
		description.addProperty("userWorkflowId");
		description.addProperty("workflow", Representation.REF);
		description.addProperty("user", Representation.REF);

		if (representation instanceof DefaultRepresentation) {

			return description;

		} else if (representation instanceof FullRepresentation) {

			description.addProperty("dateCreated");

			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("voided");
			description.addProperty("dateVoided");
			description.addProperty("voidedBy");
			description.addProperty("voidReason");

			return description;
		}
		return description;

	}

}
