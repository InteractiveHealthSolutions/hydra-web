package org.openmrs.module.hydra.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponent;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	protected DbSessionFactory sessionFactory;	

	protected static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	protected User rowling;
	
	protected Provider rowlingProvider;

	protected HydramoduleComponent preAdmission, admission, orientation;

	protected HydramodulePhase search, treat, prevent;

	/**
	 * Initialize all data objects before each test
	 * 
	 * @throws Exception on Exception
	 */
	public void initTestData() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet(DATA_XML);

		initUser();
		initComponents();
		initPhases();
	}
	
	private void initUser() {
		rowling = (User) sessionFactory.getCurrentSession().get(User.class, 1000);
		rowlingProvider = (Provider) sessionFactory.getCurrentSession().get(Provider.class, 1000);
	}

	private void initComponents() throws ParseException {
		preAdmission = new HydramoduleComponent();
		preAdmission.setComponentId(1);
		preAdmission.setName("Pre-Admission");
		preAdmission.setDescription("Pre-Admission component");
		preAdmission.setRetired(Boolean.FALSE);
		preAdmission.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006050008");

		admission = new HydramoduleComponent();
		admission.setComponentId(2);
		admission.setName("Admission");
		admission.setDescription("Admission component");
		admission.setRetired(Boolean.FALSE);
		admission.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006050009");

		orientation = new HydramoduleComponent();
		orientation.setComponentId(3);
		orientation.setName("Orientation");
		orientation.setDescription("Orientation component");
		orientation.setRetired(Boolean.TRUE);
		orientation.setDateRetired(dateFormatter.parse("2020-06-04 00:00:00"));
		orientation.setRetireReason("Due to increasing cases of rigging");
		orientation.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006050010");
	}

	private void initPhases() throws ParseException {
		search = new HydramodulePhase();
		search.setPhaseId(1);
		search.setName("Search");
		search.setDescription("Wizard hunting for admissions");
		search.setRetired(Boolean.FALSE);
		search.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120014");

		treat = new HydramodulePhase();
		treat.setPhaseId(2);
		treat.setName("Treat");
		treat.setDescription("Learn some magic");
		treat.setRetired(Boolean.FALSE);
		treat.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120015");

		prevent = new HydramodulePhase();
		prevent.setPhaseId(3);
		prevent.setName("Prevent");
		prevent.setDescription("Do not become evil");
		prevent.setRetired(Boolean.FALSE);
		prevent.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120016");
	}
}
