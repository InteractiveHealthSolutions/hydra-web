package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.impl.HydraContext;
import org.openmrs.module.hydra.model.HydramodulePhase;
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
        + "/hydra/phase", supportedClass = HydramodulePhase.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class PhaseController extends MetadataDelegatingCrudResource<HydramodulePhase> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public HydramodulePhase newDelegate() {
		return new HydramodulePhase();
	}

	@Override
	public HydramodulePhase save(HydramodulePhase delegate) {
		return HydraContext.getHydraPhaseService().savePhase(delegate);
	}

	@Override
	public HydramodulePhase getByUniqueId(String uuid) {
		return HydraContext.getHydraPhaseService().getPhaseByUUID(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramodulePhase> phase = HydraContext.getHydraPhaseService().getAllPhases();
		simpleObject.put("phases", ConversionUtil.convertToRepresentation(phase, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramodulePhase delegate, RequestContext context) throws ResponseException {
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("phaseId");
		description.addProperty("name");

		if (representation instanceof DefaultRepresentation) {
			description.addProperty("display");
			description.addProperty("concept");

			return description;

		} else if (representation instanceof FullRepresentation) {

			// description.addProperty("hydramodulePhaseComponents");
			description.addProperty("hydramoduleWorkflowPhases");

			description.addProperty("display");
			description.addProperty("concept");

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
		description.addProperty("retired");
		description.addProperty("uuid");
		description.addProperty("phaseId");

		return description;

	}

}
