package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.impl.HydraContext;
import org.openmrs.module.hydra.model.HydramoduleWorkflow;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/workflow", supportedClass = HydramoduleWorkflow.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class WorkflowController extends MetadataDelegatingCrudResource<HydramoduleWorkflow> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public HydramoduleWorkflow newDelegate() {
		return new HydramoduleWorkflow();
	}

	@Override
	public HydramoduleWorkflow save(HydramoduleWorkflow delegate) {
		return HydraContext.getHydraWorkflowService().saveWorkflow(delegate);
	}

	@Override
	public HydramoduleWorkflow getByUniqueId(String uuid) {
		return HydraContext.getHydraWorkflowService().getWorkflowByUUID(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleWorkflow> p = HydraContext.getHydraWorkflowService().getAllWorkflows();
		simpleObject.put("workflows", ConversionUtil.convertToRepresentation(p, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleWorkflow delegate, RequestContext context) throws ResponseException {

	}

	@Override
	public void delete(String uuid, String reason, RequestContext context) throws ResponseException {
		super.delete(uuid, reason, context);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("workflowId");
		description.addProperty("name");

		if (representation instanceof DefaultRepresentation) {
			description.addProperty("display");
			description.addProperty("concept");

			return description;

		} else if (representation instanceof FullRepresentation) {

			description.addProperty("hydramoduleWorkflowPhaseses");
			description.addProperty("concept");
			description.addProperty("name");

			// description.addProperty("creator");
			description.addProperty("dateCreated");

			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("retired");
			description.addProperty("dateRetired");
			description.addProperty("retiredBy");
			description.addProperty("retireReason");

			return description;
		} else {

		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("name");
		description.addProperty("description");
		description.addProperty("uuid");
		description.addProperty("workflowId");
		description.addProperty("retired");

		return description;

	}

}
