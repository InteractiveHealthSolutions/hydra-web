package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleForm;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
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
        + "/hydra/form", supportedClass = HydramoduleForm.class, supportedOpenmrsVersions = { "2.0.*,2.1.*,2.2.*" })
public class FormController extends MetadataDelegatingCrudResource<HydramoduleForm> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleForm newDelegate() {
		return new HydramoduleForm();
	}

	@Override
	public HydramoduleForm save(HydramoduleForm delegate) {
		return service.saveModuleForm(delegate);
	}

	@Override
	public HydramoduleForm getByUniqueId(String uuid) {
		return service.getHydraModuleFormByUuid(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleForm> moduleForm = service.getAllModuleForm();
		simpleObject.put("form", ConversionUtil.convertToRepresentation(moduleForm, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleForm delegate, RequestContext context) throws ResponseException {
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("uuid");
		description.addProperty("hydramoduleFormId");
		description.addProperty("name");
		description.addProperty("program");
		description.addProperty("form");

		if (representation instanceof DefaultRepresentation) {
			description.addProperty("display");
			description.addProperty("concept");

			return description;

		} else if (representation instanceof FullRepresentation) {

			description.addProperty("hydramoduleFormTagMaps");
			description.addProperty("hydramoduleComponentFormses");

			description.addProperty("program");
			description.addProperty("form");

			description.addProperty("display");
			description.addProperty("concept");

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
		description.addProperty("form");
		description.addProperty("name");
		description.addProperty("uuid");
		description.addProperty("hydramoduleFormId");
		description.addProperty("retired");

		return description;

	}

}
