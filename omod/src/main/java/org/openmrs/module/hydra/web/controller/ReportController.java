package org.openmrs.module.hydra.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Map.Entry;
import org.springframework.core.io.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getReportByName(HttpServletRequest request,
	        @RequestParam(value = "name", required = true) String name,
	        @RequestParam(value = "ext", required = true) String ext,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "ext", required = true) String to,
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
}
