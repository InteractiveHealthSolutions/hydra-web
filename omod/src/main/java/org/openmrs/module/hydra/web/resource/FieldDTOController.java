package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleFieldDTO;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Deprecated
// Use FieldController.java instead
@Resource(name = RestConstants.VERSION_1
        + "/hydra/field", supportedClass = HydramoduleFieldDTO.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class FieldDTOController extends DelegatingCrudResource<HydramoduleFieldDTO> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleFieldDTO newDelegate() {
		return new HydramoduleFieldDTO();
	}

	@Override
	public HydramoduleFieldDTO save(HydramoduleFieldDTO component) {

		service.saveField(component);
		return new HydramoduleFieldDTO();
	}

	@Override
	public HydramoduleFieldDTO getByUniqueId(String uuid) {
		return /* service.getAssetCategory(uuid) */null;
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String queryParam = context.getParameter("q");
		List<HydramoduleFieldDTO> forms = service.getFieldsByName(queryParam);

		return new NeedsPaging<HydramoduleFieldDTO>(forms, context);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		/*
		 * SimpleObject simpleObject = new SimpleObject();
		 * List<HydramoduleDTOFieldAnswer> services =
		 * service.getAllAssetCategories(true);
		 * services.addAll(service.getAllAssetCategories(false));
		 * simpleObject.put("services", ConversionUtil.convertToRepresentation(services,
		 * context.getRepresentation())); return simpleObject;
		 */
		return null;
	}

	@Override
	public void purge(HydramoduleFieldDTO component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("field");
		description.addProperty("answers");

		if (representation instanceof DefaultRepresentation) {
			return description;
		} else if (representation instanceof FullRepresentation) {

			/*
			 * description.addProperty("dateCreated");
			 * 
			 * description.addProperty("changedBy"); description.addProperty("dateChanged");
			 * 
			 * description.addProperty("retired"); description.addProperty("dateRetired");
			 * description.addProperty("retiredBy");
			 * description.addProperty("retireReason");
			 * description.addProperty("assetTypes", Representation.DEFAULT);
			 */
			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("field");
		description.addProperty("answers");
		description.addProperty("concept");
		return description;

	}

	@Override
	protected void delete(HydramoduleFieldDTO delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

}
