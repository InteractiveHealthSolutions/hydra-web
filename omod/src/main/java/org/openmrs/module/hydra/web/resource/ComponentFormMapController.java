package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
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
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ObjectNotFoundException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/componentform", supportedClass = HydramoduleComponentForm.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class ComponentFormMapController extends DelegatingCrudResource<HydramoduleComponentForm> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleComponentForm newDelegate() {
		return new HydramoduleComponentForm();
	}

	@Override
	public HydramoduleComponentForm save(HydramoduleComponentForm phaseComponent) {
		return service.saveComponentFormRelation(phaseComponent);
	}

	@Override
	public HydramoduleComponentForm getByUniqueId(String uuid) {
		return service.getComponentFormByUUID(uuid);
	}

	@Override
	protected void delete(HydramoduleComponentForm phaseComponent, String reason, RequestContext context)
	        throws ResponseException {
		service.retireComponentForm(phaseComponent);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleComponentForm> componentForms;
		try {
			componentForms = service.getAllComponentFormsRelations();
			simpleObject.put("ComponentsFormsMap",
			    ConversionUtil.convertToRepresentation(componentForms, context.getRepresentation()));
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return simpleObject;
	}

	@Override
	public void purge(HydramoduleComponentForm delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String uuid, String reason, RequestContext context) throws ResponseException {
		HydramoduleComponentForm phaseComponent = getByUniqueId(uuid);
		if (phaseComponent == null)
			throw new ObjectNotFoundException();
		service.retireComponentForm(phaseComponent);
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String queryParam = context.getParameter("q");
		List<HydramoduleComponentForm> componentForms = service.getComponentFormsByComponent(queryParam);

		return new NeedsPaging<HydramoduleComponentForm>(componentForms, context);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("id");
		description.addProperty("uuid");
		description.addProperty("displayOrder");
		description.addProperty("phase");
		description.addProperty("workflow");
		description.addProperty("component");
		description.addProperty("componentFormId");
		description.addProperty("form");
		description.addProperty("retired");

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
		description.addProperty("component");
		description.addProperty("phase");
		description.addProperty("workflow");
		description.addProperty("componentFormId");
		description.addProperty("form");
		description.addProperty("retired");

		return description;

	}

}
