package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleLocationType;
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
        + "/hydra/location-type", supportedClass = HydramoduleLocationType.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class LocationTypeController extends MetadataDelegatingCrudResource<HydramoduleLocationType> {

	protected final Log log = LogFactory.getLog(getClass());

	private HydraService hydraService = Context.getService(HydraService.class);

	@Override
	public HydramoduleLocationType newDelegate() {
		return new HydramoduleLocationType();
	}

	@Override
	public HydramoduleLocationType save(HydramoduleLocationType hydramoduleLocationType) {
		return hydraService.getHydraLocationTypeService().saveHydramoduleLocationType(hydramoduleLocationType);
	}

	@Override
	public HydramoduleLocationType getByUniqueId(String uniqueId) {
		return hydraService.getHydraLocationTypeService().getLocationTypeByUuid(uniqueId);
	}

	@Override
	public void purge(HydramoduleLocationType delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleLocationType> locationTypes = hydraService.getHydraLocationTypeService().getAllLocationType(false);
		simpleObject.put("locationTypes",
		    ConversionUtil.convertToRepresentation(locationTypes, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("locationTypeLevel");
		description.addProperty("locationTypeId");
		description.addProperty("name");
		description.addProperty("description");
		if (representation instanceof DefaultRepresentation) {
			description.addProperty("display");

			return description;

		} else if (representation instanceof FullRepresentation) {

			description.addProperty("display");

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

		description.addProperty("uuid");
		description.addProperty("name");
		description.addProperty("retired");
		description.addProperty("locationTypeId");

		description.addProperty("locationTypeLevel");

		return description;

	}

}
