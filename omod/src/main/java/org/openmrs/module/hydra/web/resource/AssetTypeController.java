package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetType;
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
		+ "/hydra/assetType", supportedClass = HydramoduleAssetType.class, supportedOpenmrsVersions = {
				"2.0.*,2.1.*,2.2.*"})
public class AssetTypeController extends MetadataDelegatingCrudResource<HydramoduleAssetType> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleAssetType newDelegate() {
		return new HydramoduleAssetType();
	}

	@Override
	public HydramoduleAssetType save(HydramoduleAssetType component) {
		return service.saveAssetType(component);
	}

	@Override
	public HydramoduleAssetType getByUniqueId(String uuid) {
		return service.getAssetType(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleAssetType> services = service.getAllAssetTypes(true);
		services.addAll(service.getAllAssetTypes(false));
		simpleObject.put("assetTypes", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleAssetType component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("assetTypeId");
		description.addProperty("name");
		description.addProperty("assetCategory", Representation.REF);

		if (representation instanceof DefaultRepresentation) {
			description.addProperty("assets", Representation.REF);
			return description;

		} else if (representation instanceof FullRepresentation) {

			description.addProperty("dateCreated");

			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("retired");
			description.addProperty("dateRetired");
			description.addProperty("retiredBy");
			description.addProperty("retireReason");
			description.addProperty("assets", Representation.REF);

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
		description.addProperty("assetTypeId");
		description.addProperty("assetCategory");

		return description;

	}

}
