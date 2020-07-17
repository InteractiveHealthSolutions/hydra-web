package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormEncounter;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/encounter-form", supportedClass = HydramoduleFormEncounter.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class FormEncounterController extends DelegatingCrudResource<HydramoduleFormEncounter> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private HydraService hydraService = Context.getService(HydraService.class);

	@Override
	public HydramoduleFormEncounter newDelegate() {
		return new HydramoduleFormEncounter();
	}

	@Override
	public HydramoduleFormEncounter save(HydramoduleFormEncounter delegate) {
		return hydraService.getHydraFormService().saveFormEncounter(delegate);
	}

	@Override
	public HydramoduleFormEncounter getByUniqueId(String uuid) {
		return hydraService.getHydraFormService().getFormEncounterByUUID(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleFormEncounter> moduleForm;
		String componentParam = context.getParameter("componentFormId");
		String patientParam = context.getParameter("patientId");
		if(componentParam!=null || patientParam!=null) {
			
			Integer componentFormId = componentParam==null?null:Integer.parseInt(componentParam);
			Integer patientId = patientParam==null?null:Integer.parseInt(patientParam);
			
			moduleForm = hydraService.getHydraFormService().getAllFormEncounters(componentFormId, patientId);
		} else {	
			moduleForm = hydraService.getHydraFormService().getAllFormEncounters();
		}
		
		simpleObject.put("formsEncounters", ConversionUtil.convertToRepresentation(moduleForm, context.getRepresentation()));
		
		return simpleObject;
	}

	@Override
	protected void delete(HydramoduleFormEncounter delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String queryParam = context.getParameter("q");
		List<HydramoduleFormEncounter> forms = hydraService.getHydraFormService().searchFormEncounters(queryParam);

		return new NeedsPaging<HydramoduleFormEncounter>(forms, context);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("formEncounterId");
		description.addProperty("uuid");

		if (representation instanceof RefRepresentation) {
			description.addProperty("encounter", Representation.REF);
			return description;
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("encounter", Representation.FULL);
			return description;
		} else {
			description.addProperty("encounter", Representation.DEFAULT);
		}
		
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		
		description.addProperty("encounter");
		description.addProperty("componentForm");

		return description;

	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
		// TODO Auto-generated method stub
		return super.getUpdatableProperties();
	}

	@Override
	public void purge(HydramoduleFormEncounter delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
		
	}
}
