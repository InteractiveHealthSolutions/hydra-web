package org.openmrs.module.hydra.api.dao;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraBaseTest;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HydraDaoTest extends HydraBaseTest {

	@Autowired
	HydraDaoImpl dao;

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
	}

	@Test
	public final void shouldGetActiveComponents() {
		Context.clearSession();
		List<HydramoduleComponent> activeComponents = Arrays.asList(preAdmission, admission);
		List<HydramoduleComponent> list = dao.getAllComponents();
		assertTrue(list.size() == activeComponents.size());
		assertThat(list, Matchers.not(Matchers.hasItem(orientation)));
	}

	@Test
	public void shouldGetAllAssetCategories() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllAssets() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllAssetTypes() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllComponentFormRelations() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllComponentFormRelationsHydramoduleForm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllComponents() {
		fail("No method available to get all components.");
	}

	@Test
	public void shouldGetAllFieldAnswersByID() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllFieldsByName() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleEncounterMapper() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleEventAssets() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleEventParticipants() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleEvents() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleEventScedules() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleEventServices() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldGetAllHydramoduleEventTypes() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleFieldAnswers() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleFieldRules() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleFields() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleFormFieldGroups() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramodulePatientWorkflows() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleRuleTokens() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllHydramoduleUserWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllModuleForm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllModuleFormByComponentUUID() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllParticipants() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllParticipantSalaryTypes() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllPhaseComponentRelations() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllPhases() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllPhasesWorkFlowPhaseRelations() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllServices() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllServiceTypes() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAllWorkflows() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAsset() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAssetCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetAssetType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetComponent() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetComponentFormRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetEncounterMapperByPatient() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetFieldAnswers() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetFormField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetFormFieldsHydramoduleForm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetFormFieldsInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydraFormByUuid() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydraFormsByTag() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEncounterMapper() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEventAsset() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEventInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEventParticipant() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEventScedule() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEventService() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEventString() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleEventType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleFieldAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleFieldRule() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleFieldRuleByTargetField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleFieldRuleByTargetFormField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydraModuleFormByTag() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleFormField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleFormFieldGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramodulePatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleRuleToken() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleUserWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetModuleForm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetParticipant() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetParticipantByUser() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetParticipantSalaryType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPhaseComponent() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetPhaseComponentRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetService() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetServiceType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetUserWorkflowByUser() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetWorkflowPhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetWorkflowPhaseRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeFieldAnswersHydramoduleField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeFieldAnswersInt() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeFieldRules() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeFormFieldsHydramoduleForm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeFormFieldsListOfHydramoduleFormField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgePhaseComponent() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeRuleTokensByFieldRuleId() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeWorkflowPhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveAsset() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveAssetCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveAssetType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveComponent() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveComponentFormRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveForm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveFormEncounter() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveFormField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleDTOFieldAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleEncounterMapper() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleEvent() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleEventAsset() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleEventParticipant() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleEventScedule() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleEventService() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleEventType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleField() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleFieldAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleFieldRule() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleFormFieldGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramodulePatientWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleRuleToken() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleUserWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveModuleForm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveParticipant() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveParticipantSalaryType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSavePhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSavePhaseComponentsRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveService() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveServiceType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveWorkflow() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveWorkflowPhase() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveWorkflowPhaseRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUpdateComponent() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUpdateComponentForm() {
		fail("Not yet implemented");
	}
}
