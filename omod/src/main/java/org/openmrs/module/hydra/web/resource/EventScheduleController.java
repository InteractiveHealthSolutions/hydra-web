package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleEventSchedule;
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
        + "/hydra/event-schedule", supportedClass = HydramoduleEventSchedule.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class EventScheduleController extends DataDelegatingCrudResource<HydramoduleEventSchedule> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleEventSchedule newDelegate() {
		return new HydramoduleEventSchedule();
	}

	@Override
	public HydramoduleEventSchedule save(HydramoduleEventSchedule component) {
		return service.saveEventSchedule(component);
	}

	@Override
	public HydramoduleEventSchedule getByUniqueId(String uuid) {
		return service.getEventSchedule(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleEventSchedule> services = service.getAllEventSchedules(true);
		services.addAll(service.getAllEventSchedules(false));
		simpleObject.put("eventSchedules", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleEventSchedule component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("scheduleId");
		description.addProperty("plannedDate");
		description.addProperty("eventDate");
		description.addProperty("endDate");
		description.addProperty("reasonDeferred");

		if (representation instanceof DefaultRepresentation) {
			/*
			 * description.addProperty("display"); description.addProperty("concept");
			 */
			return description;

		} else if (representation instanceof FullRepresentation) {

			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("plannedDate");
		description.addProperty("eventDate");
		description.addProperty("endDate");
		description.addProperty("scheduleId");
		description.addProperty("reasonDeferred");
		return description;

	}

	@Override
	protected void delete(HydramoduleEventSchedule delegate, String reason, RequestContext context)
	        throws ResponseException {
		// TODO Auto-generated method stub

	}
}
