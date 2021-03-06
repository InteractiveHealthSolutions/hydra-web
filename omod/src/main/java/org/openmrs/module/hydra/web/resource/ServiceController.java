package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleService;
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
        + "/hydra/service", supportedClass = HydramoduleService.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class ServiceController extends MetadataDelegatingCrudResource<HydramoduleService> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleService newDelegate() {
		return new HydramoduleService();
	}

	@Override
	public HydramoduleService save(HydramoduleService component) {
		return service.saveService(component);
	}

	@Override
	public HydramoduleService getByUniqueId(String uuid) {
		return service.getService(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleService> services = service.getAllServices(false);
		// services.addAll(service.getAllServices(false));
		simpleObject.put("services", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleService component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("serviceId");
		description.addProperty("unitCost");
		description.addProperty("referenceId");
		description.addProperty("name");
		description.addProperty("description");
		description.addProperty("serviceType", Representation.REF);

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
		description.addProperty("uuid");
		description.addProperty("serviceId");
		description.addProperty("description");
		description.addProperty("retired");
		description.addProperty("unitCost");
		description.addProperty("serviceType");
		description.addProperty("referenceId");
		return description;

	}

}
