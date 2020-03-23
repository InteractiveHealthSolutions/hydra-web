package org.openmrs.module.hydra.api.form_service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openmrs.Location;

public class JSONUtils {

	private static JSONUtils instance;

	private JSONUtils() {
	}

	public static JSONUtils getInstance() {
		if (instance == null) {
			instance = new JSONUtils();
		}

		return instance;
	}

	public JSONObject findJSONObjectInJSONArray(JSONArray data, String key) {
		JSONObject object;
		for (int i = 0; i < data.size(); i++) {
			object = (JSONObject) data.get(i);
			if (object.containsKey(key)) {
				return object;
			}
		}

		return null;
	}

	public String getParamValue(JSONArray data, String key) {
		JSONObject param = findJSONObjectInJSONArray(data, key);
		if (param != null && param.containsKey(key)) {
			return param.get(key).toString();
		}

		return null;
	}

	public JSONArray getParamArrayValue(JSONArray data, String key) {
		return (JSONArray) findJSONObjectInJSONArray(data, key).get(key);
	}

	public JSONArray getLocationsJSON(List<Location> locations) {

		JSONArray array = new JSONArray();
		int i = 0;
		JSONObject object = new JSONObject();
		for (Location location : locations) {
			JSONObject temp = getLocationJSON(location);

			array.add(temp);
		}

		return array;

	}

	private JSONObject getLocationJSON(Location location) {

		if (location == null)
			return null;

		JSONObject temp = new JSONObject();
		try {
			temp.put("name", location.getName());
			temp.put("locationId", location.getLocationId());
			temp.put("address1", location.getAddress1());
			temp.put("address2", location.getAddress2());
			temp.put("cityVillage", location.getCityVillage());
			temp.put("stateProvince", location.getStateProvince());
			temp.put("country", location.getCountry());
			temp.put("postalCode", location.getPostalCode());
			temp.put("latitude", location.getLatitude());
			temp.put("longitude", location.getLongitude());
			temp.put("countyDistrict", location.getCountyDistrict());
			temp.put("address3", location.getAddress3());
			temp.put("address4", location.getAddress4());
			temp.put("address5", location.getAddress5());
			temp.put("address6", location.getAddress6());
			temp.put("address7", location.getAddress7());
			temp.put("address8", location.getAddress8());
			temp.put("address9", location.getAddress9());
			temp.put("address10", location.getAddress10());
			temp.put("address11", location.getAddress11());
			temp.put("parentLocation", getLocationJSON(location.getParentLocation()));

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
}
