package org.openmrs.module.hydra.web.controller.util;

public class CustomReportsQueryBuilder {

	public static String generateQueryForScreenedPreexixting(String dateFrom, String dateTo) {

		String query = "SELECT \r\n" + "EN.encounter_id as ENCOUNTER_ID,\r\n" + "PR.GENDER as `PERSON_GENDER`, \r\n"
		        + "YEAR(PR.date_created) - YEAR(PR.BIRTHDATE) AS age,\r\n" + "EN.encounter_datetime as ENCOUNTER_DATE,\r\n"
		        + "group_concat(if(o_sc.concept_id = 168163, (Select name from `hydra`.`concept_name` where concept_id=(concat(o_sc.value_coded))and LOCALE_PREFERRED = 1 and voided=0 and locale='en'), NULL)) AS `symptom`, \r\n"
		        + "group_concat(if(o_sc.concept_id = 168180, (Select name from `hydra`.`concept_name` where concept_id=(concat(o_sc.value_coded))and LOCALE_PREFERRED = 1 and voided=0 and locale='en'), NULL)) AS `pre_condition`, \r\n"
		        + "\r\n"
		        + "group_concat(if(o_sc.concept_id = 163084, concat(o_sc.value_text), NULL)) AS NATIONALIDCARDNUMBER \r\n"
		        + "FROM `hydra`.`patient` AS PT \r\n"
		        + "LEFT JOIN `hydra`.`person` AS PR ON PR.PERSON_ID = PT.PATIENT_ID and PR.voided=0 \r\n"
		        + "LEFT JOIN `hydra`.`encounter` AS EN ON EN.PATIENT_ID = PT.PATIENT_ID \r\n"
		        + "LEFT JOIN `hydra`.`hydramodule_form_encounter` AS FE ON FE.encounter_id = EN.encounter_id \r\n"
		        + "LEFT JOIN `hydra`.`hydramodule_component_form` AS CF ON CF.component_form_id = FE.component_form_id \r\n"
		        + "LEFT JOIN `hydra`.`hydramodule_workflow` AS WF ON WF.workflow_id = CF.workflow_id \r\n"
		        + "LEFT OUTER JOIN `hydra`.obs as o_sc on o_sc.encounter_id = EN.encounter_id and o_sc.voided=0 \r\n"
		        + "where \r\n" + "EN.voided = 0 \r\n" + "and EN.encounter_datetime between '" + dateFrom + "' " + "and '"
		        + dateTo + "' " + "and EN.encounter_type = " + 392 + " " + "and PT.voided=0 " + "and WF.workflow_id=" + 49
		        + " " + "group by EN.encounter_id, gender;";
		return query;
	}

	public static String generateQueryForOxygenAgeGender(String dateFrom, String dateTo) {

		String query = "SELECT " + "EN.encounter_id as ENCOUNTER_ID," + "PR.GENDER as `PERSON_GENDER`, "
		        + "YEAR(PR.date_created) - YEAR(PR.BIRTHDATE) AS age," + "EN.encounter_datetime as ENCOUNTER_DATE, "
		        + "group_concat(if(o_sc.concept_id = 168180, (Select name from `hydra`.`concept_name` where concept_id=(concat(o_sc.value_coded))and LOCALE_PREFERRED = 1 and voided=0 and locale='en'), NULL)) AS `comorbid_condition`, \r\n"
		        + "group_concat(if(o_sc.concept_id = 168199, concat(o_sc.value_numeric), NULL)) AS pre_therapy,\r\n"
		        + "group_concat(if(o_sc.concept_id = 168202, concat(o_sc.value_numeric), NULL)) AS post_therapy, \r\n"
		        + "group_concat(if(o_sc.concept_id = 168226, concat(o_sc.value_text), NULL)) AS therapy_duration, \r\n"
		        + "group_concat(if(o_sc.concept_id = 168201, concat(o_sc.value_numeric), NULL)) AS oxygen_rate \r\n"
		        + "FROM `hydra`.`patient` AS PT "
		        + "LEFT JOIN `hydra`.`person` AS PR ON PR.PERSON_ID = PT.PATIENT_ID and PR.voided=0 "
		        + "LEFT JOIN `hydra`.`encounter` AS EN ON EN.PATIENT_ID = PT.PATIENT_ID "
		        + "LEFT JOIN `hydra`.`hydramodule_form_encounter` AS FE ON FE.encounter_id = EN.encounter_id "
		        + "LEFT JOIN `hydra`.`hydramodule_component_form` AS CF ON CF.component_form_id = FE.component_form_id "
		        + "LEFT JOIN `hydra`.`hydramodule_workflow` AS WF ON WF.workflow_id = CF.workflow_id "
		        + "LEFT OUTER JOIN `hydra`.obs as o_sc on o_sc.encounter_id = EN.encounter_id and o_sc.voided=0 " + "where "
		        + "EN.voided = 0 " + "and EN.encounter_datetime between '" + dateFrom + "' " + "and '" + dateTo + "' "
		        + "and EN.encounter_type = " + 391 + " " + "and PT.voided=0 " + "and WF.workflow_id=" + 49 + " "
		        + "group by EN.encounter_id, gender;";
		return query;
	}
}
