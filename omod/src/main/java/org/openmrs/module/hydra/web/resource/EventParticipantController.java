package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleEventParticipants;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/event-participant", supportedClass = HydramoduleEventParticipants.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class EventParticipantController extends DataDelegatingCrudResource<HydramoduleEventParticipants> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleEventParticipants newDelegate() {
		return new HydramoduleEventParticipants();
	}

	@Override
	public HydramoduleEventParticipants save(HydramoduleEventParticipants component) {
		return service.saveEventParticipant(component);
	}

	@Override
	public HydramoduleEventParticipants getByUniqueId(String uuid) {
		return service.getEventParticipant(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleEventParticipants> services = service.getAllEventParticipants(true);
		services.addAll(service.getAllEventParticipants(false));
		simpleObject.put("eventServices", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleEventParticipants component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("eventParticipantId");
		description.addProperty("attendance");
		description.addProperty("absenceReason");
		description.addProperty("plannedForEvent");
		description.addProperty("participant", Representation.FULL);
		description.addProperty("voided");

		if (representation instanceof DefaultRepresentation) {
			return description;
		} else if (representation instanceof FullRepresentation) {

			description.addProperty("dateCreated");

			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("dateVoided");
			description.addProperty("voidedBy");
			description.addProperty("voidReason");

			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("eventParticipantId");
		description.addProperty("voided");
		description.addProperty("uuid");
		description.addProperty("attendance");
		description.addProperty("absenceReason");
		description.addProperty("plannedForEvent");
		return description;

	}

	@Override
	protected void delete(HydramoduleEventParticipants delegate, String reason, RequestContext context)
	        throws ResponseException {
		// TODO Auto-generated method stub

	}

}
