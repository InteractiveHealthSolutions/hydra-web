package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleField;
import org.openmrs.module.hydra.model.workflow.HydramoduleParticipant;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/participant", supportedClass = HydramoduleParticipant.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class ParticipantController extends MetadataDelegatingCrudResource<HydramoduleParticipant> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleParticipant newDelegate() {
		return new HydramoduleParticipant();
	}

	@Override
	public HydramoduleParticipant save(HydramoduleParticipant component) {
		System.out.println(component.getName());
		return service.saveParticipant(component);
	}

	@Override
	public HydramoduleParticipant getByUniqueId(String uuid) {
		return service.getParticipant(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleParticipant> services = service.getAllParticipants(true);
		services.addAll(service.getAllParticipants(false));
		simpleObject.put("participants", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	/*
	 * @Override public SimpleObject search(RequestContext context) throws
	 * ResponseException { // TODO Auto-generated method stub return
	 * super.search(context); }
	 */

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String queryParam = context.getParameter("q");
		System.out.println(queryParam);
		List<HydramoduleParticipant> participants = service.getParticipantByUserUUID(queryParam);

		return new NeedsPaging<HydramoduleParticipant>(participants, context);
	}

	@Override
	public void purge(HydramoduleParticipant component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addSelfLink();
		description.addProperty("display");
		description.addProperty("uuid");
		description.addProperty("participantId");
		description.addProperty("name");
		description.addProperty("salaryValue");
		description.addProperty("user", Representation.REF);
		description.addProperty("salaryType", Representation.REF);

		if (representation instanceof DefaultRepresentation) {

			return description;

		} else if (representation instanceof FullRepresentation) {

			description.addProperty("dateCreated");

			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("retired");
			description.addProperty("dateRetired");
			description.addProperty("retiredBy");
			description.addProperty("retireReason");

			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("name");
		description.addProperty("retired");
		description.addProperty("description");
		description.addProperty("uuid");
		description.addProperty("participantId");
		description.addProperty("salaryType");
		description.addProperty("salaryValue");
		description.addProperty("user");
		return description;

	}

}
