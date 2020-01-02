package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponentForms;
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
import org.openmrs.module.webservices.rest.web.response.ObjectNotFoundException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/componentform", supportedClass = HydramoduleComponentForms.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class ComponentFormsMapController extends DelegatingCrudResource<HydramoduleComponentForms> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleComponentForms newDelegate() {
		return new HydramoduleComponentForms();
	}

	@Override
	public HydramoduleComponentForms save(HydramoduleComponentForms componentForm) {
		return service.saveComponentFromRelation(componentForm);
	}

	@Override
	public HydramoduleComponentForms getByUniqueId(String uuid) {
		return service.getComponentFormRelationByUUID(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleComponentForms> componentForm = service.getAllComponentFormRelations();
		simpleObject.put("ComponentFormMap",
		    ConversionUtil.convertToRepresentation(componentForm, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	protected void delete(HydramoduleComponentForms componentForm, String reason, RequestContext context)
	        throws ResponseException {

		service.deleteComponentForm(componentForm);

	}

	@Override
	public void purge(HydramoduleComponentForms delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String uuid, String reason, RequestContext context) throws ResponseException {
		HydramoduleComponentForms componentForm = getByUniqueId(uuid);
		if (componentForm == null)
			throw new ObjectNotFoundException();
		service.deleteComponentForm(componentForm);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("id");
		description.addProperty("displayOrder");
		description.addProperty("hydramoduleComponent");
		description.addProperty("hydramoduleForm");
		description.addProperty("hydramoduleWorkflow");

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
		description.addProperty("uuid");
		description.addProperty("displayOrder");
		description.addProperty("hydramoduleComponent");
		description.addProperty("hydramoduleForm");
		description.addProperty("hydramoduleWorkflow");

		return description;

	}

}
