package mars.client;


import java.util.ArrayList;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

 


/** Pulls "location" into a GPS 
 * 
 * @author Kyle Freese
 *
 */
public class CopyOfGPS {

	private Storage localConfig = Storage.getLocalStorageIfSupported();
	private int test = 1;
	private String url;
	private VerticalPanel vp;
	
	// php script
	private String proxy = "http://www.d.umn.edu/~frees033/proxy.php?q=";
	
	/**
	 * Default CTOR
	 */
	public CopyOfGPS(){
				this.url = this.proxy+"http://www.d.umn.edu/~abrooks/SomeTests.php?q=" + this.test;
				this.url = URL.encode(this.url); 
				
	}
	
	public void setTestNumber(final int test){
		this.test = test;
		this.url = this.proxy+"http://www.d.umn.edu/~abrooks/SomeTests.php?q=" + this.test;
		this.url = URL.encode(this.url);
	}
	
	public String getURL(){
		return this.url;
	}
	
	public int getCurrentTestNumber(){
		return this.test;
	}

	
	public void update(String rt) {
		//clear local storage for test case
		for (int i = 0; i < localConfig.getLength(); i++) {
			if (!localConfig.key(i).startsWith("m")) {
				localConfig.removeItem(localConfig.key(i));
			}
		}
		String configString = rt;
		JSONArray jA = null;
		try {
			jA = (JSONArray) JSONParser.parseLenient(configString);
		} catch (NullPointerException e1) {
			Window.alert("null" + e1.getMessage());
		} catch (IllegalArgumentException e2) {
			Window.alert("illegal" + e2.getMessage());
		}
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
			localConfig.setItem(
					Integer.toString(module.getCode()),
					module.toString());
		}
		
	}
	
	
	/**
	 * Sends a request to Brooks test feed
	 * @param url
	 */
	private void sendRequest(final String url){
		// Send request to server and catch any errors. 
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url); 
		 
		try { 
		 Request request = builder.sendRequest(null, new RequestCallback() { 
		 
		 public void onError(final Request request, final Throwable exception) { 
		 Window.alert("onError: Couldn't retrieve JSON"); 
		 } 
		 
		 public void onResponseReceived(final Request request, final Response response) { 
		 if (200 == response.getStatusCode()) { 
			 String rt = response.getText(); 
		 	update(rt);
		 } else { 
		 Window.alert("Couldn't retrieve JSON (" + response.getStatusText() 
		 + ")"); 
		 } 
		 } 
		 }); 
		} catch (RequestException e) { 
		 Window.alert("RequestException: Couldn't retrieve JSON"); 
		} 

	}
	
}