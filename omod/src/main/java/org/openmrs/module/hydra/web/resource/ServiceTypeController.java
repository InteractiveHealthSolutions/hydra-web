package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleServiceType;
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
        + "/hydra/service-type", supportedClass = HydramoduleServiceType.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class ServiceTypeController extends MetadataDelegatingCrudResource<HydramoduleServiceType> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private HydraService hydraService = Context.getService(HydraService.class);

	@Override
	public HydramoduleServiceType newDelegate() {
		return new HydramoduleServiceType();
	}

	@Override
	public HydramoduleServiceType save(HydramoduleServiceType component) {
		return hydraService.getHydraServiceService().saveServiceType(component);
	}

	@Override
	public HydramoduleServiceType getByUniqueId(String uuid) {
		return hydraService.getHydraServiceService().getServiceType(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleServiceType> component = hydraService.getHydraServiceService().getAllServiceTypes(false);
		simpleObject.put("serviceTypes", ConversionUtil.convertToRepresentation(component, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleServiceType component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("serviceTypeId");
		description.addProperty("name");
		description.addProperty("encounterType", Representation.REF);

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
		description.addProperty("uuid");
		description.addProperty("serviceTypeId");
		description.addProperty("encounterType");
		return description;

	}

}
