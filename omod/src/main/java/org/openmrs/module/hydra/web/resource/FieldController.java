package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleField;
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
        + "/hydra/hydra-field", supportedClass = HydramoduleField.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class FieldController extends MetadataDelegatingCrudResource<HydramoduleField> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleField newDelegate() {
		return new HydramoduleField();
	}

	@Override
	public HydramoduleField save(HydramoduleField delegate) {
		return service.saveHydramoduleField(delegate);
	}

	@Override
	public HydramoduleField getByUniqueId(String uuid) {
		return service.getHydramoduleField(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleField> moduleForm = service.getAllHydramoduleFields();
		simpleObject.put("fields", ConversionUtil.convertToRepresentation(moduleForm, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleField delegate, RequestContext context) throws ResponseException {
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String queryParam = context.getParameter("q");
		System.out.println(queryParam);
		List<HydramoduleField> forms = service.getHydramoduleFieldsByName(queryParam);

		return new NeedsPaging<HydramoduleField>(forms, context);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("answers", Representation.REF);

		description.addProperty("selectMultiple");
		description.addProperty("defaultValue");
		description.addProperty("attributeName");
		description.addProperty("tableName");
		description.addProperty("name");
		description.addProperty("fieldId");
		description.addProperty("description");
		description.addProperty("concept", Representation.REF);
		description.addProperty("fieldType", Representation.REF);
		description.addProperty("parsedRule");
		description.addProperty("isDefault");

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
		description.addProperty("answers");
		description.addProperty("selectMultiple");
		description.addProperty("defaultValue");
		description.addProperty("attributeName");
		description.addProperty("tableName");
		description.addProperty("fieldId");
		description.addProperty("name");
		description.addProperty("description");
		description.addProperty("concept");
		description.addProperty("fieldType");
		description.addProperty("isDefault");

		return description;

	}

}
