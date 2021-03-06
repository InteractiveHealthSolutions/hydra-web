package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramodulePhaseComponents;
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
import org.openmrs.module.webservices.rest.web.response.ObjectNotFoundException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/phasecomponent", supportedClass = HydramodulePhaseComponents.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class PhaseComponentsMapController extends DelegatingCrudResource<HydramodulePhaseComponents> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramodulePhaseComponents newDelegate() {
		return new HydramodulePhaseComponents();
	}

	@Override
	public HydramodulePhaseComponents save(HydramodulePhaseComponents phaseComponent) {
		return service.savePhaseComponentRelation(phaseComponent);
	}

	@Override
	public HydramodulePhaseComponents getByUniqueId(String uuid) {
		return service.getPhasesComponentRelationByUUID(uuid);
	}

	@Override
	protected void delete(HydramodulePhaseComponents phaseComponent, String reason, RequestContext context)
	        throws ResponseException {
		service.deletePhaseComponent(phaseComponent);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramodulePhaseComponents> pahseComponent = service.getAllPhaseComponentsRelations();
		simpleObject.put("PhaseComponentsMap",
		    ConversionUtil.convertToRepresentation(pahseComponent, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramodulePhaseComponents delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String uuid, String reason, RequestContext context) throws ResponseException {
		HydramodulePhaseComponents phaseComponent = getByUniqueId(uuid);
		if (phaseComponent == null)
			throw new ObjectNotFoundException();
		service.deletePhaseComponent(phaseComponent);
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
		description.addProperty("componentUUID");
		description.addProperty("workflowUUID");
		description.addProperty("hydramoduleComponent");
		description.addProperty("hydramodulePhase");
		description.addProperty("hydramoduleWorkflow");

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
		description.addProperty("hydramoduleComponent");
		description.addProperty("hydramodulePhase");
		description.addProperty("hydramoduleWorkflow");

		return description;

	}

}
