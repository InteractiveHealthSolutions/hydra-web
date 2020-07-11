package org.openmrs.module.hydra.api.impl;

import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.IHydraAssetService;
import org.openmrs.module.hydra.api.IHydraComponentService;
import org.openmrs.module.hydra.api.IHydraEventService;
import org.openmrs.module.hydra.api.IHydraFieldService;
import org.openmrs.module.hydra.api.IHydraFormService;
import org.openmrs.module.hydra.api.IHydraParticipantService;
import org.openmrs.module.hydra.api.IHydraPhaseService;
import org.openmrs.module.hydra.api.IHydraServiceService;
import org.openmrs.module.hydra.api.IHydraWorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ConditionContext;

public class HydraContext {

	private static final Logger log = LoggerFactory.getLogger(HydraContext.class);

	private static HydraService hydraService;

	static HydraService getServiceContext() {
		if (hydraService == null) {
			synchronized (HydraContext.class) {
				if (hydraService == null) {
					log.error("serviceContext is null.  Creating new ServiceContext()");
					hydraService = HydraService.getInstance();
				}
			}
		}
		log.trace("serviceContext: {}", hydraService);

		return HydraService.getInstance();
	}

	public void contextStartUp() {
		log.info("context initialized");
	}

	public void setHydraService(HydraService hs) {
		sethydraContext(hs);
	}

	public static void sethydraContext(HydraService hs) {
		hydraService = hs;
	}

	public static IHydraFormService getHydraFormService() {
		return getServiceContext().getHydraFormService();
	}

	public static IHydraParticipantService getHydraParticipantService() {
		return getServiceContext().getParticipantService();
	}

	public static IHydraComponentService getHydraComponentService() {
		return getServiceContext().getComponentService();
	}

	public static IHydraPhaseService getHydraPhaseService() {
		return getServiceContext().getPhaseService();
	}

	public static IHydraServiceService getHydraServiceService() {
		return getServiceContext().getServiceService();
	}

	public static IHydraWorkflowService getHydraWorkflowService() {
		return getServiceContext().getWorkflowService();
	}

	public static IHydraEventService getHydraEventService() {
		return getServiceContext().getEventService();
	}

	public static IHydraFieldService getHydraFieldService() {
		return getServiceContext().getFieldService();
	}

	public static IHydraAssetService getHydraAssetService() {
		return getServiceContext().getAssetService();
	}
}
