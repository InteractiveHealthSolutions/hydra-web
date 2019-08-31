package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflowPhases;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/workflowphases", supportedClass = HydramoduleWorkflowPhases.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class WorkflowPhasesMapController extends DelegatingCrudResource<HydramoduleWorkflowPhases> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleWorkflowPhases newDelegate() {
		return new HydramoduleWorkflowPhases();
	}

	@Override
	public HydramoduleWorkflowPhases save(HydramoduleWorkflowPhases delegate) {
		return service.saveWorkflowPhaseRelation(delegate);
	}

	@Override
	public HydramoduleWorkflowPhases getByUniqueId(String uuid) {
		return service.getWorkflowPhasesRelationByUUID(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleWorkflowPhases> p = service.getAllWorkflowPhaseRelations();
		simpleObject.put("workflowPhasesMap", ConversionUtil.convertToRepresentation(p, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleWorkflowPhases delegate, RequestContext context) throws ResponseException {

	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("id");
		description.addProperty("displayOrder");
		description.addProperty("phaseUUID");
		description.addProperty("workflowUUID");

		if (representation instanceof DefaultRepresentation) {

			return description;

		} else if (representation instanceof FullRepresentation) {

			return description;
		} else {

		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addProperty("uuid");
		description.addProperty("displayOrder");
		description.addProperty("hydramodulePhase");
		description.addProperty("hydramoduleWorkflow");

		return description;

	}

	@Override
	protected void delete(HydramoduleWorkflowPhases delegate, String reason, RequestContext context)
	        throws ResponseException {

	}

}
