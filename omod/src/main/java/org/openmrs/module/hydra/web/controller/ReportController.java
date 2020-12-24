package org.openmrs.module.hydra.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.web.controller.util.CustomReportsQueryBuilder;
import org.openmrs.module.hydra.web.controller.util.DistributionItem;
import org.openmrs.module.hydra.web.controller.util.FilterItem;
import org.openmrs.module.hydra.web.controller.util.ReportConfigHolder;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opencsv.CSVWriter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import java.text.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import javax.persistence.ParameterMode;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/hydra/report")
public class ReportController {

	private static Log log = LogFactory.getLog(ReportController.class);

	public static final String STANDARD_DATE = "dd/MM/yyyy";

	public static final String STANDARD_DATE_HYPHENATED = "dd-MM-yyyy";

	public static final String SQL_DATE = "yyyy-MM-dd";

	public static final String SQL_DATESTAMP = "yyyyMMdd";

	public static final String SQL_DATEIMESTAMP = "yyyyMMddHHmmss";

	private static final Map<String, String> DATE_FORMATS;

	@Autowired
	private DbSessionFactory sessionFactory;

	// @Autowired
	private HydraService service;

	static {
		DATE_FORMATS = new HashMap();

		// Date only
		DATE_FORMATS.put("^\\d{2}[0-1]\\d[0-3]\\d$", "yyMMdd");
		DATE_FORMATS.put("^\\d{4}[0-1]\\d[0-3]\\d$", SQL_DATESTAMP);
		DATE_FORMATS.put("^\\d{2}-[0-1]\\d-[0-3]\\d$", "yy-MM-dd");
		DATE_FORMATS.put("^\\d{4}-[0-1]\\d-[0-3]\\d$", SQL_DATE);
		DATE_FORMATS.put("^[0-3]\\d-[0-1]\\d-\\d{2}$", "dd-MM-yy");
		DATE_FORMATS.put("^[0-3]\\d-[0-1]\\d-\\d{2} d{2}:d{2}:d{2}$", "dd-MM-yy hh:mm:ss");
		DATE_FORMATS.put("^[0-3]\\d-[0-1]\\d-\\d{4}$", STANDARD_DATE_HYPHENATED);
		DATE_FORMATS.put("^[0-3]\\d [0-1]\\d \\d{2}$", "dd MM yy");
		DATE_FORMATS.put("^[0-3]\\d [0-1]\\d \\d{4}$", "dd MM yyyy");
		DATE_FORMATS.put("^[0-3]\\d/[0-1]\\d/\\d{2}$", "dd/MM/yy");
		DATE_FORMATS.put("^[0-3]\\d/[0-1]\\d/\\d{4}$", STANDARD_DATE);
		DATE_FORMATS.put("^[0-3]\\d [a-z]{3} \\d{2}$", "dd MMM yy");
		DATE_FORMATS.put("^[0-3]\\d [a-z]{3} \\d{4}$", "dd MMM yyyy");

	}

	private List<Map<String, Object>> generateScreenedPreexisting(String from, String to) {
		String query = CustomReportsQueryBuilder.generateQueryForScreenedPreexixting(from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("###QUERY_CUSTOM: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(aliasToValueMapList);

		DistributionItem[] distItems = new DistributionItem[6];
		distItems[0] = new DistributionItem(Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		        "11", "12", "13", "14", "15", "16", "17", "18", "19" }), "<20");
		distItems[1] = new DistributionItem(
		        Arrays.asList(new String[] { "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }), "20-29");
		distItems[2] = new DistributionItem(
		        Arrays.asList(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39" }), "30-39");
		distItems[3] = new DistributionItem(
		        Arrays.asList(new String[] { "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" }), "40-49");
		distItems[4] = new DistributionItem(
		        Arrays.asList(new String[] { "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }), "50-59");
		distItems[5] = new DistributionItem(Arrays.asList(new String[] { "60", "61", "62", "63", "64", "65", "66", "67",
		        "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
		        "86", "87", "88", "899", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", }), ">59");
		FilterItem[] filterItems = new FilterItem[3];
		filterItems[0] = new FilterItem(Arrays.asList(new String[] { "ASTHMA", "TUBERCULOSIS", "DIABETES MELLITUS",
		        "HYPERTENSION", "HEART DISEASE", "CANCER", "PREGNANCY", "HEPATITIS B/C" }), "Pre Condition");
		filterItems[1] = new FilterItem(Arrays.asList(new String[] { "NONE OF THE ABOVE" }), "Non Pre Condition");
		filterItems[2] = new FilterItem(Arrays.asList(new String[] { "OTHER" }), "Other");

		ReportConfigHolder configHolder = new ReportConfigHolder();
		configHolder.setDistributionColumn("age");
		configHolder.setDistributionOn(distItems);

		configHolder.setFilterColumn("pre_condition");
		configHolder.setFilterIn(filterItems);

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < configHolder.getDistributionOn().length; i++) { // number of rows
			DistributionItem item = configHolder.getDistributionOn()[i];
			LinkedHashMap<String, Object> dataRow = new LinkedHashMap<String, Object>();
			dataRow.put(configHolder.getDistributionColumn(), item.getAlias());

			// TODO loop below can be avoided by making use of the same loop later
			for (FilterItem filterFrom : configHolder.getFilterIn()) {
				dataRow.put(filterFrom.getAlias(), 0);
			}
			for (Map<String, Object> row : aliasToValueMapList) {
				FilterItem filterFrom = null;
				for (int j = 0; j < configHolder.getFilterIn().length; j++) {
					filterFrom = configHolder.getFilterIn()[j];
					String valueDistributionOn = row.get(configHolder.getDistributionColumn()).toString().toLowerCase();
					// System.out.println("ON: " + valueDistributionOn);
					if (!item.getItemNames().contains(valueDistributionOn)) {
						continue;
					}

					String valueToFilter = row.get(configHolder.getFilterColumn()).toString().toLowerCase();
					// System.out.println("VALUE: " + valueToFilter + "\n" +
					// filterFrom.getFilterValues());

					boolean valueMatched = false;
					String[] valuesToFilterArray = valueToFilter.split(",");
					for (int k = 0; k < valuesToFilterArray.length; k++) {
						String v = valuesToFilterArray[k];
						if (filterFrom.getFilterValues().contains(v)) {
							valueMatched = true;
							break;
						}
					}

					if (valueMatched) {
						if (dataRow.containsKey(filterFrom.getAlias())) {
							dataRow.put(filterFrom.getAlias(),
							    (Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + 1) + "");
						} else {
							dataRow.put(filterFrom.getAlias(), 1 + "");
						}

					}
				}
			}

			finalReport.add(dataRow);

		}
		return finalReport;
	}

	private List<Map<String, Object>> generateOxygenAgeGender(String from, String to) {
		String query = CustomReportsQueryBuilder.generateQueryForOxygenAgeGender(from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("###QUERY_CUSTOM: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(aliasToValueMapList);

		DistributionItem[] distItems = new DistributionItem[6];
		distItems[0] = new DistributionItem(Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		        "11", "12", "13", "14", "15", "16", "17", "18", "19" }), "<20");
		distItems[1] = new DistributionItem(
		        Arrays.asList(new String[] { "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }), "20-29");
		distItems[2] = new DistributionItem(
		        Arrays.asList(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39" }), "30-39");
		distItems[3] = new DistributionItem(
		        Arrays.asList(new String[] { "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" }), "40-49");
		distItems[4] = new DistributionItem(
		        Arrays.asList(new String[] { "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }), "50-59");
		distItems[5] = new DistributionItem(Arrays.asList(new String[] { "60", "61", "62", "63", "64", "65", "66", "67",
		        "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
		        "86", "87", "88", "899", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", }), ">59");

		FilterItem[] filterItems = new FilterItem[2];
		filterItems[0] = new FilterItem(Arrays.asList(new String[] { "M" }), "Male");
		filterItems[1] = new FilterItem(Arrays.asList(new String[] { "F" }), "Female");

		ReportConfigHolder configHolder = new ReportConfigHolder();
		configHolder.setDistributionColumn("age");
		configHolder.setDistributionOn(distItems);

		configHolder.setFilterColumn("PERSON_GENDER");
		configHolder.setFilterIn(filterItems);

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < configHolder.getDistributionOn().length; i++) { // number of rows
			DistributionItem item = configHolder.getDistributionOn()[i];
			LinkedHashMap<String, Object> dataRow = new LinkedHashMap<String, Object>();
			dataRow.put(configHolder.getDistributionColumn(), item.getAlias());

			// TODO loop below can be avoided by making use of the same loop later
			for (FilterItem filterFrom : configHolder.getFilterIn()) {
				dataRow.put(filterFrom.getAlias(), 0);
			}
			for (Map<String, Object> row : aliasToValueMapList) {

				int preTHerapyValue = Integer.parseInt(row.get("pre_therapy").toString());
				int postTherapyValue = Integer.parseInt(row.get("post_therapy").toString());

				// This is where clause alternate
				if (preTHerapyValue < 80 && postTherapyValue > 100) {
					continue;
				}

				FilterItem filterFrom = null;
				for (int j = 0; j < configHolder.getFilterIn().length; j++) {
					filterFrom = configHolder.getFilterIn()[j];
					String valueDistributionOn = row.get(configHolder.getDistributionColumn()).toString().toLowerCase();
					// System.out.println("ON: " + valueDistributionOn);
					if (!item.getItemNames().contains(valueDistributionOn)) {
						continue;
					}

					String valueToFilter = row.get(configHolder.getFilterColumn()).toString().toLowerCase();
					// System.out.println("VALUE: " + valueToFilter + "\n" +
					// filterFrom.getFilterValues());

					boolean valueMatched = false;
					String[] valuesToFilterArray = valueToFilter.split(",");
					for (int k = 0; k < valuesToFilterArray.length; k++) {
						String v = valuesToFilterArray[k];
						if (filterFrom.getFilterValues().contains(v)) {
							valueMatched = true;
							break;
						}
					}

					if (valueMatched) {
						if (dataRow.containsKey(filterFrom.getAlias())) {
							dataRow.put(filterFrom.getAlias(),
							    (Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + 1) + "");
						} else {
							dataRow.put(filterFrom.getAlias(), 1 + "");
						}

					}
				}
			}

			finalReport.add(dataRow);

		}
		return finalReport;
	}

	private List<Map<String, Object>> generateOxygenAgeGenderCondition(String from, String to) {
		String query = CustomReportsQueryBuilder.generateQueryForOxygenAgeGender(from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("###QUERY_CUSTOM: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(aliasToValueMapList);

		DistributionItem[] distItems = new DistributionItem[6];
		distItems[0] = new DistributionItem(Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		        "11", "12", "13", "14", "15", "16", "17", "18", "19" }), "<20");
		distItems[1] = new DistributionItem(
		        Arrays.asList(new String[] { "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }), "20-29");
		distItems[2] = new DistributionItem(
		        Arrays.asList(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39" }), "30-39");
		distItems[3] = new DistributionItem(
		        Arrays.asList(new String[] { "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" }), "40-49");
		distItems[4] = new DistributionItem(
		        Arrays.asList(new String[] { "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }), "50-59");
		distItems[5] = new DistributionItem(Arrays.asList(new String[] { "60", "61", "62", "63", "64", "65", "66", "67",
		        "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
		        "86", "87", "88", "899", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", }), ">59");

		FilterItem[] filterItems = new FilterItem[16];
		filterItems[0] = new FilterItem(Arrays.asList(new String[] { "TUBERCULOSIS" }), "Male TB");
		filterItems[1] = new FilterItem(Arrays.asList(new String[] { "TUBERCULOSIS" }), "Femal TB");
		filterItems[2] = new FilterItem(Arrays.asList(new String[] { "ASTHMA" }), "Male ASTHMA");
		filterItems[3] = new FilterItem(Arrays.asList(new String[] { "ASTHMA" }), "Femal ASTHMA");
		filterItems[4] = new FilterItem(Arrays.asList(new String[] { "CANCER" }), "Male CANCER");
		filterItems[5] = new FilterItem(Arrays.asList(new String[] { "CANCER" }), "Femal CANCER");
		filterItems[6] = new FilterItem(Arrays.asList(new String[] { "DIABETES" }), "Male DIABETES");
		filterItems[7] = new FilterItem(Arrays.asList(new String[] { "DIABETES" }), "Femal DIABETES");
		filterItems[8] = new FilterItem(Arrays.asList(new String[] { "HYPERTENSION" }), "Male HYPERTENSION");
		filterItems[9] = new FilterItem(Arrays.asList(new String[] { "HYPERTENSION" }), "Femal HYPERTENSION");
		filterItems[10] = new FilterItem(Arrays.asList(new String[] { "HEART DISEASE" }), "Male HEART DISEASE");
		filterItems[11] = new FilterItem(Arrays.asList(new String[] { "HEART DISEASE" }), "Femal HEART DISEASE");
		filterItems[12] = new FilterItem(Arrays.asList(new String[] { "PREGNANCY" }), "Male PREGNANCY");
		filterItems[13] = new FilterItem(Arrays.asList(new String[] { "PREGNANCY" }), "Femal PREGNANCY");
		filterItems[14] = new FilterItem(Arrays.asList(new String[] { "HEPATITIS B/C" }), "Male HEPATITIS B/C");
		filterItems[15] = new FilterItem(Arrays.asList(new String[] { "HEPATITIS B/C" }), "Femal HEPATITIS B/C");

		ReportConfigHolder configHolder = new ReportConfigHolder();
		configHolder.setDistributionColumn("age");
		configHolder.setDistributionOn(distItems);

		configHolder.setFilterColumn("comorbid_condition");
		configHolder.setFilterIn(filterItems);

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < configHolder.getDistributionOn().length; i++) { // number of rows
			DistributionItem item = configHolder.getDistributionOn()[i];
			LinkedHashMap<String, Object> dataRow = new LinkedHashMap<String, Object>();
			dataRow.put(configHolder.getDistributionColumn(), item.getAlias());

			// TODO loop below can be avoided by making use of the same loop later
			for (FilterItem filterFrom : configHolder.getFilterIn()) {
				dataRow.put(filterFrom.getAlias(), 0);
			}
			for (Map<String, Object> row : aliasToValueMapList) {

				int preTHerapyValue = Integer.parseInt(row.get("pre_therapy").toString());
				int postTherapyValue = Integer.parseInt(row.get("post_therapy").toString());

				// This is where clause alternate
				if (preTHerapyValue < 80 && postTherapyValue > 100) {
					continue;
				}

				FilterItem filterFrom = null;
				for (int j = 0; j < configHolder.getFilterIn().length; j++) {
					filterFrom = configHolder.getFilterIn()[j];
					String valueDistributionOn = row.get(configHolder.getDistributionColumn()).toString().toLowerCase();
					// System.out.println("ON: " + valueDistributionOn);
					if (!item.getItemNames().contains(valueDistributionOn)) {
						continue;
					}

					if (!filterFrom.getAlias().toString().toLowerCase()
					        .startsWith(row.get("PERSON_GENDER").toString().toLowerCase())) {
						continue;
					}

					String valueToFilter = row.get(configHolder.getFilterColumn()).toString().toLowerCase();
					// System.out.println("VALUE: " + valueToFilter + "\n" +
					// filterFrom.getFilterValues());

					boolean valueMatched = false;
					String[] valuesToFilterArray = valueToFilter.split(",");
					for (int k = 0; k < valuesToFilterArray.length; k++) {
						String v = valuesToFilterArray[k];
						if (filterFrom.getFilterValues().contains(v)) {
							valueMatched = true;
							break;
						}
					}

					if (valueMatched) {
						if (dataRow.containsKey(filterFrom.getAlias())) {
							dataRow.put(filterFrom.getAlias(),
							    (Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + 1) + "");
						} else {
							dataRow.put(filterFrom.getAlias(), 1 + "");
						}

					}
				}
			}

			finalReport.add(dataRow);

		}
		return finalReport;
	}

	private List<Map<String, Object>> generateScreenedAgeGenderCondition(String from, String to) {
		String query = CustomReportsQueryBuilder.generateQueryForScreenedPreexixting(from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("###QUERY_CUSTOM: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(aliasToValueMapList);

		DistributionItem[] distItems = new DistributionItem[6];
		distItems[0] = new DistributionItem(Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		        "11", "12", "13", "14", "15", "16", "17", "18", "19" }), "<20");
		distItems[1] = new DistributionItem(
		        Arrays.asList(new String[] { "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }), "20-29");
		distItems[2] = new DistributionItem(
		        Arrays.asList(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39" }), "30-39");
		distItems[3] = new DistributionItem(
		        Arrays.asList(new String[] { "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" }), "40-49");
		distItems[4] = new DistributionItem(
		        Arrays.asList(new String[] { "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }), "50-59");
		distItems[5] = new DistributionItem(Arrays.asList(new String[] { "60", "61", "62", "63", "64", "65", "66", "67",
		        "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
		        "86", "87", "88", "899", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", }), ">59");

		FilterItem[] filterItems = new FilterItem[16];
		filterItems[0] = new FilterItem(Arrays.asList(new String[] { "TUBERCULOSIS" }), "Male TB");
		filterItems[1] = new FilterItem(Arrays.asList(new String[] { "TUBERCULOSIS" }), "Female TB");
		filterItems[2] = new FilterItem(Arrays.asList(new String[] { "ASTHMA" }), "Male ASTHMA");
		filterItems[3] = new FilterItem(Arrays.asList(new String[] { "ASTHMA" }), "Female ASTHMA");
		filterItems[4] = new FilterItem(Arrays.asList(new String[] { "CANCER" }), "Male CANCER");
		filterItems[5] = new FilterItem(Arrays.asList(new String[] { "CANCER" }), "Female CANCER");
		filterItems[6] = new FilterItem(Arrays.asList(new String[] { "DIABETES" }), "Male DIABETES");
		filterItems[7] = new FilterItem(Arrays.asList(new String[] { "DIABETES" }), "Female DIABETES");
		filterItems[8] = new FilterItem(Arrays.asList(new String[] { "HYPERTENSION" }), "Male HYPERTENSION");
		filterItems[9] = new FilterItem(Arrays.asList(new String[] { "HYPERTENSION" }), "Female HYPERTENSION");
		filterItems[10] = new FilterItem(Arrays.asList(new String[] { "HEART DISEASE" }), "Male HEART DISEASE");
		filterItems[11] = new FilterItem(Arrays.asList(new String[] { "HEART DISEASE" }), "Female HEART DISEASE");
		filterItems[12] = new FilterItem(Arrays.asList(new String[] { "PREGNANCY" }), "Male PREGNANCY");
		filterItems[13] = new FilterItem(Arrays.asList(new String[] { "PREGNANCY" }), "Female PREGNANCY");
		filterItems[14] = new FilterItem(Arrays.asList(new String[] { "HEPATITIS B/C" }), "Male HEPATITIS B/C");
		filterItems[15] = new FilterItem(Arrays.asList(new String[] { "HEPATITIS B/C" }), "Female HEPATITIS B/C");

		ReportConfigHolder configHolder = new ReportConfigHolder();
		configHolder.setDistributionColumn("age");
		configHolder.setDistributionOn(distItems);

		configHolder.setFilterColumn("pre_condition");
		configHolder.setFilterIn(filterItems);

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < configHolder.getDistributionOn().length; i++) { // number of rows
			DistributionItem item = configHolder.getDistributionOn()[i];
			LinkedHashMap<String, Object> dataRow = new LinkedHashMap<String, Object>();
			dataRow.put(configHolder.getDistributionColumn(), item.getAlias());

			// TODO loop below can be avoided by making use of the same loop later
			for (FilterItem filterFrom : configHolder.getFilterIn()) {
				dataRow.put(filterFrom.getAlias(), 0);
			}
			for (Map<String, Object> row : aliasToValueMapList) {

				/*
				 * int preTHerapyValue = Integer.parseInt(row.get("pre_therapy").toString());
				 * int postTherapyValue = Integer.parseInt(row.get("post_therapy").toString());
				 * 
				 * // This is where clause alternate if (preTHerapyValue < 80 &&
				 * postTherapyValue > 100) { continue; }
				 */

				FilterItem filterFrom = null;
				for (int j = 0; j < configHolder.getFilterIn().length; j++) {
					filterFrom = configHolder.getFilterIn()[j];
					String valueDistributionOn = row.get(configHolder.getDistributionColumn()).toString().toLowerCase();
					// System.out.println("ON: " + valueDistributionOn);
					if (!item.getItemNames().contains(valueDistributionOn)) {
						continue;
					}

					if (!filterFrom.getAlias().toString().toLowerCase()
					        .startsWith(row.get("PERSON_GENDER").toString().toLowerCase())) {
						continue;
					}

					String valueToFilter = row.get(configHolder.getFilterColumn()).toString().toLowerCase();
					// System.out.println("VALUE: " + valueToFilter + "\n" +
					// filterFrom.getFilterValues());

					boolean valueMatched = false;
					String[] valuesToFilterArray = valueToFilter.split(",");
					for (int k = 0; k < valuesToFilterArray.length; k++) {
						String v = valuesToFilterArray[k];
						if (filterFrom.getFilterValues().contains(v)) {
							valueMatched = true;
							break;
						}
					}

					if (valueMatched) {
						if (dataRow.containsKey(filterFrom.getAlias())) {
							dataRow.put(filterFrom.getAlias(),
							    (Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + 1) + "");
						} else {
							dataRow.put(filterFrom.getAlias(), 1 + "");
						}

					}
				}
			}

			finalReport.add(dataRow);

		}
		return finalReport;
	}

	private List<Map<String, Object>> generateOxygenTherapyAvgAge(String from, String to) {
		String query = CustomReportsQueryBuilder.generateQueryForOxygenAgeGender(from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("###QUERY_CUSTOM: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(aliasToValueMapList);

		DistributionItem[] distItems = new DistributionItem[6];
		distItems[0] = new DistributionItem(Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		        "11", "12", "13", "14", "15", "16", "17", "18", "19" }), "<20");
		distItems[1] = new DistributionItem(
		        Arrays.asList(new String[] { "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }), "20-29");
		distItems[2] = new DistributionItem(
		        Arrays.asList(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39" }), "30-39");
		distItems[3] = new DistributionItem(
		        Arrays.asList(new String[] { "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" }), "40-49");
		distItems[4] = new DistributionItem(
		        Arrays.asList(new String[] { "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }), "50-59");
		distItems[5] = new DistributionItem(Arrays.asList(new String[] { "60", "61", "62", "63", "64", "65", "66", "67",
		        "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
		        "86", "87", "88", "899", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", }), ">59");

		FilterItem[] filterItems = new FilterItem[1];
		filterItems[0] = new FilterItem(Arrays.asList(new String[] { "Avg Rate" }), "Average Rate");

		ReportConfigHolder configHolder = new ReportConfigHolder();
		configHolder.setDistributionColumn("age");
		configHolder.setDistributionOn(distItems);

		configHolder.setFilterColumn("oxygen_rate");
		configHolder.setFilterIn(filterItems);

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < configHolder.getDistributionOn().length; i++) { // number of rows
			DistributionItem item = configHolder.getDistributionOn()[i];
			LinkedHashMap<String, Object> dataRow = new LinkedHashMap<String, Object>();
			dataRow.put(configHolder.getDistributionColumn(), item.getAlias());

			// TODO loop below can be avoided by making use of the same loop later
			for (FilterItem filterFrom : configHolder.getFilterIn()) {
				dataRow.put(filterFrom.getAlias(), 0);
			}
			for (Map<String, Object> row : aliasToValueMapList) {

				int preTHerapyValue = Integer.parseInt(row.get("pre_therapy").toString());
				int postTherapyValue = Integer.parseInt(row.get("post_therapy").toString());

				// This is where clause alternate
				if (preTHerapyValue < 80 && postTherapyValue > 100) {
					continue;
				}

				FilterItem filterFrom = null;
				for (int j = 0; j < configHolder.getFilterIn().length; j++) {
					filterFrom = configHolder.getFilterIn()[j];
					String valueDistributionOn = row.get(configHolder.getDistributionColumn()).toString().toLowerCase();
					// System.out.println("ON: " + valueDistributionOn);
					if (!item.getItemNames().contains(valueDistributionOn)) {
						continue;
					}

					String valueToFilter = row.get(configHolder.getFilterColumn()).toString().toLowerCase();
					// System.out.println("VALUE: " + valueToFilter + "\n" +
					// filterFrom.getFilterValues());

					boolean valueMatched = true;
					/*
					 * String[] valuesToFilterArray = valueToFilter.split(","); for (int k = 0; k <
					 * valuesToFilterArray.length; k++) { String v = valuesToFilterArray[k]; if
					 * (filterFrom.getFilterValues().contains(v)) { valueMatched = true; break; } }
					 */

					if (valueMatched) {
						if (dataRow.containsKey(filterFrom.getAlias())) {
							dataRow.put(filterFrom.getAlias(),
							    ((Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString())
							            + Integer.valueOf(valueToFilter)) / 2) + "");
						} else {
							dataRow.put(filterFrom.getAlias(), valueToFilter + "");
						}

					}
				}
			}

			finalReport.add(dataRow);

		}
		return finalReport;
	}

	private List<Map<String, Object>> generateOxygenTherapyAvgAgeCondition(String from, String to) {
		String query = CustomReportsQueryBuilder.generateQueryForOxygenAgeGender(from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("###QUERY_CUSTOM: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(aliasToValueMapList);

		DistributionItem[] distItems = new DistributionItem[6];
		distItems[0] = new DistributionItem(Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		        "11", "12", "13", "14", "15", "16", "17", "18", "19" }), "<20");
		distItems[1] = new DistributionItem(
		        Arrays.asList(new String[] { "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }), "20-29");
		distItems[2] = new DistributionItem(
		        Arrays.asList(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39" }), "30-39");
		distItems[3] = new DistributionItem(
		        Arrays.asList(new String[] { "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" }), "40-49");
		distItems[4] = new DistributionItem(
		        Arrays.asList(new String[] { "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }), "50-59");
		distItems[5] = new DistributionItem(Arrays.asList(new String[] { "60", "61", "62", "63", "64", "65", "66", "67",
		        "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
		        "86", "87", "88", "899", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", }), ">59");

		FilterItem[] filterItems = new FilterItem[2];
		filterItems[0] = new FilterItem(Arrays.asList(new String[] { "Avg Rate PreExixting" }), "Average Rate pre-existing");
		filterItems[1] = new FilterItem(Arrays.asList(new String[] { "Avg Rate Non PreExixting" }),
		        "Average Rate non pre-existing");

		ReportConfigHolder configHolder = new ReportConfigHolder();
		configHolder.setDistributionColumn("age");
		configHolder.setDistributionOn(distItems);

		configHolder.setFilterColumn("oxygen_rate");
		configHolder.setFilterIn(filterItems);

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		List<String> comorbidConditions = Arrays.asList(new String[] { "ASTHMA", "TUBERCULOSIS", "DIABETES MELLITUS",
		        "HYPERTENSION", "HEART DISEASE", "CANCER", "PREGNANCY", "HEPATITIS B/C", "OTHER" });

		for (int i = 0; i < configHolder.getDistributionOn().length; i++) { // number of rows
			DistributionItem item = configHolder.getDistributionOn()[i];
			LinkedHashMap<String, Object> dataRow = new LinkedHashMap<String, Object>();
			dataRow.put(configHolder.getDistributionColumn(), item.getAlias());

			// TODO loop below can be avoided by making use of the same loop later
			for (FilterItem filterFrom : configHolder.getFilterIn()) {
				dataRow.put(filterFrom.getAlias(), 0);
			}
			for (Map<String, Object> row : aliasToValueMapList) {

				int preTHerapyValue = Integer.parseInt(row.get("pre_therapy").toString());
				int postTherapyValue = Integer.parseInt(row.get("post_therapy").toString());

				// This is where clause alternate
				if (preTHerapyValue < 80 && postTherapyValue > 100) {
					continue;
				}

				FilterItem filterFrom = null;
				for (int j = 0; j < configHolder.getFilterIn().length; j++) {
					filterFrom = configHolder.getFilterIn()[j];
					String valueDistributionOn = row.get(configHolder.getDistributionColumn()).toString().toLowerCase();
					// System.out.println("ON: " + valueDistributionOn);
					if (!item.getItemNames().contains(valueDistributionOn)) {
						continue;
					}

					String preCondition = row.get("comorbid_condition").toString();
					if (j == 0) {
						if (!comorbidConditions.contains(preCondition)) {
							continue;
						}
					} else if (j == 1) {
						if (comorbidConditions.contains(preCondition)) {
							continue;
						}
					}

					String valueToFilter = row.get(configHolder.getFilterColumn()).toString().toLowerCase();
					// System.out.println("VALUE: " + valueToFilter + "\n" +
					// filterFrom.getFilterValues());

					boolean valueMatched = true;
					/*
					 * String[] valuesToFilterArray = valueToFilter.split(","); for (int k = 0; k <
					 * valuesToFilterArray.length; k++) { String v = valuesToFilterArray[k]; if
					 * (filterFrom.getFilterValues().contains(v)) { valueMatched = true; break; } }
					 */

					if (valueMatched) {
						if (dataRow.containsKey(filterFrom.getAlias())) {
							dataRow.put(filterFrom.getAlias(),
							    ((Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString())
							            + Integer.valueOf(valueToFilter)) / 2) + "");
						} else {
							dataRow.put(filterFrom.getAlias(), valueToFilter + "");
						}

					}
				}
			}

			finalReport.add(dataRow);

		}
		return finalReport;
	}

	private List<Map<String, Object>> generateOxygenTherapyAvg(String from, String to) {
		String query = CustomReportsQueryBuilder.generateQueryForOxygenAgeGender(from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("###QUERY_CUSTOM: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(aliasToValueMapList);

		DistributionItem[] distItems = new DistributionItem[1];
		distItems[0] = new DistributionItem(Arrays.asList(
		    new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
		            "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
		            "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
		            "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
		            "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86",
		            "87", "88", "899", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }),
		        "Any");

		FilterItem[] filterItems = new FilterItem[4];
		filterItems[0] = new FilterItem(Arrays.asList(new String[] { "Avg Age" }), "Average Age");
		filterItems[1] = new FilterItem(Arrays.asList(new String[] { "Avg Rate" }), "Average Rate");
		filterItems[2] = new FilterItem(Arrays.asList(new String[] { "Avg Rate Oxygen Sat Pre Therapy" }),
		        "Average Oxygen Sat Pre Therapy");
		filterItems[3] = new FilterItem(Arrays.asList(new String[] { "Avg Rate Oxygen Sat Post Therapy" }),
		        "Average Oxygen Sat Post Therapy");

		ReportConfigHolder configHolder = new ReportConfigHolder();
		configHolder.setDistributionColumn("age");
		configHolder.setDistributionOn(distItems);

		configHolder.setFilterColumn("oxygen_rate");
		configHolder.setFilterIn(filterItems);

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < configHolder.getDistributionOn().length; i++) { // number of rows
			DistributionItem item = configHolder.getDistributionOn()[i];
			LinkedHashMap<String, Object> dataRow = new LinkedHashMap<String, Object>();
			dataRow.put(configHolder.getDistributionColumn(), item.getAlias());

			// TODO loop below can be avoided by making use of the same loop later
			for (FilterItem filterFrom : configHolder.getFilterIn()) {
				dataRow.put(filterFrom.getAlias(), 0);
			}
			for (Map<String, Object> row : aliasToValueMapList) {

				int preTHerapyValue = Integer.parseInt(row.get("pre_therapy").toString());
				int postTherapyValue = Integer.parseInt(row.get("post_therapy").toString());

				// This is where clause alternate
				if (preTHerapyValue < 80 && postTherapyValue > 100) {
					continue;
				}

				FilterItem filterFrom = null;
				for (int j = 0; j < configHolder.getFilterIn().length; j++) {
					filterFrom = configHolder.getFilterIn()[j];
					String valueDistributionOn = row.get(configHolder.getDistributionColumn()).toString().toLowerCase();
					// System.out.println("ON: " + valueDistributionOn);
					if (!item.getItemNames().contains(valueDistributionOn)) {
						continue;
					}

					int age = Integer.valueOf(row.get("age").toString());
					int pre_therapy = Integer.valueOf(row.get("pre_therapy").toString());
					int post_therapy = Integer.valueOf(row.get("post_therapy").toString());
					int oxygen_rate = Integer.valueOf(row.get("oxygen_rate").toString());

					if (j == 0) {
						dataRow.put(filterFrom.getAlias(),
						    ((Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + Integer.valueOf(age)) / 2)
						            + "");
					} else if (j == 1) {
						dataRow.put(filterFrom.getAlias(),
						    ((Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + Integer.valueOf(oxygen_rate))
						            / 2) + "");
					} else if (j == 2) {
						dataRow.put(filterFrom.getAlias(),
						    ((Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + Integer.valueOf(pre_therapy))
						            / 2) + "");
					} else if (j == 3) {
						dataRow.put(filterFrom.getAlias(),
						    ((Integer.valueOf(dataRow.get(filterFrom.getAlias()).toString()) + Integer.valueOf(post_therapy))
						            / 2) + "");
					}

				}
			}

			finalReport.add(dataRow);

		}
		return finalReport;
	}

	@RequestMapping(value = "/dump/custom", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getCustomDumps(HttpServletRequest request,
	        @RequestParam(value = "workflow", required = false) String workflow,
	        @RequestParam(value = "form", required = false) String form,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to,
	        @RequestParam(value = "custom-type", required = true) String customType) throws JRException, IOException {
		service = Context.getService(HydraService.class);
		if (workflow == null)
			workflow = "";

		aliasList = new ArrayList<String>();
		duplicateAliasCount = 0;
		String format = detectDateFormat(from);
		Date sDate = fromString(from, format);
		String format1 = detectDateFormat(to);
		Date eDate = fromString(to, format1);

		Calendar c = Calendar.getInstance();
		c.setTime(eDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		eDate = c.getTime();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATEIMESTAMP);
		String prefix = sdf.format(cal.getTime());

		List<Map<String, Object>> finalReport = new ArrayList<Map<String, Object>>();

		if (customType.equals("screened-preexisting")) {
			finalReport = generateScreenedPreexisting(from, to);
		} else if (customType.equals("oxygen-age-gender")) {
			finalReport = generateOxygenAgeGender(from, to);
		} else if (customType.equals("oxygen-age-gender-condition")) {
			finalReport = generateOxygenAgeGenderCondition(from, to);
		} else if (customType.equals("screened-age-gender-condition")) {
			finalReport = generateScreenedAgeGenderCondition(from, to);
		} else if (customType.equals("oxygen-therapy-avg-age")) {
			finalReport = generateOxygenTherapyAvgAge(from, to);
		} else if (customType.equals("oxygen-therapy-age-gender-condition")) {
			finalReport = generateOxygenTherapyAvgAgeCondition(from, to);
		} else if (customType.equals("oxygen-therapy-avg")) {
			finalReport = generateOxygenTherapyAvg(from, to);
		}

		System.out.println("FINAL REPORT " + finalReport);
		String zipFile = System.getProperty("java.io.tmpdir") + File.separator + form + "_forms_" + prefix + ".zip";
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		byte[] buffer = new byte[1024];
		if (!finalReport.isEmpty()) {

			String outputFile = System.getProperty("java.io.tmpdir") + File.separator + form + "_" + prefix + ".csv";
			createCSVFileFromMapList(finalReport, outputFile);
			System.out.println("CSV: " + outputFile);
			File srcFile = new File(outputFile);

			FileInputStream fis = new FileInputStream(srcFile);

			// begin writing a new ZIP entry, positions the stream to the start of the entry
			// data
			zos.putNextEntry(new ZipEntry(srcFile.getName()));

			int length;

			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}

			zos.closeEntry();

			// close the InputStream
			fis.close();

			srcFile.delete();

		}

		zos.close();
		byte[] bytes = Files.readAllBytes(Paths.get(zipFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + form + "_" + prefix + ".zip");
		header.setContentLength(bytes.length);

		File file = new File(zipFile);
		file.delete();

		return new HttpEntity<byte[]>(bytes, header);

	}

	@RequestMapping(value = "/dump/encounters", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getEncounterDumps(HttpServletRequest request,
	        @RequestParam(value = "workflow", required = false) String workflow,
	        @RequestParam(value = "form", required = false) String form,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to) throws JRException, IOException {
		service = Context.getService(HydraService.class);
		if (workflow == null)
			workflow = "";

		aliasList = new ArrayList<String>();
		duplicateAliasCount = 0;
		String format = detectDateFormat(from);
		Date sDate = fromString(from, format);
		String format1 = detectDateFormat(to);
		Date eDate = fromString(to, format1);

		Calendar c = Calendar.getInstance();
		c.setTime(eDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		eDate = c.getTime();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATEIMESTAMP);
		String prefix = sdf.format(cal.getTime());

		int workflowId = service.getHydraWorkflowService().getWorkflowByName(workflow).getId();
		int encounterId = service.getHydraFormService().getHydraModuleFormByName(form).getEncounterType().getId();

		String query = generateQuery(encounterId, workflowId, form, from, to);

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(query);
		System.out.println("QUERY2: " + query);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = new ArrayList<Map<String, Object>>();
		try {
			aliasToValueMapList = sql.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("CSV rows size: " + aliasToValueMapList.size());

		String zipFile = System.getProperty("java.io.tmpdir") + File.separator + form + "_forms_" + prefix + ".zip";
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		byte[] buffer = new byte[1024];
		if (!aliasToValueMapList.isEmpty()) {

			String outputFile = System.getProperty("java.io.tmpdir") + File.separator + form + "_" + prefix + ".csv";
			createCSVFileFromMapList(aliasToValueMapList, outputFile);
			System.out.println("CSV: " + outputFile);
			File srcFile = new File(outputFile);

			FileInputStream fis = new FileInputStream(srcFile);

			// begin writing a new ZIP entry, positions the stream to the start of the entry
			// data
			zos.putNextEntry(new ZipEntry(srcFile.getName()));

			int length;

			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}

			zos.closeEntry();

			// close the InputStream
			fis.close();

			srcFile.delete();

		}

		zos.close();
		byte[] bytes = Files.readAllBytes(Paths.get(zipFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + form + "_" + prefix + ".zip");
		header.setContentLength(bytes.length);

		File file = new File(zipFile);
		file.delete();

		return new HttpEntity<byte[]>(bytes, header);

	}

	private String generateQuery(int encounterId, int workflowId, String form, String from, String to) {
		service = Context.getService(HydraService.class);
		String query = "SELECT EN.encounter_id as ENCOUNTER_ID, " + "ID.IDENTIFIER AS PATIENT_ID, "
		        + "UPPER(CONCAT(NM.GIVEN_NAME, ' ', NM.FAMILY_NAME)) AS PATIENT_NAME,PR.GENDER as `PERSON_GENDER`, "
		        + "YEAR(PR.date_created) - YEAR(PR.BIRTHDATE) AS AGE,PA.address2 AS ADDRESS,PA.state_province AS PROVINCE,PA.city_village AS CITY,PA.address3 As LAND_MARK "
		        + ",EN.encounter_datetime as ENCOUNTER_DATE, " + "US.USERNAME as USERNAME, LO.NAME as LOCATION";

		HydramoduleForm hydraForm = service.getHydraFormService().getHydraModuleFormByName(form);
		List<HydramoduleFormField> formFields = hydraForm.getFormFields();
		Collections.sort(formFields, new Comparator<HydramoduleFormField>() {

			@Override
			public int compare(HydramoduleFormField o1, HydramoduleFormField o2) {
				if (o1.getDisplayOrder() == null || o1.getDisplayOrder() == null) {
					return 0;
				}

				return o1.getDisplayOrder() - o2.getDisplayOrder();
			}

		});
		System.out.println("\n\n####Changed####\n\n");
		for (HydramoduleFormField f : formFields) {
			HydramoduleField field = f.getField();
			System.out.println(field.getFieldType().getId() + " FIELD: " + f.getId());
			if (field.getFieldType().getId() == 13 || field.getId() == 453) {
				String obsAge = getObsValue(168079, "Datetime", "contact_ages");
				query += ("," + obsAge);

				String obsGender = getObsValue(168080, "Text", "contact_genders");
				query += ("," + obsGender);

				String obsGiven = getObsValue(168081, "Text", "contact_names");
				query += ("," + obsGiven);

				String obsFamily = getObsValue(168082, "Text", "contact_family_names");
				query += ("," + obsFamily);

				String obsIdentifier = getObsValue(168083, "Text", "contact_identifiers");
				query += ("," + obsIdentifier);

				String obsRelation = getObsValue(168084, "Text", "contact_relations");
				query += ("," + obsRelation);
			}

			if (field.getConcept() != null) {
				String obsValue = getObsValue(field.getConcept().getConceptId(), field.getAttributeName(), getAliasFor(f));
				if (!obsValue.isEmpty())
					query += ("," + obsValue);
			}
		}

		query += " FROM" + " `hydra`.`patient` AS PT"
		        + " LEFT JOIN `hydra`.`patient_identifier` AS ID ON ID.PATIENT_ID = PT.PATIENT_ID AND ID.IDENTIFIER_TYPE = 3 and ID.voided=0"
		        + " LEFT JOIN `hydra`.`person_name` AS NM ON NM.PERSON_ID = PT.PATIENT_ID and NM.voided=0"
		        + " LEFT JOIN `hydra`.`person` AS PR ON PR.PERSON_ID = PT.PATIENT_ID and PR.voided=0"
		        + " LEFT JOIN `hydra`.`person_address` AS PA ON PA.PERSON_ID = PT.PATIENT_ID and PA.voided=0 and PA.person_address_id=(select max(person_address_id) from person_address where person_id=PT.patient_id)"
		        + " LEFT JOIN `hydra`.`encounter` AS EN ON EN.PATIENT_ID = PT.PATIENT_ID LEFT JOIN `hydra`.`users` AS US ON US.USER_ID = EN.CREATOR "
		        + " LEFT JOIN `hydra`.`location` AS LO ON LO.LOCATION_ID = EN.LOCATION_ID"
		        + " LEFT JOIN `hydra`.`hydramodule_form_encounter` AS FE ON FE.encounter_id = EN.encounter_id"
		        + " LEFT JOIN `hydra`.`hydramodule_component_form` AS CF ON CF.component_form_id = FE.component_form_id"
		        + " LEFT JOIN `hydra`.`hydramodule_workflow` AS WF ON WF.workflow_id = CF.workflow_id"
		        + " LEFT OUTER JOIN `hydra`.obs as o_sc on o_sc.encounter_id = EN.encounter_id and o_sc.voided=0";

		query += " where" + " EN.voided = 0 and" + " EN.encounter_datetime between '" + from + "' and '" + to + "' and"
		        + " EN.encounter_type = " + encounterId + "  and PT.voided=0 and WF.workflow_id=" + workflowId
		        + " group by EN.encounter_id;";

		return query;
	}

	private List<String> aliasList;

	private int duplicateAliasCount = 0;

	private String getAliasFor(HydramoduleFormField f) {
		String alias = f.getName().replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
		alias = processAlias(alias);
		return alias;
	}

	private String processAlias(String alias) {
		if (!aliasList.contains(alias)) {
			aliasList.add(alias);
		} else {
			alias = alias + "_" + (duplicateAliasCount + 1);
			processAlias(alias);
		}

		return alias;
	}

	public String getObsValue(int conceptId, String obsValueType, String alias) {
		// 168079, field.getAttributeName(), "contact_ages"
		String toReturn = "";

		if ("Coded".equals(obsValueType)) {
			toReturn = " group_concat(if(o_sc.concept_id = " + conceptId
			        + ", (Select name from `hydra`.`concept_name` where concept_id=(concat(o_sc.value_coded))and LOCALE_PREFERRED = 1 and voided=0 and locale='en'), NULL)) AS "
			        + alias;
		} else if ("Text".equals(obsValueType)) {
			toReturn = " group_concat(if(o_sc.concept_id = " + conceptId + ", concat(o_sc.value_text), NULL)) AS " + alias;
		} else if ("Datetime".equals(obsValueType)) {
			toReturn = " group_concat(if(o_sc.concept_id = " + conceptId + ", concat(o_sc.value_datetime), NULL)) AS "
			        + alias;
		} else if ("Numeric".equals(obsValueType)) {
			toReturn = " group_concat(if(o_sc.concept_id = " + conceptId + ", concat(o_sc.value_numeric), NULL)) AS "
			        + alias;
		}
		System.out.println("Processed: " + toReturn);
		return toReturn;
	}

	@RequestMapping(value = "/dump/patients", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getPatientDumps(HttpServletRequest request,
	        @RequestParam(value = "workflow", required = false) String workflow,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to) throws JRException, IOException {

		if (workflow == null)
			workflow = "";

		String format = detectDateFormat(from);
		Date sDate = fromString(from, format);
		String format1 = detectDateFormat(to);
		Date eDate = fromString(to, format1);

		Calendar c = Calendar.getInstance();
		c.setTime(eDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		eDate = c.getTime();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATEIMESTAMP);
		String prefix = Context.getUserContext().getAuthenticatedUser().getUsername().replace(".", "") + "_"
		        + sdf.format(cal.getTime());

		ProcedureCall call = sessionFactory.getCurrentSession().createStoredProcedureCall("norm_patient");

		call.registerParameter(1, Date.class, ParameterMode.IN).bindValue(sDate);
		call.registerParameter(2, Date.class, ParameterMode.IN).bindValue(eDate);
		call.registerParameter(3, String.class, ParameterMode.IN).bindValue(workflow);
		call.registerParameter(4, String.class, ParameterMode.IN).bindValue(prefix);
		call.getOutputs().getCurrent();

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery("select * from normpatient_" + prefix);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = sql.list();
		String outputFile = System.getProperty("java.io.tmpdir") + File.separator + "patients_dump_" + prefix + ".csv";
		createCSVFileFromMapList(aliasToValueMapList, outputFile);

		byte[] bytes = Files.readAllBytes(Paths.get(outputFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "patients_dump_" + prefix + ".csv");
		header.setContentLength(bytes.length);

		File file = new File(outputFile);
		file.delete();

		SQLQuery dropSql = sessionFactory.getCurrentSession().createSQLQuery("drop table if exists normpatient_" + prefix);
		dropSql.executeUpdate();

		return new HttpEntity<byte[]>(bytes, header);

	}

	@RequestMapping(value = "/dump/providers", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getProviderDumps(HttpServletRequest request,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to) throws JRException, IOException {

		String format = detectDateFormat(from);
		Date sDate = fromString(from, format);
		String format1 = detectDateFormat(to);
		Date eDate = fromString(to, format1);

		Calendar c = Calendar.getInstance();
		c.setTime(eDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		eDate = c.getTime();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATEIMESTAMP);
		String prefix = Context.getUserContext().getAuthenticatedUser().getUsername().replace(".", "") + "_"
		        + sdf.format(cal.getTime());

		ProcedureCall call = sessionFactory.getCurrentSession().createStoredProcedureCall("norm_provider");

		call.registerParameter(1, Date.class, ParameterMode.IN).bindValue(sDate);
		call.registerParameter(2, Date.class, ParameterMode.IN).bindValue(eDate);
		call.registerParameter(3, String.class, ParameterMode.IN).bindValue(prefix);
		call.getOutputs().getCurrent();

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery("select * from normprovider_" + prefix);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = sql.list();
		String outputFile = System.getProperty("java.io.tmpdir") + File.separator + "providers_dump_" + prefix + ".csv";
		createCSVFileFromMapList(aliasToValueMapList, outputFile);

		byte[] bytes = Files.readAllBytes(Paths.get(outputFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "providers_dump_" + prefix + ".csv");
		header.setContentLength(bytes.length);

		File file = new File(outputFile);
		file.delete();

		SQLQuery dropSql = sessionFactory.getCurrentSession().createSQLQuery("drop table if exists normprovider_" + prefix);
		dropSql.executeUpdate();

		return new HttpEntity<byte[]>(bytes, header);

	}

	@RequestMapping(value = "/dump/locations", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getLocationDumps(HttpServletRequest request,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to) throws JRException, IOException {

		String format = detectDateFormat(from);
		Date sDate = fromString(from, format);
		String format1 = detectDateFormat(to);
		Date eDate = fromString(to, format1);

		Calendar c = Calendar.getInstance();
		c.setTime(eDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		eDate = c.getTime();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATEIMESTAMP);
		String prefix = Context.getUserContext().getAuthenticatedUser().getUsername().replace(".", "") + "_"
		        + sdf.format(cal.getTime());

		ProcedureCall call = sessionFactory.getCurrentSession().createStoredProcedureCall("norm_location");

		call.registerParameter(1, Date.class, ParameterMode.IN).bindValue(sDate);
		call.registerParameter(2, Date.class, ParameterMode.IN).bindValue(eDate);
		call.registerParameter(3, String.class, ParameterMode.IN).bindValue(prefix);
		call.getOutputs().getCurrent();

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery("select * from normlocation_" + prefix);
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = sql.list();
		String outputFile = System.getProperty("java.io.tmpdir") + File.separator + "locations_dump_" + prefix + ".csv";
		createCSVFileFromMapList(aliasToValueMapList, outputFile);

		byte[] bytes = Files.readAllBytes(Paths.get(outputFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "locations_dump_" + prefix + ".csv");
		header.setContentLength(bytes.length);

		File file = new File(outputFile);
		file.delete();

		SQLQuery dropSql = sessionFactory.getCurrentSession().createSQLQuery("drop table if exists normlocation_" + prefix);
		dropSql.executeUpdate();

		return new HttpEntity<byte[]>(bytes, header);

	}

	public void createCSVFileFromMapList(List<Map<String, Object>> aliasToValueMapList, String outputFile) {

		File file = new File(outputFile);

		try {
			FileWriter outputfile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputfile);

			if (!aliasToValueMapList.isEmpty()) {

				// adding header to csv
				Map<String, Object> extractHeader = aliasToValueMapList.get(0);
				String[] header = new String[extractHeader.size()];
				int i = 0;
				for (Map.Entry<String, Object> entry : extractHeader.entrySet()) {
					header[i++] = entry.getKey();
				}
				writer.writeNext(header);

				// add data to csv
				for (Map<String, Object> maps : aliasToValueMapList) {
					Object[] data = new Object[extractHeader.size()];
					i = 0;
					for (Map.Entry<String, Object> entry : maps.entrySet()) {
						data[i++] = entry.getValue();
					}

					String[] stringArray = new String[data.length];
					for (int j = 0; j < data.length; j++)
						stringArray[j] = String.valueOf(data[j]);

					writer.writeNext(stringArray);

				}

			}

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getReportByName(HttpServletRequest request,
	        @RequestParam(value = "name", required = true) String name,
	        @RequestParam(value = "ext", required = true) String ext,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to,
	        @RequestParam(required = false) Map<String, String> params) throws JRException, IOException {

		String filePath = generateJasperReport(name, ext, params);
		String fileName = name + "." + ext;

		byte[] bytes = Files.readAllBytes(Paths.get(filePath));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.setContentLength(bytes.length);

		return new HttpEntity<byte[]>(bytes, header);

	}

	public String generateJasperReport(String reportName, String extension, Map<String, String> params) throws JRException {

		InputStream reportStream = getClass().getResourceAsStream("/rpt/" + reportName + ".jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

		String outputFile = System.getProperty("java.io.tmpdir") + File.separator + reportName;

		String url = Context.getRuntimeProperties().getProperty("connection.url", null);

		Connection conn;
		try {
			conn = connect(url);
		}
		catch (SQLException e) {
			log.error("Error connecting to DB.", e);
			return "Error connecting to DB.";
		}

		/* Map to hold Jasper report Parameters */
		Map<String, Object> parameters = new HashMap<String, Object>();
		String startDate = params.get("from");
		String format = detectDateFormat(startDate);
		Date sDate = fromString(startDate, format);
		parameters.put("from", sDate);

		String endDate = params.get("to");
		String format1 = detectDateFormat(endDate);
		Date eDate = fromString(endDate, format1);
		parameters.put("to", eDate);

		params.remove("from");
		params.remove("to");

		Iterator it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			parameters.put(pair.getKey().toString(), pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}

		// Using compiled version(.jasper) of Jasper report to generate PDF
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

		if (extension.equals("xlsx")) {
			outputFile = outputFile + ".xlsx";
			exportExcelFormat(jasperPrint, outputFile);
		} else if (extension.equals("pdf")) {
			outputFile = outputFile + ".pdf";
			exportPDFFormat(jasperPrint, outputFile, true);
		}

		return outputFile;
	}

	/**
	 * Generates a PDF report
	 * 
	 * @param jasperPrint
	 * @param exportPath
	 * @param pdfAutoPrint
	 * @throws JRException
	 */
	private static void exportPDFFormat(JasperPrint jasperPrint, String exportPath, boolean pdfAutoPrint)
	        throws JRException {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, exportPath);
		if (pdfAutoPrint)
			exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
		exporter.exportReport();

	}

	/**
	 * Generates an Excel report
	 * 
	 * @param jasperPrint
	 * @param exportPath
	 * @throws JRException
	 */
	private static void exportExcelFormat(JasperPrint jasperPrint, String exportPath) throws JRException {
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, exportPath);
		exporter.exportReport();
	}

	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static Connection connect(String url) throws SQLException {
		// Step 1: Load the JDBC driver.
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			log.error("Could not find JDBC driver class.", e);
			throw (SQLException) e.fillInStackTrace();
		}

		// Step 2: Establish the connection to the database.
		String username = Context.getRuntimeProperties().getProperty("connection.username");
		String password = Context.getRuntimeProperties().getProperty("connection.password");
		log.debug("connecting to DATABASE: " + OpenmrsConstants.DATABASE_NAME + " USERNAME: " + username + " URL: " + url);
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Returns closest matching date format from given date in string
	 * 
	 * @param dateString
	 * @return
	 */
	public static String detectDateFormat(String dateString) {
		for (Entry<String, String> entry : DATE_FORMATS.entrySet()) {
			if (dateString.toLowerCase().matches(entry.getKey())) {
				return entry.getValue();
			}
		}
		throw new InvalidParameterException("Given date does not match any of the standard conventions.");
	}

	public static Date fromString(String string, String format) {
		if (string == null) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date;
		try {
			date = simpleDateFormat.parse(string);
		}
		catch (ParseException e) {
			try {
				simpleDateFormat = new SimpleDateFormat(detectDateFormat(string));
				date = simpleDateFormat.parse(string);
			}
			catch (ParseException e2) {
				log.error(e2.getMessage());
				return null;
			}
		}
		return date;
	}

	public static class AliasToEntityOrderedMapResultTransformer extends AliasedTupleSubsetResultTransformer {

		public final static AliasToEntityOrderedMapResultTransformer INSTANCE = new AliasToEntityOrderedMapResultTransformer();

		/**
		 * Disallow instantiation of AliasToEntityOrderedMapResultTransformer .
		 */
		private AliasToEntityOrderedMapResultTransformer() {
		}

		/**
		 * {@inheritDoc}
		 */
		public Object transformTuple(Object[] tuple, String[] aliases) {
			/* please note here LinkedHashMap is used so hopefully u ll get ordered key */
			Map result = new LinkedHashMap(tuple.length);
			for (int i = 0; i < tuple.length; i++) {
				String alias = aliases[i];
				if (alias != null) {
					result.put(alias, tuple[i]);
				}
			}
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
			return false;
		}

		/**
		 * Serialization hook for ensuring singleton uniqueing.
		 * 
		 * @return The singleton instance : {@link #INSTANCE}
		 */
		private Object readResolve() {
			return INSTANCE;
		}
	}

}
