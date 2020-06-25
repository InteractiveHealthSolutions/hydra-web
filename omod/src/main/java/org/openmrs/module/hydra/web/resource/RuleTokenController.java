package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.HydramoduleRuleToken;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/rule-token", supportedClass = HydramoduleRuleToken.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class RuleTokenController extends DelegatingCrudResource<HydramoduleRuleToken> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleRuleToken newDelegate() {
		return new HydramoduleRuleToken();
	}

	@Override
	public HydramoduleRuleToken save(HydramoduleRuleToken delegate) {
		return service.saveHydramoduleRuleToken(delegate);
	}

	@Override
	public HydramoduleRuleToken getByUniqueId(String uuid) {
		return service.getHydramoduleRuleToken(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleRuleToken> moduleForm = service.getAllHydramoduleRuleTokens();
		simpleObject.put("rules", ConversionUtil.convertToRepresentation(moduleForm, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleRuleToken delegate, RequestContext context) throws ResponseException {
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);

		description.addProperty("uuid");
		description.addProperty("tokenId");
		description.addProperty("typeName");
		description.addProperty("value");
		// description.addProperty("rule", Representation.REF);

		if (representation instanceof DefaultRepresentation) {
			description.addProperty("display");
			return description;

		} else if (representation instanceof FullRepresentation) {
			return description;
		} else {

		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");
		description.addProperty("tokenId");
		description.addProperty("typeName");
		description.addProperty("value");

		return description;

	}

	@Override
	protected void delete(HydramoduleRuleToken delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

}
