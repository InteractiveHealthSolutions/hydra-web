package org.openmrs.module.hydra.api;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.hydra.api.dao.HydraDaoImpl;
import org.openmrs.module.hydra.model.HydramoduleField;
import org.openmrs.module.hydra.model.HydramoduleFieldRule;
import org.openmrs.module.hydra.model.HydramoduleFormField;
import org.openmrs.module.hydra.model.HydramoduleRuleToken;

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
		actionNames.put("Open Form", "openFormWhen");
		actionNames.put("autoselect", "autoselectWhen");

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
		// "parsedRule":
		// "{\"hiddenWhen\":[{\"questionId\":10,\"notEquals\":[{\"uuid\":\"OTHER\"}],\"id\":10},\"OR\"]}"
		this.dao = dao;
		HydramoduleField field = ff.getField();
		JSONObject ruleObj = new JSONObject();

		List<HydramoduleFieldRule> rules = dao.getHydramoduleFieldRuleByTargetFormField(ff);

		// {"hiddenWhen":[{"questionId":10,"notEquals":[{"uuid":"OTHER"}],"id":10},"OR"]}
		// {"autoSelect":[{"questionId":10,"notEquals":[{"uuid":"OTHER"}],"id":10},"OR"]}
		/**
		 * now, null needs to be handled
		 **/

		if (rules.size() > 0) {
			for (int i = 0; i < rules.size(); i++) {
				HydramoduleFieldRule rule = rules.get(i);
				String actionName = rule.getActionName();
				if (actionName.equals("hide")) {
					try {
						ruleObj.put(actionNames.get(actionName), compile(rule, actionName));
					}
					catch (Exception e) {
						e.printStackTrace(); // TODO a NullPointErexception needs to be avoided in #compile()
					}
				} else if (actionName.equals("autoselect")) {
					try {
						JSONObject object = new JSONObject();
						object.put("targetFieldAnswer", rule.getTargetFieldAnswer().getConcept().getDisplayString());
						object.put("when", compile(rule, actionName));

						JSONArray array;
						if (ruleObj.containsKey(actionNames.get(actionName))) {
							array = (JSONArray) ruleObj.get(actionNames.get(actionName));
						} else {
							array = new JSONArray();
						}

						array.add(object);
						ruleObj.put(actionNames.get(actionName), array);
					}
					catch (Exception e) {
						e.printStackTrace(); // TODO a NullPointErexception needs to be avoided in #compile()
					}
				}
			}

			System.out.println("The parsed RULE Object " + ruleObj);
			return ruleObj.toString();
		}

		return null;
	}

	public JSONArray compile(HydramoduleFieldRule rule, String actionName) {
		JSONObject ruleObj = new JSONObject();
		JSONArray whenArray = new JSONArray();
		JSONObject conditionObject = new JSONObject();
		JSONArray conditionArray = new JSONArray();
		HashMap<String, HydramoduleRuleToken> singleRuleTokens = new HashMap<String, HydramoduleRuleToken>();
		boolean operatorAvailable = false;
		List<HydramoduleRuleToken> tokens = rule.getTokens();
		// System.out.println("Tokens Received: " + tokens.size());
		for (HydramoduleRuleToken token : tokens) {
			// System.out.println("Token Value: " + token.getValue());
			if (conditionalOperatorsMap.containsKey(token.getValue())) {
				if (!operatorAvailable)
					whenArray.add(token.getValue());
				operatorAvailable = true;
			} else {
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
					conditionObject = compileSingleObj(singleRuleTokens.get("Operator"), singleRuleTokens.get("Question"),
					    singleRuleTokens.get("Value"));

					whenArray.add(conditionObject);
					singleRuleTokens.clear();
				}
			}
			// System.out.println("TokenType: " + token.getTypeName() + " , value: " +
			// token.getValue());
		}

		if (!operatorAvailable)
			whenArray.add("OR");

		return whenArray;
	}

}
