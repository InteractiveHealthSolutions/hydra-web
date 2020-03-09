package org.openmrs.module.hydra.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/hydra/formData")
public class FormSubmissionController {

	private static Log log = LogFactory.getLog(FormSubmissionController.class);

	public static final String STANDARD_DATE = "dd/MM/yyyy";

	public static final String STANDARD_DATE_HYPHENATED = "dd-MM-yyyy";

	public static final String SQL_DATE = "yyyy-MM-dd";

	public static final String SQL_DATESTAMP = "yyyyMMdd";

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getReportByName(HttpServletRequest request,
	        @RequestParam(value = "name", required = true) String name,
	        @RequestParam(value = "ext", required = true) String ext,
	        @RequestParam(value = "from", required = true) String from,
	        @RequestParam(value = "ext", required = true) String to,
	        @RequestParam(required = false) Map<String, String> params) throws JRException, IOException {

		String filePath = "";
		String fileName = name + "." + ext;

		byte[] bytes = Files.readAllBytes(Paths.get(filePath));

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.setContentLength(bytes.length);

		return new HttpEntity<byte[]>(bytes, header);

	}

}
