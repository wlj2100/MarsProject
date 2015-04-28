package mars.client;

import java.util.ArrayList;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;

/**
 * @author Liangji 
 */
public class Configuration {
	private final ArrayList<Module> minConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> minConfig2 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig2 = new ArrayList<Module>();
	private int addCounter;
	private Storage localConfig = Storage.getLocalStorageIfSupported();

	public Configuration() {
		localConfig.setItem("minConfig1", listToConfig(minConfig1));
		localConfig.setItem("minConfig2", listToConfig(minConfig2));
		localConfig.setItem("maxConfig1", listToConfig(maxConfig1));
		localConfig.setItem("maxConfig2", listToConfig(maxConfig2));
		addCounter = 0;
		// TODO
	}

	public void addConfig(ArrayList<Module> list) {
		localConfig.setItem("c" + Integer.toString(addCounter),
				listToConfig(list));
		addCounter++;
	}

	public ArrayList<Module> ConfigToList(String key) {
		ArrayList<Module> list = new ArrayList<Module>();
		Object obj = JSONParser.parseLenient(localConfig.getItem(key));
		JSONArray jA = (JSONArray) obj;
		for (int i = 0; i < jA.size(); i++) {
			JSONObject jO = (JSONObject) jA.get(i);
			JSONNumber jN;
			JSONString jS;
			Module module = new Module();
			jN = (JSONNumber) jO.get("code");
			module.setCode((int) jN.doubleValue());
			jS = (JSONString) jO.get("status");
			module.setTheString(jS.stringValue());
			jN = (JSONNumber) jO.get("turns");
			module.setTurns((int) jN.doubleValue());
			jN = (JSONNumber) jO.get("X");
			module.setXcoord((int) jN.doubleValue());
			jN = (JSONNumber) jO.get("Y");
			module.setYcoord((int) jN.doubleValue());
			list.add(module);
		}
		return list;
	}

	public String listToConfig(ArrayList<Module> list) {
		StringBuilder aStringBuilder = new StringBuilder();
		aStringBuilder.append("[");
		for (int i = 0; i < list.size(); i++) {
			aStringBuilder.append((list.get(i)).toString()).append(",");
		}
		//delete extra ","
		aStringBuilder.deleteCharAt(aStringBuilder.length());
		aStringBuilder.append("]");
		return aStringBuilder.toString();
	}
}
