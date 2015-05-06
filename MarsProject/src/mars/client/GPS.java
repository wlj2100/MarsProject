package mars.client;

import mars.client.Configuration;
import mars.client.Module;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;



public class GPS {
		
	public static void TestCaseChoice(int choice){		
		String proxy ="http://www.d.umn.edu/~mckeo044/Proxy.php?url=";
		String url = proxy+"http://www.d.umn.edu/~abrooks/SomeTests.php?q=" + Integer.toString(choice);		
		url = URL.encode(url);
	
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert("onError: Couldn't retrieve JSON");
				}
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						String rt = response.getText();
						update(rt); //METHOD CALL TO DO SOMETHING WITH RESPONSE TEXT
					} //if
					else {
						Window.alert("Couldn't retrieve JSON (" + response.getStatusCode()+ ")");
					} //else
				} //onResponseReceived
			}); //send request
		} // RequestBuilder
		catch (RequestException e) {
			Window.alert("RequestException: Couldn't retrieve JSON");
		} //Catch
		
	} // TestCaseChoice
		
	public static void update(final String rt) {		 
		
		JSONArray jArray = (JSONArray)JSONParser.parseLenient(rt); 
		JSONNumber jNumber; 
		JSONString jString; 
		double code;
		int id;
		int xC;
		int yC;
		int numTurns;
		double turns; 
		double x; 
		double y;
		String status;
		Module coordinate;
		Module modStatus;	
		
		//Changes the JSON into datatypes
		for (int i = 0; i < jArray.size(); ++i) { 			 
			//Type and ID
			JSONObject jO = (JSONObject)jArray.get(i); 
			jNumber = (JSONNumber) jO.get("code"); 
			code = jNumber.doubleValue();
			id = (int) code;
			//Status
			jString = (JSONString) jO.get("status"); 
			status = jString.stringValue(); 		 
			//Orientation
			jNumber = (JSONNumber) jO.get("turns"); 
			turns = jNumber.doubleValue();		 
			numTurns = (int) turns;
			//Coordinates
			jNumber = (JSONNumber) jO.get("X"); 
			x = jNumber.doubleValue(); 	
			xC = (int) x;
			jNumber = (JSONNumber) jO.get("Y"); 
			y = jNumber.doubleValue(); 	
			yC = (int) y;

			
	

		} 

	}
}