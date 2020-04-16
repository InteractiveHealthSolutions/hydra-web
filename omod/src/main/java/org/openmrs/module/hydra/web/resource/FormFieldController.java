package org.openmrs.module.hydra.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleFormField;
import org.openmrs.module.webservices.rest.SimpleObject;
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
		+ "/hydra/formField", supportedClass = HydramoduleFormField.class, supportedOpenmrsVersions = {
				"2.0.*,2.1.*,2.2.*"})
public class FormFieldController extends MetadataDelegatingCrudResource<HydramoduleFormField> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleFormField newDelegate() {
		return new HydramoduleFormField();
	}

	@Override
	public HydramoduleFormField save(HydramoduleFormField component) {

		//
		return new HydramoduleFormField();
	}

	@Override
	public HydramoduleFormField getByUniqueId(String uuid) {
		return service.getFormFieldByUUID(uuid);
	}

	/*
	 * @Override protected PageableResult doSearch(RequestContext context) { String
	 * queryParam = context.getParameter("q"); List<HydramoduleFormField> forms =
	 * service.getFieldsByName(queryParam);
	 * 
	 * return new NeedsPaging<HydramoduleFormField>(forms, context); }
	 */

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
	public void purge(HydramoduleFormField component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("formFieldId");
		description.addProperty("displayOrder");
		description.addProperty("minOccurrence");
		description.addProperty("maxOccurrence");
		description.addProperty("minValue");
		description.addProperty("maxValue");
		description.addProperty("minLength");
		description.addProperty("maxLength");
		description.addProperty("minSelections");
		description.addProperty("allowFutureDate");
		description.addProperty("allowPastDate");
		description.addProperty("displayText");
		description.addProperty("errorMessage");
		description.addProperty("scoreable");
		description.addProperty("allowDecimal");
		description.addProperty("mandatory");
		description.addProperty("defaultValue");
		description.addProperty("regix");
		description.addProperty("characters");
		description.addProperty("scoreField");
		description.addProperty("disabled");
		description.addProperty("uuid");
		description.addProperty("createPatient");
		description.addProperty("field", Representation.FULL);
		description.addProperty("fieldData", Representation.FULL);

		description.addProperty("group", Representation.REF);
		description.addProperty("children", Representation.REF);

		// description.addProperty("form", Representation.REF);

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
		description.addProperty("formFieldId");
		description.addProperty("displayOrder");
		description.addProperty("minOccurrence");
		description.addProperty("maxOccurrence");
		description.addProperty("minValue");
		description.addProperty("maxValue");
		description.addProperty("minLength");
		description.addProperty("maxLength");
		description.addProperty("minSelections");
		description.addProperty("allowFutureDate");
		description.addProperty("allowPastDate");
		description.addProperty("displayText");
		description.addProperty("mandatory");
		description.addProperty("defaultValue");
		description.addProperty("regix");
		description.addProperty("characters");
		description.addProperty("errorMessage");
		description.addProperty("scoreable");
		description.addProperty("allowDecimal");
		description.addProperty("field");
		description.addProperty("form");
		description.addProperty("scoreField");
		description.addProperty("disabled");
		description.addProperty("children");
		description.addProperty("group");
		description.addProperty("createPatient");

		return description;

	}

}
