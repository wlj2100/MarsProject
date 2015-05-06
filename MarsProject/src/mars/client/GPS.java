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
/*import com.google.gwt.user.client.ui.Image;
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
		Model.removeAll();
		Variables.mListBox().clear();
		Variables.cListBox().clear();
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
		ModuleType type;
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
			coordinate = new Point (xC, yC);
			
			
			//Convert Status
			if (status == "undamaged"){
				modStatus = ModuleStatus.USABLE;
			}
			else if (status == "damaged"){
				modStatus = ModuleStatus.BEYONDREPAIR;
			}
			else{
				modStatus = ModuleStatus.USABLEAFTERREPAIR;
			}
			

			
			//Convert Type
			if (0 < code && code < 41) {
				type = ModuleType.PLAIN;
			} // if
			else if (60 < code && code < 81) {
				type = ModuleType.DORMITORY;
			} // if
			else if (90 < code && code < 101) {
				type = ModuleType.SANITATION;
			} // if
			else if (110 < code && code < 121) {
				type = ModuleType.FOODWATERSTORAGE;
			} // if
			else if (130 < code && code < 135) {
				type = ModuleType.GYMRELAXATION;
			} // if
			else if (140 < code && code < 145) {
				type = ModuleType.CANTEEN;
			} // if
			else if (150 < code && code < 155) {
				type = ModuleType.POWER;
			} // if
			else if (160 < code && code < 165) {
				type = Module.CONTROL;
			} // if
			else if (170 < code && code < 175) {
				type = ModuleType.AIRLOCK;
			} // if
			else if (180 < code && code < 185) {
				type = ModuleType.MEDICAL;
			} // if
			else {
				type = null;
			} //else
			
			Variables.mListBox().addItem("Module #" + id);		
			Module tempModule = new Module(type, id, coordinate, modStatus, numTurns, false);			
			Model.addModule(tempModule);

		} //for

		if (ConfigurationBuilder.minConfigPossible()){
			final DialogBox minConfigAlert = new DialogBox();
            minConfigAlert.setText("Minimum Configuration Available");

            // Create a table to layout the content
            VerticalPanel dialogContents = new VerticalPanel();
            dialogContents.setSpacing(4);
            minConfigAlert.setWidget(dialogContents);

            // Add some text to the top of the dialog
            HTML details = new HTML("Go to Configurations tab to view the minimum configuration available.");
            dialogContents.add(details);
            dialogContents.setCellHorizontalAlignment(
                details, HasHorizontalAlignment.ALIGN_CENTER);

            // Add an image to the dialog
            Image image = new Image("images/yay");
            image.setHeight(Variables.px130);
            image.setWidth(Variables.px200);
            dialogContents.add(image);
            dialogContents.setCellHorizontalAlignment(
                image, HasHorizontalAlignment.ALIGN_CENTER);

            // Add a close button at the bottom of the dialog
            Button closeButton = new Button(
                "Close", new ClickHandler() {
                  public void onClick(ClickEvent event) {
                	  minConfigAlert.hide();
                  }
                });
            dialogContents.add(closeButton);
            if (LocaleInfo.getCurrentLocale().isRTL()) {
              dialogContents.setCellHorizontalAlignment(
                  closeButton, HasHorizontalAlignment.ALIGN_LEFT);

            } // if 
            else {
              dialogContents.setCellHorizontalAlignment(
                  closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
            } // else
            
            minConfigAlert.center();
            minConfigAlert.show();
            Variables.minConfigSound().play();
			Variables.setMinConfigReached(true);
		}
		else{
			Variables.setMinConfigReached(false);
		}
		
	
	} //update

}//TestCases Class*/