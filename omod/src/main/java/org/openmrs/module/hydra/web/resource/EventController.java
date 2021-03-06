package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleEvent;
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
        + "/hydra/event", supportedClass = HydramoduleEvent.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class EventController extends DataDelegatingCrudResource<HydramoduleEvent> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleEvent newDelegate() {
		return new HydramoduleEvent();
	}

	@Override
	public HydramoduleEvent save(HydramoduleEvent component) {
		return service.saveEvent(component);
	}

	@Override
	public HydramoduleEvent getByUniqueId(String uuid) {
		return service.getEvent(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleEvent> services = service.getAllEvents(true);
		services.addAll(service.getAllEvents(false));
		simpleObject.put("events", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleEvent component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("eventId");
		description.addProperty("name");
		description.addProperty("uuid");
		description.addProperty("closed");
		description.addProperty("description");
		description.addProperty("closureNotes");
		description.addProperty("location", Representation.REF);
		description.addProperty("schedule", Representation.REF);
		description.addProperty("eventType", Representation.REF);
		description.addProperty("eventAssets", Representation.REF);
		description.addProperty("eventServices", Representation.REF);
		description.addProperty("eventParticipants", Representation.REF);
		description.addProperty("voided");
		description.addProperty("voidReason");
		if (representation instanceof DefaultRepresentation) {
			/*
			 * description.addProperty("display"); description.addProperty("concept");
			 */
			return description;

		} else if (representation instanceof FullRepresentation) {

			/*
			 * description.addProperty("display"); description.addProperty("concept");
			 */

			description.addProperty("dateCreated");
			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("dateVoided");
			description.addProperty("voidedBy");

			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("name");
		description.addProperty("uuid");
		description.addProperty("description");
		description.addProperty("closureNotes");
		description.addProperty("eventId");
		description.addProperty("closed");
		description.addProperty("location");
		description.addProperty("schedule");
		description.addProperty("eventType");
		description.addProperty("description");
		description.addProperty("eventAssets");
		description.addProperty("eventServices");
		description.addProperty("voidReason");
		description.addProperty("eventParticipants");
		description.addProperty("voided");
		return description;
	}

	@Override
	protected void delete(HydramoduleEvent arg0, String arg1, RequestContext arg2) throws ResponseException {

	}

}
