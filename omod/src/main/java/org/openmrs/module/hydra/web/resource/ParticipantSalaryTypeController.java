package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleParticipantSalaryType;
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
        + "/hydra/participantSalaryType", supportedClass = HydramoduleParticipantSalaryType.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class ParticipantSalaryTypeController extends MetadataDelegatingCrudResource<HydramoduleParticipantSalaryType> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	// @Autowired
	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleParticipantSalaryType newDelegate() {
		return new HydramoduleParticipantSalaryType();
	}

	@Override
	public HydramoduleParticipantSalaryType save(HydramoduleParticipantSalaryType component) {
		System.out.println(component.getName());
		return service.saveParticipantSalaryType(component);
	}

	@Override
	public HydramoduleParticipantSalaryType getByUniqueId(String uuid) {
		return service.getParticipantSalaryType(uuid);
	}

	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		SimpleObject simpleObject = new SimpleObject();
		List<HydramoduleParticipantSalaryType> services = service.getAllParticipantSalaryTypes(true);
		services.addAll(service.getAllParticipantSalaryTypes(false));
		simpleObject.put("salaryTypes", ConversionUtil.convertToRepresentation(services, context.getRepresentation()));
		return simpleObject;
	}

	@Override
	public void purge(HydramoduleParticipantSalaryType component, RequestContext context) throws ResponseException {
		// service.purgeComponent(component);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addSelfLink();
		description.addProperty("display");
		description.addProperty("uuid");
		description.addProperty("salaryTypeId");
		description.addProperty("name");

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
		description.addProperty("name");
		description.addProperty("retired");
		description.addProperty("description");
		description.addProperty("uuid");
		description.addProperty("salaryTypeId");
		return description;

	}

}
