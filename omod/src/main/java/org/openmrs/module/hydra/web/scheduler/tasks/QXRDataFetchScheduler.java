package org.openmrs.module.hydra.web.scheduler.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.workflow.HydramoduleEncounterMapper;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.openmrs.util.OpenmrsConstants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QXRDataFetchScheduler extends AbstractTask {

	private static final Log log = LogFactory.getLog(QXRDataFetchScheduler.class);

	private HydraService service = Context.getService(HydraService.class);

	@Override
	public void execute() {
		if (!isExecuting) {
			if (log.isDebugEnabled()) {
				log.debug("Start task");
			}
			startExecuting();
			try {
				EncounterType encounterType = Context.getEncounterService().getEncounterType("Xray Order Form");
				if (encounterType != null) {
					Statement stmt = null;
					String encounterFilterQuery = "Select e.encounter_id encounter_id , e.patient_id patient_id, p.identifier identifier from hydra.encounter e , hydra.patient_identifier p where e.patient_id = p.patient_id and encounter_type="
					        + encounterType.getEncounterTypeId()
					        + " and encounter_id not in (select order_encounter_id from hydra.hydramodule_encounter_mapper where result_encounter_id <> null)";
					Connection conn;
					try {
						conn = establishDatabaseConnection();
						try {
							stmt = conn.createStatement();
							ResultSet data = stmt.executeQuery(encounterFilterQuery);
							while (data.next()) {
								Encounter orderEncounter = Context.getEncounterService()
								        .getEncounter(data.getInt("encounter_id"));

								processDataForPatientID(data.getString("identifier"), orderEncounter,
								    data.getInt("patient_id"));
							}
						}
						catch (SQLException e) {
							log.error(e.getMessage() + " " + encounterFilterQuery);
						}
						conn.close();
					}
					catch (SQLException e) {
						log.error("Error connecting to DB.", e);
					}
				}

			}
			catch (Exception e) {
				log.error("execution stopped " + e);
			}
			finally {
				stopExecuting();
			}
		}

	}

	public void processDataForPatientID(String patientIdentifier, Encounter orderEncounter, Integer patientId) {

		Response response = null;
		try {

			OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
			        .writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
			String url = "https://covidapi.qure.ai/v2/cxr/batch_results/" + patientIdentifier + "/";
			// String url = "https://covidapi.qure.ai/v2/cxr/batch_results/122T3-0/";
			Request request = new Request.Builder().url(url).method("GET", null)
			        .addHeader("Authorization", "Token 04d48517b635caa928da6894133b54b9a96962cc").build();
			response = client.newCall(request).execute();
			// System.out.println(response.body().string());
			int responseCode = response.code();
			if (responseCode != 200) {
				log.error("Invalid Response " + responseCode + " " + response.message());
			} else {

				Scanner sc = new Scanner(response.body().string());
				String data = "";

				while (sc.hasNext()) {
					data += sc.nextLine();
				}

				sc.close();
				//response.close();

				JSONParser jsonParser = new JSONParser();
				JSONArray jsonArray = (JSONArray) jsonParser.parse(data);

				String covidResult = "";

				for (int i = 0; i < jsonArray.size(); i++) {

					JSONObject element = (JSONObject) jsonArray.get(i);
					JSONObject metaDataObj = (JSONObject) element.get("metadata");
					covidResult = (String) metaDataObj.get("covid_score");

				}
				Encounter resultEncounter = new Encounter();

				EncounterType encounterType = Context.getEncounterService().getEncounterType("Xray Result Form");
				resultEncounter.setEncounterType(encounterType);

				User user = Context.getUserService().getUserByUsername("QXR-user");

				resultEncounter.setCreator(user);

				Patient patient = Context.getPatientService().getPatient(patientId);

				resultEncounter.setPatient(patient);

				resultEncounter.setEncounterDatetime(new Date());
				Concept concept = Context.getConceptService().getConceptByName("XRAY COVID RESULT");

				if (concept != null) {

					Obs obs = new Obs();
					obs.setConcept(concept);
					concept = Context.getConceptService().getConceptByName(covidResult);
					obs.setValueCoded(concept);
					obs.setDateCreated(new Date());
					obs.setCreator(user);
					resultEncounter.addObs(obs);
				}

				Context.getEncounterService().saveEncounter(resultEncounter);

				HydramoduleEncounterMapper encounterMapper = new HydramoduleEncounterMapper();

				encounterMapper.setOrderEncounterId(orderEncounter);
				encounterMapper.setResultEncounterId(resultEncounter);
				encounterMapper.setCreator(user);
				encounterMapper.setDateCreated(new Date());

				service.saveHydramoduleEncounterMapper(encounterMapper);

				log.info(data);

			}

		}
		catch (MalformedURLException e) {
			log.error("Invalid url " + e);
		}
		catch (IOException e) {
			log.error("unable to create http connection " + e);
		}
		catch (ParseException e) {
			log.error(e);
		}
		finally {
			response.close();
		}

	}

	Connection establishDatabaseConnection() throws SQLException {

		String url = Context.getRuntimeProperties().getProperty("connection.url", null);

		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			log.error("Could not find JDBC driver class.", e);

			throw (SQLException) e.fillInStackTrace();
		}

		String username = Context.getRuntimeProperties().getProperty("connection.username");
		String password = Context.getRuntimeProperties().getProperty("connection.password");
		log.debug("connecting to DATABASE: " + OpenmrsConstants.DATABASE_NAME + " USERNAME: " + username + " URL: " + url);
		return DriverManager.getConnection(url, username, password);

	}

}
