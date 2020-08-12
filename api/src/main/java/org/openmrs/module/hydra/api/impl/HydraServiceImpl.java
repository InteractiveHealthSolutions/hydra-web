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

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.IHydraAssetService;
import org.openmrs.module.hydra.api.IHydraComponentService;
import org.openmrs.module.hydra.api.IHydraEventService;
import org.openmrs.module.hydra.api.IHydraFieldService;
import org.openmrs.module.hydra.api.IHydraFormService;
import org.openmrs.module.hydra.api.IHydraLocationTypeService;
import org.openmrs.module.hydra.api.IHydraParticipantService;
import org.openmrs.module.hydra.api.IHydraPhaseService;
import org.openmrs.module.hydra.api.IHydraServiceService;
import org.openmrs.module.hydra.api.IHydraWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;

public class HydraServiceImpl extends BaseOpenmrsService implements HydraService {

	@Autowired
	private IHydraComponentService hydraComponentService;

	@Autowired
	private IHydraAssetService hydraAssetService;

	@Autowired
	private IHydraEventService hydraEventService;

	@Autowired
	private IHydraFieldService hydraFieldService;

	@Autowired
	private IHydraFormService hydraFormService;

	@Autowired
	private IHydraParticipantService hydraParticipantService;

	@Autowired
	private IHydraPhaseService hydraPhaseService;

	@Autowired
	private IHydraWorkflowService hydraWorkflowService;

	@Autowired
	private IHydraServiceService hydraServiceService;

	@Autowired
	private IHydraLocationTypeService locationTypeService;

	@Override
	public void setHydraAssetService(IHydraAssetService hydraAssetService) {
		this.hydraAssetService = hydraAssetService;
	}

	@Override
	public void setHydraComponentService(IHydraComponentService hydraComponentService) {
		this.hydraComponentService = hydraComponentService;
	}

	@Override
	public void setHydraEventService(IHydraEventService hydraEventService) {
		this.hydraEventService = hydraEventService;
	}

	@Override
	public void setHydraFieldService(IHydraFieldService hydraFieldService) {
		this.hydraFieldService = hydraFieldService;
	}

	@Override
	public void setHydraFormService(IHydraFormService hydraFormService) {
		this.hydraFormService = hydraFormService;
	}

	@Override
	public void setHydraParticipantService(IHydraParticipantService hydraParticipantService) {
		this.hydraParticipantService = hydraParticipantService;
	}

	@Override
	public void setHydraPhaseService(IHydraPhaseService hydraPhaseService) {
		this.hydraPhaseService = hydraPhaseService;
	}

	@Override
	public void setHydraServiceService(IHydraServiceService hydraServiceService) {
		this.hydraServiceService = hydraServiceService;
	}

	@Override
	public void setHydraWorkflowService(IHydraWorkflowService hydraWorkflowService) {
		this.hydraWorkflowService = hydraWorkflowService;
	}

	@Override
	public IHydraAssetService getHydraAssetService() {
		return hydraAssetService;
	}

	@Override
	public IHydraComponentService getHydraComponentService() {
		return hydraComponentService;
	}

	@Override
	public IHydraEventService getHydraEventService() {
		return hydraEventService;
	}

	@Override
	public IHydraFieldService getHydraFieldService() {
		return hydraFieldService;
	}

	@Override
	public IHydraFormService getHydraFormService() {
		return hydraFormService;
	}

	@Override
	public IHydraParticipantService getHydraParticipantService() {
		return hydraParticipantService;
	}

	@Override
	public IHydraPhaseService getHydraPhaseService() {
		return hydraPhaseService;
	}

	@Override
	public IHydraServiceService getHydraServiceService() {
		return hydraServiceService;
	}

	@Override
	public IHydraWorkflowService getHydraWorkflowService() {
		return hydraWorkflowService;
	}

	@Override
	public void setHydraLocationTypeService(IHydraLocationTypeService hydraLocationTypeService) {
		this.locationTypeService = hydraLocationTypeService;
	}

	@Override
	public IHydraLocationTypeService getHydraLocationTypeService() {
		return locationTypeService;
	}

}
