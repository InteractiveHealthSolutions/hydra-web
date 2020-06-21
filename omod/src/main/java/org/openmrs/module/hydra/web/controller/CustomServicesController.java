package org.openmrs.module.hydra.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.AbstractDocument.Content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.HydraService;
import org.openmrs.module.hydra.model.HydramoduleComponentForm;
import org.openmrs.module.hydra.model.HydramoduleForm;
import org.openmrs.module.hydra.model.HydramoduleFormEncounter;
import org.openmrs.module.hydra.model.HydramodulePatientWorkflow;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/hydra/customservices")
public class CustomServicesController {

	private static Log log = LogFactory.getLog(CustomServicesController.class);
	
	private HydraService hydraService = Context.getService(HydraService.class);


	@RequestMapping(value = "/getUserByRole", method = RequestMethod.GET)
	@ResponseBody
	List<String> getUsersByRole(HttpServletRequest request, @RequestParam(value = "role", required = true) String roleUuid) {

		Role role = Context.getUserService().getRoleByUuid(roleUuid);
		try {
			if (role != null) {
				List<User> usersAssociatedWithTheRole = Context.getUserService().getUsersByRole(role);
				List<String> userSystemIds = new ArrayList();
				for (int i = 0; i < usersAssociatedWithTheRole.size(); i++) {
					userSystemIds.add(usersAssociatedWithTheRole.get(i).getSystemId());
				}
				return userSystemIds;
			}
		}
		catch (Exception e) {
			log.error(e);
		}
		return null;

	}
	 
	@RequestMapping(value = "/saveformencounterqxr", method = RequestMethod.GET)
	@ResponseBody
	public String saveFormEncounterForQXR(HttpServletRequest request, @RequestParam(value="patientId", required=true) Integer patientId,@RequestParam(value="resultencounterId", required=true) Integer resultEncounterId ) {
		try {
			HydramoduleForm form = hydraService.getHydraModuleFormByName("Xray Result Form");
			
			HydramodulePatientWorkflow hydramodulePatientWorkflow = hydraService
			        .getHydramodulePatientWorkflowByPatient(patientId);
			
			HydramoduleComponentForm componentForm = hydraService.getComponentFormByFormAndWorkflow(form,hydramodulePatientWorkflow.getWorkflow());
			
			HydramoduleFormEncounter formEncounter = new HydramoduleFormEncounter();
			
			Encounter resultEncounter = Context.getEncounterService().getEncounter(resultEncounterId);
			
			formEncounter.setComponentForm(componentForm);
			formEncounter.setEncounter(resultEncounter);
			
			hydraService.saveFormEncounter(formEncounter);
			
			return "sucessfully saved";
		}
		catch (Exception e) {
			log.error(e);
		}
			return null;
	}
}
