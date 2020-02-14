package org.openmrs.module.hydra.api;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.model.workflow.HydramoduleField;
import org.openmrs.module.hydra.model.workflow.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.workflow.HydramoduleFormField;
import org.openmrs.module.hydra.model.workflow.HydramoduleRuleToken;

public class SExprHelper {

	HashMap<String, String> conditionalOperatorsMap = new HashMap<String, String>();

	HashMap<String, String> operatorsMap = new HashMap<String, String>();

	HashMap<String, String> actionNames = new HashMap<String, String>();

	private static SExprHelper instance;

	private HydraDaoImpl dao;

	public static synchronized SExprHelper getInstance() {
		if (instance == null)
			instance = new SExprHelper();

		return instance;
	}

	private SExprHelper() {
		actionNames.put("hide", "hiddenWhen");
		actionNames.put("Open Form", "openForm");

		operatorsMap.put("!=", "notEquals");
		operatorsMap.put("=", "equals");
		operatorsMap.put("<", "lessThan");
		operatorsMap.put(">", "greaterThan");
		operatorsMap.put("<=", "lessThanEquals");
		operatorsMap.put(">=", "greaterThanEquals");

		conditionalOperatorsMap.put("OR", "OR");
		conditionalOperatorsMap.put("AND", "AND");
	}

	private JSONObject compileSingleObj(HydramoduleRuleToken operatorToken, HydramoduleRuleToken questionToken,
	        HydramoduleRuleToken answerToken) {

		JSONObject conditionObject = new JSONObject();
		JSONArray conditionArray = new JSONArray();

		// Question part of the expression
		String questionString = questionToken.getValue();
		HydramoduleField responseField = dao.getHydramoduleField(questionString);
		conditionObject.put("id", responseField.getFieldId());
		conditionObject.put("questionId", responseField.getFieldId());

		// Operator part of the expression
		String operatorName = operatorsMap.get(operatorToken.getValue());
		conditionObject.put(operatorName, conditionArray);

		// Value part of the expression
		if (answerToken.getTypeName().equals("CodedValue")) {
			Concept concept = Context.getConceptService().getConceptByUuid(answerToken.getValue());
			JSONObject responseValueObj = new JSONObject();
			responseValueObj.put("uuid", concept.getDisplayString());
			conditionArray.add(responseValueObj);
		} else if (answerToken.getTypeName().equals("OpenValue")) {
			JSONObject responseValueObj = new JSONObject();
			responseValueObj.put("uuid", answerToken.getValue());
			conditionArray.add(responseValueObj);
		}

		return conditionObject;
	}

	public String compileComplex(HydraDaoImpl dao, HydramoduleFormField ff) {
		this.dao = dao;
		HydramoduleField field = ff.getField();
		JSONObject hiddenWhenObj = new JSONObject();
		JSONArray hiddenWhenArray = new JSONArray();
		JSONObject conditionObject = new JSONObject();
		JSONArray conditionArray = new JSONArray();
		List<HydramoduleFieldRule> rules = dao.getHydramoduleFieldRuleByTargetField(field);
		boolean operatorAvailable = false;
		/**
		 * now, null
		 **/
		HashMap<String, HydramoduleRuleToken> singleRuleTokens = new HashMap<String, HydramoduleRuleToken>();
		if (rules.size() > 0) {
			HydramoduleFieldRule rule = rules.get(0); // Currently expecting only one rule from a field
			String actionName = rule.getActionName();
			if (actionName.equals("hide")) { // TODO this only controls hard coded value right now
				List<HydramoduleRuleToken> tokens = rule.getTokens();
				System.out.println("Tokens Received: " + tokens.size());
				for (HydramoduleRuleToken token : tokens) {
					if (conditionalOperatorsMap.containsKey(token.getTypeName())) {
						hiddenWhenArray.add(token.getTypeName());
						operatorAvailable = true;
					}
					// Question part of the expression
					if (token.getTypeName().equals("Question")) {
						singleRuleTokens.put("Question", token);
					}
					// Operator part of the expression
					else if (token.getTypeName().equals("Operator")) {
						singleRuleTokens.put("Operator", token);
					}
					// Value part of the expression
					else if (token.getTypeName().endsWith("Value")) {
						singleRuleTokens.put("Value", token);
					}

					if (singleRuleTokens.size() == 3) {
						conditionObject = compileSingleObj(singleRuleTokens.get("Operator"),
						    singleRuleTokens.get("Question"), singleRuleTokens.get("Value"));
						hiddenWhenArray.add(conditionObject);
						singleRuleTokens = new HashMap<String, HydramoduleRuleToken>();
					}
					System.out.println("TokenType: " + token.getTypeName() + " , value: " + token.getValue());
				}

				if (!operatorAvailable)
					hiddenWhenArray.add("OR");
				hiddenWhenObj.put(actionNames.get(actionName), hiddenWhenArray);
				System.out.println("The parsed RULE Object " + hiddenWhenObj);
				return hiddenWhenObj.toString();
			}
		}

		return null;
	}

	public String compile(HydraDaoImpl dao, HydramoduleFormField ff) {
		this.dao = dao;
		HydramoduleField field = ff.getField();
		JSONObject hiddenWhenObj = new JSONObject();
		JSONArray hiddenWhenArray = new JSONArray();
		JSONObject conditionObject = new JSONObject();
		JSONArray conditionArray = new JSONArray();
		List<HydramoduleFieldRule> rules = dao.getHydramoduleFieldRuleByTargetField(field);

		/**
		 * now, null
		 **/
		if (rules.size() > 0) {
			HydramoduleFieldRule rule = rules.get(0); // Currently expecting only one rule from a field
			String actionName = rule.getActionName();
			if (actionName.equals("hide")) { // TODO this only controls hard coded value right now
				List<HydramoduleRuleToken> tokens = rule.getTokens();
				System.out.println("Tokens Received: " + tokens.size());
				for (HydramoduleRuleToken token : tokens) {
					if (conditionalOperatorsMap.containsKey(token.getTypeName())) {
						// TODO Handle condition
					}
					// Question part of the expression
					if (token.getTypeName().equals("Question")) {
						String questionString = token.getValue();
						HydramoduleField responseField = dao.getHydramoduleField(questionString);
						conditionObject.put("id", responseField.getFieldId());
						conditionObject.put("questionId", responseField.getFieldId());

					}
					// Operator part of the expression
					else if (token.getTypeName().equals("Operator")) {
						String operatorName = operatorsMap.get(token.getValue());
						conditionObject.put(operatorName, conditionArray);

					}
					// Value part of the expression
					else if (token.getTypeName().endsWith("Value")) {
						if (token.getTypeName().equals("CodedValue")) {
							Concept concept = Context.getConceptService().getConceptByUuid(token.getValue());
							JSONObject responseValueObj = new JSONObject();
							responseValueObj.put("uuid", concept.getDisplayString());
							conditionArray.add(responseValueObj);
						} else if (token.getTypeName().equals("OpenValue")) {
							JSONObject responseValueObj = new JSONObject();
							responseValueObj.put("uuid", token.getValue());
							conditionArray.add(responseValueObj);
						}
					}
					System.out.println("TokenType: " + token.getTypeName() + " , value: " + token.getValue());
				}

				hiddenWhenArray.add(conditionObject);
				hiddenWhenArray.add("OR");
				hiddenWhenObj.put(actionNames.get(actionName), hiddenWhenArray);
				System.out.println("The parsed RULE Object " + hiddenWhenObj);
				return hiddenWhenObj.toString();
			}
		}

		return null;
	}

}
