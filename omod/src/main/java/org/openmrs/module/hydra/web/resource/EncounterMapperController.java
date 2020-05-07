package org.openmrs.module.hydra.web.resource;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleEncounterMapper;
import org.openmrs.module.hydra.model.workflow.HydramoduleEventAsset;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/hydra/encounterMapper", supportedClass = HydramoduleEncounterMapper.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*,2.2.*" })
public class EncounterMapperController extends DataDelegatingCrudResource<HydramoduleEncounterMapper> {

	private final Log log = LogFactory.getLog(getClass());

	private HydraService service = Context.getService(HydraService.class);

	@Override
	public HydramoduleEncounterMapper newDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HydramoduleEncounterMapper save(HydramoduleEncounterMapper delegate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HydramoduleEncounterMapper getByUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String queryParam = context.getParameter("q");
		List<HydramoduleEncounterMapper> encounterMapper = service.getEncounterMapperByPatient(queryParam);
		return encounterMapper != null ? new NeedsPaging<HydramoduleEncounterMapper>(encounterMapper, context) : null;
	}

	@Override
	protected void delete(HydramoduleEncounterMapper delegate, String reason, RequestContext context)
	        throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purge(HydramoduleEncounterMapper delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();

		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addSelfLink();
		description.addProperty("uuid");
		description.addProperty("encounterMapperId");
		description.addProperty("orderEncounterId", Representation.REF);
		description.addProperty("resultEncounterId", Representation.REF);

		if (representation instanceof DefaultRepresentation) {

			return description;

		} else if (representation instanceof FullRepresentation) {

			description.addProperty("dateCreated");

			description.addProperty("changedBy");
			description.addProperty("dateChanged");

			description.addProperty("voided");
			description.addProperty("dateVoided");
			description.addProperty("voidedBy");
			description.addProperty("voidReason");

			return description;
		}
		return description;
	}

}
