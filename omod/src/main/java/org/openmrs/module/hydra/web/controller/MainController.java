package org.openmrs.module.hydra.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Naveed
 */
@Controller
@RequestMapping(value = "module/hydra")
public class MainController {

	HttpServletRequest request;

	protected final Log log = LogFactory.getLog(getClass());

	private final String SUCCESS_FORM_VIEW = "/module/hydra/index";

	@RequestMapping(value = "/index.form", method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(value = "/index.form", method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession,
			@ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors) {

		if (errors.hasErrors()) {

		}
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(value = "/phases", method = RequestMethod.GET)
	public String getEncounters(HttpServletRequest request) throws Exception {

		return ":D";
	}

}
