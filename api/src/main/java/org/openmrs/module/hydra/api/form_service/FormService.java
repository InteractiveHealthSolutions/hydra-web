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
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.model.workflow.HydramoduleDTOFormSubmissionData;

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
		ADDRESS
	}

	public void createNewForm(HydramoduleDTOFormSubmissionData formSubmissionData)
	        throws ParseException, org.json.simple.parser.ParseException {
		JSONParser parser = new JSONParser();
		JSONArray data = (JSONArray) parser.parse(formSubmissionData.getData());
		JSONObject metadata = (JSONObject) parser.parse(formSubmissionData.getMetadata());

		createNewForm(data, metadata);
	}

	public void createNewForm(JSONArray data, JSONObject metadata) throws ParseException {

		// Getting user info
		String username;
		String password;
		String providerUUID;
		JSONObject authentication = (JSONObject) metadata.get("authentication");
		username = (String) authentication.get("USERNAME");
		password = (String) authentication.get("PASSWORD");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + username);
		SecretKey sec;
		String decPassword = null;
		try {
			sec = AES256Endec.getInstance().generateKey();
			decPassword = AES256Endec.getInstance().decrypt(password, sec);
		}
		catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% PASS" + decPassword);
		providerUUID = (String) authentication.get("provider");
		Context.authenticate(username, decPassword);

		// getting EncounterType String
		String encounterTypeString = (String) metadata.get(ParamNames.ENCOUNTER_TYPE);
		if (encounterTypeString.equals("Create Patient")) {
			createPatient(encounterTypeString, data);
			return;
		} else {
			JSONObject patientJson = (JSONObject) metadata.get("patient");

			PersonService personService = Context.getPersonService();
			ConceptService conceptService = Context.getConceptService();
			PatientService patientService = Context.getPatientService();
			EncounterService encounterService = Context.getEncounterService();
			LocationService locationService = Context.getLocationService();

			EncounterType encounterType = null;
			encounterType = encounterService.getEncounterType(encounterTypeString);

			Location location = null;

			List<Obs> obsList = new ArrayList();
			List<PersonAttribute> personAttributes = new ArrayList();
			List<PatientIdentifier> patientIdentifiers = new ArrayList();
			PersonName personName = null;
			SortedSet<PersonName> names = new TreeSet<PersonName>();
			// Set<PersonName> names = new SortedM();

			Date dateEntered = null;
			for (int i = 0; i < data.size(); i++) {
				JSONObject dataItem = (JSONObject) data.get(i);
				if (dataItem.containsKey(ParamNames.USERNAME) || dataItem.containsKey(ParamNames.PASSWORD))
					continue;
				if (!dataItem.containsKey(ParamNames.DATA_TYPE))
					continue;
				DATA_TYPE dataType = DATA_TYPE.valueOf(dataItem.get(ParamNames.DATA_TYPE).toString());
				switch (dataType) {
					case OBS: {
						String paramName = dataItem.get(ParamNames.PARAM_NAME).toString();
						String value = dataItem.get(paramName).toString();

						Concept concept = conceptService.getConcept(paramName);
						if (concept == null) {
							concept = createTextConcept(dataItem);
						}
						Obs obs = new Obs();
						obs.setConcept(concept);
						obs.setValueText(value);

						obsList.add(obs);
					}
						break;
					case OBS_DATE_TIME: {

						String paramNameDateTime = dataItem.get(ParamNames.PARAM_NAME).toString();
						String valueDateTime = dataItem.get(paramNameDateTime).toString();
						Date dateValue = Utils.formatterTimeDate.parse(valueDateTime);

						Concept conceptDateTime = conceptService.getConcept(paramNameDateTime);
						/*
						 * if(conceptDateTime == null) { conceptDateTime = createDateConcept(); }
						 */
						Obs obsDateTime = new Obs();
						obsDateTime.setConcept(conceptDateTime);
						obsDateTime.setValueDate(dateValue);
						obsDateTime.setValueDatetime(dateValue);
						obsList.add(obsDateTime);
					}
					case OBS_CODED: {

						String paramNameDateTime = dataItem.get(ParamNames.PARAM_NAME).toString();
						String valueDateTime = dataItem.get(paramNameDateTime).toString();
						Date dateValue = Utils.formatterTimeDate.parse(valueDateTime);

						Concept conceptDateTime = conceptService.getConcept(paramNameDateTime);
						/*
						 * if(conceptDateTime == null) { conceptDateTime = createDateConcept(); }
						 */
						Obs obsDateTime = new Obs();
						obsDateTime.setConcept(conceptDateTime);
						obsDateTime.setValueDate(dateValue);
						obsDateTime.setValueDatetime(dateValue);
						obsList.add(obsDateTime);
					}
					case OBS_CODED_MULTI: {

						String paramNameDateTime = dataItem.get(ParamNames.PARAM_NAME).toString();
						String valueDateTime = dataItem.get(paramNameDateTime).toString();
						Date dateValue = Utils.formatterTimeDate.parse(valueDateTime);

						Concept conceptDateTime = conceptService.getConcept(paramNameDateTime);
						/*
						 * if(conceptDateTime == null) { conceptDateTime = createDateConcept(); }
						 */
						Obs obsDateTime = new Obs();
						obsDateTime.setConcept(conceptDateTime);
						obsDateTime.setValueDate(dateValue);
						obsDateTime.setValueDatetime(dateValue);
						obsList.add(obsDateTime);
					}
						break;
					case LOCATION:
						String locationString = dataItem.get(dataItem.get(ParamNames.PARAM_NAME)).toString();
						location = findOrCreateLocation(locationString);
						break;

					case PERSON_ATTRIBUTE: {
						String attribType = dataItem.get(ParamNames.PARAM_NAME).toString();
						String attribValue = dataItem.get(attribType).toString();

						PersonAttributeType attributeType = personService.getPersonAttributeTypeByName(attribType);
						if (attributeType == null) {
							attributeType = createStringAttributeType(dataItem);
						}

						PersonAttribute personAttrib = new PersonAttribute();
						personAttrib.setAttributeType(attributeType);
						personAttrib.setValue(attribValue);

						personAttributes.add(personAttrib);
					}
						break;
					case DATE_ENTERED: {
						String date = dataItem.get(dataItem.get(ParamNames.PARAM_NAME).toString()).toString();
						try {
							dateEntered = Utils.formatterTimeDate.parse(date);
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
					default:
						break;
				}
			}

			if (encounterType == null) {
				System.out.println("encounter type is null");
				return;
			}

			System.out.println(encounterType.getName());
			if (encounterType.getName().equals(ParamNames.ENCOUNTER_TYPE_TERMINATION)) {
				if (location == null)
					location = findOrCreateLocation("Termination");
			}

			if (location == null) {
				System.out.println("location is null");
				return;
			}

			{
				Patient patient = findPatient(patientIdentifiers.get(0).getIdentifier());
				Encounter encounter = new Encounter();
				encounter.setEncounterType(encounterType);
				encounter.setPatient(patient);
				encounter.setDateCreated(new Date());
				encounter.setEncounterDatetime(dateEntered);
				encounter.setLocation(location);
				for (Obs obs : obsList) {
					obs.setLocation(location);
					obs.setPerson(patient);
					encounter.addObs(obs);
				}

				encounterService.saveEncounter(encounter);
			}
		}
	}

	private void createPatient(String encounterTypeString, JSONArray data) throws ParseException {
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

		List<PersonAttribute> personAttributes = new ArrayList();
		List<PatientIdentifier> patientIdentifiers = new ArrayList();

		for (int i = 0; i < data.size(); i++) {
			JSONObject dataItem = (JSONObject) data.get(i);
			if (dataItem.containsKey(ParamNames.USERNAME) || dataItem.containsKey(ParamNames.PASSWORD))
				continue;
			if (!dataItem.containsKey(ParamNames.PAYLOAD_TYPE))
				continue;
			DATA_TYPE dataType = DATA_TYPE.valueOf(dataItem.get(ParamNames.PAYLOAD_TYPE).toString());
			System.out.println("CHanginf the way again!!!!!!!!!!! Data: " + data);
			switch (dataType) {
				case IDENTIFIER: {
					String identifierType = dataItem.get(ParamNames.PARAM_NAME).toString();
					String IdentifierValue = dataItem.get(identifierType).toString();
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
					String paramName = dataItem.get(ParamNames.VALUE).toString();
					JSONArray namesArray = (JSONArray) dataItem.get(paramName);
					String firstName = "";
					String lastName = "";

					for (int j = 0; j < namesArray.size(); j++) {
						JSONObject nameObj = (JSONObject) namesArray.get(j);
						if (nameObj.containsKey(ParamNames.GIVEN_NAME)) {
							firstName = nameObj.get(ParamNames.GIVEN_NAME).toString();
						} else if (nameObj.containsKey(ParamNames.FAMILY_NAME)) {
							lastName = nameObj.get(ParamNames.FAMILY_NAME).toString();
						}
					}
					personName = new PersonName();
					personName.setGivenName(firstName);
					personName.setFamilyName(lastName);
					names.add(personName);

				}
					break;
				case AGE: {
					String strAge = dataItem.get(dataItem.get(ParamNames.VALUE).toString()).toString();
					System.out.println(strAge);
					age = Integer.valueOf(strAge);
				}
					break;
				case GENDER: {
					gender = dataItem.get(dataItem.get(ParamNames.VALUE).toString()).toString();
					if (gender.toLowerCase().equals("male")) {
						gender = "M";
					} else {
						gender = "F";
					}
				}
					break;
				case DOB: {
					String date = dataItem.get(dataItem.get(ParamNames.VALUE).toString()).toString();
					dob = Utils.formatterTimeDate.parse(date);
				}
					break;
				case DATE_ENTERED: {
					String date = dataItem.get(dataItem.get(ParamNames.VALUE).toString()).toString();
					try {
						dateEntered = Utils.formatterTimeDate.parse(date);
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
				default:
					break;
			}
		}

		System.out.println("3333333333333333333333333333 Out of the loop");

		if (gender == null)
			gender = "male";
		if (gender == null || names.size() == 0 || patientIdentifiers.size() == 0) {
			return;
		}
		Patient patient = new Patient();
		patient.addName(personName);
		// patient.setNames(names);
		patient.setGender(gender);
		if (dob == null) {
			System.out.println(age);
			patient.setBirthdateFromAge(age, dateEntered);
		} else {
			System.out.println(dob);
			patient.setBirthdate(dob);
		}

		// patient.setDateCreated(dateEntered);
		for (PatientIdentifier patientIdentifieri : patientIdentifiers) {
			System.out.println("IIIIIIIIIIIIIIIII " + patientIdentifieri.getIdentifier());
			patientIdentifieri.setLocation(location);
			patient.addIdentifier(patientIdentifieri);
		}

		for (PersonAttribute attribute : personAttributes) {
			patient.addAttribute(attribute);
		}

		patient = patientService.savePatient(patient);
		/*
		 * Encounter encounter = new Encounter();
		 * encounter.setEncounterType(encounterType); encounter.setPatient(patient);
		 * encounter.setDateCreated(new Date());
		 * encounter.setEncounterDatetime(dateEntered); encounter.setLocation(location);
		 * for (Obs obs : obsList) { if (obs == null) {
		 * System.out.println("??????????????????????????? NULL OBS FOUND"); }
		 * obs.setLocation(location); obs.setPerson(patient); encounter.addObs(obs); }
		 * 
		 * encounterService.saveEncounter(encounter);
		 */
	}

	private PersonAttributeType createStringAttributeType(JSONObject dataItem) {
		String name = dataItem.get(ParamNames.PARAM_NAME).toString();
		String description = dataItem.get(ParamNames.QUESTION).toString();

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
		// List<PatientIdentifierType> typeList = new ArrayList();
		PatientService patientService = Context.getPatientService();
		// typeList.add(patientService.getPatientIdentifierTypeByName("SZC
		// ID"));
		List<Patient> patients = Context.getPatientService().getPatients(null, patientId,
		    patientService.getAllPatientIdentifierTypes(), false);
		if (patients.size() > 0) {
			return (Patient) patients.get(0);
		}
		return null;
	}

	public Location findOrCreateLocation(String location) {
		LocationService locationService = Context.getLocationService();
		Location oLocation = locationService.getLocation(location);
		if (oLocation == null) {
			oLocation = new Location();
			oLocation.setName(location);
			oLocation.setDateCreated(new Date());
			locationService.saveLocation(oLocation);
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
