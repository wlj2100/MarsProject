/**
 * 
 */
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
 * this class is for save and load habitat configuration
 * the astronaut should be able to sav a selected configuration, exit the system, then load the saved configuration
 * related to HTML5 local storage
 */
public class Configuration {
	private ArrayList<Module> minConfig1 = new ArrayList<Module>();
	private ArrayList<Module> minConfig2 = new ArrayList<Module>();
	private ArrayList<Module> maxConfig1 = new ArrayList<Module>();
	private ArrayList<Module> maxConfig2 = new ArrayList<Module>();
	private int addCounter;
	private Storage localConfig= Storage.getLocalStorageIfSupported();
	public Configuration() {
		
		addCounter = 0;
		//TODO
	}
	
	public void  addConfig(ArrayList<Module> list) {

		localConfig.setItem("config" + Integer.toString(addCounter), listToConfig());
		addCounter++;
	}
	
	public ArrayList<Module> getConfig(String key) {
		ArrayList<Module> list = new ArrayList<Module>();
		Object obj = JSONParser.parseLenient(localConfig.getItem(key));
		JSONArray jA = (JSONArray)obj;
		for (int i =0; i < jA.size(); i++) {
			JSONObject jO = (JSONObject)jA.get(i);
			JSONNumber jN;
	    	JSONString jS;
	    	Module module = new Module();
	    	jN = (JSONNumber)jO.get("code");
	    	module.setCode((int)jN.doubleValue());
	    	jS = (JSONString)jO.get("status");
	    	module.setTheString(jS.stringValue());
	    	jN = (JSONNumber)jO.get("turns");
	    	module.setTurns((int)jN.doubleValue());
	    	jN = (JSONNumber)jO.get("X");
	    	module.setXcoord((int)jN.doubleValue());
	    	jN = (JSONNumber)jO.get("Y");
	    	module.setYcoord((int)jN.doubleValue());
	    	list.add(module);
		}
		return list;
	}
	
	public String listToConfig() {
		StringBuilder aStringBuilder = new StringBuilder();
		//TODO
		return aStringBuilder.toString();
	}
}
