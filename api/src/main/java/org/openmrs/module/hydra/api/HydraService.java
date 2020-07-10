package org.openmrs.module.hydra.api;

public interface HydraService {

	void setHydraAssetService(IHydraAssetService hydraAssetService);

	void setHydraComponentService(IHydraComponentService hydraComponentService);

	void setHydraEventService(IHydraEventService hydraEventService);

	void setHydraFieldService(IHydraFieldService hydraFieldService);

	void setHydraFormService(IHydraFormService hydraFormService);

	void setHydraParticipantService(IHydraParticipantService hydraParticipantService);

	void setHydraPhaseService(IHydraPhaseService hydraPhaseService);

	void setHydraServiceService(IHydraServiceService hydraServiceService);

	void setHydraWorkflowService(IHydraWorkflowService hydraWorkflowService);

	IHydraAssetService getHydraAssetService();

	IHydraComponentService getHydraComponentService();

	IHydraEventService getHydraEventService();

	IHydraFieldService getHydraFieldService();

	IHydraFormService getHydraFormService();

	IHydraParticipantService getHydraParticipantService();

	IHydraPhaseService getHydraPhaseService();

	IHydraServiceService getHydraServiceService();

	IHydraWorkflowService getHydraWorkflowService();

}
