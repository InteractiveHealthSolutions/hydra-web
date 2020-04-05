package org.openmrs.module.hydra.api.form_service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.crypto.SecretKey;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.Encounter;
import org.openmrs.EncounterRole;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.Relationship;
import org.openmrs.RelationshipType;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleDTOFormSubmissionData;
import org.openmrs.module.hydra.model.workflow.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.workflow.HydramodulePatientWorkflow;
import org.openmrs.module.hydra.model.workflow.HydramoduleWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;

public class FormService {

	private static FormService instance;

	public static FormService getInstance() {
		if (instance == null) {
			instance = new FormService();
		}

		return instance;
	}

	public static enum DATA_TYPE {
		IDENTIFIER,
		PERSON_ATTRIBUTE,
		NAME,
		OBS,
		OBS_CODED,
		OBS_NUMERIC,
		OBS_CODED_MULTI,
		OBS_DATE_TIME,
		LOCATION,
		ENCOUNTER_TYPE,
		AGE,
		GENDER,
		DOB,
		DATE_ENTERED,
		ADDRESS,
		CONTACT_TRACING
	}

	HydraService service;

	public synchronized void createNewForm(HydraService service, HydramoduleDTOFormSubmissionData formSubmissionData)
	        throws ParseException, org.json.simple.parser.ParseException {
		this.service = service;
		JSONParser parser = new JSONParser();

		JSONArray data = (JSONArray) parser.parse(formSubmissionData.getData());
		JSONObject metadata = (JSONObject) parser.parse(formSubmissionData.getMetadata());
		System.out.println(data + "\n" + metadata);
		createNewForm(data, metadata);
	}

	public synchronized void createNewForm(JSONArray data, JSONObject metadata) throws ParseException {

		// Getting user info
		String username;
		String password;
		String providerUUID;
		JSONObject authentication = (JSONObject) metadata.get("authentication");
		JSONObject formDetails = (JSONObject) metadata.get("formDetails");
		username = (String) authentication.get("USERNAME");
		password = (String) authentication.get("PASSWORD");
		SecretKey sec;
		String decPassword = null;
		try {
			sec = AES256Endec.getInstance().generateKey();
			decPassword = AES256Endec.getInstance().decrypt(password, sec);
		}
		catch (Exception e1) {
			e1.printStackTrace();
			// return;
		}

		providerUUID = (String) authentication.get("provider");
		try {
			Context.authenticate(username, decPassword);
		}
		catch (ContextAuthenticationException exception) {
			Context.authenticate(username, password);
		}
		// getting EncounterType String
		String encounterTypeString = (String) metadata.get(ParamNames.ENCOUNTER_TYPE);
		String workflowUUID = (String) metadata.get(ParamNames.WORKFLOW);
		// Patient Creation Form
		if (encounterTypeString.equals("Create Patient")) {
			createPatient(workflowUUID, encounterTypeString, data);
			return;
		}
		// Rest of the forms
		// TODO this should also moved to a separate function
		else {
			String patientIdentifier = null;
			JSONObject patientJson = (JSONObject) metadata.get("patient");
			JSONArray identifiersJSON = (JSONArray) patientJson.get("identifiers");
			if (identifiersJSON != null) {
				for (int i = 0; i < identifiersJSON.size(); i++) {
					JSONObject id = (JSONObject) identifiersJSON.get(i);
					patientIdentifier = (String) id.get(ParamNames.VALUE);
				}
			}
			// Fetching patient
			Patient patient = findPatient(patientIdentifier);
			// Getting the required service objects
			PersonService personService = Context.getPersonService();
			ConceptService conceptService = Context.getConceptService();
			PatientService patientService = Context.getPatientService();
			EncounterService encounterService = Context.getEncounterService();
			LocationService locationService = Context.getLocationService();

			EncounterType encounterType = null;
			encounterType = encounterService.getEncounterType(encounterTypeString);

			Location location = null;
			PersonAddress personAddress = new PersonAddress();

			List<Obs> obsList = new ArrayList();
			List<PersonAttribute> personAttributes = new ArrayList();
			List<PatientIdentifier> patientIdentifiers = new ArrayList();

			Date dateEntered = null;
			for (int i = 0; i < data.size(); i++) {
				JSONObject dataItem = (JSONObject) data.get(i);

				if (dataItem.containsKey(ParamNames.USERNAME) || dataItem.containsKey(ParamNames.PASSWORD))
					continue;
				if (!dataItem.containsKey(ParamNames.PAYLOAD_TYPE))
					continue;
				System.out.println("There " + dataItem);

				DATA_TYPE dataType = DATA_TYPE.valueOf(dataItem.get(ParamNames.PAYLOAD_TYPE).toString());
				switch (dataType) {
					case ADDRESS: {
						JSONObject addressObj = (JSONObject) dataItem.get(ParamNames.VALUE);
						personAddress = new PersonAddress();
						personAddress.setStateProvince(addressObj.get("Province/State").toString());
						personAddress.setCityVillage(addressObj.get("City/Village").toString());
						personAddress.setAddress2(addressObj.get("address2").toString()); // open
						                                                                  // address
						personAddress.setAddress3(addressObj.get("address3").toString()); // nearest
						                                                                  // landmark
					}
						break;
					case OBS: {

						String paramName = dataItem.get(ParamNames.PARAM_NAME).toString();
						String value = dataItem.get(ParamNames.VALUE).toString();

						Concept concept = conceptService.getConceptByUuid(paramName);
						if (concept == null) {
							System.out.println(paramName);
							break;
							// concept = createTextConcept(dataItem);
						}
						if (!value.isEmpty()) {
							Obs obs = new Obs();
							obs.setConcept(concept);
							obs.setValueText(value);
							obsList.add(obs);
						}
					}
						break;
					case OBS_NUMERIC: {

						String paramName = dataItem.get(ParamNames.PARAM_NAME).toString();
						String value = dataItem.get(ParamNames.VALUE).toString();

						Concept concept = conceptService.getConceptByUuid(paramName);
						if (concept == null) {
							break;
						}
						if (!value.isEmpty()) {
							Obs obs = new Obs();
							obs.setConcept(concept);
							obs.setValueNumeric(Double.parseDouble(value));
							obsList.add(obs);
						}
					}
						break;
					case OBS_DATE_TIME: {
						String paramNameDateTime = dataItem.get(ParamNames.PARAM_NAME).toString();
						String valueDateTime = dataItem.get(ParamNames.VALUE).toString();
						Date dateValue = Utils.openMrsDateFormat.parse(valueDateTime);

						Concept conceptDateTime = conceptService.getConceptByUuid(paramNameDateTime);
						/*
						 * if(conceptDateTime == null) { conceptDateTime = createDateConcept(); }
						 */
						if (dateValue != null) {
							Obs obsDateTime = new Obs();
							obsDateTime.setConcept(conceptDateTime);
							obsDateTime.setValueDatetime(dateValue);
							obsList.add(obsDateTime);
						}
					}
						break;
					case OBS_CODED: {
						String questionConceptStr = (String) dataItem.get(ParamNames.PARAM_NAME);
						String valueConceptStr = dataItem.get(ParamNames.VALUE).toString();

						Concept questionConcept = conceptService.getConceptByUuid(questionConceptStr);
						Concept valueConcept = conceptService.getConceptByUuid(valueConceptStr);
						/*
						 * if(conceptDateTime == null) { conceptDateTime = createDateConcept(); }
						 */

						Obs obsCoded = new Obs();
						obsCoded.setConcept(questionConcept);
						obsCoded.setValueCoded(valueConcept);
						obsList.add(obsCoded);
					}
						break;
					case OBS_CODED_MULTI: {
						// TODO

						// Creating a unique value group id
						Calendar c = Calendar.getInstance();
						int valueGroupId = c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH)
						        + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND)
						        + c.get(Calendar.MILLISECOND) + (i + 1);

						String questionConceptStr = (String) dataItem.get(ParamNames.PARAM_NAME);
						String valueConceptStr = dataItem.get(ParamNames.VALUE).toString();

						Concept questionConcept = conceptService.getConceptByUuid(questionConceptStr);
						Concept valueConcept = conceptService.getConceptByUuid(valueConceptStr);

						Obs obsCoded = new Obs();
						obsCoded.setValueGroupId(valueGroupId);
						obsCoded.setConcept(questionConcept);
						obsCoded.setValueCoded(valueConcept);
						obsList.add(obsCoded);

					}
						break;
					case LOCATION:
						String locationString = dataItem.get(ParamNames.VALUE).toString();
						location = findLocation(locationString);
						break;

					case PERSON_ATTRIBUTE: {
						String attribType = (String) dataItem.get(ParamNames.PARAM_NAME);
						String attribValue = dataItem.get(ParamNames.VALUE).toString();

						PersonAttributeType attributeType = personService.getPersonAttributeTypeByName(attribType);
						if (attributeType == null) {
							attributeType = createStringAttributeType(attribType);
						}

						PersonAttribute personAttrib = new PersonAttribute();
						personAttrib.setAttributeType(attributeType);
						personAttrib.setValue(attribValue);

						personAttributes.add(personAttrib);
					}
						break;
					case DATE_ENTERED: {
						String date = dataItem.get(ParamNames.VALUE).toString();
						try {
							dateEntered = Utils.openMrsDateFormat.parse(date);
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(dateEntered);
							calendar.add(Calendar.MINUTE, -5);
							dateEntered = calendar.getTime();
						}
						catch (ParseException e) {
							e.printStackTrace();
						}
					}
						break;

					case CONTACT_TRACING: {
						JSONArray contactsArray = (JSONArray) dataItem.get(ParamNames.VALUE);
						boolean createPatient = (Boolean) dataItem.get("createPatient");
						
						double numberOfPeople = (Double) dataItem.get("numberOfPeople");
						Concept questionNumberOfContacts = conceptService.getConceptByUuid("0e594b8c-cd8c-4437-9c6e-bc4e30ec7598");
						Obs obsNumberOfContacts = new Obs();
						obsNumberOfContacts.setConcept(questionNumberOfContacts);
						obsNumberOfContacts.setValueNumeric(numberOfPeople);
						obsList.add(obsNumberOfContacts);
						

						List<String> identifiers = new ArrayList<String>();
						for (int j = 0; j < contactsArray.size(); j++) {
							JSONObject contactObj = (JSONObject) contactsArray.get(j);
							String identifier = (String) contactObj.get("patientID");
							if (identifiers.contains(identifier))
								continue;
							identifiers.add(identifier);

							String givenName = (String) contactObj.get("PatientFirstName");
							String familyName = (String) contactObj.get("PatientFamilyName");
							String gender = (String) contactObj.get("gender");
							String age = (String) contactObj.get("age");
							String dob = (String) contactObj.get("dob");
							String relationship = (String) contactObj.get("relation");

							Date birthDate = Utils.formatterDate.parse(dob);

							if (createPatient) {
								createContactPatient(patient, contactObj, location, workflowUUID, data);
							}

							// Creating a unique value group id
							
						Calendar c = Calendar.getInstance();
						int valueGroupId = (c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH)
								+ c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND)
								+ c.get(Calendar.MILLISECOND) + (i + 1)) * (j + 2);

						Concept questionConceptDOB = conceptService
								.getConceptByUuid("160751AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
						Concept questionConceptGender = conceptService
								.getConceptByUuid("75a17321-fd69-47be-831e-cd773dc9c3cc");
						Concept questionConceptGivenName = conceptService
								.getConceptByUuid("85fc8b27-8a08-4bdc-bf91-e34af50265aa");
						Concept questionConceptFamilyName = conceptService
								.getConceptByUuid("82c65f7c-b9e8-4775-a8bd-f7ad55ab8bf0");
						Concept questionConceptIdentifier = conceptService
								.getConceptByUuid("a24d649b-fb89-4b8c-beeb-aacf2872cf22");
						Concept questionConceptRelationship = conceptService
								.getConceptByUuid("7579f93d-ebe7-423f-822b-dbe792248499");

						Obs obsDOB = new Obs();
						Obs obsGender = new Obs();
						Obs obsGivenName = new Obs();
						Obs obsFamilyName = new Obs();
						Obs obsIdentifier = new Obs();
						Obs obsRelationship = new Obs();

						obsDOB.setValueGroupId(valueGroupId);
						obsGender.setValueGroupId(valueGroupId);
						obsGivenName.setValueGroupId(valueGroupId);
						obsFamilyName.setValueGroupId(valueGroupId);
						obsIdentifier.setValueGroupId(valueGroupId);
						obsRelationship.setValueGroupId(valueGroupId);

						obsDOB.setConcept(questionConceptDOB);
						obsGender.setConcept(questionConceptGender);
						obsGivenName.setConcept(questionConceptGivenName);
						obsFamilyName.setConcept(questionConceptFamilyName);
						obsIdentifier.setConcept(questionConceptIdentifier);
						obsRelationship.setConcept(questionConceptRelationship);

						obsDOB.setValueDate(birthDate);
						obsGender.setValueText(gender);
						obsGivenName.setValueText(givenName);
						obsFamilyName.setValueText(familyName);
						obsIdentifier.setValueText(identifier);
						obsRelationship.setValueText(relationship);

						obsList.add(obsDOB);
						obsList.add(obsGender);
						obsList.add(obsGivenName);
						obsList.add(obsFamilyName);
						obsList.add(obsIdentifier);
						obsList.add(obsRelationship);
							

						}
					}
						break;
					default:
						break;
				}
			}

			if (encounterType == null) {
				System.out.println("encounter type is null");
				return;
			}

			if (location == null) {
				System.out.println("location is null");
				return;
			}

			{

				if (patient != null) {
					// Saving address
					personAddress.setPerson(patient);
					personService.savePersonAddress(personAddress);
					// Preparing encounter
					Encounter encounter = new Encounter();
					encounter.setEncounterType(encounterType);
					encounter.setPatient(patient);
					encounter.setDateCreated(new Date());
					encounter.setEncounterDatetime(dateEntered);
					encounter.setLocation(location);

					// Adding obs in encounter
					for (int i = 0; i < obsList.size(); i++) {
						Obs obs = obsList.get(i);
						obs.setLocation(location);
						obs.setPerson(patient);
						encounter.addObs(obs);
					}

					// Setting provider for encounter
					EncounterRole encounterRole = encounterService.getEncounterRole(1);
					Provider provider = Context.getProviderService().getProviderByUuid(providerUUID);
					if (encounterRole != null && provider != null)
						encounter.setProvider(encounterRole, provider);

					// Saving encounter
					Encounter savedEncoounter = encounterService.saveEncounter(encounter);

					// Mapping encounter with workflow
					HydramoduleFormEncounter formEncounter = new HydramoduleFormEncounter();
					String formDetailsUUID = formDetails.get("uuid").toString(); // UUID
					                                                             // of
					                                                             // componentForm
					System.out.println("FormDetailsUUID: " + formDetailsUUID);
					HydramoduleComponentForm componentForm = service.getComponentFormByUUID(formDetailsUUID);
					if (componentForm != null) {
						formEncounter.setComponentForm(componentForm);
						formEncounter.setEncounter(savedEncoounter);
						service.saveFormEncounter(formEncounter);
					}
				} else {
					System.out.println("Patient is null");
					return;
				}
			}
		}
	}

	private void createContactPatient(Patient indexPatient, JSONObject contactObj, Location location, String workflowUUID,
	        JSONArray data) throws ParseException {
		PersonService personService = Context.getPersonService();
		ConceptService conceptService = Context.getConceptService();
		PatientService patientService = Context.getPatientService();
		EncounterService encounterService = Context.getEncounterService();
		LocationService locationService = Context.getLocationService();

		String identifier = (String) contactObj.get("patientID");
		List<PatientIdentifier> patientIdentifiers = new ArrayList<PatientIdentifier>();
		PatientIdentifierType patientIdentifierType = patientService.getPatientIdentifierTypeByName("Patient Identifier");
		PatientIdentifier patientIdentifier = new PatientIdentifier();
		patientIdentifier.setIdentifier(identifier);
		patientIdentifier.setIdentifierType(patientIdentifierType);
		patientIdentifier.setPreferred(true);
		patientIdentifiers.add(patientIdentifier);

		String givenName = (String) contactObj.get("patientGivenName");
		String familyName = (String) contactObj.get("patientFamilyName");
		SortedSet<PersonName> names = new TreeSet<PersonName>();
		PersonName personName = new PersonName();
		personName.setGivenName(givenName);
		personName.setFamilyName(familyName);
		names.add(personName);

		String gender = (String) contactObj.get("gender");
		String dob = (String) contactObj.get("age");
		String birthDate = (String) contactObj.get("dob");
		String relationship = (String) contactObj.get("relation");

		gender = gender.toLowerCase().startsWith("m") ? "M" : "F";

		int age = 0;

		if (gender == null)
			gender = "male";
		if (gender == null || names.size() == 0 || patientIdentifiers.size() == 0) {
			return;
		}

		Patient patient = new Patient();
		patient.addName(personName);
		// patient.setNames(names);
		patient.setGender(gender);

		// System.out.println();
		Date dateOfBirth = Utils.formatterDate.parse(birthDate);
		patient.setBirthdate(dateOfBirth);

		// patient.setDateCreated(dateEntered);
		for (PatientIdentifier patientIdentifieri : patientIdentifiers) {
			System.out.println("IIIIIIIIIIIIIIIII " + patientIdentifieri.getIdentifier());
			patientIdentifieri.setLocation(location);
			patient.addIdentifier(patientIdentifieri);
		}

		System.out.println(patient.getBirthdate());
		System.out.println(patient.getGender());
		System.out.println(patient.getPerson().getGivenName());
		patient = patientService.savePatient(patient);
		if (patient != null) {
			HydramoduleWorkflow workflow = service.getWorkflowByUUID(workflowUUID);
			if (workflow != null) {
				HydramodulePatientWorkflow patientWorkflow = new HydramodulePatientWorkflow();
				patientWorkflow.setWorkflow(workflow);
				patientWorkflow.setPatient(patient);
				service.saveHydramodulePatientWorkflow(patientWorkflow);
			}

			// Save relationship
			RelationshipType relationshipType = personService.getRelationshipTypeByName(relationship);
			if (relationshipType != null) {
				Relationship relationshipObj = new Relationship(indexPatient.getPerson(), patient.getPerson(),
				        relationshipType);
				personService.saveRelationship(relationshipObj);
			}
		}
	}

	private void createPatient(String workflowUUID, String encounterTypeString, JSONArray data) throws ParseException {
		PersonName personName = new PersonName();
		SortedSet<PersonName> names = new TreeSet<PersonName>();
		String gender = "M";
		int age = 0;
		Date dob = new Date();
		Date dateEntered = new Date();
		PersonService personService = Context.getPersonService();
		ConceptService conceptService = Context.getConceptService();
		PatientService patientService = Context.getPatientService();
		EncounterService encounterService = Context.getEncounterService();
		LocationService locationService = Context.getLocationService();

		EncounterType encounterType = new EncounterType();
		encounterType = encounterService.getEncounterType(encounterTypeString);
		Location location = new Location();

		List<PersonAttribute> personAttributes = new ArrayList<PersonAttribute>();

		List<PatientIdentifier> patientIdentifiers = new ArrayList<PatientIdentifier>();

		for (int i = 0; i < data.size(); i++) {

			JSONObject dataItem = (JSONObject) data.get(i);
			System.out.println(dataItem);
			if (dataItem.containsKey(ParamNames.USERNAME) || dataItem.containsKey(ParamNames.PASSWORD))
				continue;
			if (!dataItem.containsKey(ParamNames.PAYLOAD_TYPE))
				continue;
			DATA_TYPE dataType = DATA_TYPE.valueOf(dataItem.get(ParamNames.PAYLOAD_TYPE).toString());
			switch (dataType) {
				case IDENTIFIER: {

					String identifierType = dataItem.get(ParamNames.PARAM_NAME).toString();

					String IdentifierValue = dataItem.get(ParamNames.VALUE).toString();
					System.out.println("Identifier: " + IdentifierValue);
					PatientIdentifierType patientIdentifierType = patientService
					        .getPatientIdentifierTypeByName(identifierType);

					PatientIdentifier patientIdentifier = new PatientIdentifier();
					patientIdentifier.setIdentifier(IdentifierValue);
					patientIdentifier.setIdentifierType(patientIdentifierType);

					patientIdentifier.setPreferred(true);

					patientIdentifiers.add(patientIdentifier);

				}
					break;

				case NAME: {
					String firstName = "";
					String lastName = "";

					if (dataItem.containsKey(ParamNames.GIVEN_NAME)) {
						firstName = dataItem.get(ParamNames.GIVEN_NAME).toString();
					}
					if (dataItem.containsKey(ParamNames.FAMILY_NAME)) {
						lastName = dataItem.get(ParamNames.FAMILY_NAME).toString();
					}
					System.out.println("First Name: " + firstName + " LastName: " + lastName);
					personName = new PersonName();
					personName.setGivenName(firstName);
					personName.setFamilyName(lastName);
					names.add(personName);

				}
					break;
				case AGE: {
					String strAge = dataItem.get(ParamNames.VALUE).toString();
					System.out.println(strAge);
					age = Integer.valueOf(strAge);
				}
					break;
				case GENDER: {
					gender = dataItem.get(ParamNames.VALUE).toString();
					if (gender.toLowerCase().equals("male")) {
						gender = "M";
					} else {
						gender = "F";
					}

					System.out.println("Gender: " + gender);
				}
					break;
				case DOB: {
					String date = dataItem.get(ParamNames.VALUE).toString();
					dob = Utils.openMrsDateFormat.parse(date);
					System.out.println("date of birth: " + dob.toString());
				}
					break;
				case DATE_ENTERED: {
					String date = dataItem.get(ParamNames.VALUE).toString();
					try {
						dateEntered = Utils.openMrsDateFormat.parse(date);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dateEntered);
						calendar.add(Calendar.MINUTE, -5);
						dateEntered = calendar.getTime();
					}
					catch (ParseException e) {
						e.printStackTrace();
					}
				}
					break;
				case LOCATION:
					String locationString = dataItem.get(ParamNames.VALUE).toString();
					location = findLocation(locationString);
					break;
				default:
					break;
			}
		}

		if (gender == null)
			gender = "male";
		if (gender == null || names.size() == 0 || patientIdentifiers.size() == 0) {
			return;
		}
		Patient patient = new Patient();
		patient.addName(personName);
		// patient.setNames(names);
		patient.setGender(gender);

		System.out.println(dob);
		patient.setBirthdate(dob);

		// patient.setDateCreated(dateEntered);
		for (PatientIdentifier patientIdentifieri : patientIdentifiers) {
			System.out.println("IIIIIIIIIIIIIIIII " + patientIdentifieri.getIdentifier());
			patientIdentifieri.setLocation(location);
			patient.addIdentifier(patientIdentifieri);
		}

		for (PersonAttribute attribute : personAttributes) {
			patient.addAttribute(attribute);
		}

		System.out.println(patient.getBirthdate());
		System.out.println(patient.getGender());
		System.out.println(patient.getPerson().getGivenName());
		patient = patientService.savePatient(patient);
		if (patient != null) {
			HydramoduleWorkflow workflow = service.getWorkflowByUUID(workflowUUID);
			if (workflow != null) {
				HydramodulePatientWorkflow patientWorkflow = new HydramodulePatientWorkflow();
				patientWorkflow.setWorkflow(workflow);
				patientWorkflow.setPatient(patient);
				service.saveHydramodulePatientWorkflow(patientWorkflow);
			}
		}
		/*
		 * Encounter encounter = new Encounter();
		 * encounter.setEncounterType(encounterType); encounter.setPatient(patient);
		 * encounter.setDateCreated(new Date());
		 * encounter.setEncounterDatetime(dateEntered); encounter.setLocation(location);
		 * for (Obs obs : obsList) { if (obs == null) { System.out.println(
		 * "??????????????????????????? NULL OBS FOUND"); } obs.setLocation(location);
		 * obs.setPerson(patient); encounter.addObs(obs); }
		 * 
		 * encounterService.saveEncounter(encounter);
		 */
	}

	private PersonAttributeType createStringAttributeType(String dataItem) {
		String name = dataItem;
		String description = "Attribute type "+dataItem+" created by Hydra";

		PersonService personService = Context.getPersonService();
		PersonAttributeType type = new PersonAttributeType();
		type.setName(name);
		type.setDescription(description);
		type.setFormat("java.lang.String");

		personService.savePersonAttributeType(type);
		return type;
	}

	private Concept createTextConcept(JSONObject dataItem) {

		ConceptService conceptService = Context.getConceptService();

		ConceptDescription description = new ConceptDescription();
		description.setDescription(dataItem.get(ParamNames.QUESTION).toString());
		Set<ConceptDescription> descriptions = new HashSet();
		descriptions.add(description);

		ConceptName conceptName = new ConceptName();
		conceptName.setLocale(Locale.US);
		conceptName.setName(dataItem.get(ParamNames.PARAM_NAME).toString());

		ConceptClass conceptClass = conceptService.getConceptClassByName("Question");
		ConceptDatatype conceptDatatype = conceptService.getConceptDatatypeByName("Text");

		Concept concept = new Concept();
		concept.setFullySpecifiedName(conceptName);
		concept.setDescriptions(descriptions);
		concept.setConceptClass(conceptClass);
		concept.setDatatype(conceptDatatype);

		return conceptService.saveConcept(concept);
	}

	public Date findDOBFromAge(int age) {

		String toDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String dob = toDate.substring(4, 19);
		int currentYear = Integer.parseInt(toDate.substring(0, 4));
		dob = currentYear - age + dob;
		try {
			return Utils.openMrsDateFormat.parse(dob);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Patient findPatient(String patientId) {
		PatientService patientService = Context.getPatientService();
		List<PatientIdentifierType> typeList = patientService.getAllPatientIdentifierTypes();
		System.out.println("Type List: " + typeList.size() + "\n" + typeList.toArray().toString());

		List<Patient> patients = Context.getPatientService().getPatients(patientId);
		System.out.println("Identifier " + patientId + " " + patients.size());
		// return Context.getPatientService().getPatient(2844);
		if (patients.size() > 0) {
			return (Patient) patients.get(0);
		}
		return null;
	}

	public Location findLocation(String location) {
		LocationService locationService = Context.getLocationService();
		Location oLocation = locationService.getLocationByUuid(location);
		System.out.println("LOCATION UUID: " + location);
		if (oLocation == null) {
			return null;
		}
		return oLocation;
	}

	public List<Location> findLocations() {
		LocationService locationService = Context.getLocationService();
		List<Location> oLocation = locationService.getAllLocations();

		return oLocation;
	}

	public void validateUser(JSONObject jsonReponse, JSONArray data) {
		try {
			String username = JSONUtils.getInstance().getParamValue(data, "USERNAME");
			String password = JSONUtils.getInstance().getParamValue(data, "PASSWORD");
			Context.authenticate(username, password);
			User user = Context.getUserService().getUserByUsername(username);
			JSONArray rolesArray = new JSONArray();
			Set<Role> userRoles = user.getAllRoles();
			for (Role r : userRoles) {
				rolesArray.add(r.getRole());
			}
			jsonReponse.put("result", "success: Logedin");
			jsonReponse.put("roles", rolesArray);
		}
		catch (Exception e) {
			jsonReponse.put("result", "failure: Invalid Username or Password");
		}
	}

	public void getPatientData(String username, JSONArray data) {
		JSONUtils jsonUtils = JSONUtils.getInstance();
		String dateEntred = jsonUtils.getParamValue(data, "DATE_ENTERED");
		Date date = null;
		String patientMHId = jsonUtils.getParamValue(data, "PATIENT_MH_ID");
	}
}
