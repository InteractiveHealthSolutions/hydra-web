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
import org.openmrs.PersonAddress;
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
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.model.workflow.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.workflow.HydramoduleDTOFormSubmissionData;
import org.openmrs.module.hydra.model.workflow.HydramoduleFormEncounter;
import org.springframework.beans.factory.annotation.Autowired;

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

	HydraService service;

	public void createNewForm(HydraService service, HydramoduleDTOFormSubmissionData formSubmissionData)
	        throws ParseException, org.json.simple.parser.ParseException {
		this.service = service;
		JSONParser parser = new JSONParser();

		JSONArray data = (JSONArray) parser.parse(formSubmissionData.getData());
		JSONObject metadata = (JSONObject) parser.parse(formSubmissionData.getMetadata());
		System.out.println(data + "\n" + metadata);
		createNewForm(data, metadata);
	}

	public void createNewForm(JSONArray data, JSONObject metadata) throws ParseException {

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
			return;
		}

		providerUUID = (String) authentication.get("provider");
		Context.authenticate(username, decPassword);

		// getting EncounterType String
		String encounterTypeString = (String) metadata.get(ParamNames.ENCOUNTER_TYPE);
		// Patient Creation Form
		if (encounterTypeString.equals("Create Patient")) {
			createPatient(encounterTypeString, data);
			return;
		}
		// Rest of the forms
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
				// System.out.println("There " + dataItem);

				DATA_TYPE dataType = DATA_TYPE.valueOf(dataItem.get(ParamNames.PAYLOAD_TYPE).toString());
				switch (dataType) {
					case ADDRESS: {
						JSONObject addressObj = (JSONObject) dataItem.get(ParamNames.VALUE);
						personAddress = new PersonAddress();
						personAddress.setStateProvince(addressObj.get("Province/State").toString());
						personAddress.setCityVillage(addressObj.get("City/Village").toString());
						personAddress.setAddress2(addressObj.get("address2").toString()); // open address
						personAddress.setAddress3(addressObj.get("address3").toString()); // nearest landmark
					}
						break;
					case OBS: {

						String paramName = dataItem.get(ParamNames.PARAM_NAME).toString();
						String value = dataItem.get(ParamNames.VALUE).toString();

						Concept concept = conceptService.getConceptByUuid(paramName);
						if (concept == null) {
							concept = createTextConcept(dataItem);
						}
						Obs obs = new Obs();
						obs.setConcept(concept);
						obs.setValueText(value);
						if (!value.isEmpty())
							obsList.add(obs);
					}
						break;
					case OBS_NUMERIC: {

						String paramName = dataItem.get(ParamNames.PARAM_NAME).toString();
						String value = dataItem.get(ParamNames.VALUE).toString();

						Concept concept = conceptService.getConceptByUuid(paramName);
						if (concept == null) {
							concept = createTextConcept(dataItem);
						}
						Obs obs = new Obs();
						obs.setConcept(concept);
						obs.setValueNumeric(Double.parseDouble(value));
						if (!value.isEmpty())
							obsList.add(obs);
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
						Obs obsDateTime = new Obs();
						obsDateTime.setConcept(conceptDateTime);
						obsDateTime.setValueDatetime(dateValue);
						if (dateValue != null)
							obsList.add(obsDateTime);
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

						/*
						 * String paramNameDateTime = dataItem.get(ParamNames.VALUE).toString(); String
						 * valueDateTime = dataItem.get(paramNameDateTime).toString(); Date dateValue =
						 * Utils.formatterTimeDate.parse(valueDateTime);
						 * 
						 * Concept conceptDateTime = conceptService.getConcept(paramNameDateTime);
						 * 
						 * if(conceptDateTime == null) { conceptDateTime = createDateConcept(); }
						 * 
						 * Obs obsDateTime = new Obs(); obsDateTime.setConcept(conceptDateTime);
						 * obsDateTime.setValueDate(dateValue); obsDateTime.setValueDatetime(dateValue);
						 * obsList.add(obsDateTime);
						 */
					}
						break;
					case LOCATION:
						String locationString = dataItem.get(ParamNames.VALUE).toString();
						location = findOrCreateLocation(locationString);
						break;

					case PERSON_ATTRIBUTE: {
						String attribType = dataItem.get(ParamNames.VALUE).toString();
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
						String date = dataItem.get(ParamNames.VALUE).toString();
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

			if (location == null) {
				System.out.println("location is null");
				return;
			}

			{
				// Fetching patient
				Patient patient = findPatient(patientIdentifier);

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
					for (Obs obs : obsList) {
						obs.setLocation(location);
						obs.setPerson(patient);
						encounter.addObs(obs);
					}

					// Save encounter
					Encounter savedEncoounter = encounterService.saveEncounter(encounter);
					// Saving encounter
					HydramoduleFormEncounter formEncounter = new HydramoduleFormEncounter();
					// Mapping encounter with workflow
					String formDetailsUUID = formDetails.get("uuid").toString(); // UUID of componentForm
					System.out.println("FormDetailsUUID: " + formDetailsUUID);
					HydramoduleComponentForm componentForm = service.getComponentFormByUUID(formDetailsUUID);
					formEncounter.setComponentForm(componentForm);
					if (componentForm != null) {
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

		List<PersonAttribute> personAttributes = new ArrayList<PersonAttribute>();

		List<PatientIdentifier> patientIdentifiers = new ArrayList<PatientIdentifier>();

		for (int i = 0; i < data.size(); i++) {

			JSONObject dataItem = (JSONObject) data.get(i);
			if (dataItem.containsKey(ParamNames.USERNAME) || dataItem.containsKey(ParamNames.PASSWORD))
				continue;
			if (!dataItem.containsKey(ParamNames.PAYLOAD_TYPE))
				continue;
			DATA_TYPE dataType = DATA_TYPE.valueOf(dataItem.get(ParamNames.PAYLOAD_TYPE).toString());
			switch (dataType) {
				case IDENTIFIER: {

					String identifierType = dataItem.get(ParamNames.PARAM_NAME).toString();

					String IdentifierValue = dataItem.get(ParamNames.VALUE).toString();

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
				}
					break;
				case DOB: {
					String date = dataItem.get(ParamNames.VALUE).toString();
					dob = Utils.openMrsDateFormat.parse(date);
				}
					break;
				case DATE_ENTERED: {
					String date = dataItem.get(ParamNames.VALUE).toString();
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
				case LOCATION:
					String locationString = dataItem.get(ParamNames.PARAM_NAME).toString();
					location = findOrCreateLocation(locationString);
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

		System.out.println(patient.getBirthdate());
		System.out.println(patient.getGender());
		System.out.println(patient.getPerson().getGivenName());
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
