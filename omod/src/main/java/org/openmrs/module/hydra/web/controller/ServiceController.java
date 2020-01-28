package org.openmrs.module.hydra.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author naveed.iqbal@ihsinformatics.com
 */
@Controller
// @RestController
public class ServiceController {

	@RequestMapping(value = "module/hydra/homepage", method = RequestMethod.GET)
	@ResponseBody
	public String showHomepage(HttpServletRequest request) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		return "Daaaata";
	}

	/*
	 * HttpServletRequest request;
	 * 
	 * protected final Log log = LogFactory.getLog(getClass());
	 * 
	 * @RequestMapping(value = "module/hydra/getQuestions", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public String getEncounters(HttpServletRequest request) throws
	 * Exception { String uuid = request.getParameter("uuid"); return null; }
	 * 
	 * @RequestMapping(value = "module/hydra/saveQuestion", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public String getImages(HttpServletRequest request) throws
	 * Exception { HttpClient client = new DefaultHttpClient(); String uuid =
	 * request.getParameter("uuid");
	 * 
	 * return "kaksdkasdkksak"; }
	 */

}
