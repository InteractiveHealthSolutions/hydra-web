package org.openmrs.module.hydra.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSessionFactory;
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

	private static final Map<String, String> DATE_FORMATS;

	@Autowired
	private DbSessionFactory sessionFactory;

	static {
		DATE_FORMATS = new HashMap();

		// Date only
		DATE_FORMATS.put("^\\d{2}[0-1]\\d[0-3]\\d$", "yyMMdd");
		DATE_FORMATS.put("^\\d{4}[0-1]\\d[0-3]\\d$", SQL_DATESTAMP);
		DATE_FORMATS.put("^\\d{2}-[0-1]\\d-[0-3]\\d$", "yy-MM-dd");
		DATE_FORMATS.put("^\\d{4}-[0-1]\\d-[0-3]\\d$", SQL_DATE);
		DATE_FORMATS.put("^[0-3]\\d-[0-1]\\d-\\d{2}$", "dd-MM-yy");
		DATE_FORMATS.put("^[0-3]\\d-[0-1]\\d-\\d{4}$", STANDARD_DATE_HYPHENATED);
		DATE_FORMATS.put("^[0-3]\\d [0-1]\\d \\d{2}$", "dd MM yy");
		DATE_FORMATS.put("^[0-3]\\d [0-1]\\d \\d{4}$", "dd MM yyyy");
		DATE_FORMATS.put("^[0-3]\\d/[0-1]\\d/\\d{2}$", "dd/MM/yy");
		DATE_FORMATS.put("^[0-3]\\d/[0-1]\\d/\\d{4}$", STANDARD_DATE);
		DATE_FORMATS.put("^[0-3]\\d [a-z]{3} \\d{2}$", "dd MMM yy");
		DATE_FORMATS.put("^[0-3]\\d [a-z]{3} \\d{4}$", "dd MMM yyyy");

	}

	@RequestMapping(value = "/dump/encounters", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getEncounterDumps(HttpServletRequest request,
	        @RequestParam(value = "workflow", required = true) String workflow,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to) throws JRException, IOException {

		String format = detectDateFormat(from);
		Date sDate = fromString(from, format);

		String format1 = detectDateFormat(to);
		Date eDate = fromString(to, format1);

		ProcedureCall call = sessionFactory.getCurrentSession().createStoredProcedureCall("deencountrised");

		call.registerParameter(1, Date.class, ParameterMode.IN).bindValue(sDate);
		call.registerParameter(2, Date.class, ParameterMode.IN).bindValue(eDate);
		call.registerParameter(3, String.class, ParameterMode.IN).bindValue(workflow);
		call.getOutputs().getCurrent();

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(
		    "select CONCAT('enc_',alphanum(LOWER(encounter_type.name))) from encounter_type where retired = 0");
		List<String> encounterTables = sql.list();

		String zipFile = System.getProperty("java.io.tmpdir") + File.separator + "encounters_dump.zip";
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		byte[] buffer = new byte[1024];

		for (String encounterTable : encounterTables) {

			sql = sessionFactory.getCurrentSession().createSQLQuery("select * from " + encounterTable);
			sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
			List<Map<String, Object>> aliasToValueMapList = sql.list();

			if (!aliasToValueMapList.isEmpty()) {

				String outputFile = System.getProperty("java.io.tmpdir") + File.separator + encounterTable + ".csv";
				createCSVFileFromMapList(aliasToValueMapList, outputFile);

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

			}

		}

		zos.close();

		byte[] bytes = Files.readAllBytes(Paths.get(zipFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "encounters_dump.zip");
		header.setContentLength(bytes.length);

		return new HttpEntity<byte[]>(bytes, header);

	}

	@RequestMapping(value = "/dump/patients", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getPatientDumps(HttpServletRequest request,
	        @RequestParam(value = "workflow", required = true) String workflow,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "to", required = true) String to) throws JRException, IOException {

		String format = detectDateFormat(from);
		Date sDate = fromString(from, format);

		String format1 = detectDateFormat(to);
		Date eDate = fromString(to, format1);

		ProcedureCall call = sessionFactory.getCurrentSession().createStoredProcedureCall("norm_patient");

		call.registerParameter(1, Date.class, ParameterMode.IN).bindValue(sDate);
		call.registerParameter(2, Date.class, ParameterMode.IN).bindValue(eDate);
		call.registerParameter(3, String.class, ParameterMode.IN).bindValue(workflow);
		call.getOutputs().getCurrent();

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery("select * from norm_patient");
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = sql.list();
		String outputFile = System.getProperty("java.io.tmpdir") + File.separator + "patients_dump.csv";
		if (aliasToValueMapList.isEmpty())
			new File(outputFile);
		else
			createCSVFileFromMapList(aliasToValueMapList, outputFile);

		byte[] bytes = Files.readAllBytes(Paths.get(outputFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "patients_dump.csv");
		header.setContentLength(bytes.length);

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

		ProcedureCall call = sessionFactory.getCurrentSession().createStoredProcedureCall("norm_provider");

		call.registerParameter(1, Date.class, ParameterMode.IN).bindValue(sDate);
		call.registerParameter(2, Date.class, ParameterMode.IN).bindValue(eDate);
		call.getOutputs().getCurrent();

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery("select * from norm_provider");
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = sql.list();
		String outputFile = System.getProperty("java.io.tmpdir") + File.separator + "providers_dump.csv";
		if (aliasToValueMapList.isEmpty())
			new File(outputFile);
		else
			createCSVFileFromMapList(aliasToValueMapList, outputFile);

		byte[] bytes = Files.readAllBytes(Paths.get(outputFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "providers_dump.csv");
		header.setContentLength(bytes.length);

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

		ProcedureCall call = sessionFactory.getCurrentSession().createStoredProcedureCall("norm_location");

		call.registerParameter(1, Date.class, ParameterMode.IN).bindValue(sDate);
		call.registerParameter(2, Date.class, ParameterMode.IN).bindValue(eDate);
		call.getOutputs().getCurrent();

		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery("select * from norm_location");
		sql.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = sql.list();
		String outputFile = System.getProperty("java.io.tmpdir") + File.separator + "locations_dump.csv";
		if (aliasToValueMapList.isEmpty())
			new File(outputFile);
		else
			createCSVFileFromMapList(aliasToValueMapList, outputFile);

		byte[] bytes = Files.readAllBytes(Paths.get(outputFile));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "locations_dump.csv");
		header.setContentLength(bytes.length);

		return new HttpEntity<byte[]>(bytes, header);

	}

	public void createCSVFileFromMapList(List<Map<String, Object>> aliasToValueMapList, String outputFile) {

		File file = new File(outputFile);

		try {
			FileWriter outputfile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputfile);

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
