package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleEventAsset;
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
        + "/hydra/event-asset", supportedClass = HydramoduleEventAsset.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class EventAssetController extends DataDelegatingCrudResource<HydramoduleEventAsset> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private HydraService hydraService = Context.getService(HydraService.class);

	@Override
	public HydramoduleEventAsset newDelegate() {
		return new HydramoduleEventAsset();
	}

	@Override
	public HydramoduleEventAsset save(HydramoduleEventAsset component) {
		return hydraService.getHydraEventService().saveEventAsset(component);
	}

	@Override
	public HydramoduleEventAsset getByUniqueId(String uuid) {
		return hydraService.getHydraEventService().getEventAsset(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleEventAsset> services = hydraService.getHydraEventService().getAllEventAssets(true);
		services.addAll(hydraService.getHydraEventService().getAllEventAssets(false));
		simpleObject.put("services", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleEventAsset component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("eventAssetId");
		description.addProperty("quantity");
		description.addProperty("actualCost");
		description.addProperty("plannedForEvent");
		description.addProperty("availableInEvent");
		description.addProperty("description");
		description.addProperty("asset", Representation.FULL);

		if (representation instanceof DefaultRepresentation) {
			return description;
		} else if (representation instanceof FullRepresentation) {

			description.addProperty("dateCreated");

			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("voided");
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
		description.addProperty("eventAssetId");
		description.addProperty("voided");
		description.addProperty("uuid");
		description.addProperty("asset");
		description.addProperty("event");
		description.addProperty("quantity");
		description.addProperty("actualCost");
		description.addProperty("plannedForEvent");
		description.addProperty("availableInEvent");
		description.addProperty("description");
		return description;

	}

	@Override
	protected void delete(HydramoduleEventAsset delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

}
