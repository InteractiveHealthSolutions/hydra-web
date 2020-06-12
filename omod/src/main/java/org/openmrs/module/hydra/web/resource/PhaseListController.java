package org.openmrs.module.hydra.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramodulePhase;
import org.openmrs.module.hydra.model.list_holders.PhasesList;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/hydra/phases", supportedClass = PhasesList.class, supportedOpenmrsVersions = {
        "2.0.*,2.1.*" })
public class PhaseListController extends DataDelegatingCrudResource<PhasesList> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private HydraService service = Context.getService(HydraService.class);

	@Override
	public PhasesList newDelegate() {
		return new PhasesList();
	}

	@Override
	public PhasesList save(PhasesList labTestAttribute) {
		/*
		 * List<PhasesList> listLabTestAttributes =
		 * service.saveLabTestAttributes(labTestAttribute .getListAttributes());
		 * PhasesList labTestPackage = new PhasesList();
		 * labTestPackage.setListAttributes(listLabTestAttributes);
		 */
		return null;
	}

	@Override
	public PhasesList getByUniqueId(String uniqueId) {
		List<HydramodulePhase> phases = service.getAllPhases();
		return PhasesList.createFromPhases(phases);
	}

	@Override
	protected void delete(PhasesList delegate, String reason, RequestContext context) throws ResponseException {

	}

	@Override
	public void purge(PhasesList delegate, RequestContext context) throws ResponseException {

	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		return null;
	}

}
