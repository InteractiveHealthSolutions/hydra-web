package org.openmrs.module.hydra.web.resource;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.form_service.FormService;
import org.openmrs.module.hydra.model.HydramoduleDTOFormSubmissionData;
import org.openmrs.module.webservices.rest.SimpleObject;
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
        + "/hydra/form-submission", supportedClass = HydramoduleDTOFormSubmissionData.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class FormSubmissionDataController extends DelegatingCrudResource<HydramoduleDTOFormSubmissionData> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleDTOFormSubmissionData newDelegate() {
		return new HydramoduleDTOFormSubmissionData();
	}

	@Override
	public HydramoduleDTOFormSubmissionData save(HydramoduleDTOFormSubmissionData formSubmissionData) {
		try {
			FormService.getInstance().createNewForm(service, formSubmissionData);
		}
		catch (ContextAuthenticationException e) {
			e.printStackTrace();
			String s = null;
			s.toString();
		}
		catch (ParseException e) {
			e.printStackTrace();
			String s = null;
			s.toString();
		}
		catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
			String s = null;
			s.toString();
		}
		// service.saveField(component);
		return new HydramoduleDTOFormSubmissionData();
	}

	@Override
	public HydramoduleDTOFormSubmissionData getByUniqueId(String uuid) {
		return /* service.getAssetCategory(uuid) */null;
	}

	/*
	 * @Override protected PageableResult doSearch(RequestContext context) { String
	 * queryParam = context.getParameter("q"); List<HydramoduleFieldDTO> forms =
	 * service.getFieldsByName(queryParam);
	 * 
	 * return new NeedsPaging<HydramoduleFieldDTO>(forms, context); }
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
	public void purge(HydramoduleDTOFormSubmissionData component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("data");
		description.addProperty("metadata");

		if (representation instanceof DefaultRepresentation) {
			return description;
		} else if (representation instanceof FullRepresentation) {

			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("data");
		description.addProperty("metadata");
		return description;

	}

	@Override
	protected void delete(HydramoduleDTOFormSubmissionData delegate, String reason, RequestContext context)
	        throws ResponseException {
		// TODO Auto-generated method stub

	}

}
