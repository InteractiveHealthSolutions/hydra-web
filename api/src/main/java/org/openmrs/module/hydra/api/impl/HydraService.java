/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hydra.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.api.APIException;
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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HydraService implements ApplicationContextAware {

	private static final Logger log = LoggerFactory.getLogger(HydraService.class);

	private ApplicationContext applicationContext;

	private static boolean refreshingHydraContext = false;

	private static final Object refreshingHydraContextLock = new Object();

	Map<Class, Object> services = new HashMap<Class, Object>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Returns the current proxy that is stored for the Class <code>cls</code>
	 * 
	 * @param cls
	 * @return Object that is a proxy for the <code>cls</code> class
	 */
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<? extends T> cls) {
		if (log.isTraceEnabled()) {
			log.trace("Getting service: " + cls);
		}

		// if the context is refreshing, wait until it is
		// done -- otherwise a null service might be returned
		synchronized (refreshingHydraContextLock) {
			try {
				while (refreshingHydraContext) {
					if (log.isDebugEnabled()) {
						log.debug("Waiting to get service: " + cls + " while the context is being refreshed");
					}

					refreshingHydraContextLock.wait();

					if (log.isDebugEnabled()) {
						log.debug("Finished waiting to get service " + cls + " while the context was being refreshed");
					}
				}

			}
			catch (InterruptedException e) {
				log.warn("Refresh lock was interrupted", e);
			}
		}

		Object service = services.get(cls);
		if (service == null) {
			throw new APIException("Service not found: " + cls);
		}

		return (T) service;
	}

	public void setService(Class cls, Object classInstance) {

		log.debug("Setting service: " + cls);

		if (cls != null && classInstance != null) {
			// try {
			// Advised cachedService = (Advised) services.get(cls);
			// boolean noExistingService = cachedService == null;
			// boolean replacingService = cachedService != null && cachedService !=
			// classInstance;
			// boolean serviceAdvised = classInstance instanceof Advised;
			//
			// if (noExistingService || replacingService) {
			//
			// Advised advisedService;
			//
			// if (!serviceAdvised) {
			// // Adding a bare service, wrap with AOP proxy
			// Class[] interfaces = { cls };
			// ProxyFactory factory = new ProxyFactory(interfaces);
			// factory.setTarget(classInstance);
			// advisedService = (Advised)
			// factory.getProxy(OpenmrsClassLoader.getInstance());
			// } else {
			// advisedService = (Advised) classInstance;
			// }
			//
			// if (replacingService) {
			// moveAddedAOP(cachedService, advisedService);
			// }

			services.put(cls, classInstance);
		}
		log.debug("Service: " + cls + " set successfully");
		// }
		// catch (Exception e) {
		// throw new APIException("service.unable.create.proxy.factory", new Object[] {
		// classInstance.getClass()
		// .getName() }, e);
		// }

		// }
	}

	public void setComponentService(IHydraComponentService componentService) {
		setService(IHydraComponentService.class, componentService);
	}

	public void setEventService(IHydraEventService eventService) {
		setService(IHydraEventService.class, eventService);
	}

	public void setFieldService(IHydraFieldService fieldService) {
		setService(IHydraFieldService.class, fieldService);
	}

	public void setHydraFormService(IHydraFormService formService) {
		setService(IHydraFormService.class, formService);
	}

	public void setParticipantService(IHydraParticipantService participantService) {
		setService(IHydraParticipantService.class, participantService);
	}

	public void setPhaseService(IHydraPhaseService phaseService) {
		setService(IHydraPhaseService.class, phaseService);
	}

	public void setServiceService(IHydraServiceService serviceService) {
		setService(IHydraServiceService.class, serviceService);
	}

	public void setWorkflowService(IHydraWorkflowService workflowService) {
		setService(IHydraWorkflowService.class, workflowService);
	}

	public void setAssetService(IHydraAssetService assetService) {
		setService(IHydraAssetService.class, assetService);
	}

	public IHydraComponentService getComponentService() {
		return getService(IHydraComponentService.class);
	}

	public IHydraEventService getEventService() {
		return getService(IHydraEventService.class);
	}

	public IHydraFieldService getFieldService() {
		return getService(IHydraFieldService.class);
	}

	public IHydraFormService getHydraFormService() {
		return getService(IHydraFormService.class);
	}

	public IHydraParticipantService getParticipantService() {
		return getService(IHydraParticipantService.class);
	}

	public IHydraPhaseService getPhaseService() {
		return getService(IHydraPhaseService.class);
	}

	public IHydraServiceService getServiceService() {
		return getService(IHydraServiceService.class);
	}

	public IHydraWorkflowService getWorkflowService() {
		return getService(IHydraWorkflowService.class);
	}

	public IHydraAssetService getAssetService() {
		return getService(IHydraAssetService.class);
	}

	private static class HydraServiceContextHolder {

		private HydraServiceContextHolder() {
		}

		private static HydraService instance = null;
	}

	/**
	 * There should only be one ServiceContext per openmrs (java virtual machine). This method should be
	 * used when wanting to fetch the service context Note: The ServiceContext shouldn't be used
	 * independently. All calls should go through the Context
	 * 
	 * @return This VM's current ServiceContext.
	 * @see org.openmrs.api.context.Context
	 */
	public static HydraService getInstance() {
		if (HydraServiceContextHolder.instance == null) {
			HydraServiceContextHolder.instance = new HydraService();
		}

		return HydraServiceContextHolder.instance;
	}

}
