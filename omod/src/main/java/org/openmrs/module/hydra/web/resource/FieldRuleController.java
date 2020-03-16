package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleField;
import org.openmrs.module.hydra.model.workflow.HydramoduleFieldRule;
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
        + "/hydra/fieldRule", supportedClass = HydramoduleFieldRule.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class FieldRuleController extends MetadataDelegatingCrudResource<HydramoduleFieldRule> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleFieldRule newDelegate() {
		return new HydramoduleFieldRule();
	}

	@Override
	public HydramoduleFieldRule save(HydramoduleFieldRule delegate) {
		return service.saveHydramoduleFieldRule(delegate);
	}

	@Override
	public HydramoduleFieldRule getByUniqueId(String uuid) {
		return service.getHydramoduleFieldRule(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleFieldRule> moduleForm = service.getAllHydramoduleFieldRules(false);
		simpleObject.put("fields", ConversionUtil.convertToRepresentation(moduleForm, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleFieldRule delegate, RequestContext context) throws ResponseException {
	}

	/*
	 * @Override protected PageableResult doSearch(RequestContext context) { String
	 * queryParam = context.getParameter("q"); System.out.println(queryParam);
	 * List<HydramoduleFieldRule> forms =
	 * service.getHydramoduleFieldsByName(queryParam);
	 * 
	 * return new NeedsPaging<HydramoduleFieldRule>(forms, context); }
	 */

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);

		description.addProperty("uuid");
		description.addProperty("ruleId");
		description.addProperty("name");
		description.addProperty("actionName");
		description.addProperty("description");
		description.addProperty("form", Representation.REF);
		description.addProperty("targetForm", Representation.REF);
		description.addProperty("targetQuestion", Representation.REF);
		description.addProperty("tokens", Representation.FULL);

		if (representation instanceof DefaultRepresentation) {
			// description.addProperty("display");

			return description;

		} else if (representation instanceof FullRepresentation) {

			// description.addProperty("display");

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
		description.addProperty("ruleId");
		description.addProperty("name");
		description.addProperty("actionName");
		description.addProperty("description");
		description.addProperty("form");
		description.addProperty("targetForm");
		description.addProperty("targetQuestion");
		description.addProperty("tokens");

		return description;

	}

}
