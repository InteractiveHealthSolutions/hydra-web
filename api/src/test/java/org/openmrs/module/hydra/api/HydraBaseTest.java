package org.openmrs.module.hydra.api;

import org.openmrs.Provider;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 * 
 * @author owais.hussain@ihsinformatics.com
 */
public class HydraBaseTest extends BaseModuleContextSensitiveTest {

	protected static final String DATA_XML = "HydraService-initialData.xml";

	protected Provider rowling;

	protected HydramoduleComponent preAdmission, admission, orientation;

	/**
	 * Initialize all data objects before each test
	 * 
	 * @throws Exception on Exception
	 */
	public void initTestData() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet(DATA_XML);
		
		
		preAdmission = new HydramoduleComponent();
		preAdmission.setComponentId(1);
		preAdmission.setName("Pre-Admission");
		preAdmission.setDescription("Pre-Admission component");
		preAdmission.setRetired(Boolean.FALSE);
		preAdmission.setUuid("aaaaaaaa-bbbb-cccc-dddd-abcdef100008");

		admission = new HydramoduleComponent();
		preAdmission.setComponentId(2);
		preAdmission.setName("Admission");
		preAdmission.setDescription("Admission component");
		preAdmission.setRetired(Boolean.FALSE);
		preAdmission.setUuid("aaaaaaaa-bbbb-cccc-dddd-abcdef100009");
		
		orientation = new HydramoduleComponent();
		admission = new HydramoduleComponent();
		preAdmission.setComponentId(3);
		preAdmission.setName("Orientation");
		preAdmission.setDescription("Orientation component");
		preAdmission.setRetired(Boolean.TRUE);
		preAdmission.setUuid("aaaaaaaa-bbbb-cccc-dddd-abcdef100010");

	}
}
