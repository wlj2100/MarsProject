package mars.client;


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

	
	private int test = 1;
	private String url;
	private VerticalPanel vp;
	
	// php script
	private String proxy = "http://www.d.umn.edu/~frees033/proxy.php?q=";
	
	/**
	 * Default CTOR
	 */
	public CopyOfGPS(){
				this.url = 
				this.proxy+"http://www.d.umn.edu/~abrooks/SomeTests.php?q=" + this.test;
				this.url = URL.encode(this.url); 
				
	}
	
	public void setTestNumber(final int test1){
		this.test = test1;
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
		 VerticalPanel vp = new VerticalPanel();
		 vp.add(new Label(rt)); //TO VIEW
		 //RootLayoutPanel.get().add(vp);

		 String sAll = rt;
		 JSONArray jA =
		 (JSONArray)JSONParser.parseLenient(sAll);
		 JSONNumber jN;
		 JSONString jS;
		 double d;
		 String s;
		 
		 for (int i = 0; i < jA.size(); i++) {
			 JSONObject jO = (JSONObject)jA.get(i);
			 jN = (JSONNumber) jO.get("code");
			 d = jN.doubleValue();
			 
			 vp.add(new Label(Double.toString(d))); //TO VIEW
			 jS = (JSONString) jO.get("status");
			 
			 s = jS.stringValue();
			 vp.add(new Label(s)); //TO VIEW
			 
			 jN = (JSONNumber) jO.get("turns");
			 d = jN.doubleValue();
			 
			 vp.add(new Label(Double.toString(d))); //TO VIEW
			 jN = (JSONNumber) jO.get("X");
			 d = jN.doubleValue();
			 
			 vp.add(new Label(Double.toString(d))); //TO VIEW
			 jN = (JSONNumber) jO.get("Y");
			 d = jN.doubleValue();
			 
			 vp.add(new Label(Double.toString(d))); //TO VIEW
			 vp.add(new HTML("<hr />"));
		 }
			 RootLayoutPanel.get().add(vp);
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
	
	public VerticalPanel getTestData(){
		return this.vp;
	}
}