package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleFieldAnswer;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/hydraFieldAnswer", supportedClass = HydramoduleFieldAnswer.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class FieldAnswerController extends DelegatingCrudResource<HydramoduleFieldAnswer> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleFieldAnswer newDelegate() {
		return new HydramoduleFieldAnswer();
	}

	@Override
	public HydramoduleFieldAnswer save(HydramoduleFieldAnswer delegate) {
		return service.saveHydramoduleFieldAnswer(delegate);
	}

	@Override
	public HydramoduleFieldAnswer getByUniqueId(String uuid) {
		return service.getHydramoduleFieldAnswer(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleFieldAnswer> moduleForm = service.getAllHydramoduleFieldAnswers(false);
		simpleObject.put("fieldAnswers", ConversionUtil.convertToRepresentation(moduleForm, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleFieldAnswer delegate, RequestContext context) throws ResponseException {
	}

	/*
	 * @Override protected PageableResult doSearch(RequestContext context) { String
	 * queryParam = context.getParameter("q"); List<HydramoduleField> forms =
	 * service.getAllModuleFormsByComponent(queryParam);
	 * 
	 * return new NeedsPaging<HydramoduleField>(forms, context); }
	 */

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("concept", Representation.REF);
		description.addProperty("fieldAnswerId");

		if (representation instanceof DefaultRepresentation) {

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

		description.addProperty("concept");
		description.addProperty("field");

		return description;

	}

	@Override
	protected void delete(HydramoduleFieldAnswer delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

}
